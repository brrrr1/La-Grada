package com.triana.salesianos.dam.lagrada.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.triana.salesianos.dam.lagrada.model.User;


import java.util.UUID;

public record UserResponse(
        UUID id,
        String correo,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String token,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String refreshToken

) {

    public static UserResponse of (User user) {
        return new UserResponse(user.getId(), user.getCorreo(), null, null);
    }

    public static UserResponse of (User user, String token, String refreshToken) {
        return new UserResponse(user.getId(), user.getCorreo(), token, refreshToken);
    }

}