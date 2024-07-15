package com.kev.forohub.domain.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DatosRegistroTopico(
        @NotNull Long id_curso,
        @NotBlank String titulo,
        @NotBlank String mensaje
        ) {
}
