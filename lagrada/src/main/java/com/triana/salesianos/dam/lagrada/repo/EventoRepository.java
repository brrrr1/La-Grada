package com.triana.salesianos.dam.lagrada.repo;

import com.triana.salesianos.dam.lagrada.model.Evento;
import com.triana.salesianos.dam.lagrada.model.Equipo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface EventoRepository extends JpaRepository<Evento, UUID> {

    @Query("SELECT e FROM Evento e WHERE e.fechaYHora > CURRENT_TIMESTAMP AND (e.equipo1 = :equipo OR e.equipo2 = :equipo) ORDER BY e.fechaYHora ASC")
    List<Evento> findNext4EventsByTeam(Equipo equipo, Pageable pageable);
}
