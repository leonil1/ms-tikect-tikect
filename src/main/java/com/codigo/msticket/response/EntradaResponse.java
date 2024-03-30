package com.codigo.msticket.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntradaResponse {
    private Long idAsiento;
    private String nombreSector;
    private Long idEvento;
    private String nombreEvento;
    private String descripcionAsiento;

}
