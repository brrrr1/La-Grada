package com.triana.salesianos.dam.lagrada.controller;

import com.triana.salesianos.dam.lagrada.dto.CreateEventoDto;
import com.triana.salesianos.dam.lagrada.dto.UpdateEventoDto;
import com.triana.salesianos.dam.lagrada.model.Evento;
import com.triana.salesianos.dam.lagrada.service.EventoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/evento")
public class EventoController {

    private final EventoService eventoService;

    @PostMapping
    public ResponseEntity<Evento> createEvento(@RequestBody CreateEventoDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(eventoService.createEvento(dto));
    }


    @GetMapping("/proximos")
    public ResponseEntity<List<Evento>> getProximosEventos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Evento> eventos = eventoService.getAllNextEvents(pageable);
        return ResponseEntity.ok(eventos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Evento> updateEvento(@PathVariable UUID id, @RequestBody UpdateEventoDto dto) {
        try {
            Evento updatedEvento = eventoService.updateEvento(id, dto);
            return ResponseEntity.ok(updatedEvento);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvento(@PathVariable UUID id) {
        try {
            eventoService.deleteEvento(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
