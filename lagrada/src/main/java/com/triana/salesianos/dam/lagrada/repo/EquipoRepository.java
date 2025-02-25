package com.triana.salesianos.dam.lagrada.repo;

import com.triana.salesianos.dam.lagrada.model.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EquipoRepository extends JpaRepository<Equipo, UUID> {

    //exists by nombre
    boolean existsByNombre(String nombre);


}
