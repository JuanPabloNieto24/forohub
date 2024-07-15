package com.kev.forohub.domain.respuesta;

import com.kev.forohub.domain.topico.TopicoRespository;
import com.kev.forohub.helper.ResponseMessage;
import com.kev.forohub.helper.Type;
import com.kev.forohub.infra.security.usuario.Usuario;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class RespuestaService {

    private final RespuestaRepository respuestaRepository;
    private final TopicoRespository topicoRespository;

    public RespuestaService(RespuestaRepository respuestaRepository,TopicoRespository topicoRespository) {
        this.respuestaRepository = respuestaRepository;
        this.topicoRespository = topicoRespository;
    }

    @Transactional
    public DatosDetalleRespuesta registrarRespuesta(DatosRegistroRespuesta datos) {

        if(datos.idTopico() == null){
            throw new ValidationException("El id del topico es obligatorio y no puede ser nulo");
        }

        if(!topicoRespository.existsById(datos.idTopico())){
            throw new EntityNotFoundException("No se encontro ningun topico con ese id");
        }

        if(respuestaRepository.existsByMensaje(datos.mensaje())){
            throw new DataIntegrityViolationException("Ya existe un respuesta con este mensaje");
        }

        if(respuestaRepository.existsBySolucion(datos.solucion())){
            throw new DataIntegrityViolationException("Solucion duplicada");
        }

        var topico = topicoRespository.getReferenceById(datos.idTopico());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var usuario = (Usuario) authentication.getPrincipal();

        var respuesta = new Respuesta(datos,topico,usuario);

        respuestaRepository.save(respuesta);

        return new DatosDetalleRespuesta(
                respuesta.getId(),
                respuesta.getMensaje(),
                respuesta.getSolucion(),
                respuesta.getAutor().getNombre(),
                respuesta.getTopico().getTitulo(),
                respuesta.getFechaCreacion(),
                respuesta.getStatus()
        );
    }

    public ResponseMessage deleteRespuesta(Long id) {
        if(id ==  null){
            throw new ValidationException("El id de la respuesta no debe ser nulo");
        }

        if (!respuestaRepository.existsById(id)){
            throw new EntityNotFoundException("el id de referencia a la respuesta no existe");
        }

        var respuesta = respuestaRepository.getReferenceById(id);
        respuestaRepository.delete(respuesta);

        return new ResponseMessage(Type.SUCCESS, "La respuesta se elimino correctamente");
    }

    public DatosDetalleRespuesta updateRespuesta(DatosUpdateRespuesta datos) {

        if(datos.idRespuesta() == null){
            throw new ValidationException("El id de la respuesta no debe ser nulo");
        }

        if(!respuestaRepository.existsById(datos.idRespuesta())){
            throw new EntityNotFoundException("el id de referencia a la respuesta no existe");
        }

        var respuesta = respuestaRepository.getReferenceById(datos.idRespuesta());

        respuesta.updateDatos(datos);

        return new DatosDetalleRespuesta(
                respuesta.getId(),
                respuesta.getMensaje(),
                respuesta.getSolucion(),
                respuesta.getAutor().getNombre(),
                respuesta.getTopico().getTitulo(),
                respuesta.getFechaCreacion(),
                respuesta.getStatus()
        );
    }
}
