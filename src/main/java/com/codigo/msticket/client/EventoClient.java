package com.codigo.msticket.client;

import com.codigo.msticket.model.Evento;
import com.codigo.msticket.response.ResponseBase;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@FeignClient(name = "ms-evento ", url = "http://localhost:8080")

//@RequestMapping(value = "/evento")
@FeignClient(name = "ms-evento")

public interface EventoClient {

    


    @GetMapping("/evento/obtener/{id}") // Ajusta la ruta según corresponda
    ResponseEntity<ResponseBase<Evento>> obtenerEvento(@PathVariable Long id);
    //Evento obtenerEvento(@PathVariable Long id);

    @PutMapping("/evento/asiento/{id}/estado")
    ResponseEntity<ResponseBase> cambiarEstadoAsiento(@PathVariable("id") Long id, @RequestParam("numeroAsiento") int numeroAsiento, @RequestParam("estado") boolean estado);

}

//import com.codigo.msticket.model.Evento;
//
//import com.codigo.msticket.response.ResponseBase;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
////@RequestMapping(value = "/evento")
////@FeignClient(name = "ms-evento")
//@FeignClient(name = "ms-evento",  url = "http://localhost:8080")
//public interface EventoCliene {
//
//    @GetMapping("/evento/obtener/{id}")
//    Evento obtenerEvento(@PathVariable Long id);
////    @GetMapping("/{id}")
////    public ResponseEntity<Evento> getEventoById(@PathVariable("id") Long id);
////
////    @GetMapping("/asiento/{id}/estado")
////    public ResponseEntity<ResponseBase> cambiarEstadoAsiento(@PathVariable("id") Long id, @RequestParam("estado") boolean estado);
//
//   // public ResponseEntity<ResponseBase> cambiarEstadoAsiento(@PathVariable Long id, @RequestParam boolean estado);
//
//
//
//
//}
