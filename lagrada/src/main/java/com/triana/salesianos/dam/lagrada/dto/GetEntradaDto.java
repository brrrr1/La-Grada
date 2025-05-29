package com.triana.salesianos.dam.lagrada.dto;

import java.util.UUID;
import com.triana.salesianos.dam.lagrada.model.Entrada;
import com.triana.salesianos.dam.lagrada.model.User;

public record GetEntradaDto(
    UUID id,
    UUID usuarioId,
    String usuarioNombre,
    String usuarioApellidos,
    String usuarioCorreo
) {
    public static GetEntradaDto from(Entrada entrada) {
        User u = entrada.getUsuario();
        return new GetEntradaDto(
            entrada.getId(),
            u.getId(),
            u.getNombre(),
            u.getApellidos(),
            u.getCorreo()
        );
    }
}
