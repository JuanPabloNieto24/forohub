package com.kev.forohub.domain.respuesta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DatosRegistroRespuesta(
        @NotNull Long idTopico,
        @NotBlank String mensaje,
        String solucion

) {
}
