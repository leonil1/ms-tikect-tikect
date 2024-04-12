package com.codigo.msticket.service;

import com.codigo.msticket.aggregates.response.ResponseBase;
import com.codigo.msticket.entity.TipoPagoEntity;

import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface TipoPagoService {
    ResponseBase save(TipoPagoEntity tipoPago);

    ResponseBase listaTipoPago();

    ResponseBase findById(Long id);

    ResponseBase actualizar(Long id, TipoPagoEntity tipoPagoEntity);

    ResponseBase deleteById(Long id);
}
