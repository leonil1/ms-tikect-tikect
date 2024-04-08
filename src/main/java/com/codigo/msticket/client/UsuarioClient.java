package com.codigo.msticket.client;

import com.codigo.msticket.model.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "ms-security")
public interface UsuarioClient {
    @GetMapping("/api/v1/autenticacion/usuarioautenticado")
    Usuario getUsuarioAutenticado(@RequestHeader("Authorization") String token);
}
