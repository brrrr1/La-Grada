package com.triana.salesianos.dam.lagrada.dto;

public record CreateUserRequest(
        String username, String correo, String password, String verifyPassword
) {
}
