package com.codigo.msticket.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class SectorAsiento {
    private Long id;

    private String nombre;

    private BigDecimal precio;

    private int capacidad;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    //@JsonIgnore
    @OneToMany(mappedBy = "sectorAsiento", cascade = CascadeType.ALL)
    private List<Asiento> asientos;

}
