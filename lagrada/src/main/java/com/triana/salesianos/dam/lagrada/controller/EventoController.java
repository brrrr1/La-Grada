package com.triana.salesianos.dam.lagrada.controller;

import com.triana.salesianos.dam.lagrada.dto.*;
import com.triana.salesianos.dam.lagrada.model.Evento;
import com.triana.salesianos.dam.lagrada.service.EventoService;
import com.triana.salesianos.dam.lagrada.util.MailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    private final MailService mailService;


    @Operation(summary = "Método para crear un evento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha creado el evento",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Evento.class)),
                            examples = {@ExampleObject(
                                    value = """
                                               {
                                                   "id": "7b34b93e-5e47-4eda-8747-593dbeb01239",
                                                   "nombre": "Final Champions League",
                                                   "descripcion": "La final del torneo más importante de Europa",
                                                   "fechaYHora": "2025-05-28T21:00:00",
                                                   "equipo1": {
                                                       "id": "b8e30540-5025-4306-bc9f-ec425c58ffb5",
                                                       "nombre": "Getafe CF",
                                                       "fotoEscudo": "unnamed_384690.jpg",
                                                       "fotoFondo": null
                                                   },
                                                   "equipo2": {
                                                       "id": "4beb55b7-6bea-472d-ae67-428864202fea",
                                                       "nombre": "Sevilla FC",
                                                       "fotoEscudo": "61vF-2q-qjL_380172.jpg",
                                                       "fotoFondo": null
                                                   },
                                                   "entradasRestantes": 450,
                                                   "entradasTotales": 450,
                                                   "precio": 150.0,
                                                   "tipoEvento": "FINAL",
                                                   "entradas": null
                                               } 
                                            """

                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha podido crear el evento",
                    content = @Content),
    })
    @PostMapping
    public ResponseEntity<Evento> createEvento(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Cuerpo del evento a editar", required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CreateEventoDto.class),
                            examples = @ExampleObject(value = """
                                                           {
                                                                "nombre": "Final Champions League",
                                                                "descripcion": "La final del torneo más importante de Europa",
                                                                "fechaYHora": "2025-05-28T21:00:00",
                                                                "equipo1Id": "{{idEquipo1}}",
                                                                "equipo2Id": "{{idEquipo2}}",
                                                                "entradasTotales": 450,
                                                                "precio": 150.0,
                                                                "tipo": "FINAL"
                                                           }
                            
""")))
            @RequestBody CreateEventoDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(eventoService.createEvento(dto));
    }

    @Operation(summary = "Obtiene los próximos eventos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado los próximos eventos",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetEventoDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {
                                                    "nombre": "Clásico",
                                                    "descripcion": "Partido entre Madrid y Barcelona",
                                                    "fechaYHora": "2025-03-05T11:51:54.022541",
                                                    "equipo1": {
                                                        "id": "b8e30540-5025-4306-bc9f-ec425c58ffb5",
                                                        "nombre": "Real Madrid CF",
                                                        "fotoEscudo": "madrid.jpg",
                                                        "fotoFondo": null
                                                    },
                                                    "equipo2": {
                                                        "id": "4beb55b7-6bea-472d-ae67-428864202fea",
                                                        "nombre": "FC Barcelona",
                                                        "fotoEscudo": "barcelona.jpg",
                                                        "fotoFondo": null
                                                    }
                                                },
                                                {
                                                    "nombre": "Derbi",
                                                    "descripcion": "Partido entre Betis y Sevilla",
                                                    "fechaYHora": "2025-03-05T11:51:54.022541",
                                                    "equipo1": {
                                                        "id": "b8e30540-5025-4306-bc9f-ec425c58ffb5",
                                                        "nombre": "Real Betis",
                                                        "fotoEscudo": "betis.jpg",
                                                        "fotoFondo": null
                                                    },
                                                    "equipo2": {
                                                        "id": "4beb55b7-6bea-472d-ae67-428864202fea",
                                                        "nombre": "Sevilla FC",
                                                        "fotoEscudo": "sevilla.jpg",
                                                        "fotoFondo": null
                                                    }
                                                }
                                            ]
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado los próximos eventos",
                    content = @Content),
    })
    @GetMapping("/proximos")
    public ResponseEntity<List<GetEventoDto>> getProximosEventos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        List<GetEventoDto> eventos = eventoService.getAllNextEvents(pageable)
                .stream()
                .map(GetEventoDto::from) // Convierte cada Evento en GetEventoDto
                .toList();

        return ResponseEntity.ok(eventos);
    }

    @Operation(summary = "Obtiene todos los próximos eventos sin paginación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado los próximos eventos",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetEventoDto.class))
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado los próximos eventos",
                    content = @Content),
    })
    @GetMapping("/proximos/all")
    public ResponseEntity<List<GetEventoDto>> getAllNextEventsNoPage(
            @RequestParam(required = false) String nombreEquipo,
            @RequestParam(required = false) String nombreEvento,
            @RequestParam(required = false) Boolean tieneEntradasDisponibles) {
        
        EventoFilterDto filter = new EventoFilterDto(nombreEquipo, nombreEvento, tieneEntradasDisponibles);
        List<GetEventoDto> eventos = eventoService.getAllNextEventsNoPageFiltered(filter)
                .stream()
                .map(GetEventoDto::from)
                .toList();
        return ResponseEntity.ok(eventos);
    }

    @Operation(summary = "Obtiene todos los próximos eventos cotidianos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado los próximos eventos cotidianos",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetEventoDto.class))
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado los próximos eventos cotidianos",
                    content = @Content),
    })
    @GetMapping("/proximos/cotidianos")
    public ResponseEntity<List<GetEventoDto>> getAllNextEventsCotidianos(
            @RequestParam(required = false) String nombreEquipo,
            @RequestParam(required = false) String nombreEvento,
            @RequestParam(required = false) Boolean tieneEntradasDisponibles) {
        
        EventoFilterDto filter = new EventoFilterDto(nombreEquipo, nombreEvento, tieneEntradasDisponibles);
        List<GetEventoDto> eventos = eventoService.getAllNextEventsCotidianosFiltered(filter)
                .stream()
                .map(GetEventoDto::from)
                .toList();
        return ResponseEntity.ok(eventos);
    }

    @Operation(summary = "Obtiene todos los próximos eventos importantes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado los próximos eventos importantes",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetEventoDto.class))
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado los próximos eventos importantes",
                    content = @Content),
    })
    @GetMapping("/proximos/importantes")
    public ResponseEntity<List<GetEventoDto>> getAllNextEventsImportantes(
            @RequestParam(required = false) String nombreEquipo,
            @RequestParam(required = false) String nombreEvento,
            @RequestParam(required = false) Boolean tieneEntradasDisponibles) {
        
        EventoFilterDto filter = new EventoFilterDto(nombreEquipo, nombreEvento, tieneEntradasDisponibles);
        List<GetEventoDto> eventos = eventoService.getAllNextEventsImportantesFiltered(filter)
                .stream()
                .map(GetEventoDto::from)
                .toList();
        return ResponseEntity.ok(eventos);
    }

    @Operation(summary = "Obtiene todos los próximos eventos finales")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado los próximos eventos finales",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetEventoDto.class))
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado los próximos eventos finales",
                    content = @Content),
    })
    @GetMapping("/proximos/finales")
    public ResponseEntity<List<GetEventoDto>> getAllNextEventsFinales(
            @RequestParam(required = false) String nombreEquipo,
            @RequestParam(required = false) String nombreEvento,
            @RequestParam(required = false) Boolean tieneEntradasDisponibles) {
        
        EventoFilterDto filter = new EventoFilterDto(nombreEquipo, nombreEvento, tieneEntradasDisponibles);
        List<GetEventoDto> eventos = eventoService.getAllNextEventsFinalesFiltered(filter)
                .stream()
                .map(GetEventoDto::from)
                .toList();
        return ResponseEntity.ok(eventos);
    }

    @Operation(summary = "Método para editar la información de un evento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha editado el evento",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetEventoCompletoDto.class)),
                            examples = {@ExampleObject("""
                                            {
                                                "id": "89efbfe3-8284-49a7-9adc-0a5a171b8d51",
                                                "nombre": "Sevilla - Getafe Final UCL",
                                                "descripcion": "FINAL CHAMPIONS",
                                                "fechaYHora": "2025-03-15T20:00:00",
                                                "equipo1": {
                                                    "id": "b8e30540-5025-4306-bc9f-ec425c58ffb5",
                                                    "nombre": "Getafe CF",
                                                    "fotoEscudo": "unnamed_384690.jpg",
                                                    "fotoFondo": null
                                                },
                                                "equipo2": {
                                                    "id": "4beb55b7-6bea-472d-ae67-428864202fea",
                                                    "nombre": "Sevilla FC",
                                                    "fotoEscudo": "61vF-2q-qjL_380172.jpg",
                                                    "fotoFondo": null
                                                },
                                                "entradasRestantes": 200,
                                                "entradasTotales": 200,
                                                "precio": 12.0,
                                                "tipoEvento": "FINAL",
                                                "entradas": []
                                            }
                                    
                                    """

                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha podido editar el evento",
                    content = @Content),
    })
    @PutMapping("/{id}")
    public ResponseEntity<GetEventoCompletoDto> updateEvento(@PathVariable UUID id,
                                               @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                       description = "Cuerpo del usuario a editar", required = true,
                                                       content = @Content(mediaType = "application/json",
                                                               schema = @Schema(implementation = UpdateEventoDto.class),
                                                               examples = @ExampleObject(value = """
                                                           {
                                                                "nombre": "Sevilla - Getafe Final UCL",
                                                                "descripcion": "FINAL CHAMPIONS",
                                                                "fechaYHora": "2025-03-15T20:00:00",
                                                                "equipo1Id": "{{idEquipo1}}",
                                                                "equipo2Id": "{{idEquipo2}}",
                                                                "entradasTotales": 200,
                                                                "precio": 12,
                                                                "tipo": "FINAL"
                                                           }
                            
""")))
                                               @RequestBody UpdateEventoDto dto) {
        try {
            Evento updatedEvento = eventoService.updateEvento(id, dto);
            return ResponseEntity.ok(GetEventoCompletoDto.from(updatedEvento));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @Operation(summary = "Borra un evento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha eliminado el evento",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ResponseEntity.class))
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha podido borrar el evento",
                    content = @Content),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvento(@PathVariable UUID id) {
        try {
            eventoService.deleteEvento(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @Operation(summary = "Obtiene los eventos de un equipo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado los eventos",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetEventoDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {
                                                    "nombre": "Clásico",
                                                    "descripcion": "Partido entre Madrid y Barcelona",
                                                    "fechaYHora": "2025-03-05T10:49:40.17043"
                                                },
                                                {
                                                    "nombre": "Derbi",
                                                    "descripcion": "Partido entre Betis y Sevilla",
                                                    "fechaYHora": "2025-03-05T10:49:40.17043"
                                                }
                                            ]
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado los eventos",
                    content = @Content),
    })
    @GetMapping("/equipo/{equipo}")
    public ResponseEntity<List<GetEventoDto>> obtenerEventosFuturosPorEquipo(@PathVariable String equipo) {
        return ResponseEntity.ok(eventoService.obtenerEventosFuturosPorEquipo(equipo));
    }

    @Operation(
            summary = "Obtiene todos los eventos sin filtrar por fecha",
            description = "Este endpoint devuelve una lista de todos los eventos en el sistema, tanto pasados como futuros, " +
                    "ordenados por fecha y hora de forma ascendente. Incluye todos los detalles de cada evento, " +
                    "incluyendo información completa de los equipos participantes, entradas disponibles y tipo de evento."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado todos los eventos",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetEventoCompletoDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {
                                                    "id": "b8e30540-5025-4306-bc9f-ec425c58ffb5",
                                                    "nombre": "Clásico",
                                                    "descripcion": "Partido entre Madrid y Barcelona",
                                                    "fechaYHora": "2025-03-05T11:51:54.022541",
                                                    "equipo1": {
                                                        "id": "b8e30540-5025-4306-bc9f-ec425c58ffb5",
                                                        "nombre": "Real Madrid CF",
                                                        "fotoEscudo": "madrid.jpg",
                                                        "fotoFondo": null
                                                    },
                                                    "equipo2": {
                                                        "id": "4beb55b7-6bea-472d-ae67-428864202fea",
                                                        "nombre": "FC Barcelona",
                                                        "fotoEscudo": "barcelona.jpg",
                                                        "fotoFondo": null
                                                    },
                                                    "entradasRestantes": 450,
                                                    "entradasTotales": 500,
                                                    "precio": 150.0,
                                                    "tipoEvento": "IMPORTANTE"
                                                },
                                                {
                                                    "id": "4beb55b7-6bea-472d-ae67-428864202fea",
                                                    "nombre": "Derbi",
                                                    "descripcion": "Partido entre Betis y Sevilla",
                                                    "fechaYHora": "2025-03-06T11:51:54.022541",
                                                    "equipo1": {
                                                        "id": "b8e30540-5025-4306-bc9f-ec425c58ffb5",
                                                        "nombre": "Real Betis",
                                                        "fotoEscudo": "betis.jpg",
                                                        "fotoFondo": null
                                                    },
                                                    "equipo2": {
                                                        "id": "4beb55b7-6bea-472d-ae67-428864202fea",
                                                        "nombre": "Sevilla FC",
                                                        "fotoEscudo": "sevilla.jpg",
                                                        "fotoFondo": null
                                                    },
                                                    "entradasRestantes": 300,
                                                    "entradasTotales": 400,
                                                    "precio": 100.0,
                                                    "tipoEvento": "COTIDIANO"
                                                }
                                            ]
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado eventos",
                    content = @Content),
            @ApiResponse(responseCode = "500",
                    description = "Error interno del servidor",
                    content = @Content)
    })
    @GetMapping("/todos")
    public ResponseEntity<List<GetEventoCompletoDto>> getAllEvents() {
        List<GetEventoCompletoDto> eventos = eventoService.getAllEvents()
                .stream()
                .map(GetEventoCompletoDto::from)
                .toList();
        return ResponseEntity.ok(eventos);
    }

    @Operation(summary = "Obtiene los detalles de un evento por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado el evento",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetEventoDetailDto.class)
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado el evento",
                    content = @Content),
    })
    @GetMapping("/{id}")
    public ResponseEntity<GetEventoDetailDto> getEventoById(@PathVariable UUID id) {
        return eventoService.findById(id)
                .map(evento -> GetEventoDetailDto.from(evento, mailService))
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

}
