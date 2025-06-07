package com.triana.salesianos.dam.lagrada.dto;

import com.triana.salesianos.dam.lagrada.model.Evento;
import com.triana.salesianos.dam.lagrada.model.TipoEvento;

import java.time.LocalDateTime;
import java.util.UUID;

public record GetEventoCompletoDto(
        UUID id,
        String nombre,
        String descripcion,
        LocalDateTime fechaYHora,
        GetEquipoDto equipo1,
        GetEquipoDto equipo2,
        int entradasRestantes,
        int entradasTotales,
        Double precio,
        TipoEvento tipoEvento
) {
    public static GetEventoCompletoDto from(Evento e) {
        return new GetEventoCompletoDto(
                e.getId(),
                e.getNombre(),
                e.getDescripcion(),
                e.getFechaYHora(),
                e.getEquipo1() != null ? GetEquipoDto.of(e.getEquipo1()) : null,
                e.getEquipo2() != null ? GetEquipoDto.of(e.getEquipo2()) : null,
                e.getEntradasRestantes(),
                e.getEntradasTotales(),
                e.getPrecio(),
                e.getTipoEvento()
        );
    }
} 