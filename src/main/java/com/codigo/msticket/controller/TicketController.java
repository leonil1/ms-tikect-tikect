package com.codigo.msticket.controller;

import com.codigo.msticket.aggregates.response.ResponseBase;
import com.codigo.msticket.entity.TicketEntity;
import com.codigo.msticket.request.TicketResponse;
import com.codigo.msticket.service.TicketService;
import com.codigo.msticket.service.TipoPagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ticket")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;
    private final TipoPagoService tipoPagoService;

    @PostMapping("/form")
    public ResponseBase create(@RequestBody TicketResponse ticket) {
        return ticketService.save(ticket);
    }
    @PostMapping("/form/{id}")
    public ResponseBase create(@RequestBody TicketEntity ticket, @PathVariable Long id) {
        return ticketService.update(ticket, id);
    }

    @GetMapping("/obtener/{id}")
    public ResponseBase obtenerTicketId(@PathVariable Long id){
        ResponseBase responseBase=ticketService.findById(id);
        return responseBase;
    }
    @GetMapping("/delete/{id}")
    public ResponseBase deleteTicketId(@PathVariable Long id){
        ResponseBase responseBase=ticketService.findById(id);
        return responseBase;
    }

    @GetMapping("/entrada/{id}")
    public ResponseBase buscarEntrada(@PathVariable Long id){
        ResponseBase responseBase=ticketService.generarentrada(id);
       return responseBase;
    }

}
