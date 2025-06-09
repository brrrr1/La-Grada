package com.triana.salesianos.dam.lagrada.dto;

import java.util.UUID;

public record CreateUserRequest(
        String nombre, String apellidos, String username, String correo, String password, String verifyPassword,
        UUID equipoFavoritoId
) {
}

