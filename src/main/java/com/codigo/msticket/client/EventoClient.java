package com.codigo.msticket.client;

import com.codigo.msticket.aggregates.response.ResponseBase;
import com.codigo.msticket.model.Evento;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@FeignClient(name = "ms-evento")
public interface EventoClient {
    @GetMapping("/evento/obtener/{id}") // Ajusta la ruta según corresponda
    Optional<Evento> obtenerEventoId(@PathVariable Long id, @RequestHeader("Authorization") String token);

    @PutMapping("/evento/asiento/{id}/estado")
    ResponseBase cambiarEstadoAsiento(@PathVariable("id") Long id, @RequestParam("numeroAsiento") int numeroAsiento,
                                      @RequestParam("estado") boolean estado, @RequestHeader("Authorization") String token);

}

