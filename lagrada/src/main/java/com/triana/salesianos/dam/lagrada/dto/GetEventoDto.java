package com.triana.salesianos.dam.lagrada.dto;

import com.triana.salesianos.dam.lagrada.model.Evento;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record GetEventoDto(
        UUID id,
        String nombre,
        String descripcion,
        LocalDateTime fechaYHora,
        GetEquipoDto equipo1,
        GetEquipoDto equipo2
) {

    public static GetEventoDto from(Evento e) {
        return new GetEventoDto(
                e.getId(),
                e.getNombre(),
                e.getDescripcion(),
                e.getFechaYHora(),
                e.getEquipo1() != null ? GetEquipoDto.of(e.getEquipo1()) : null,
                e.getEquipo2() != null ? GetEquipoDto.of(e.getEquipo2()) : null
        );
    }
}
