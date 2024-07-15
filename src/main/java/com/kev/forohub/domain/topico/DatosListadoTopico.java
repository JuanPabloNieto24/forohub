package com.kev.forohub.domain.topico;

import java.time.LocalDateTime;

public record DatosListadoTopico(
        Long id,
        String curso,
        String titulo,
        String mensaje,
        String autor,
        String status,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaActualizacion

) {
    public DatosListadoTopico(Topico topico){
        this(
                topico.getId(),
                topico.getCurso().getNombre(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getAutor().getNombre(),
                topico.getStatus().toString(),
                topico.getFechaCreacion(),
                topico.getFechaActualizacion()
        );
    }
}
