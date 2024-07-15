package com.kev.forohub.domain.topico;

import com.kev.forohub.domain.respuesta.DatosRespuesta;
import com.kev.forohub.domain.respuesta.Respuesta;

import java.time.LocalDateTime;
import java.util.List;

public record DatoRespuestaGetTopico(
        Long id,
        String titulo,
        String mensaje,
        String autor,
        String status,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaActualizacion,
        List<DatosRespuesta> respuestas
) {
}
