package com.triana.salesianos.dam.lagrada.dto;

import com.triana.salesianos.dam.lagrada.model.User;
import java.util.UUID;

public record UserListResponse(
    UUID id,
    String username,
    String nombre,
    String apellidos,
    String correo,
    String equipoFavorito,
    boolean enabled
) {
    public static UserListResponse of(User user) {
        String equipoFavorito = user.getEquipoFavorito() != null ? user.getEquipoFavorito().getNombre() : null;
        return new UserListResponse(
            user.getId(),
            user.getUsername(),
            user.getNombre(),
            user.getApellidos(),
            user.getCorreo(),
            equipoFavorito,
            user.isEnabled()
        );
    }
} 