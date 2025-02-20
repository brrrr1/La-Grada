package com.triana.salesianos.dam.lagrada.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.triana.salesianos.dam.lagrada.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findFirstByUsername(String username);

    Optional<User> findByActivationToken(String activationToken);
}
