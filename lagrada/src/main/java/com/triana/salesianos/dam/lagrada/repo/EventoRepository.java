package com.triana.salesianos.dam.lagrada.repo;

import com.triana.salesianos.dam.lagrada.model.Evento;
import com.triana.salesianos.dam.lagrada.model.Equipo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface EventoRepository extends JpaRepository<Evento, UUID> {

    @Query("SELECT e FROM Evento e WHERE e.fechaYHora > CURRENT_TIMESTAMP AND (e.equipo1 = :equipo OR e.equipo2 = :equipo) ORDER BY e.fechaYHora ASC")
    List<Evento> findNext4EventsByTeam(Equipo equipo, Pageable pageable);

    @Query("SELECT e FROM Evento e WHERE e.fechaYHora > CURRENT_TIMESTAMP ORDER BY e.fechaYHora ASC")
    List<Evento> findNextEvents(Pageable pageable);

    @Query("SELECT e FROM Evento e WHERE (e.equipo1.nombre = :equipo OR e.equipo2.nombre = :equipo) AND e.fechaYHora > :fechaActual")
    List<Evento> findByEquipoAndFechaFutura(@Param("equipo") String equipo, @Param("fechaActual") LocalDateTime fechaActual);

    @Query("SELECT e FROM Evento e WHERE e.fechaYHora > CURRENT_TIMESTAMP ORDER BY e.fechaYHora ASC")
    List<Evento> findAllNextEvents();

    @Query("SELECT e FROM Evento e WHERE e.fechaYHora > CURRENT_TIMESTAMP AND e.tipoEvento = 'COTIDIANO' ORDER BY e.fechaYHora ASC")
    List<Evento> findAllNextEventsCotidianos();

    @Query("SELECT e FROM Evento e WHERE e.fechaYHora > CURRENT_TIMESTAMP AND e.tipoEvento = 'IMPORTANTE' ORDER BY e.fechaYHora ASC")
    List<Evento> findAllNextEventsImportantes();

    @Query("SELECT e FROM Evento e WHERE e.fechaYHora > CURRENT_TIMESTAMP AND e.tipoEvento = 'FINAL' ORDER BY e.fechaYHora ASC")
    List<Evento> findAllNextEventsFinales();

}
