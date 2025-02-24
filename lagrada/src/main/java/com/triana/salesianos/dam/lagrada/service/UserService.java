package com.triana.salesianos.dam.lagrada.service;

import com.triana.salesianos.dam.lagrada.dto.CreateUserRequest;
import com.triana.salesianos.dam.lagrada.error.ActivationExpiredException;
import com.triana.salesianos.dam.lagrada.model.*;
import com.triana.salesianos.dam.lagrada.repo.*;
import com.triana.salesianos.dam.lagrada.util.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Log
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MembresiaRepository membresiaRepository;
    private final EquipoRepository equipoRepository;
    private final EventoRepository eventoRepository;
    private final EntradaRepository entradaRepository;
    private final MailService mailService;

    @Value("${activation.duration}")
    private int activationDuration;

    public User createUser(CreateUserRequest createUserRequest) {
        User user = User.builder()
                .username(createUserRequest.username())
                .password(passwordEncoder.encode(createUserRequest.password()))
                .correo(createUserRequest.correo())
                .roles(Set.of(UserRole.USER))
                .activationToken(generateRandomActivationCode())
                .build();

        log.info("Activation token %s".formatted(user.getActivationToken()));

        try {
            mailService.sendVerificationEmail(createUserRequest.correo(), user.getActivationToken());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al enviar el email de activación");
        }

        return userRepository.save(user);
    }

    public String generateRandomActivationCode() {
        return UUID.randomUUID().toString();
    }

    public User activateAccount(String token) {
        return userRepository.findByActivationToken(token)
                .filter(user -> ChronoUnit.MINUTES.between(Instant.now(), user.getCreatedAt()) - activationDuration < 0)
                .map(user -> {
                    user.setEnabled(true);
                    user.setActivationToken(null);
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new ActivationExpiredException("El código de activación no existe o ha caducado"));
    }

    // Metodo para comprar una membresía
    public void buyMembership(UUID userId, UUID membershipId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el usuario con id " + userId));
        user.setMembresia(membresiaRepository.findById(membershipId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la membresía con id " + membershipId)));
        userRepository.save(user);
    }

    // Metodo para elegir un equipo favorito
    public void chooseFavoriteTeam(UUID userId, UUID teamId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el usuario con id " + userId));
        Equipo equipo = equipoRepository.findById(teamId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el equipo con id " + teamId));

        user.setEquipoFavorito(equipo);
        userRepository.save(user);
    }

    // Metodo para cambiar el equipo favorito
    public void changeFavoriteTeam(UUID userId, UUID newTeamId) {
        chooseFavoriteTeam(userId, newTeamId);
    }

    // Metodo para comprar una entrada para un evento
    public void buyTicket(UUID userId, UUID eventId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el usuario con id " + userId));
        Evento evento = eventoRepository.findById(eventId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el evento con id " + eventId));

        if (evento.getEntradasRestantes() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No hay entradas disponibles para este evento");
        }

        Entrada entrada = Entrada.builder()
                .usuario(user)
                .evento(evento)
                .build();

        entradaRepository.save(entrada);
        evento.setEntradasRestantes(evento.getEntradasRestantes() - 1);
        eventoRepository.save(evento);
    }

    public List<Evento> getNext4EventsOfFavoriteTeam(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        // Obtenemos los próximos 4 eventos del equipo favorito del usuario
        return eventoRepository.findNext4EventsByTeam(user.getEquipoFavorito(), PageRequest.of(0, 4));
    }








}
