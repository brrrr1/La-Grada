package com.triana.salesianos.dam.lagrada.controller;

import com.triana.salesianos.dam.lagrada.dto.CreateEquipoDto;
import com.triana.salesianos.dam.lagrada.dto.GetEquipoDto;
import com.triana.salesianos.dam.lagrada.dto.GetEventoDto;
import com.triana.salesianos.dam.lagrada.model.Equipo;
import com.triana.salesianos.dam.lagrada.model.Evento;
import com.triana.salesianos.dam.lagrada.service.EquipoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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


    @Operation(summary = "Obtiene un equipo por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado el equipo",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetEventoDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                                {
                                                    "id": "c76e77b6-9894-4e51-80b2-77978f0817d9",
                                                    "nombre": "Sevilla FC",
                                                    "fotoEscudo": "61vF-2q-qjL_236288.jpg",
                                                    "fotoFondo": null
                                                }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado el equipo",
                    content = @Content),
    })
    @GetMapping("/{id}")
    public ResponseEntity<GetEquipoDto> getEquipoById(@PathVariable UUID id) {
        if (id == null) {
            return ResponseEntity.badRequest().body(null);
        }
        Equipo equipo = equipoService.findById(id);
        return ResponseEntity.ok(
                GetEquipoDto.of(equipo)
        );
    }

    // Ver todos los equipos
    @Operation(summary = "Obtiene todos los equipos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado los equipos",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetEventoDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {
                                                    "id": "351d9607-90d2-4df8-ad50-2fe0557e63c3",
                                                    "nombre": "Real Madrid",
                                                    "fotoEscudo": "foto_Escudo_rm.png",
                                                    "fotoFondo": "foto_Fondo_rm.jpg"
                                                },
                                                {
                                                    "id": "278417c7-dee0-4c22-b92a-29543493485b",
                                                    "nombre": "FC Barcelona",
                                                    "fotoEscudo": "foto_Escudo_fcb.png",
                                                    "fotoFondo": "foto_Fondo_fcb.jpg"
                                                },
                                                {
                                                    "id": "c76e77b6-9894-4e51-80b2-77978f0817d9",
                                                    "nombre": "Sevilla FC",
                                                    "fotoEscudo": "61vF-2q-qjL_236288.jpg",
                                                    "fotoFondo": null
                                                }
                                            ]
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado los equipos",
                    content = @Content),
    })
    @GetMapping
    public ResponseEntity<List<GetEquipoDto>> findAll() {
        return ResponseEntity.ok(
                equipoService.findAll()
                        .stream()
                        .map(GetEquipoDto::of) // Ya maneja el ID
                        .toList()
        );
    }

    @Operation(summary = "Método para crear un equipo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha creado el equipo",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Evento.class)),
                            examples = {@ExampleObject(
                                    value = """
                                               {
                                                   "id": "c76e77b6-9894-4e51-80b2-77978f0817d9",
                                                   "nombre": "Sevilla FC",
                                                   "fotoEscudo": "61vF-2q-qjL_236288.jpg",
                                                   "fotoFondo": null
                                               } 
                                            """

                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha podido crear el equipo",
                    content = @Content),
    })
    @PostMapping
    public ResponseEntity<GetEquipoDto> create(
            @Parameter(description = "Imagen del escudo", required = true)
            @RequestPart("file") MultipartFile file,
            @Parameter(description = "Imagen del estadio", required = true)
            @RequestPart("file2") MultipartFile file2,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del equipo en JSON",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CreateEquipoDto.class)))
            @RequestPart("equipo") CreateEquipoDto newEquipo
    ) {
        Equipo equipo = equipoService.save(newEquipo, file, file2);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(GetEquipoDto.of(equipo));
    }



}