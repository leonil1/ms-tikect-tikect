package com.codigo.msticket.service.impl;

import com.codigo.msticket.aggregates.response.ResponseBase;
import com.codigo.msticket.client.EventoClient;
import com.codigo.msticket.client.UsuarioClient;
import com.codigo.msticket.controller.TicketController;
import com.codigo.msticket.entity.TicketEntity;
import com.codigo.msticket.entity.TipoPagoEntity;
import com.codigo.msticket.model.*;
import com.codigo.msticket.repository.TicketRepository;
import com.codigo.msticket.repository.TipoPagoRepository;
import com.codigo.msticket.request.TicketResponse;
import com.codigo.msticket.response.EntradaResponse;
import com.codigo.msticket.service.TicketService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private static final Logger logger = LoggerFactory.getLogger(TicketController.class);
    private final TicketRepository ticketRepository;
    private final TipoPagoRepository tipoPagoRepository;
    private final UsuarioClient usuarioClient;
    @Autowired
    private HttpServletRequest request;
    private final EventoClient eventoClient;


    @Override
    public ResponseBase save(TicketResponse ticketResponse) {
        try {

            TipoPagoEntity tipoPago = tipoPagoRepository.findById(ticketResponse.getTipoPagoId()).orElse(null);
            String token = request.getHeader("Authorization");

            Optional<Evento> responseBase= eventoClient.obtenerEventoId(ticketResponse.getEventoId(),token);
            Evento evento=null;
            if(responseBase.isPresent()){
                evento = responseBase.get();

            }

            Asiento asiento=null;
            TicketEntity ticket=new TicketEntity();

            ticket.setIdEvento(evento.getId());
            ticket.setNumeroAsiento(ticketResponse.getNumeroAsiento());
            ticket.setTipoPago(tipoPago);
            ticket.setObservaciones(ticketResponse.getObservaciones());
            Usuario usuario=usuarioClient.getUsuarioAutenticado(token);
            ticket.setIdUsuario(usuario.getIdUsuario());
            ticket.setUserCreate(usuario.getUsername());

            for(SectorAsiento sectorAsiento:evento.getSector()){
                ticket.setTotal(sectorAsiento.getPrecio().multiply(BigDecimal.valueOf(ticket.getCantidad())));
                for (Asiento asientobusqueda:sectorAsiento.getAsientos()){

                    if( asientobusqueda.getNumeroAsiento()==ticketResponse.getNumeroAsiento()){
                        if(!asientobusqueda.isEstado()){
                            return ResponseBase.errorNotFound("Asiento no disponible");

                        }

                    }
                }
            }


            eventoClient.cambiarEstadoAsiento(evento.getId(),ticketResponse.getNumeroAsiento(),false, token);

            ticketRepository.save(ticket);

            return ResponseBase.exitoso("Ticket creado correctamente", Optional.of(ticket.getTicketId()));
        } catch (ConstraintViolationException e) {
            return ResponseBase.errorViolationException("Error de validación: " + e.getMessage());
        } catch (Exception e) {
            return ResponseBase.errorInternalSErverError("Error al procesar la solicitud"+e.getMessage());
        }
    }


    @Override
    public ResponseBase update(TicketEntity ticketEntity, Long id) {
        try {
            Optional<TicketEntity> existingTicketOpt = ticketRepository.findById(id);
            if (existingTicketOpt.isPresent()) {
                TicketEntity existingTicket = existingTicketOpt.get();

                TipoPagoEntity tipoPago = tipoPagoRepository.findById(ticketEntity.getTipoPago().getTipoPagoId()).orElse(null);
                String token = request.getHeader("Authorization");

                Optional<Evento> responseBase = eventoClient.obtenerEventoId(ticketEntity.getTicketId(), token);
                Evento evento = null;
                if (responseBase.isPresent()) {
                    evento = responseBase.get();
                }

                existingTicket.setIdEvento(evento.getId());
                existingTicket.setNumeroAsiento(ticketEntity.getNumeroAsiento());
                existingTicket.setTipoPago(tipoPago);
                Usuario usuario = usuarioClient.getUsuarioAutenticado(token);
                existingTicket.setIdUsuario(usuario.getIdUsuario());
                existingTicket.setUserModif(usuario.getUsername());
                existingTicket.setDateModif(new Date());

                eventoClient.cambiarEstadoAsiento(evento.getId(), ticketEntity.getNumeroAsiento(), false, token);

                ticketRepository.save(existingTicket);

                return ResponseBase.exitoso("Ticket actualizado correctamente", Optional.of(existingTicket.getTicketId()));
            } else {
                return ResponseBase.errorNotFound("Ticket no encontrado");
            }
        } catch (ConstraintViolationException e) {
            return ResponseBase.errorViolationException("Error de validación: " + e.getMessage());
        } catch (Exception e) {
            return ResponseBase.errorInternalSErverError("Error al procesar la solicitud" + e.getMessage());
        }
    }


    @Override
    public ResponseBase findById(Long id) {
        try {
            Optional<TicketEntity> ticketEntity = ticketRepository.findById(id);
            if (ticketEntity.isPresent()) {
                return ResponseBase.exitoso("Ticket encontrado", ticketEntity.get());
            } else {
                return ResponseBase.errorNotFound("Ticket no encontrado");
            }
        } catch (Exception e) {
            return ResponseBase.errorInternalSErverError("Ocurrió un error al buscar el ticket: " + e.getMessage());
        }
    }
    @Override
    public ResponseBase deleteById(Long id) {
        try {
            Optional<TicketEntity> ticketEntity = ticketRepository.findById(id);
            if (ticketEntity.isPresent()) {
                ticketRepository.deleteById(id);
                return ResponseBase.exitoso("Ticket Eliminado con exito", Optional.of(null));
            } else {
                return ResponseBase.errorNotFound("Ticket no encontrado");
            }
        } catch (Exception e) {
            return ResponseBase.errorInternalSErverError("Ocurrió un error al buscar el ticket: " + e.getMessage());
        }
    }




    @Override
    public ResponseBase generarentrada(Long id) {
        EntradaResponse entradaResponse = new EntradaResponse();
        try {
            Optional<TicketEntity> ticketEntity = ticketRepository.findById(id);
            if (ticketEntity.isPresent()) {
                TicketEntity ticketEntityEncontrado=ticketEntity.get();
                String token = request.getHeader("Authorization");
                Optional<Evento> responseBase = eventoClient.obtenerEventoId(ticketEntity.get().getIdEvento(), token);



                if (responseBase.isPresent() && responseBase.isPresent()) {
                    Evento evento = responseBase.get();
                   ResponseEntity<Usuario> usuario=usuarioClient.getUsuarioById(token,ticketEntity.get().getIdUsuario());

                    entradaResponse.setTitulo(evento.getTitulo());
                    entradaResponse.setDescripcion(evento.getDescripcion());
                    entradaResponse.setTelefono(usuario.getBody().getTelefono());
                    entradaResponse.setEmail(usuario.getBody().getEmail());
                    entradaResponse.setTotal(ticketEntityEncontrado.getTotal());


                    for (SectorAsiento sectorAsiento : evento.getSector()) {


                        for (Asiento asiento : sectorAsiento.getAsientos()) {



                            if(asiento.getNumeroAsiento()==ticketEntityEncontrado.getNumeroAsiento()){
                                entradaResponse.setNombreSector(sectorAsiento.getNombreSector());

                                entradaResponse.setNumeroAsiento(ticketEntityEncontrado.getNumeroAsiento());

                            }


                        }
                    }

                    return ResponseBase.exitoso("Entrada encontrada", entradaResponse);
                } else {
                    return ResponseBase.errorNotFound("Evento no encontrado");
                }
            } else {
                return ResponseBase.errorNotFound("Ticket no encontrado");
            }
        } catch (Exception e) {
            return ResponseBase.errorInternalSErverError("Ocurrió un error: " + e.getMessage());
        }
    }


}
