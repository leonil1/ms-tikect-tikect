package com.codigo.msticket.entity;

import com.codigo.msticket.aggregates.constantes.Constants;
import com.codigo.msticket.entity.common.Audit;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Setter
@Getter
@Table(name = "tickes")
public class TicketEntity extends Audit implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id")
    private Long ticketId;

    @Min(value = 0, message = "La cantidad debe ser mayor  a cero")
    private int cantidad;

    @Min(value = 0, message = "El id del evento no puede ser cero")
    private Long idEvento;

    @Min(value = 0, message = "El numero de asiento debe ser mayor  a cero")
    private int numeroAsiento;

    private boolean activo = true;

    @NotNull(message = "Total no puede tener valor null")
    @Min(value =0, message = "El valor de total deberi ser mayor que cero")
    private BigDecimal total;

    private String observaciones;

    @Min(value = 0, message = "El id del usaurio  debe ser mayor  a cero")
    private Long idUsuario;

    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipopago_id")
    private TipoPagoEntity tipoPago;

    public TicketEntity(String userCreate, Date dateCreate, String userModif, Date dateModif, String userDelet, Date dateDelet) {
        super(userCreate, dateCreate, userModif, dateModif, userDelet, dateDelet);
    }
    public TicketEntity() {

    }

    @PrePersist
    public void setfechaCreacion(){
    this.cantidad=1;
        setDateCreate(new Date());

    }

}
