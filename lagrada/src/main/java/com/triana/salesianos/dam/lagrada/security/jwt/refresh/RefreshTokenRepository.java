package com.triana.salesianos.dam.lagrada.security.jwt.refresh;

import com.triana.salesianos.dam.lagrada.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface RefreshTokenRepository
        extends JpaRepository<RefreshToken, UUID> {


    @Modifying
    @Transactional
    void deleteByUser(User user);

}
