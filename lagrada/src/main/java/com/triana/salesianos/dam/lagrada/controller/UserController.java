package com.triana.salesianos.dam.lagrada.controller;

import com.triana.salesianos.dam.lagrada.dto.*;
import com.triana.salesianos.dam.lagrada.model.Evento;
import com.triana.salesianos.dam.lagrada.model.User;
import com.triana.salesianos.dam.lagrada.security.exceptionhandling.AuthenticationExceptionHandler;
import com.triana.salesianos.dam.lagrada.security.jwt.access.JwtService;
import com.triana.salesianos.dam.lagrada.security.jwt.refresh.RefreshToken;
import com.triana.salesianos.dam.lagrada.security.jwt.refresh.RefreshTokenRequest;
import com.triana.salesianos.dam.lagrada.security.jwt.refresh.RefreshTokenService;
import com.triana.salesianos.dam.lagrada.service.EventoService;
import com.triana.salesianos.dam.lagrada.service.UserService;
import com.triana.salesianos.dam.lagrada.util.MailService;
import com.triana.salesianos.dam.lagrada.util.SearchCriteria;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import lombok.extern.java.Log;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import jakarta.servlet.ServletException;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@RestController
@RequiredArgsConstructor
@Log
@Tag(name = "Usuario", description = "El controlador de usuario")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final EventoService eventoService;
    private final MailService mailService;
    private final AuthenticationFailureHandler authenticationFailureHandler;

    @Operation(summary = "Sirve para que un usuario se registre en la aplicación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha registrado el usuario",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "id": "2458c026-22ab-4799-98c3-553b36267ea5",
                                                "correo": "delgado.hebru24@triana.salesianos.edu"
                                            }
                                            """

                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha registrado el usuario",
                    content = @Content),
    })
    @PostMapping("/auth/register")
    public ResponseEntity<UserResponse> register(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Cuerpo del usuario a crear", required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CreateUserRequest.class),
                            examples = @ExampleObject(value = """
                                        {
                                            "correo": "delgado.hebru24@triana.salesianos.edu",
                                            "username": "br1",
                                            "password": "12345678",
                                            "verifyPassword": "12345678"
                                            "nombre": "Bruno",
                                            "apellidos": "Delgado Herrero"
                                        }
                                    
""")))
            @RequestBody @Valid CreateUserRequest createUserRequest) {
        User user = userService.createUser(createUserRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.of(user));
    }

    @Operation(summary = "Sirve para iniciar sesión con un usuario registrado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha iniciado sesión",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ResponseEntity.class)),
                            examples = {@ExampleObject(
                                    value = """
                                        
                                            {
                                                "id": "fc72a8b4-02d8-47d1-ba1e-cc8e3e3403ea",
                                                "correo": "delgado.hebru24@triana.salesianos.edu",
                                                "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmYzcyYThiNC0wMmQ4LTQ3ZDEtYmExZS1jYzhlM2UzNDAzZWEiLCJpYXQiOjE3NDA1NTg0NjUsImV4cCI6MTc0MTIxMTI2NX0.3V-Fm5gHnF-v6wQJ1BFRl9Y4kDM5yfAhD73PsehLJ9s",
                                                "refreshToken": "0ad26975-9da1-4c59-af63-47e8cb231e39"
                                            }
                                            """

                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha iniciado sesión",
                    content = @Content),
    })
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequest loginRequest,
            HttpServletRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            User user = (User) authentication.getPrincipal();
            String accessToken = jwtService.generateAccessToken(user);
            RefreshToken refreshToken = refreshTokenService.create(user);

            // Get client IP address
            String ipAddress = request.getRemoteAddr();
            if (request.getHeader("X-Forwarded-For") != null) {
                ipAddress = request.getHeader("X-Forwarded-For").split(",")[0];
            }

            // Send login notification email
            try {
                mailService.sendLoginNotificationEmail(user.getCorreo(), ipAddress);
            } catch (Exception e) {
                // Log the error but don't fail the login
                log.warning("Error sending login notification email: " + e.getMessage());
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.of(user, accessToken, refreshToken.getToken()));
        } catch (AuthenticationException e) {
            Map<String, Object> body = new HashMap<>();
            body.put("status", HttpStatus.UNAUTHORIZED.value());
            body.put("error", "Unauthorized");
            
            String message;
            if (e instanceof BadCredentialsException) {
                message = "Credenciales incorrectas. Por favor, verifica tu usuario y contraseña.";
            } else if (e instanceof DisabledException) {
                message = "Tu cuenta está deshabilitada. Por favor, contacta con nosotros: lagrada.8k@gmail.com";
            } else {
                message = e.getMessage();
            }
            
            body.put("message", message);
            body.put("path", request.getServletPath());
            
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
        }
    }

    @Operation(summary = "Sirve para que un usuario refresque su token de acceso")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha refrescado el token de acceso",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ResponseEntity.class)),
                            examples = {@ExampleObject(
                                    value = """
                                        {
                                            "id": "fc72a8b4-02d8-47d1-ba1e-cc8e3e3403ea",
                                            "correo": "delgado.hebru24@triana.salesianos.edu",
                                            "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmYzcyYThiNC0wMmQ4LTQ3ZDEtYmExZS1jYzhlM2UzNDAzZWEiLCJpYXQiOjE3NDA1NTc3NzYsImV4cCI6MTc0MTIxMDU3Nn0.tl5u7u-z4D90AKj4NfME4Nm2uyzKXunPggC-u7gN1fQ",
                                            "refreshToken": "b454bed6-2b07-4e4d-a76d-54c1ac0a719d"
                                        }
                                            """

                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha refrescado el token de acceso",
                    content = @Content),
    })
    @PostMapping("/auth/refresh/token")
    public ResponseEntity<?> refreshToken(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Cuerpo del token a refrescar", required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RefreshTokenRequest.class),
                            examples = @ExampleObject(value = """
                                        {
                                            "refreshToken": "3adb0133-e18f-4ce3-bcd3-174ff4e7d6b5"
                                        }
                                    
""")))
            @RequestBody RefreshTokenRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(refreshTokenService.refreshToken(req.refreshToken()));
    }


    @Operation(summary = "Sirve para que un usuario active su cuenta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha refrescado el token de acceso",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ResponseEntity.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "id": "300955b1-6c7a-4544-add8-5e07404c4245",
                                                "correo": "delgado.hebru24@triana.salesianos.edu"
                                            }
                                            """

                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha activado la cuenta",
                    content = @Content),
    })
    @PostMapping("/activate/account")
    public ResponseEntity<?> activateAccount(@io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Cuerpo de la cuenta a activar", required = true,
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ActivateAccountRequest.class),
                    examples = @ExampleObject(value = """
                                        {
                                            "token": "ce65621f-7ecb-4213-9a53-d988a743fc10"
                                        }
                                    
""")))
                                                 @RequestBody ActivateAccountRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.of(userService.activateAccount(req.token())));
    }

    @Operation(summary = "Sirve para que un usuario cierre su sesión")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha refrescado el token de acceso",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ResponseEntity.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            
                                            """

                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha activado la cuenta",
                    content = @Content),
    })
    @PostMapping("/auth/logout")
    public ResponseEntity<?> logout(@AuthenticationPrincipal User user) {
        refreshTokenService.deleteByUser(user);
        return ResponseEntity.noContent().build();
    }



    @Operation(summary = "Obtiene los detalles del usuario que hace la petición")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado los detalles del usuario",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetUserDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "correo": "cr7@gmail.com",
                                                "nombre": "Cristiano",
                                                "apellidos": "Ronaldo",
                                                "equipoFavorito": "Real Madrid"
                                            }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado el usuario",
                    content = @Content),
    })
    @GetMapping("/me")
    public GetUserDto me(@AuthenticationPrincipal User user) {
        String equipoFavorito = (user.getEquipoFavorito() != null) ? user.getEquipoFavorito().getNombre() : null;
        return new GetUserDto(user.getCorreo(), user.getNombre(), user.getApellidos(), equipoFavorito);
    }


    @Operation(summary = "Obtiene los detalles del usuario admin que hace la petición")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado los detalles del usuario admin",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetUserDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "correo": "admin@lagrada.com",
                                                "nombre": "Admin",
                                                "apellidos": "Administradorez",
                                                "equipoFavorito": null
                                            }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado el admin",
                    content = @Content),
    })
    @GetMapping("/me/admin")
    public GetUserDto adminMe(@AuthenticationPrincipal User user) {
        return new GetUserDto(user.getCorreo(), user.getNombre(), user.getApellidos(), null);
    }


    /*@PostMapping("/user/buy-membership/{membershipId}")
    public ResponseEntity<?> buyMembership(@AuthenticationPrincipal User user, @PathVariable UUID membershipId) {
        userService.buyMembership(user.getId(), membershipId);
        return ResponseEntity.ok().build();
    }*/

    @Operation(summary = "Sirve para que un usuario eliga su equipo favorito")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha asignado el equipo como favorito",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ResponseEntity.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                
                                            }
                                            """

                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha podido asignar el equipo como favorito",
                    content = @Content),
    })
    @PostMapping("/user/choose-favorite-team/{teamId}")
    public ResponseEntity<?> chooseFavoriteTeam(@AuthenticationPrincipal User user, @PathVariable UUID teamId) {
        userService.chooseFavoriteTeam(user.getId(), teamId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Sirve para que un usuario cambie su equipo favorito")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha cambiado el equipo favorito",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ResponseEntity.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                
                                            }
                                            """

                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha podido cambiar el equipo favorito",
                    content = @Content),
    })
    @PostMapping("/user/change-favorite-team/{newTeamId}")
    public ResponseEntity<?> changeFavoriteTeam(@AuthenticationPrincipal User user, @PathVariable UUID newTeamId) {
        userService.changeFavoriteTeam(user.getId(), newTeamId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Sirve para que un usuario compre una entrada para un evento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha comprado la entrada",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ResponseEntity.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                
                                            }
                                            """

                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha podido comprar la entrada",
                    content = @Content),
    })
    @PostMapping("/user/buy-ticket/{eventId}")
    public ResponseEntity<?> buyTicket(@AuthenticationPrincipal User user, @PathVariable UUID eventId) {
        userService.buyTicket(user.getId(), eventId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Obtiene los próximos 4 eventos del equipo favorito del usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado los próximos 4 eventos del equipo favorito del usuario",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetEventoDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {
                                                    "nombre": "Clásico",
                                                    "descripcion": "Partido entre Madrid y Barcelona",
                                                    "fechaYHora": "2025-03-05T10:00:34.038894"
                                                },
                                                {
                                                    "nombre": "Clásico",
                                                    "descripcion": "Partido entre Madrid y Barcelona",
                                                    "fechaYHora": "2025-03-05T10:00:34.038894"
                                                },
                                                {
                                                    "nombre": "Clásico",
                                                    "descripcion": "Partido entre Madrid y Barcelona",
                                                    "fechaYHora": "2025-03-05T10:00:34.038894"
                                                }
                                            ]
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado los próximos 4 eventos del equipo favorito del usuario",
                    content = @Content),
    })
    @GetMapping("/user/favorite-team-events")
    public ResponseEntity<List<GetEventoDto>> getNext4EventsOfFavoriteTeam(@AuthenticationPrincipal User user) {
        List<GetEventoDto> eventos = userService.getNext4EventsOfFavoriteTeam(user.getId())
                .stream()
                .map(GetEventoDto::from)
                .toList();
        return ResponseEntity.ok(eventos);
    }

    @Operation(summary = "Obtiene los próximos eventos para los que tiene entradas el usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado los próximos eventos para los que tiene entradas el usuario",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetEventoDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {
                                                    "nombre": "Clásico",
                                                    "descripcion": "Partido entre Madrid y Barcelona",
                                                    "fechaYHora": "2025-03-05T10:00:34.038894"
                                                },
                                                {
                                                    "nombre": "Clásico",
                                                    "descripcion": "Partido entre Madrid y Barcelona",
                                                    "fechaYHora": "2025-03-05T10:00:34.038894"
                                                },
                                                {
                                                    "nombre": "Clásico",
                                                    "descripcion": "Partido entre Madrid y Barcelona",
                                                    "fechaYHora": "2025-03-05T10:00:34.038894"
                                                }
                                            ]
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado los próximos eventos para los que tiene entradas el usuario",
                    content = @Content),
    })
    @GetMapping("/eventos-futuros")
    public ResponseEntity<List<GetEventoDto>> obtenerEventosFuturos(@AuthenticationPrincipal User user) {
        // Obtener la lista de eventos futuros para el usuario
        List<Evento> eventosFuturos = userService.obtenerEventosFuturosUsuario(user);

        // Convertir cada evento a GetEventoDto
        List<GetEventoDto> eventosFuturosDto = eventosFuturos.stream()
                .map(GetEventoDto::from)
                .collect(Collectors.toList());

        // Devolver la lista de GetEventoDto
        return ResponseEntity.ok(eventosFuturosDto);
    }

    @Operation(summary = "Obtiene los a los que ya ha asistido el usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado los eventos a los que ya ha asistido el usuario",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetEventoDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {
                                                    "nombre": "Clásico",
                                                    "descripcion": "Partido entre Madrid y Barcelona",
                                                    "fechaYHora": "2025-03-05T10:00:34.038894"
                                                },
                                                {
                                                    "nombre": "Clásico",
                                                    "descripcion": "Partido entre Madrid y Barcelona",
                                                    "fechaYHora": "2025-03-05T10:00:34.038894"
                                                },
                                                {
                                                    "nombre": "Clásico",
                                                    "descripcion": "Partido entre Madrid y Barcelona",
                                                    "fechaYHora": "2025-03-05T10:00:34.038894"
                                                }
                                            ]
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado los eventos a los que ya ha asistido el usuario",
                    content = @Content),
    })
    @GetMapping("/eventos-pasados")
    public ResponseEntity<List<GetEventoDto>> obtenerEventosPasados(@AuthenticationPrincipal User user) {
        // Obtener la lista de eventos pasados para el usuario
        List<Evento> eventosPasados = userService.obtenerEventosPasadosUsuario(user);

        // Convertir cada evento a GetEventoDto
        List<GetEventoDto> eventosPasadosDto = eventosPasados.stream()
                .map(GetEventoDto::from)
                .collect(Collectors.toList());

        // Devolver la lista de GetEventoDto
        return ResponseEntity.ok(eventosPasadosDto);
    }


    @Operation(summary = "Sirve para que un usuario edite su información")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "El usuario ha editado su información",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)),
                            examples = {@ExampleObject("""
                                            {
                                                "id": "c2e48c17-3488-4ecd-877d-491908bd7913",
                                                "correo": "delgado.hebru24@triana.salesianos.edu"
                                            }
                                    
                                    """

                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "El usuario no ha podido editar su información",
                    content = @Content),
    })
    @PutMapping("/user/edit-info")
    public ResponseEntity<UserResponse> updateUserInfo(@AuthenticationPrincipal User user, @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Cuerpo del usuario a editar", required = true,
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = EditUserInfoDto.class),
                    examples = @ExampleObject(value = """
                                                           {
                                                            "nombre": "Bruno",
                                                            "apellidos": "Delgado",
                                                            "correo": "delgado.hebru24@triana.salesianos.edu",
                                                            "equipoFavoritoId": null
                                                           }
                            
""")))
    @RequestBody EditUserInfoDto editUserInfoDto) {
        User updatedUser = userService.updateUserInfo(user.getId(), editUserInfoDto);
        return ResponseEntity.ok(UserResponse.of(updatedUser));
    }

    @Operation(summary = "Sirve para que un usuario edite su contraseña")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "El usuario ha editado su contraseña",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)),
                            examples = {@ExampleObject("""
                                            {
                                                "id": "c2e48c17-3488-4ecd-877d-491908bd7913",
                                                "correo": "delgado.hebru24@triana.salesianos.edu"
                                            }
                                    
                                    """

                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "El usuario no ha podido editar su contraseña",
                    content = @Content),
    })
    @PutMapping("/user/edit-password")
    public ResponseEntity<UserResponse> updateUserPassword(@AuthenticationPrincipal User user,
                                                           @io.swagger.v3.oas.annotations.parameters.RequestBody(
                                                                   description = "Cuerpo del usuario a editar", required = true,
                                                                   content = @Content(mediaType = "application/json",
                                                                           schema = @Schema(implementation = EditUserPasswordDto.class),
                                                                           examples = @ExampleObject(value = """
                                                           {
                                                                "oldPassword": "12345678",
                                                                "newPassword": "contraseña_nueva"
                                                           }
                            
""")))
                                                           @RequestBody EditUserPasswordDto editUserPasswordDto) {
        User updatedUser = userService.updateUserPassword(user.getId(), editUserPasswordDto);
        return ResponseEntity.ok(UserResponse.of(updatedUser));
    }


    @Operation(summary = "Obtiene los usuarios que cumplan los criterios de busqueda")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado los usuarios que cumplen los criterios de busqueda",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetEventoDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                    {
                                                        "correo": "cr7@gmail.com",
                                                        "nombre": "Cristiano",
                                                        "apellidos": "Ronaldo",
                                                        "equipoFavorito": "Real Madrid"
                                                    },
                                                    {
                                                        "correo": "messi@gmail.com",
                                                        "nombre": "Leo",
                                                        "apellidos": "Messi",
                                                        "equipoFavorito": "FC Barcelona"
                                            ]
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado los usuarios que cumplen los criterios de busqueda",
                    content = @Content),
    })
    @GetMapping("/search")
    public List<GetUserDto> buscar(@RequestParam(value = "search", required = false) String search) {
        log.info(search);
        List<SearchCriteria> params = new ArrayList<>();

        if (search != null) {
            Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)([^,]+),");
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                log.info(matcher.group(1));
                log.info(matcher.group(2));
                log.info(matcher.group(3));
                params.add(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
            }
        }

        return userService.search(params)
                .stream()
                .map(user -> new GetUserDto(
                        user.getCorreo(),
                        user.getNombre(),
                        user.getApellidos(),
                        user.getEquipoFavorito() != null ? user.getEquipoFavorito().getNombre() : null))
                .toList();
    }

    @Operation(summary = "Sirve para que un usuario quite su equipo favorito")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha quitado el equipo favorito",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ResponseEntity.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                
                                            }
                                            """

                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha podido quitar el equipo favorito",
                    content = @Content),
    })
    @DeleteMapping("/user/remove-favorite-team")
    public ResponseEntity<?> removeFavoriteTeam(@AuthenticationPrincipal User user) {
        userService.removeFavoriteTeam(user.getId());
        return ResponseEntity.ok().build();
    }

    @Operation(
        summary = "Comprueba si un nombre de usuario ya está registrado",
        description = "Devuelve true si el username ya existe, false si está disponible. Útil para validación en el registro."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Resultado de la comprobación",
            content = @Content(mediaType = "application/json",
                examples = {
                    @ExampleObject(name = "Username disponible", value = "false"),
                    @ExampleObject(name = "Username ya existe", value = "true")
                }
            )
        )
    })
    @GetMapping("/auth/check-username")
    public ResponseEntity<Boolean> checkUsername(
            @io.swagger.v3.oas.annotations.Parameter(description = "Nombre de usuario a comprobar", example = "br1")
            @RequestParam String username) {
        boolean exists = userService.existsByUsername(username);
        return ResponseEntity.ok(exists);
    }

    @Operation(
        summary = "Comprueba si un correo electrónico ya está registrado",
        description = "Devuelve true si el correo ya existe, false si está disponible. Útil para validación en el registro."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
            description = "Resultado de la comprobación",
            content = @Content(mediaType = "application/json",
                examples = {
                    @ExampleObject(name = "Correo disponible", value = "false"),
                    @ExampleObject(name = "Correo ya existe", value = "true")
                }
            )
        )
    })
    @GetMapping("/auth/check-email")
    public ResponseEntity<Boolean> checkEmail(
            @io.swagger.v3.oas.annotations.Parameter(description = "Correo electrónico a comprobar", example = "usuario@email.com")
            @RequestParam String correo) {
        boolean exists = userService.existsByCorreo(correo);
        return ResponseEntity.ok(exists);
    }

    @Operation(summary = "Obtiene las entradas futuras del usuario (con QR)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado las entradas futuras del usuario",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetEntradaDto.class))
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado entradas futuras",
                    content = @Content),
    })
    @GetMapping("/user/entradas/futuras")
    public ResponseEntity<List<GetEntradaDto>> obtenerEntradasFuturas(@AuthenticationPrincipal User user) {
        List<GetEntradaDto> entradas = userService.obtenerEntradasFuturasUsuario(user);
        return ResponseEntity.ok(entradas);
    }

    @Operation(summary = "Obtiene las entradas pasadas del usuario (con QR)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado las entradas pasadas del usuario",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetEntradaDto.class))
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado entradas pasadas",
                    content = @Content),
    })
    @GetMapping("/user/entradas/pasadas")
    public ResponseEntity<List<GetEntradaDto>> obtenerEntradasPasadas(@AuthenticationPrincipal User user) {
        List<GetEntradaDto> entradas = userService.obtenerEntradasPasadasUsuario(user);
        return ResponseEntity.ok(entradas);
    }

    @Operation(summary = "Obtiene la lista de todos los usuarios (solo para administradores)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha obtenido la lista de usuarios correctamente",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UserListResponse.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {
                                                    "id": "2458c026-22ab-4799-98c3-553b36267ea5",
                                                    "username": "usuario1",
                                                    "nombre": "Juan",
                                                    "apellidos": "Pérez",
                                                    "correo": "juan@example.com",
                                                    "equipoFavorito": "Real Madrid",
                                                    "enabled": true
                                                }
                                            ]
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "403",
                    description = "No tienes permisos para acceder a este recurso",
                    content = @Content),
    })
    @GetMapping("/admin/users")
    public List<UserListResponse> getAllUsers() {
        return userService.findAllUsers().stream()
                .map(UserListResponse::of)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Desactiva un usuario (solo para administradores)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha desactivado el usuario correctamente",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserListResponse.class),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "id": "2458c026-22ab-4799-98c3-553b36267ea5",
                                                "username": "usuario1",
                                                "nombre": "Juan",
                                                "apellidos": "Pérez",
                                                "correo": "juan@example.com",
                                                "equipoFavorito": "Real Madrid",
                                                "enabled": false
                                            }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "403",
                    description = "No tienes permisos para acceder a este recurso o no se puede deshabilitar un administrador",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Usuario no encontrado",
                    content = @Content)
    })
    @PostMapping("/admin/users/disable")
    public ResponseEntity<?> disableUser(@RequestBody @Valid DisableUserRequest request) {
        try {
            User disabledUser = userService.disableUser(request.userId());
            return ResponseEntity.ok(UserListResponse.of(disabledUser));
        } catch (ResponseStatusException e) {
            Map<String, Object> body = new HashMap<>();
            body.put("status", e.getStatusCode().value());
            body.put("error", e.getStatusCode().toString());
            body.put("message", e.getReason());
            return ResponseEntity.status(e.getStatusCode()).body(body);
        }
    }

    @Operation(summary = "Habilita un usuario (solo para administradores)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha habilitado el usuario correctamente",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserListResponse.class),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "id": "2458c026-22ab-4799-98c3-553b36267ea5",
                                                "username": "usuario1",
                                                "nombre": "Juan",
                                                "apellidos": "Pérez",
                                                "correo": "juan@example.com",
                                                "equipoFavorito": "Real Madrid",
                                                "enabled": true
                                            }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "403",
                    description = "No tienes permisos para acceder a este recurso",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "Usuario no encontrado",
                    content = @Content)
    })
    @PostMapping("/admin/users/enable")
    public UserListResponse enableUser(@RequestBody @Valid EnableUserRequest request) {
        User enabledUser = userService.enableUser(request.userId());
        return UserListResponse.of(enabledUser);
    }

}