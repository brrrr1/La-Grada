package com.triana.salesianos.dam.lagrada.controller;

import com.triana.salesianos.dam.lagrada.dto.CreateEquipoDto;
import com.triana.salesianos.dam.lagrada.model.Equipo;
import com.triana.salesianos.dam.lagrada.service.EquipoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/equipo/")
public class EquipoController {

    private final EquipoService equipoService;

    // Crear equipo con DTO
    @PostMapping
    public ResponseEntity<Equipo> createEquipo(@RequestBody CreateEquipoDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(equipoService.createEquipo(dto));
    }

    // Ver equipo por id
    @GetMapping("/{id}")
    public ResponseEntity<Equipo> getEquipoById(UUID id) {
        return ResponseEntity.ok(equipoService.findById(id));
    }

    //Ver todos los equipos
    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(equipoService.findAll());
    }


}