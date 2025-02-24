package com.triana.salesianos.dam.lagrada.dto;

import com.triana.salesianos.dam.lagrada.model.Evento;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record GetEventoDto(
        String nombre,
        String descripcion,
        LocalDateTime fechaYHora
) {

    public static GetEventoDto from(Evento e) {
        return new GetEventoDto(
                e.getNombre(),
                e.getDescripcion(),
                e.getFechaYHora()
        );
    }
}
