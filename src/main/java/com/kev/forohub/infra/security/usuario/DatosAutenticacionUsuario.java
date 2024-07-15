package com.kev.forohub.infra.security.usuario;

import jakarta.validation.constraints.Email;

public record DatosAutenticacionUsuario(
        @Email String email,
        String clave
) {
}
