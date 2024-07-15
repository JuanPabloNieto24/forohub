package com.kev.forohub.controllers;

import com.kev.forohub.domain.respuesta.DatosRespuesta;
import com.kev.forohub.domain.topico.*;
import com.kev.forohub.helper.ResponseMessage;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topico")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {

    private final TopicoService topicoService;

    public TopicoController(TopicoService topicoService){
        this.topicoService = topicoService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DatoRespuestaTopico> registrarTopico(@RequestBody @Valid DatosRegistroTopico datos, UriComponentsBuilder uriComponentsBuilder){
        var topico = topicoService.registrarTopico(datos);
        URI url = uriComponentsBuilder.path("/topico/{id}").buildAndExpand(topico.id()).toUri();
        return ResponseEntity.created(url).body(topico);
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<DatosListadoTopico>>> listarTopicos(@PageableDefault(size = 5) Pageable pageable){
        return ResponseEntity.ok(topicoService.listarTopicos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatoRespuestaGetTopico> getTopico(@PathVariable Long id){
        var topico = topicoService.getTopico(id);
        return ResponseEntity.ok(topico);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosRespuestaTopicoUpdate> updateTopico(@PathVariable Long id, @RequestBody DatosUpdateTopico datosUpdateTopico){
        var topicoResponse = topicoService.updateTopico(id,datosUpdateTopico);
        return ResponseEntity.ok(topicoResponse);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<ResponseMessage> deleteTopico(@PathVariable Long id){
        var response = topicoService.deleteTopico(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/respuestas")
    @Transactional
    public ResponseEntity<PagedModel<EntityModel<DatosRespuesta>>> getRespuestasDelTopico(@PathVariable Long id, @PageableDefault(size = 5) Pageable pageable){
        return ResponseEntity.ok(topicoService.getRespuestas(id,pageable));
    }

}
