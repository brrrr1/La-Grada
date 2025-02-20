package com.triana.salesianos.dam.lagrada.dto;

public record CreateUserRequest(
        String correo, String password, String verifyPassword
) {
}
