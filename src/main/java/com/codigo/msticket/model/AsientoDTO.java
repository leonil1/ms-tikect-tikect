package com.codigo.msticket.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AsientoDTO {
    private Long idEvento;
    private String titulo;
    private LocalDate fechaEvento;
    private LocalTime horaEvento;
    private String duracionEvento;
    private String nombreSector;
    private int numeroAsiento;
    private String descripcionAsiento;
    private String telefono;
    private BigDecimal precio;

}
