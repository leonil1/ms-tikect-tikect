package com.codigo.msticket.client;

import com.codigo.msticket.model.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "ms-security")
public interface UsuarioClient {

    @GetMapping("/api/v1/usuarios/{id}")
    ResponseEntity<Usuario> getUsuarioById(@RequestHeader("Authorization") String token,@PathVariable Long id);

    @GetMapping("/api/v1/autenticacion/usuarioautenticado")
    Usuario getUsuarioAutenticado(@RequestHeader("Authorization") String token);


}
