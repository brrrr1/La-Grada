package com.triana.salesianos.dam.lagrada.dto;

import com.triana.salesianos.dam.lagrada.model.Evento;
import com.triana.salesianos.dam.lagrada.model.TipoEvento;
import com.triana.salesianos.dam.lagrada.util.MailService;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record GetEventoDetailDto(
        UUID id,
        String nombre,
        String descripcion,
        LocalDateTime fechaYHora,
        GetEquipoDto equipo1,
        GetEquipoDto equipo2,
        int entradasRestantes,
        int entradasTotales,
        Double precio,
        TipoEvento tipoEvento,
        List<GetEntradaDto> entradas
) {
    public static GetEventoDetailDto from(Evento e, MailService mailService) {
        return new GetEventoDetailDto(
                e.getId(),
                e.getNombre(),
                e.getDescripcion(),
                e.getFechaYHora(),
                e.getEquipo1() != null ? GetEquipoDto.of(e.getEquipo1()) : null,
                e.getEquipo2() != null ? GetEquipoDto.of(e.getEquipo2()) : null,
                e.getEntradasRestantes(),
                e.getEntradasTotales(),
                e.getPrecio(),
                e.getTipoEvento(),
                e.getEntradas() != null ? e.getEntradas().stream().map(entrada -> GetEntradaDto.from(entrada, mailService)).collect(Collectors.toList()) : List.of()
        );
    }

    public static GetEventoDetailDto from(Evento e) {
        throw new UnsupportedOperationException("Debes usar from(Evento, MailService) para incluir los QR en las entradas");
    }
} 