package com.triana.salesianos.dam.lagrada.dto;

import com.triana.salesianos.dam.lagrada.model.Equipo;

import java.util.UUID;

public record GetEquipoDto(UUID id, String nombre, String fotoEscudo, String fotoFondo) {

    public static GetEquipoDto of(Equipo equipo) {
        return new GetEquipoDto(
                equipo.getId(),
                equipo.getNombre(),
                equipo.getFotoEscudo(),
                equipo.getFotoFondo()
        );
    }
}
