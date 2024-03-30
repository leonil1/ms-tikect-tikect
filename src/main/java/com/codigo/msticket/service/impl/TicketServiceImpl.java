package com.codigo.msticket.service.impl;

//import com.codigo.msticket.client.EventoCliene;
import com.codigo.msticket.client.EventoClient;
import com.codigo.msticket.client.UsuarioClient;
import com.codigo.msticket.controller.TicketController;
import com.codigo.msticket.domain.AsientoNotFoundException;
import com.codigo.msticket.domain.BuscarAsientoException;
import com.codigo.msticket.domain.EventoNotFoundException;
import com.codigo.msticket.domain.TicketNotFoundException;
import com.codigo.msticket.entity.Ticket;
import com.codigo.msticket.entity.TipoPago;
import com.codigo.msticket.model.*;
import com.codigo.msticket.repository.TicketRepository;
import com.codigo.msticket.repository.TipoPagoRepository;
import com.codigo.msticket.request.TicketResponse;
import com.codigo.msticket.response.ResponseBase;
import com.codigo.msticket.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
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
    //private final UsuarioRepository usuarioRepository;
    private final UsuarioClient usuarioClient;


    private final EventoClient eventoClient;

    public ResponseEntity<ResponseBase> cambiarEstadoAsiento(Long id, int numeroAsiento, boolean estado) {
        return eventoClient.cambiarEstadoAsiento(id, numeroAsiento, estado);
    }
    public ResponseEntity<ResponseBase> buscarAsiento(Long idAsiento) {
        try {
            // Obtener el evento con todos sus sectores y asientos
            ResponseEntity<ResponseBase<Evento>> responseEntity = eventoClient.obtenerEvento(3L);
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


//    public ResponseEntity<ResponseBase> buscarAsiento(Long idAsiento) {
//        try {
//            // Obtener el evento con todos sus sectores y asientos
//            ResponseEntity<ResponseBase<Evento>> responseEntity = eventoClient.obtenerEvento(3L);
//            if (responseEntity.getStatusCode().is2xxSuccessful()) {
//                ResponseBase<Evento> responseBase = responseEntity.getBody();
//                if (responseBase != null && responseBase.getData() != null) {
//                    Evento evento = responseBase.getData();
//                    for (SectorAsiento sector : evento.getSector()) {
//                        // Iterar sobre los asientos del sector actual
//                        for (Asiento asiento : sector.getAsientos()) {
//                            // Verificar si el ID del asiento coincide
//                            if (asiento.getId().equals(idAsiento)) {
//                                // El asiento pertenece al sector y al evento
//                                String nombreEvento = evento.getTitulo();
//                                String nombreSector = sector.getNombre();
//                                int numeroAsiento = asiento.getNumeroAsiento();
//                                String descripcionAsiento = asiento.getDescripcion();
//
//                                // Puedes hacer lo que necesites con los datos
//                                // Por ejemplo, devolverlos como respuesta
//                                return ResponseEntity.ok(ResponseBase.exitoso("Asiento encontrado",
//                                        "Asiento " + numeroAsiento + " pertenece al sector " + nombreSector +
//                                                " del evento " + nombreEvento + ". Descripción: " + descripcionAsiento));
//                            }
//                        }
//                    }
//
//
//                    // Iterar sobre los sectores del evento
////                    for (SectorAsiento sector : evento.getSector()) {
////                        // Iterar sobre los asientos del sector actual
////                        for (Asiento asiento : sector.getAsientos()) {
////                            // Verificar si el ID del asiento coincide
////                            if (asiento.getId().equals(idAsiento)) {
////                                // El asiento pertenece al sector y al evento
////                                String nombreEvento = evento.getTitulo();
////                                String nombreSector = sector.getNombre();
////                                int numeroAsiento = asiento.getNumeroAsiento();
////                                String descripcionAsiento = asiento.getDescripcion();
////
////                                // Puedes hacer lo que necesites con los datos
////                                // Por ejemplo, devolverlos como respuesta
////                                return ResponseEntity.ok(ResponseBase.exitoso("Asiento encontrado",
////                                        "Asiento " + numeroAsiento + " pertenece al sector " + nombreSector +
////                                                " del evento " + nombreEvento + ". Descripción: " + descripcionAsiento));
////                            }
////                        }
////                    }
//
//                    // Si llegamos aquí, el asiento no fue encontrado
//                    return ResponseEntity.ok(ResponseBase.exitoso("Asiento no encontrado", null));
//                }
//            }
//            return ResponseEntity.ok(ResponseBase.exitoso("Evento no encontrado", null));
//        } catch (Exception e) {
//            return ResponseEntity.ok(ResponseBase.error("Ocurrió un error al buscar el asiento", null));
//        }
//    }

    public ResponseEntity<ResponseBase> obtenerEvento(Long id) {
        try {
            logger.info("Consultando evento con ID: {}", id);
            ResponseEntity<ResponseBase<Evento>> responseEntity = eventoClient.obtenerEvento(id);
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

  //  private final EventoFeignClient eventoFeignClient;


//    @Override
//    public Ticket save(Ticket ticket) {
//        return null;
//    }

    public AsientoDTO buscarAsiento(int numeroAsiento, Long idEvento, Long idUsuario) {
        try {
            Usuario usuario=usuarioClient.getUsuarioAutenticado();
            // Obtener el evento con todos sus sectores y asientos
            ResponseEntity<ResponseBase<Evento>> responseEntity = eventoClient.obtenerEvento(idEvento);
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
            // validarCapacidadYCrearAsientos(ticket);

            // Obtener el TipoPago y Usuario asociados al Ticket a partir de sus identificadores
            TipoPago tipoPago = tipoPagoRepository.findById(ticketResponse.getTipoPagoId()).orElse(null);
            //Usuario usuario = usuarioRepository.findById(ticketResponse.getUsuarioId()).orElse(null);
//            ResponseEntity<ResponseBase<Evento>> response = eventoClient.obtenerEvento(ticketResponse.getEventoId());
//            if (response.getStatusCode().is2xxSuccessful()) {
//                Evento evento = response.getBody().getData(); // Suponiendo que getData() devuelve el Evento dentro de ResponseBase
//                // Usa el evento como necesites
//            } else {
//                // Maneja el caso en que la solicitud no fue exitosa
//            }

            ResponseEntity<ResponseBase<Evento>> responseEntity = eventoClient.obtenerEvento(ticketResponse.getEventoId());
            ResponseBase<Evento> responseBase = responseEntity.getBody();
            Evento evento = null;
            Asiento asiento=null;
            //ResponseBase<Evento> responseBase = eventoClient.obtenerEvento(3L);
            if (responseBase.getData() != null) {
                evento = responseBase.getData();
//                AsientoDTO asientoDTO=buscarAsiento(ticketResponse.getAsientoId(),evento.getId());


                String titulo = evento.getTitulo();
                String descripcion = evento.getDescripcion();
                LocalDate fechaEvento = evento.getFechaEvento();
                LocalTime horaEvento = evento.getHoraEvento();
                String duracionEvento = evento.getDuracionEvento();


            }
            com.codigo.msticket.model.Usuario usuarioautenticated= usuarioClient.getUsuarioAutenticado();

            //eventoClient.cambiarEstadoAsiento(evento.getId(),ticketResponse.getAsientoId(),true);
            Ticket ticket=new Ticket();
            //Evento evento=eventoClient.obtenerEvento(3L);
            // Asignar el TipoPago y Usuario al Ticket

            ticket.setIdEvento(evento.getId());
            ticket.setIdNumeroAsiento(ticketResponse.getAsientoId());
            ticket.setTipoPago(tipoPago);
            Usuario usuario=usuarioClient.getUsuarioAutenticado();
            ticket.setIdUsuario(usuario.getIdUsuario());

            eventoClient.cambiarEstadoAsiento(evento.getId(),ticketResponse.getAsientoId(),true);



            //eventoClient.cambiarEstadoAsiento(22L,true);



            // Guardar el Ticket en la base de datos
            ticketRepository.save(ticket);

            //ResponseEntity<ResponseBase> responseEntity = eventoFeignClient.cambiarEstadoAsiento(ticket.getAsientoId(), true);

            return ResponseBase.exitoso("Ticket creado correctamente", Optional.of(ticket.getTicketId()));
        } catch (ConstraintViolationException e) {
            return ResponseBase.error("Error de validación: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return ResponseBase.error("Error al procesar la solicitud", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//@Override
//public ResponseBase save(Ticket ticket) {
//    try {
//       // validarCapacidadYCrearAsientos(ticket);
//
//        // Obtener el TipoPago y Usuario asociados al Ticket a partir de sus identificadores
//        TipoPago tipoPago = tipoPagoRepository.findById(ticket.getTipoPago().getTipoPagoId()).orElse(null);
//        Usuario usuario = usuarioRepository.findById(ticket.getUsuario().getUsuarioId()).orElse(null);
//        ResponseEntity<ResponseBase<Evento>> responseEntity = eventoClient.obtenerEvento(3L);
//        ResponseBase<Evento> responseBase = responseEntity.getBody();
//        Evento evento = null;
//        //ResponseBase<Evento> responseBase = eventoClient.obtenerEvento(3L);
//        if (responseBase.getData() != null) {
//            evento = responseBase.getData();
//        }
//        eventoClient.cambiarEstadoAsiento(evento.getId(),3,false);
//        //Evento evento=eventoClient.obtenerEvento(3L);
//        // Asignar el TipoPago y Usuario al Ticket
//        ticket.setTipoPago(tipoPago);
//        ticket.setUsuario(usuario);
//        //eventoClient.cambiarEstadoAsiento(22L,true);
//
//
//
//        // Guardar el Ticket en la base de datos
//        ticketRepository.save(ticket);
//
//        //ResponseEntity<ResponseBase> responseEntity = eventoFeignClient.cambiarEstadoAsiento(ticket.getAsientoId(), true);
//
//        return ResponseBase.exitoso("Ticket creado correctamente", Optional.of(ticket.getTicketId()));
//    } catch (ConstraintViolationException e) {
//        return ResponseBase.error("Error de validación: " + e.getMessage(), HttpStatus.BAD_REQUEST);
//    } catch (Exception e) {
//        return ResponseBase.error("Error al procesar la solicitud", HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//}

//
//    private void validarCapacidadYCrearAsientos(Ticket ticket) {
//
//    }

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
