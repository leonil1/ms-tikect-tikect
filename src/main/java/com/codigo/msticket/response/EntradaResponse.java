package com.codigo.msticket.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class EntradaResponse {
    private String titulo;
    private String descripcion;
    private int numeroAsiento;
    private String nombreSector;
    private String  telefono;
    private String email;
    private BigDecimal total;


}
