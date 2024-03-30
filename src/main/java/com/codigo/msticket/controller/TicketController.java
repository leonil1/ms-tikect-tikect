package com.codigo.msticket.controller;

import com.codigo.msticket.client.EventoClient;
import com.codigo.msticket.client.UsuarioClient;
import com.codigo.msticket.entity.Ticket;
import com.codigo.msticket.entity.TipoPago;
import com.codigo.msticket.model.AsientoDTO;
import com.codigo.msticket.response.EntradaResponse;
import com.codigo.msticket.request.TicketResponse;
import com.codigo.msticket.response.ResponseBase;
import com.codigo.msticket.service.TicketService;
import com.codigo.msticket.service.TipoPagoService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/ticket")
@RequiredArgsConstructor
public class TicketController {
    private static final Logger logger = LoggerFactory.getLogger(TicketController.class);


    private final TicketService ticketService;
    private final TipoPagoService tipoPagoService;
    //private final UsuarioService usuarioService;
    private final UsuarioClient usuarioClient;

    private final EventoClient eventoClient;


    @GetMapping("/entradas/{idTicket}")
    public AsientoDTO buscarEntrada(@PathVariable Long idTicket){
         Ticket ticket=ticketService.findById(idTicket);
        AsientoDTO asientoDTO=ticketService.buscarAsiento(ticket.getIdNumeroAsiento(),ticket.getIdEvento(),ticket.getIdUsuario());

        return asientoDTO;

        //return ticketService.buscarAsiento(numeroAsiento,idEvento);
    }




    @GetMapping("/usuarioutenticado")
    public ResponseEntity<com.codigo.msticket.model.Usuario> obtenerUsuarioAutenticado() {
        com.codigo.msticket.model.Usuario usuario = usuarioClient.getUsuarioAutenticado();
        return ResponseEntity.ok(usuario);
    }

    @PostMapping("/form")
    public ResponseEntity<ResponseBase> create(@RequestBody TicketResponse ticket) {
        ResponseBase response = ticketService.save(ticket);
        return ResponseEntity.status(response.getCode()).body(response);

    }



    @PutMapping("/evento/asiento/{id}/estado")
    public ResponseEntity<ResponseBase> cambiarEstadoAsiento(@PathVariable("id") Long id, @RequestParam("numeroAsiento") int numeroAsiento, @RequestParam("estado") boolean estado) {
        return ticketService.cambiarEstadoAsiento(id, numeroAsiento, estado);
    }
    @GetMapping("/obtener/{id}")
    public ResponseEntity<ResponseBase> obtenerEvento(@PathVariable Long id) {
        return ticketService.obtenerEvento(id);
    }

//    @GetMapping("/buscarA/{numeroAsiento}/{idEvento}")
//    public AsientoDTO buscarAsientoEventoAsiento(@PathVariable int numeroAsiento, @PathVariable Long idEvento){
//        return ticketService.buscarAsiento(numeroAsiento,idEvento);
//    }

//    @GetMapping("/buscarA/{numeroAsiento}/{idEvento}")
//    public Evento buscarAsiento(@PathVariable int numeroAsiento, @PathVariable Long idEvento) {
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
//                            // Verificar si el número del asiento coincide
//                            if (asiento.getNumeroAsiento() == numeroAsiento) {
//                                // El asiento pertenece al sector y al evento
//                                String nombreEvento = evento.getTitulo();
//                                String nombreSector = sector.getNombre();
//                                String descripcionAsiento = asiento.getDescripcion();
//
//                                // Aquí se persisten los cambios en las entidades
//                                //entityManager.persist(evento);
//                                //entityManager.persist(sector);
//                                //7entityManager.persist(asiento);
//
//                                // Puedes hacer lo que necesites con los datos
//                                // Por ejemplo, devolverlos como respuesta
//                                return evento;
//                            }
//                        }
//                    }
//                    // Si llegamos aquí, significa que el asiento no se encontró en ningún sector
//                    throw new AsientoNotFoundException("Asiento no encontrado");
//                }
//            }
//            throw new EventoNotFoundException("Evento no encontrado");
//        } catch (Exception e) {
//            throw new BuscarAsientoException("Ocurrió un error al buscar el asiento", e);
//        }
//    }
//

//    public ResponseEntity<ResponseBase> buscarAsiento(@PathVariable int numeroAsiento) {
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
//                            // Verificar si el número del asiento coincide
//                            if (asiento.getNumeroAsiento() == numeroAsiento) {
//                                // El asiento pertenece al sector y al evento
//                                String nombreEvento = evento.getTitulo();
//                                String nombreSector = sector.getNombre();
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
//                    // Si llegamos aquí, significa que el asiento no se encontró en ningún sector
//                    return ResponseEntity.ok(ResponseBase.exitoso("Asiento no encontrado", null));
//                }
//            }
//            return ResponseEntity.ok(ResponseBase.exitoso("Evento no encontrado", null));
//        } catch (Exception e) {
//            return ResponseEntity.ok(ResponseBase.error("Ocurrió un error al buscar el asiento", null));
//        }
//    }

//    @GetMapping("/buscarA/{id}")
//    public ResponseEntity<ResponseBase> buscarAsiento(@PathVariable Long idAsiento){
//        return ticketService.buscarAsiento(idAsiento);
//    }

//    @GetMapping("/obtener/{id}")
//    public ResponseEntity<ResponseBase> obtenerEvento(@PathVariable Long id) {
//        try {
//            logger.info("Consultando evento con ID: {}", id);
//            Evento evento = eventoCliene.obtenerEvento(id);
//            logger.info("Evento encontrado: {}", evento);
//
//            return ResponseEntity.ok(ResponseBase.exitoso("Evento encontrado", evento));
//        } catch (ResponseStatusException e) {
//            return ResponseEntity.ok(ResponseBase.exitoso("ocurrio error", null));
//
//        }
//    }
//@GetMapping("/obtener/{id}")
//public ResponseEntity<ResponseBase> obtenerEvento(@PathVariable Long id) {
//    try {
//        logger.info("Consultando evento con ID: {}", id);
//        ResponseEntity<ResponseBase<Evento>> responseEntity = eventoClient.obtenerEvento(id);
//        if(responseEntity.getStatusCode().is2xxSuccessful()) {
//            ResponseBase<Evento> responseBase = responseEntity.getBody();
//            if (responseBase != null && responseBase.getData() != null) {
//                Evento evento = responseBase.getData();
//                logger.info("Evento encontrado: {}", evento);
//                return ResponseEntity.ok(ResponseBase.exitoso("Evento encontrado", evento));
//            }
//        }
//        return ResponseEntity.ok(ResponseBase.exitoso("Evento no encontrado", null));
//    } catch (Exception e) {
//        return ResponseEntity.ok(ResponseBase.exitoso("ocurrio error", null));
//    }
//}




    @GetMapping("/todospago")
    public ResponseEntity<ResponseBase> obtenerEvento() {
        try {
            List<TipoPago> tipoPago = tipoPagoService.listaEvento();
            return ResponseEntity.ok(ResponseBase.exitoso("Tipos de pago encontrados", tipoPago));
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ResponseBase.error("Tipos de pago no encontrados", HttpStatus.NOT_FOUND));
        }
    }



    //REGGISTRO DE tIPOpAGO
    @PostMapping("/tipo/form")
    public ResponseEntity<ResponseBase> createTipoPago(@RequestBody TipoPago tipoPago) {
        ResponseBase response = tipoPagoService.save(tipoPago);
        return ResponseEntity.status(response.getCode()).body(response);

    }

//    {
//        "metodo":"EFECTIVO"
//
//    }

    //REGGISTRO DE USUARIO
//    @PostMapping("/usuario/form")
//    public ResponseEntity<ResponseBase> createUsuario(@RequestBody Usuario usuario) {
//        ResponseBase response = usuarioService.save(usuario);
//        return ResponseEntity.status(response.getCode()).body(response);
//
//    }
//    {
//        "nombre":"juan pepoe",
//            "email":"juan@gmail.com",
//            "contrasena":"343532"
//
//    }




//    {
//        "cantidad":20,
//            "tipoPago":{
//        "tipoPagoId":1
//    },
//        "usuario":{
//        "usuarioId":1
//    }
//    }
//{
//    "cantidad":20,
//        "tipoPago":{
//    "tipoPagoId":2
//},
//    "usuario":{
//    "usuarioId":2
//}
//}



}
