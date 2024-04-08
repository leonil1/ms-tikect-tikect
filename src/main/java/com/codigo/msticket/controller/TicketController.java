package com.codigo.msticket.controller;

import com.codigo.msticket.client.EventoClient;
import com.codigo.msticket.client.UsuarioClient;
import com.codigo.msticket.entity.Ticket;
import com.codigo.msticket.entity.TipoPago;
import com.codigo.msticket.model.AsientoDTO;
import com.codigo.msticket.request.TicketResponse;
import com.codigo.msticket.response.ResponseBase;
import com.codigo.msticket.service.TicketService;
import com.codigo.msticket.service.TipoPagoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final UsuarioClient usuarioClient;

    @Autowired
    private HttpServletRequest request;

    @GetMapping("/entradas/{idTicket}")
    public AsientoDTO buscarEntrada(@PathVariable Long idTicket){
         Ticket ticket=ticketService.findById(idTicket);
        AsientoDTO asientoDTO=ticketService.buscarAsiento(ticket.getIdNumeroAsiento(),ticket.getIdEvento(),ticket.getIdUsuario());

        return asientoDTO;
    }

    //@GetMapping("/usuarioutenticado")
    //public ResponseEntity<com.codigo.msticket.model.Usuario> obtenerUsuarioAutenticado() {
        //com.codigo.msticket.model.Usuario usuario = usuarioClient.getUsuarioAutenticado( token );
        //return ResponseEntity.ok(usuario);
    //}

    @PostMapping("/form")
    public ResponseEntity<ResponseBase> create(@RequestBody TicketResponse ticket) {
        ResponseBase response = ticketService.save(ticket);
        return ResponseEntity.status(response.getCode()).body(response);

    }

    @PutMapping("/evento/asiento/{id}/estado")
    public ResponseEntity<ResponseBase> cambiarEstadoAsiento(@PathVariable("id") Long id,
                                                             @RequestParam("numeroAsiento") int numeroAsiento, @RequestParam("estado") boolean estado) {
        return ticketService.cambiarEstadoAsiento(id, numeroAsiento, estado);
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<ResponseBase> obtenerEvento(@PathVariable Long id) {
        return ticketService.obtenerEvento(id);
    }

    @GetMapping("/hola")
    public ResponseEntity<String> saludo(){
        String token = request.getHeader("Authorization");
        return ResponseEntity.ok("Hola");
    }

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

    @PostMapping("/tipo/form")
    public ResponseEntity<ResponseBase> createTipoPago(@RequestBody TipoPago tipoPago) {
        ResponseBase response = tipoPagoService.save(tipoPago);
        return ResponseEntity.status(response.getCode()).body(response);

    }

}
