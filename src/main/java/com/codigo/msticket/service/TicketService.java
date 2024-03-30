package com.codigo.msticket.service;

import com.codigo.msticket.entity.Ticket;
import com.codigo.msticket.model.AsientoDTO;
import com.codigo.msticket.request.TicketResponse;
import com.codigo.msticket.response.ResponseBase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

public interface TicketService {

    ResponseBase save(TicketResponse ticket);
    //Evento findEventoByTituloWithSectorAndAsientos(@Param("titulo") String titulo);

    AsientoDTO buscarAsiento(int numeroAsiento,  Long idEvento, Long idUsuario);
    ResponseEntity<ResponseBase> buscarAsiento(Long idAsiento);

    ResponseEntity<ResponseBase> obtenerEvento(Long id);
    ResponseEntity<ResponseBase> cambiarEstadoAsiento(Long id, int numeroAsiento, boolean estado);
    List<Ticket> listaEvento();

    Ticket findById(Long id);

    ResponseEntity<String> actualizar(Long id, Map<String, String> requestMap);

    void deleteById(Long id);
}
