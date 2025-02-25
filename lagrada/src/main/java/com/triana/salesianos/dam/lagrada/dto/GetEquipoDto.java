package com.triana.salesianos.dam.lagrada.dto;

import com.triana.salesianos.dam.lagrada.model.Equipo;

public record GetEquipoDto (String nombre, String fotoEscudo, String fotoFondo) {

    public static GetEquipoDto of(Equipo equipo, String fotoEscudo, String fotoEstadio){
        return new GetEquipoDto(equipo.getNombre(), equipo.getFotoEscudo(), equipo.getFotoFondo());
    }
}
