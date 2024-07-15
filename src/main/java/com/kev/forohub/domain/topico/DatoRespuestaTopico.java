package com.kev.forohub.domain.topico;

import java.time.LocalDateTime;

public record DatoRespuestaTopico(
        Long id,
        String titulo,
        String mensaje,
        String autor,
        String status,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaActualizacion
) {
}
