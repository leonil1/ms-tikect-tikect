package com.codigo.msticket.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Setter
@Getter
@Table(name = "tickes")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Long ticketId;

    private Integer cantidad;

    @Column(name = "fecha_compra", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp fechaCompra;

    private Long idUsuario;

    private Long idEvento;

    private int idNumeroAsiento;

    private boolean activo = true;

    @Column(name = "fecha_creacion", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private Timestamp fechaActualizacion;

    @Column(name = "usuario_actualizacion")
    private String usuarioActualizacion;

    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipopago_id")
    private TipoPago tipoPago;

    @PrePersist
    public void setfechaCreacion(){

        this.fechaCreacion=new Timestamp(new Date().getTime());
        this.fechaCompra=new Timestamp(new Date().getTime());
    }

}
