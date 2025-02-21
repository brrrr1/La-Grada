package com.triana.salesianos.dam.lagrada.repo;

import com.triana.salesianos.dam.lagrada.model.Membresia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MembresiaRepository extends JpaRepository<Membresia, UUID> {


}
