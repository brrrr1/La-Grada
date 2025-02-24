package com.triana.salesianos.dam.lagrada.controller;

import com.triana.salesianos.dam.lagrada.dto.CreateEventoDto;
import com.triana.salesianos.dam.lagrada.model.Evento;
import com.triana.salesianos.dam.lagrada.service.EventoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/evento")
public class EventoController {

    private final EventoService eventoService;

    @PostMapping
    public ResponseEntity<Evento> createEvento(@RequestBody CreateEventoDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(eventoService.createEvento(dto));
    }
}
