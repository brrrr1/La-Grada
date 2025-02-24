package com.triana.salesianos.dam.lagrada.controller;

import com.triana.salesianos.dam.lagrada.dto.ActivateAccountRequest;
import com.triana.salesianos.dam.lagrada.dto.CreateUserRequest;
import com.triana.salesianos.dam.lagrada.dto.LoginRequest;
import com.triana.salesianos.dam.lagrada.dto.UserResponse;
import com.triana.salesianos.dam.lagrada.model.Evento;
import com.triana.salesianos.dam.lagrada.model.User;
import com.triana.salesianos.dam.lagrada.security.jwt.access.JwtService;
import com.triana.salesianos.dam.lagrada.security.jwt.refresh.RefreshToken;
import com.triana.salesianos.dam.lagrada.security.jwt.refresh.RefreshTokenRequest;
import com.triana.salesianos.dam.lagrada.security.jwt.refresh.RefreshTokenService;
import com.triana.salesianos.dam.lagrada.service.EventoService;
import com.triana.salesianos.dam.lagrada.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
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

    @PostMapping("/user/{userId}/buy-membership/{membershipId}")
    public ResponseEntity<?> buyMembership(@PathVariable UUID userId, @PathVariable UUID membershipId) {
        userService.buyMembership(userId, membershipId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/user/{userId}/choose-favorite-team/{teamId}")
    public ResponseEntity<?> chooseFavoriteTeam(@PathVariable UUID userId, @PathVariable UUID teamId) {
        userService.chooseFavoriteTeam(userId, teamId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/user/{userId}/change-favorite-team/{newTeamId}")
    public ResponseEntity<?> changeFavoriteTeam(@PathVariable UUID userId, @PathVariable UUID newTeamId) {
        userService.changeFavoriteTeam(userId, newTeamId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/user/{userId}/buy-ticket/{eventId}")
    public ResponseEntity<?> buyTicket(@PathVariable UUID userId, @PathVariable UUID eventId) {
        userService.buyTicket(userId, eventId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/user/{userId}/favorite-team-events")
    public ResponseEntity<List<Evento>> getNext4EventsOfFavoriteTeam(@PathVariable UUID userId) {
        List<Evento> eventos = userService.getNext4EventsOfFavoriteTeam(userId);
        return ResponseEntity.ok(eventos);
    }







}
