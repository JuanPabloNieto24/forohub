package com.kev.forohub.controllers;

import com.kev.forohub.domain.respuesta.DatosDetalleRespuesta;
import com.kev.forohub.domain.respuesta.DatosRegistroRespuesta;
import com.kev.forohub.domain.respuesta.DatosUpdateRespuesta;
import com.kev.forohub.domain.respuesta.RespuestaService;
import com.kev.forohub.helper.ResponseMessage;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/respuesta")
@SecurityRequirement(name = "bearer-key")
public class RespuestaController {

    private final RespuestaService respuestaService;

    public RespuestaController(RespuestaService respuestaService) {
        this.respuestaService = respuestaService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DatosDetalleRespuesta> registrarRespuesta(@RequestBody @Valid DatosRegistroRespuesta datos, UriComponentsBuilder uriComponentsBuilder){
        var response = respuestaService.registrarRespuesta(datos);
        URI url = uriComponentsBuilder.path("/respuesta/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(url).body(response);
    }


    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<ResponseMessage> deleteRespuesta(@PathVariable Long id){
        var response  = respuestaService.deleteRespuesta(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping()
    @Transactional
    public ResponseEntity<DatosDetalleRespuesta> updateRespuesta(@RequestBody @Valid DatosUpdateRespuesta datos){
        var response = respuestaService.updateRespuesta(datos);
        return ResponseEntity.ok().body(response);
    }
}
