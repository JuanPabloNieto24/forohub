package com.kev.forohub.domain.topico;

import com.kev.forohub.domain.curso.CursoRepository;
import com.kev.forohub.domain.respuesta.DatosRespuesta;
import com.kev.forohub.domain.respuesta.RespuestaRepository;
import com.kev.forohub.helper.ResponseMessage;
import com.kev.forohub.helper.Type;
import com.kev.forohub.infra.security.usuario.Usuario;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicoService {

    private final TopicoRespository topicoRespository;
    private final CursoRepository cursoRepository;
    private final RespuestaRepository respuestaRepository;
    private final PagedResourcesAssembler<DatosRespuesta> pagedResourcesAssemblerDatosRespuesta;
    private final PagedResourcesAssembler<DatosListadoTopico> pagedResourcesAssemblerDatosListadoTopico;

    public TopicoService(
            TopicoRespository topicoRespository,
            CursoRepository cursoRepository,
            RespuestaRepository respuestaRepository,
            PagedResourcesAssembler<DatosRespuesta> pagedResourcesAssemblerDatosRespuesta,
            PagedResourcesAssembler<DatosListadoTopico> pagedResourcesAssemblerDatosListadoTopico
    ){
        this.topicoRespository = topicoRespository;
        this.cursoRepository = cursoRepository;
        this.respuestaRepository = respuestaRepository;
        this.pagedResourcesAssemblerDatosRespuesta = pagedResourcesAssemblerDatosRespuesta;
        this.pagedResourcesAssemblerDatosListadoTopico = pagedResourcesAssemblerDatosListadoTopico;
    }

    public DatoRespuestaTopico registrarTopico(DatosRegistroTopico datos){

        if(datos.id_curso() == null){
            throw new ValidationException("El id de curso no debe ser nulo");
        }

        if (!cursoRepository.existsById(datos.id_curso())){
            throw new EntityNotFoundException("el id de referencia al curso no existe");
        }

        if(topicoRespository.existsByTitulo(datos.titulo())){
            throw new DataIntegrityViolationException("Ya existe un topico con este titulo");
        }

        if(topicoRespository.existsByMensaje(datos.mensaje())){
            throw  new DataIntegrityViolationException("Ya existe un topico con este mensaje");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = (Usuario) authentication.getPrincipal();

        var curso = cursoRepository.getReferenceById(datos.id_curso());

        var topico = new Topico(datos,curso,usuario);


        topicoRespository.save(topico);

        return new DatoRespuestaTopico(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getAutor().getNombre(),
                topico.getStatus().toString(),
                topico.getFechaCreacion(),
                topico.getFechaActualizacion()
        );
    }

    public PagedModel<EntityModel<DatosListadoTopico>> listarTopicos(Pageable pageable) {
        var pagedModel = topicoRespository.findAll(pageable).map(DatosListadoTopico::new);
        return pagedResourcesAssemblerDatosListadoTopico.toModel(pagedModel);
    }

    public DatoRespuestaGetTopico getTopico(Long id) {

        if(id == null){
            throw new ValidationException("El id del topico no debe ser nulo");
        }

        if (!topicoRespository.existsById(id)){
            throw new EntityNotFoundException("el id de referencia al topico no existe");
        }

        var topico = topicoRespository.getReferenceById(id);

        List<DatosRespuesta> respuestas = topico.getRespuestas()
                .stream()
                .map(r -> new DatosRespuesta(
                        r.getId(),
                        r.getMensaje(),
                        r.getSolucion(),
                        r.getAutor().getNombre(),
                        r.getFechaCreacion()
                )).collect(Collectors.toList());

        return new DatoRespuestaGetTopico(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getAutor().getNombre(),
                topico.getStatus().toString(),
                topico.getFechaCreacion(),
                topico.getFechaActualizacion(),
                respuestas
        );
    }

    public DatosRespuestaTopicoUpdate updateTopico(Long idTopico, DatosUpdateTopico datosUpdateTopico) {
        if(idTopico == null){
            throw new ValidationException("El id del topico no debe ser nulo");
        }

        if (!topicoRespository.existsById(idTopico)){
            throw new EntityNotFoundException("el id de referencia al topico no existe");
        }

        var topico = topicoRespository.getReferenceById(idTopico);
        topico.updateDatos(datosUpdateTopico);

        return new DatosRespuestaTopicoUpdate(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getAutor().getNombre(),
                topico.getStatus().toString(),
                topico.getFechaActualizacion()
        );
    }

    public ResponseMessage deleteTopico(Long id) {
        if(id == null){
            throw new ValidationException("El id del topico no debe ser nulo");
        }

        if (!topicoRespository.existsById(id)){
            throw new EntityNotFoundException("el id de referencia al topico no existe");
        }
        var topico = topicoRespository.getReferenceById(id);
        topicoRespository.delete(topico);

        return new ResponseMessage(Type.SUCCESS,"El topico con id:"+id+" fue eliminado correctamente");
    }


    public PagedModel<EntityModel<DatosRespuesta>> getRespuestas(Long id , Pageable pageable) {

        if(id == null){
            throw new ValidationException("El id del topico no debe ser nulo");
        }

        if (!topicoRespository.existsById(id)){
            throw new EntityNotFoundException("el id de referencia al topico no existe");
        }

        var topico = topicoRespository.getReferenceById(id);

        var respuestaPage = respuestaRepository.findAllByTopico(pageable,topico).map(DatosRespuesta::new);

        return pagedResourcesAssemblerDatosRespuesta.toModel(respuestaPage);

    }
}
