package com.codigo.msticket.service.impl;

import com.codigo.msticket.client.EventoClient;
import com.codigo.msticket.client.UsuarioClient;
import com.codigo.msticket.controller.TicketController;
import com.codigo.msticket.exception.AsientoNotFoundException;
import com.codigo.msticket.exception.BuscarAsientoException;
import com.codigo.msticket.exception.EventoNotFoundException;
import com.codigo.msticket.exception.TicketNotFoundException;
import com.codigo.msticket.entity.Ticket;
import com.codigo.msticket.entity.TipoPago;
import com.codigo.msticket.model.*;
import com.codigo.msticket.repository.TicketRepository;
import com.codigo.msticket.repository.TipoPagoRepository;
import com.codigo.msticket.request.TicketResponse;
import com.codigo.msticket.response.ResponseBase;
import com.codigo.msticket.service.TicketService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
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

    public ResponseEntity<ResponseBase> cambiarEstadoAsiento(Long id, int numeroAsiento, boolean estado) {
        String token = request.getHeader("Authorization");
        return eventoClient.cambiarEstadoAsiento(id, numeroAsiento, estado, token);
    }
    public ResponseEntity<ResponseBase> buscarAsiento(Long idAsiento) {
        try {
            String token = request.getHeader("Authorization");
            // Obtener el evento con todos sus sectores y asientos
            ResponseEntity<ResponseBase<Evento>> responseEntity = eventoClient.obtenerEvento(3L, token);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                ResponseBase<Evento> responseBase = responseEntity.getBody();
                if (responseBase != null && responseBase.getData() != null) {
                    Evento evento = responseBase.getData();
                    for (SectorAsiento sector : evento.getSector()) {
                        // Iterar sobre los asientos del sector actual
                        for (Asiento asiento : sector.getAsientos()) {
                            // Verificar si el ID del asiento coincide
                            if (asiento.getId().equals(idAsiento)) {
                                // El asiento pertenece al sector y al evento
                                String nombreEvento = evento.getTitulo();
                                String nombreSector = sector.getNombre();
                                int numeroAsiento = asiento.getNumeroAsiento();
                                String descripcionAsiento = asiento.getDescripcion();

                                // Puedes hacer lo que necesites con los datos
                                // Por ejemplo, devolverlos como respuesta
                                return ResponseEntity.ok(ResponseBase.exitoso("Asiento encontrado",
                                        "Asiento " + numeroAsiento + " pertenece al sector " + nombreSector +
                                                " del evento " + nombreEvento + ". Descripción: " + descripcionAsiento));
                            }
                        }
                    }
                    // Si llegamos aquí, significa que el asiento no se encontró en ningún sector
                    return ResponseEntity.ok(ResponseBase.exitoso("Asiento no encontrado", null));
                }
            }
            return ResponseEntity.ok(ResponseBase.exitoso("Evento no encontrado", null));
        } catch (Exception e) {
            return ResponseEntity.ok(ResponseBase.error("Ocurrió un error al buscar el asiento", null));
        }
    }

    public ResponseEntity<ResponseBase> obtenerEvento(Long id) {
        try {
            logger.info("Consultando evento con ID: {}", id);
            String token = request.getHeader("Authorization");
            ResponseEntity<ResponseBase<Evento>> responseEntity = eventoClient.obtenerEvento(id, token);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                ResponseBase<Evento> responseBase = responseEntity.getBody();
                if (responseBase != null && responseBase.getData() != null) {
                    Evento evento = responseBase.getData();
                    logger.info("Evento encontrado: {}", evento);
                    return ResponseEntity.ok(ResponseBase.exitoso("Evento encontrado", evento));
                }
            }
            return ResponseEntity.ok(ResponseBase.exitoso("Evento no encontrado", null));
        } catch (Exception e) {
            logger.error("Error al consultar evento con ID: {}", id, e);
            return ResponseEntity.ok(ResponseBase.error("Ocurrió un error al consultar el evento", null));
        }
    }

    public AsientoDTO buscarAsiento(int numeroAsiento, Long idEvento, Long idUsuario) {
        try {
            String token = request.getHeader("Authorization");
            Usuario usuario=usuarioClient.getUsuarioAutenticado(token);
            // Obtener el evento con todos sus sectores y asientos
            ResponseEntity<ResponseBase<Evento>> responseEntity = eventoClient.obtenerEvento(idEvento, token);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                ResponseBase<Evento> responseBase = responseEntity.getBody();
                if (responseBase != null && responseBase.getData() != null) {
                    Evento evento = responseBase.getData();
                    for (SectorAsiento sector : evento.getSector()) {
                        // Iterar sobre los asientos del sector actual
                        for (Asiento asiento : sector.getAsientos()) {
                            // Verificar si el número del asiento coincide
                            if (asiento.getNumeroAsiento() == numeroAsiento) {
                                // El asiento pertenece al sector y al evento
                                AsientoDTO asientoDTO = new AsientoDTO();
                                asientoDTO.setIdEvento(evento.getId());
                                asientoDTO.setTitulo(evento.getTitulo());
                                asientoDTO.setFechaEvento(evento.getFechaEvento());
                                asientoDTO.setHoraEvento(evento.getHoraEvento());
                                asientoDTO.setDuracionEvento(evento.getDuracionEvento());
                                asientoDTO.setNombreSector(sector.getNombre());
                                asientoDTO.setNumeroAsiento(asiento.getNumeroAsiento());
                                asientoDTO.setDescripcionAsiento(asiento.getDescripcion());
                                asientoDTO.setTelefono(usuario.getTelefono());
                                asientoDTO.setPrecio(sector.getPrecio());
                                return asientoDTO;
                            }
                        }
                    }
                    // Si llegamos aquí, significa que el asiento no se encontró en ningún sector
                    throw new AsientoNotFoundException("Asiento no encontrado");
                }
            }
            throw new EventoNotFoundException("Evento no encontrado");
        } catch (Exception e) {
            throw new BuscarAsientoException("Ocurrió un error al buscar el asiento", e);
        }
    }

    @Override
    public ResponseBase save(TicketResponse ticketResponse) {
        try {

            TipoPago tipoPago = tipoPagoRepository.findById(ticketResponse.getTipoPagoId()).orElse(null);
            String token = request.getHeader("Authorization");
            ResponseEntity<ResponseBase<Evento>> responseEntity = eventoClient.obtenerEvento(ticketResponse.getEventoId(), token);
            ResponseBase<Evento> responseBase = responseEntity.getBody();
            Evento evento = null;
            Asiento asiento=null;
            if (responseBase.getData() != null) {
                evento = responseBase.getData();

            }

            Ticket ticket=new Ticket();

            ticket.setIdEvento(evento.getId());
            ticket.setIdNumeroAsiento(ticketResponse.getAsientoId());
            ticket.setTipoPago(tipoPago);
            Usuario usuario=usuarioClient.getUsuarioAutenticado( token);
            ticket.setIdUsuario(usuario.getIdUsuario());

            eventoClient.cambiarEstadoAsiento(evento.getId(),ticketResponse.getAsientoId(),true, token);

            ticketRepository.save(ticket);

            return ResponseBase.exitoso("Ticket creado correctamente", Optional.of(ticket.getTicketId()));
        } catch (ConstraintViolationException e) {
            return ResponseBase.error("Error de validación: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseBase.error("Error al procesar la solicitud", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<Ticket> listaEvento() {
        return null;
    }

    @Override
    public Ticket findById(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found with id " + id));
    }

    @Override
    public ResponseEntity<String> actualizar(Long id, Map<String, String> requestMap) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
