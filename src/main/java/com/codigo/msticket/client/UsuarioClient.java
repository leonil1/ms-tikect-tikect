package com.codigo.msticket.client;

import com.codigo.msticket.model.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "ms-security")
public interface UsuarioClient {
    @GetMapping("/api/v1/autenticacion/usuarioautenticado")
    Usuario getUsuarioAutenticado();
}
