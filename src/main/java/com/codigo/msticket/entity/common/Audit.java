package com.codigo.msticket.entity.common;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@MappedSuperclass
@Getter
@Setter
public class Audit {
    @Column(name = "user_create",length = 45,nullable = true)
    private String userCreate;

    @Column(name = "date_create",nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreate;

    @Column(name = "user_modif",length = 45,nullable = true)
    private String userModif;
    @Column(name = "date_modif",nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateModif;
    @Column(name = "user_delet",length = 45,nullable = true)
    private String userDelet;
    @Column(name = "date_delet",nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDelet;


    public Audit(){

    }

    public Audit(String userCreate, Date dateCreate, String userModif, Date dateModif, String userDelet, Date dateDelet) {
        this.userCreate = userCreate;
        this.dateCreate = dateCreate;
        this.userModif = userModif;
        this.dateModif = dateModif;
        this.userDelet = userDelet;
        this.dateDelet = dateDelet;
    }

}
