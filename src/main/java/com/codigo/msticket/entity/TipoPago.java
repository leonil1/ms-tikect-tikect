package com.codigo.msticket.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "tipo_pagos")
public class TipoPago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tipo_pago_id")
    private Long tipoPagoId;

    private String metodo;

    @Column(name = "fecha_creacion", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private Timestamp fechaActualizacion;

    @Column(name = "usuario_actualizacion")
    private String usuarioActualizacion;

    @PrePersist
    public void setfechaCreacion(){

        this.fechaCreacion=new Timestamp(new Date().getTime());
    }
}
