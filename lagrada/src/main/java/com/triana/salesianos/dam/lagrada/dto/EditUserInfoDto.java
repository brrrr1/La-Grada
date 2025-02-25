package com.triana.salesianos.dam.lagrada.dto;

import java.util.UUID;

public record EditUserInfoDto(String nombre, String apellidos, String correo, UUID equipoFavoritoId) {}
