package com.codigo.msticket.service;

import com.codigo.msticket.entity.Ticket;
import com.codigo.msticket.entity.TipoPago;
import com.codigo.msticket.response.ResponseBase;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface TipoPagoService {
    ResponseBase save(TipoPago tipoPago);
    //Evento findEventoByTituloWithSectorAndAsientos(@Param("titulo") String titulo);


    List<TipoPago> listaEvento();

    TipoPago findById(Long id);

    ResponseEntity<String> actualizar(Long id, Map<String, String> requestMap);

    void deleteById(Long id);
}
