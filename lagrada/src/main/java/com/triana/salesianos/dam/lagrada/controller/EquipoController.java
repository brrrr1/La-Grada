package com.triana.salesianos.dam.lagrada.controller;

import com.triana.salesianos.dam.lagrada.dto.CreateEquipoDto;
import com.triana.salesianos.dam.lagrada.dto.GetEquipoDto;
import com.triana.salesianos.dam.lagrada.model.Equipo;
import com.triana.salesianos.dam.lagrada.service.EquipoService;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/equipo/")
public class EquipoController {

    private final EquipoService equipoService;

    // Crear equipo con DTO
    //Validar que el nombre del equipo no exista
    /*@PostMapping
    /*public ResponseEntity<Equipo> createEquipo(@RequestBody @Valid CreateEquipoDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(equipoService.createEquipo(dto));
    }*/

    // Ver equipo por id
    @GetMapping("/{id}")
    public ResponseEntity<GetEquipoDto> getEquipoById(@PathVariable UUID id) {
        if (id == null) {
            return ResponseEntity.badRequest().body(null);  // O manejarlo de otra forma
        }
        return ResponseEntity.ok(
                GetEquipoDto.of(equipoService.findById(id), getImageUrl(equipoService.findById(id).getFotoEscudo()), getImageUrl(equipoService.findById(id).getFotoFondo()))


        );
    }


    //Ver todos los equipos
    @GetMapping
    public ResponseEntity<List<GetEquipoDto>> findAll() {

        return ResponseEntity.ok(
                equipoService.findAll()
                        .stream()
                        .map(equipo -> {
                            return GetEquipoDto.of(equipo, getImageUrl(equipo.getFotoEscudo()), getImageUrl(equipo.getFotoFondo()));
                        })
                        .toList()
        );
    }

    @PostMapping
    public ResponseEntity<GetEquipoDto> create(
            @RequestPart("file") MultipartFile file,
            @RequestPart("file2") MultipartFile file2,
            @RequestPart("equipo") CreateEquipoDto newEquipo
    ) {
        Equipo equipo = equipoService.save(newEquipo,file);



        return ResponseEntity.status(HttpStatus.CREATED)
                .body(GetEquipoDto.of(equipo, getImageUrl(equipo.getFotoEscudo()), getImageUrl(equipo.getFotoFondo())));
    }

    public String getImageUrl(String filename) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(filename)
                .toUriString();
    }


}