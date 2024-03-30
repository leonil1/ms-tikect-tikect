package com.codigo.msticket.service.impl;

import com.codigo.msticket.entity.TipoPago;
import com.codigo.msticket.repository.TipoPagoRepository;
import com.codigo.msticket.response.ResponseBase;
import com.codigo.msticket.service.TipoPagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TipoPagoServiceImpl implements TipoPagoService {
    private final TipoPagoRepository tipoPagoRepository;

    @Override
    public ResponseBase save(TipoPago tipoPago) {
        tipoPagoRepository.save(tipoPago);

        return ResponseBase.exitoso("Ticket creado correctamente", Optional.of(tipoPago.getTipoPagoId()));
    }

    @Override
    public List<TipoPago> listaEvento() {
        return tipoPagoRepository.findAll();
    }

    @Override
    public TipoPago findById(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<String> actualizar(Long id, Map<String, String> requestMap) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
