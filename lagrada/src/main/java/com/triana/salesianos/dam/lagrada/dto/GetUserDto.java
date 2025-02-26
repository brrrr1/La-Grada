package com.triana.salesianos.dam.lagrada.dto;

public record GetUserDto(
        String correo,
        String nombre,
        String apellidos,
        String equipoFavorito
) {
}
