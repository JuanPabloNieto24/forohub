package com.kev.forohub.controllers;

import com.kev.forohub.domain.curso.Curso;
import com.kev.forohub.domain.curso.CursoService;
import com.kev.forohub.domain.curso.DatosRegistroCurso;
import com.kev.forohub.domain.curso.DatosRespuestaCurso;
import com.kev.forohub.helper.ResponseMessage;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/curso")
@SecurityRequirement(name = "bearer-key")
public class CursoController {

    private final CursoService cursoService;

    public CursoController(CursoService cursoService){
        this.cursoService = cursoService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DatosRespuestaCurso> registrarCurso(@RequestBody @Valid DatosRegistroCurso datos, UriComponentsBuilder uriComponentsBuilder){
        var curso = cursoService.registrarCurso(new Curso(datos));
        URI url = uriComponentsBuilder.path("/curso/{id}").buildAndExpand(curso.id()).toUri();
        return ResponseEntity.created(url).body(curso);
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<DatosRespuestaCurso>>> listarCursos(@PageableDefault(size = 5) Pageable pageable){
        return ResponseEntity.ok(cursoService.listar(pageable));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DatosRespuestaCurso> updateCurso(@PathVariable Long id, @RequestBody @Valid DatosRegistroCurso datos){
        return ResponseEntity.ok(cursoService.updateCurso(id,datos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaCurso> getCurso(@PathVariable Long id){
        return ResponseEntity.ok(cursoService.getCurso(id));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<ResponseMessage> deleteCurso(@PathVariable Long id){
        return ResponseEntity.ok(cursoService.deleteCurso(id));
    }

}
