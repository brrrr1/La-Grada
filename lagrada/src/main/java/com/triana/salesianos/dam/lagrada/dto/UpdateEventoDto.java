package com.triana.salesianos.dam.lagrada.dto;

import com.triana.salesianos.dam.lagrada.model.TipoEvento;

import java.time.LocalDateTime;
import java.util.UUID;

public record UpdateEventoDto(
        String nombre,
        String descripcion,
        LocalDateTime fechaYHora,
        UUID equipo1Id,
        UUID equipo2Id,
        int entradasTotales,
        Double precio,
        TipoEvento tipo
) {}
