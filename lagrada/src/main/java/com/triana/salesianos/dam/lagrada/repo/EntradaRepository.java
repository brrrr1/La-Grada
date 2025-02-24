package com.triana.salesianos.dam.lagrada.repo;

import com.triana.salesianos.dam.lagrada.model.Entrada;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EntradaRepository extends JpaRepository<Entrada, UUID> {
}
