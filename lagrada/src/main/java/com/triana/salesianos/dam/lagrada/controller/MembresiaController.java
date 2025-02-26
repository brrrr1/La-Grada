/*package com.triana.salesianos.dam.lagrada.controller;

import com.triana.salesianos.dam.lagrada.model.Membresia;
import com.triana.salesianos.dam.lagrada.service.MembresiaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/membresia/")
@RequiredArgsConstructor
@Tag(name = "Membresia", description = "Controlador para gestionar las membresías de La Grada")
public class MembresiaController {

    private final MembresiaService membresiaService;

    @Operation(summary = "Editar precio de una membresía",
            description = "Permite editar el precio de una membresía existente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Precio editado correctamente"),
                    @ApiResponse(responseCode = "404", description = "Membresía no encontrada")
            })
    @PutMapping("/edit/{id}")
    public void editMembresiaPrice(@PathVariable UUID id, @RequestBody Double precio) {
        membresiaService.editMembresiaPrice(id, precio);
    }

    @Operation(summary = "Obtener todas las membresías",
            description = "Devuelve una lista de todas las membresías",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de membresías obtenida correctamente",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(value = """
                                            [
                                                {
                                                    "tipo": "BASIC",
                                                    "precio": 19.99,
                                                    "nombre": "Membresía Básica",
                                                    "descripcion": "Acceso a áreas limitadas del gimnasio"
                                                },
                                                {
                                                    "tipo": "PLUS",
                                                    "precio": 29.99,
                                                    "nombre": "Membresía Plus",
                                                    "descripcion": "Acceso a todas las áreas del gimnasio"
                                                }
                                            ]
                                            """)))
            })
    @GetMapping
    public void findAll() {
        membresiaService.findAll();
    }

    @Operation(summary = "Obtener una membresía",
            description = "Devuelve una membresía por su ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Membresía obtenida correctamente",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(value = """
                                            {
                                                "tipo": "PLUS",
                                                "precio": 29.99,
                                                "nombre": "Membresía Plus",
                                                "descripcion": "Acceso a todas las áreas del gimnasio"
                                            }
                                            """))),
                    @ApiResponse(responseCode = "404", description = "Membresía no encontrada")
            })
    @GetMapping("/{id}")
    public void findById(@PathVariable UUID id) {
        membresiaService.findById(id);
    }

    @Operation(summary = "Cambiar membresía de un usuario",
            description = "Permite cambiar la membresía de un usuario por otra",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Cuerpo de la membresía a cambiar", required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Membresia.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "tipo": "PLUS",
                                        "precio": 29.99,
                                        "nombre": "Membresía Plus",
                                        "descripcion": "Acceso a todas las áreas del gimnasio"
                                    }
                                    """))),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Membresía cambiada correctamente"),
                    @ApiResponse(responseCode = "404", description = "Membresía no encontrada")
            })
    @PutMapping("/change/{id}")
    public void changeMembresia(@PathVariable UUID id, @RequestBody Membresia membresia) {
        membresiaService.changeMembresia(id, membresia);
    }

    @Operation(summary = "Cancelar membresía de un usuario",
            description = "Permite cancelar la membresía de un usuario",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Membresía cancelada correctamente",
                            content = @Content(mediaType = "application/json",
                                    examples = @ExampleObject(value = """
                                            {
                                                "message": "Membresía cancelada correctamente"
                                            }
                                            """))),
                    @ApiResponse(responseCode = "404", description = "Membresía no encontrada")
            })
    @DeleteMapping("/cancel/{id}")
    public void cancelMembresia(@PathVariable UUID id) {
        membresiaService.cancelMembresia(id);
    }


}*/