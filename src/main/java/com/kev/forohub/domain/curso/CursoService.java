package com.kev.forohub.domain.curso;

import com.kev.forohub.helper.ResponseMessage;
import com.kev.forohub.helper.Type;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;
    private final PagedResourcesAssembler<DatosRespuestaCurso> pagedResourcesAssembler;

    public CursoService(CursoRepository cursoRepository,
                        PagedResourcesAssembler<DatosRespuestaCurso> pagedResourcesAssembler
    ){
        this.cursoRepository = cursoRepository;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    public DatosRespuestaCurso registrarCurso(Curso curso){
        cursoRepository.save(curso);
        return new DatosRespuestaCurso(
                curso.getId(),
                curso.getNombre(),
                curso.getCategoria()
        );
    }


    public PagedModel<EntityModel<DatosRespuestaCurso>> listar(Pageable pageable) {
        var pagedModel =  cursoRepository.findAll(pageable).map(DatosRespuestaCurso::new);
        return pagedResourcesAssembler.toModel(pagedModel);
    }

    public DatosRespuestaCurso updateCurso(Long id, DatosRegistroCurso datos) {
        if(id == null){
            throw new ValidationException("El id del curso no puede ser nulo");
        }

        if(!cursoRepository.existsById(id)){
            throw new EntityNotFoundException("El curso al que hace referencia el id no existe");
        }

        var curso = cursoRepository.getReferenceById(id);

        curso.updateDatos(datos);

        return new DatosRespuestaCurso(
                curso.getId(),
                curso.getNombre(),
                curso.getCategoria()
        );
    }

    public DatosRespuestaCurso getCurso(Long id) {
        if(id == null){
            throw new ValidationException("El id del curso no puede ser nulo");
        }

        if(!cursoRepository.existsById(id)){
            throw new EntityNotFoundException("El curso al que hace referencia el id no existe");
        }

        var curso = cursoRepository.getReferenceById(id);

        return new DatosRespuestaCurso(curso.getId(), curso.getNombre(), curso.getCategoria());
    }

    public ResponseMessage deleteCurso(Long id) {
        if(id == null){
            throw new ValidationException("El id del curso no puede ser nulo");
        }

        if(!cursoRepository.existsById(id)){
            throw new EntityNotFoundException("El curso al que hace referencia el id no existe");
        }

        var curso = cursoRepository.getReferenceById(id);

        cursoRepository.delete(curso);

        return new ResponseMessage(Type.SUCCESS,"Curso eliminado correctamente");
    }
}
