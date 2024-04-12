package com.codigo.msticket.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
public class Evento {

    private Long id;

    private String titulo;

    private String descripcion;

    private LocalDate fechaEvento;

    private LocalTime horaEvento;

    private String duracionEvento;

    private String imagen;

    private boolean activo;


    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    @NotNull
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "evento_id")
    private List<SectorAsiento> sector;


}
