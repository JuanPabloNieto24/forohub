package com.kev.forohub.domain.topico;

import java.time.LocalDateTime;

public record DatosRespuestaTopicoUpdate(
        Long id,
        String titulo,
        String mensaje,
        String autor,
        String status,
        LocalDateTime fechaActualizacion
) {
}
