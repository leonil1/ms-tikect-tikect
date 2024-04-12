package com.codigo.msticket.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketResponse {
    private Integer cantidad;
    private Long tipoPagoId;
    private Long eventoId;
    private int numeroAsiento;
    private String observaciones;

}
