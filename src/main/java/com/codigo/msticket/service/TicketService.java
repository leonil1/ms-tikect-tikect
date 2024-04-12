package com.codigo.msticket.service;

import com.codigo.msticket.aggregates.response.ResponseBase;
import com.codigo.msticket.entity.TicketEntity;
import com.codigo.msticket.request.TicketResponse;

public interface TicketService {

    ResponseBase generarentrada(Long id);

    ResponseBase save(TicketResponse ticket);
    ResponseBase update(TicketEntity ticketResponse,Long id);


    ResponseBase findById(Long id);

    ResponseBase deleteById(Long id);
}
