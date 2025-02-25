package com.triana.salesianos.dam.lagrada.controller;

import com.triana.salesianos.dam.lagrada.dto.*;
import com.triana.salesianos.dam.lagrada.model.Evento;
import com.triana.salesianos.dam.lagrada.model.User;
import com.triana.salesianos.dam.lagrada.security.jwt.access.JwtService;
import com.triana.salesianos.dam.lagrada.security.jwt.refresh.RefreshToken;
import com.triana.salesianos.dam.lagrada.security.jwt.refresh.RefreshTokenRequest;
import com.triana.salesianos.dam.lagrada.security.jwt.refresh.RefreshTokenService;
import com.triana.salesianos.dam.lagrada.service.EventoService;
import com.triana.salesianos.dam.lagrada.service.UserService;
import com.triana.salesianos.dam.lagrada.util.SearchCriteria;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import lombok.extern.java.Log;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


import static org.hibernate.query.sqm.tree.SqmNode.log;


@RestController
@RequiredArgsConstructor
@Log
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final EventoService eventoService;

    @PostMapping("/auth/register")
    public ResponseEntity<UserResponse> register(@RequestBody CreateUserRequest createUserRequest) {
        User user = userService.createUser(createUserRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.of(user));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();
        String accessToken = jwtService.generateAccessToken(user);
        RefreshToken refreshToken = refreshTokenService.create(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.of(user, accessToken, refreshToken.getToken()));
    }

    @PostMapping("/auth/refresh/token")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(refreshTokenService.refreshToken(req.refreshToken()));
    }

    @PostMapping("/activate/account")
    public ResponseEntity<?> activateAccount(@RequestBody ActivateAccountRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(UserResponse.of(userService.activateAccount(req.token())));
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<?> logout(@AuthenticationPrincipal User user) {
        refreshTokenService.deleteByUser(user);
        return ResponseEntity.noContent().build();
    }



    @GetMapping("/me")
    public UserResponse me(@AuthenticationPrincipal User user) {
        return UserResponse.of(user);
    }

    @GetMapping("/me/admin")
    public User adminMe(@AuthenticationPrincipal User user) {
        return user;
    }

    /*@PostMapping("/user/buy-membership/{membershipId}")
    public ResponseEntity<?> buyMembership(@AuthenticationPrincipal User user, @PathVariable UUID membershipId) {
        userService.buyMembership(user.getId(), membershipId);
        return ResponseEntity.ok().build();
    }*/

    @PostMapping("/user/choose-favorite-team/{teamId}")
    public ResponseEntity<?> chooseFavoriteTeam(@AuthenticationPrincipal User user, @PathVariable UUID teamId) {
        userService.chooseFavoriteTeam(user.getId(), teamId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/user/change-favorite-team/{newTeamId}")
    public ResponseEntity<?> changeFavoriteTeam(@AuthenticationPrincipal User user, @PathVariable UUID newTeamId) {
        userService.changeFavoriteTeam(user.getId(), newTeamId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/user/buy-ticket/{eventId}")
    public ResponseEntity<?> buyTicket(@AuthenticationPrincipal User user, @PathVariable UUID eventId) {
        userService.buyTicket(user.getId(), eventId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/favorite-team-events")
    public ResponseEntity<List<Evento>> getNext4EventsOfFavoriteTeam(@AuthenticationPrincipal User user) {
        List<Evento> eventos = userService.getNext4EventsOfFavoriteTeam(user.getId());
        return ResponseEntity.ok(eventos);
    }

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


    @PutMapping("/user/edit-info")
    public ResponseEntity<UserResponse> updateUserInfo(@AuthenticationPrincipal User user, @RequestBody EditUserInfoDto editUserInfoDto) {
        User updatedUser = userService.updateUserInfo(user.getId(), editUserInfoDto);
        return ResponseEntity.ok(UserResponse.of(updatedUser));
    }

    @PutMapping("/user/edit-password")
    public ResponseEntity<UserResponse> updateUserPassword(@AuthenticationPrincipal User user, @RequestBody EditUserPasswordDto editUserPasswordDto) {
        User updatedUser = userService.updateUserPassword(user.getId(), editUserPasswordDto);
        return ResponseEntity.ok(UserResponse.of(updatedUser));
    }

    @GetMapping("/search")
    public List<User> buscar(@RequestParam(value="search", required = false) String search) {
        log.info(search);
        List<SearchCriteria> params = new ArrayList<SearchCriteria>();
        if (search != null) {
            Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
            Matcher matcher = pattern.matcher(search + ",");
            while (matcher.find()) {
                log.info(matcher.group(1));
                log.info(matcher.group(2));
                log.info(matcher.group(3));
                params.add(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)));
            }
        }

        return userService.search(params);


    }


}