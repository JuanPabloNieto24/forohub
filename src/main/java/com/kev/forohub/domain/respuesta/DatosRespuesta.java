package com.kev.forohub.domain.respuesta;

import java.time.LocalDateTime;

public record DatosRespuesta(
        Long id,
        String mensaje,
        String solucion,
        String autor,
        LocalDateTime fechaCreacion
) {
    public DatosRespuesta(Respuesta respuesta){
        this(respuesta.getId(), respuesta.getMensaje(), respuesta.getSolucion(), respuesta.getAutor().getNombre(),respuesta.getFechaCreacion());
    }
}
