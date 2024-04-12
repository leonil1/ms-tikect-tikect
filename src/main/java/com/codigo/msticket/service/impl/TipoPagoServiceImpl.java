package com.codigo.msticket.service.impl;

import com.codigo.msticket.aggregates.response.ResponseBase;
import com.codigo.msticket.client.UsuarioClient;
import com.codigo.msticket.entity.TipoPagoEntity;
import com.codigo.msticket.model.Usuario;
import com.codigo.msticket.repository.TipoPagoRepository;
import com.codigo.msticket.service.TipoPagoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;



import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TipoPagoServiceImpl implements TipoPagoService {
    private final TipoPagoRepository tipoPagoRepository;
    private final UsuarioClient usuarioClient;
    @Autowired
    private HttpServletRequest request;

    @Override
    public ResponseBase save(TipoPagoEntity tipoPago) {
        String token = request.getHeader("Authorization");
        try {
            Usuario usuario = usuarioClient.getUsuarioAutenticado(token);
            tipoPago.setUserCreate(usuario.getUsername());
            tipoPago.setDateCreate(new Date());
            TipoPagoEntity tipoPagoEntity = tipoPagoRepository.save(tipoPago);

            return ResponseBase.exitoso("Tipo de pago creado correctamente", Optional.of(tipoPagoEntity));
        } catch (ConstraintViolationException e) {
            return ResponseBase.errorViolationException("Error de validación: " + e.getMessage());
        } catch (Exception e) {
            return ResponseBase.errorInternalSErverError("Error al procesar la solicitud" + e.getMessage());
        }

    }

    @Override
    public ResponseBase listaTipoPago() {

        try {
            List<TipoPagoEntity> listTipo = tipoPagoRepository.findAll();

            return ResponseBase.exitoso("Recurso encontrado", Optional.of(listTipo));
        } catch (Exception e) {
            return ResponseBase.errorInternalSErverError("Error al procesar la solicitud" + e.getMessage());
        }
    }

    @Override
    public ResponseBase findById(Long id) {

        try {


            Optional<TipoPagoEntity> tipoPagoEntity = tipoPagoRepository.findById(id);
            if (tipoPagoEntity.isPresent()) {
                return ResponseBase.exitoso("Ticket encontrado", tipoPagoEntity.get());
            } else {
                return ResponseBase.errorNotFound("Recurso no encontrado");
            }
        } catch (Exception e) {
            return ResponseBase.errorInternalSErverError("Ocurrió un error al buscar el ticket: " + e.getMessage());
        }
    }

    @Override
    public ResponseBase actualizar(Long id, TipoPagoEntity tipoPagoEntity) {
        String token = request.getHeader("Authorization");
        try {
            Optional<TipoPagoEntity> tipoPagoEncontrado = tipoPagoRepository.findById(id);
            TipoPagoEntity tipoPagoNuevo = new TipoPagoEntity();
            if (tipoPagoEncontrado.isPresent()) {
                tipoPagoNuevo = tipoPagoEncontrado.get();
                tipoPagoNuevo.setMetodo(tipoPagoEntity.getMetodo());
                tipoPagoNuevo.setDateModif(new Date());
                Usuario usuario = usuarioClient.getUsuarioAutenticado(token);

                tipoPagoNuevo.setUserModif(usuario.getUsername());

                tipoPagoRepository.save(tipoPagoNuevo);
            } else {
                ResponseBase.errorNotFound("Recurso no encontrado");
            }


            return ResponseBase.exitoso("Ticket creado correctamente", tipoPagoNuevo);
        } catch (ConstraintViolationException e) {
            return ResponseBase.errorViolationException("Error de validación: " + e.getMessage());
        } catch (Exception e) {
            return ResponseBase.errorInternalSErverError("Error al procesar la solicitud" + e.getMessage());
        }
    }

    @Override
    public ResponseBase deleteById(Long id) {
        try {
            Optional<TipoPagoEntity> tipoPagoEntity = tipoPagoRepository.findById(id);
            if (tipoPagoEntity.isPresent()) {
                tipoPagoRepository.deleteById(id);
                return ResponseBase.exitoso("Tipo Pago eliminado con éxito", Optional.of(null));
            } else {
                return ResponseBase.errorNotFound("Tipo de pago no encontrado con ID: " + id);
            }
        } catch (Exception e) {
            return ResponseBase.errorInternalSErverError("Error al procesar la solicitud: " + e.getMessage());
        }
    }

}
