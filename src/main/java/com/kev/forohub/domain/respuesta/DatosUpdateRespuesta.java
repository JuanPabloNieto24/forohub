package com.kev.forohub.domain.respuesta;

public record DatosUpdateRespuesta(
        Long idRespuesta,
        String mensaje,
        String solucion
) {
}
