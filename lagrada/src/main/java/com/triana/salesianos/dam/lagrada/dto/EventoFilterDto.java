package com.triana.salesianos.dam.lagrada.dto;

public record EventoFilterDto(
    String nombreEquipo,
    String nombreEvento,
    Boolean tieneEntradasDisponibles
) {} 