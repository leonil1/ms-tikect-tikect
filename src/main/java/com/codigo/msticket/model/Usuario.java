package com.codigo.msticket.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class Usuario {

    private Long idUsuario;

    private String username;

    private String password;

    private String email;

    private String telefono;

    private boolean enabled;

    private boolean accountnonexpire;

    private boolean accountnonlocked;

    private boolean credentialsnonexpired;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_rol",
            joinColumns = @JoinColumn(name = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "id_rol"))
    private Set<Rol> roles = new HashSet<>();
}
