package com.triana.salesianos.dam.lagrada.dto;

public record CreateUserRequest(
        String nombre, String apellidos, String username, String correo, String password, String verifyPassword
) {
}

