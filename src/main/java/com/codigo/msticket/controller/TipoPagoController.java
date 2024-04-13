package com.codigo.msticket.controller;

import com.codigo.msticket.aggregates.response.ResponseBase;
import com.codigo.msticket.entity.TipoPagoEntity;
import com.codigo.msticket.service.TipoPagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tipopago")
@RequiredArgsConstructor
@RefreshScope
public class TipoPagoController {
    private final TipoPagoService tipoPagoService;

    @PostMapping("/form")
    public ResponseBase create(@RequestBody TipoPagoEntity tipoPagoEntity) {

        return tipoPagoService.save(tipoPagoEntity);
    }
    @GetMapping("/obtener/{id}")
    public ResponseBase obtenerTipoPagoId(@PathVariable Long id){
        ResponseBase responseBase=tipoPagoService.findById(id);
        return responseBase;
    }
    @GetMapping("/todostipopago")
    public ResponseBase obtenerTodoPago() {

        return tipoPagoService.listaTipoPago();
    }
    @PutMapping("/delete/{id}")
    public ResponseBase deleteTipoPagoId(@PathVariable Long id){
        ResponseBase responseBase=tipoPagoService.deleteById(id);
        return responseBase;
    }

}
