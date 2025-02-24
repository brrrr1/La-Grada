package com.triana.salesianos.dam.lagrada.repo;

import com.triana.salesianos.dam.lagrada.model.Entrada;
import com.triana.salesianos.dam.lagrada.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface EntradaRepository extends JpaRepository<Entrada, UUID> {
    List<Entrada> findByUsuarioId(UUID usuarioId);
    List<Entrada> findByUsuarioAndEventoFechaYHoraAfter(User usuario, LocalDateTime fecha);
    List<Entrada> findByUsuarioAndEventoFechaYHoraBefore(User usuario, LocalDateTime fecha);

}
