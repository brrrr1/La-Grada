package com.triana.salesianos.dam.lagrada.repo;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import com.triana.salesianos.dam.lagrada.model.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {

    Optional<User> findFirstByUsername(String username);

    Optional<User> findByActivationToken(String activationToken);
    

    //Find por username
    Optional<User> findByUsername(String username);

    @EntityGraph(attributePaths = "equipoFavorito")
    Optional<User> findById(UUID id);

    //exists by username
    boolean existsByUsername(String username);

}
