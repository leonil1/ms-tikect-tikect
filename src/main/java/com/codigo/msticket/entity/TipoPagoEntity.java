package com.codigo.msticket.entity;

import com.codigo.msticket.entity.common.Audit;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "tipo_pagos")
public class TipoPagoEntity extends Audit implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tipo_pago_id")
    private Long tipoPagoId;

    @NotEmpty(message = "El metodo no puede ser vacío")
    @Size(min = 2, max = 25, message = "El tamaño del metodo debe estar entre 2 y 25 caracteres")
    @Column( nullable = false, length = 25)
    private String metodo;

    public TipoPagoEntity(String userCreate, Date dateCreate, String userModif, Date dateModif, String userDelet, Date dateDelet) {
        super(userCreate, dateCreate, userModif, dateModif, userDelet, dateDelet);
    }
    public TipoPagoEntity() {

    }

    @PrePersist
    public void setfechaCreacion(){

        setDateCreate(new Date());
    }
}
