package com.triana.salesianos.dam.lagrada.service;

import com.triana.salesianos.dam.lagrada.dto.CreateUserRequest;
import com.triana.salesianos.dam.lagrada.dto.EditUserInfoDto;
import com.triana.salesianos.dam.lagrada.dto.EditUserPasswordDto;
import com.triana.salesianos.dam.lagrada.dto.GetEntradaDto;
import com.triana.salesianos.dam.lagrada.error.ActivationExpiredException;
import com.triana.salesianos.dam.lagrada.model.*;
import com.triana.salesianos.dam.lagrada.query.UserSpecificationBuilder;
import com.triana.salesianos.dam.lagrada.repo.*;
import com.triana.salesianos.dam.lagrada.util.MailService;
import com.triana.salesianos.dam.lagrada.util.SearchCriteria;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalDateTime;
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
                .nombre(createUserRequest.nombre())
                .apellidos(createUserRequest.apellidos())
                .username(createUserRequest.username())
                .correo(createUserRequest.correo())
                .password(passwordEncoder.encode(createUserRequest.password()))
                .roles(Set.of(UserRole.USER))
                .activationToken(generateRandomActivationCode())
                .build();

        if (createUserRequest.equipoFavoritoId() != null) {
            Equipo equipo = equipoRepository.findById(createUserRequest.equipoFavoritoId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el equipo con id " + createUserRequest.equipoFavoritoId()));
            user.setEquipoFavorito(equipo);
        }

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
    /*public void buyMembership(UUID userId, UUID membershipId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el usuario con id " + userId));
        user.setMembresia(membresiaRepository.findById(membershipId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la membresía con id " + membershipId)));
        userRepository.save(user);
    }*/

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
        
        // Enviar email de confirmación de compra con QR
        try {
            mailService.sendTicketPurchaseEmailWithQR(user, evento);
        } catch (Exception e) {
            // Puedes loguear el error si lo deseas
        }
    }

    /*@Transactional
    public List<Evento> getNext4EventsOfFavoriteTeam(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow();

        // Hibernate podrá inicializar correctamente el equipo favorito
        Equipo equipo = user.getEquipoFavorito();

        return eventoRepository.findNext4EventsByTeam(equipo, PageRequest.of(0, 4));
    }*/


    public List<Evento> getNext4EventsOfFavoriteTeam(UUID userId) {
    User user = userRepository.findById(userId).orElseThrow();
    return eventoRepository.findNext4EventsByTeam(user.getEquipoFavorito(), PageRequest.of(0, 4));
}


    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el usuario con nombre de usuario " + username));
    }

    @Transactional
    public User getUserWithRoles(UUID id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    /*@Transactional
    public List<Evento> getEventosPorUsuario(UUID usuarioId) {
        // Encuentra las entradas del usuario
        List<Entrada> entradas = entradaRepository.findByUsuarioId(usuarioId);

        // Extrae los eventos de esas entradas
        return entradas.stream()
                .map(Entrada::getEvento) // Obtiene el evento asociado a cada entrada
                .collect(Collectors.toList());
    }*/

    public List<Evento> obtenerEventosFuturosUsuario(User usuario) {
        LocalDateTime ahora = LocalDateTime.now();
        // Obtener los eventos a los que el usuario ha comprado entradas y cuya fecha es posterior a ahora
        List<Entrada> entradasFuturas = entradaRepository.findByUsuarioAndEventoFechaYHoraAfter(usuario, ahora);

        // Retornar solo los eventos
        return entradasFuturas.stream()
                .map(Entrada::getEvento)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Evento> obtenerEventosPasadosUsuario(User usuario) {
        LocalDateTime ahora = LocalDateTime.now();
        // Obtener los eventos a los que el usuario ha comprado entradas y cuya fecha es anterior a ahora
        List<Entrada> entradasPasadas = entradaRepository.findByUsuarioAndEventoFechaYHoraBefore(usuario, ahora);

        // Retornar solo los eventos
        return entradasPasadas.stream()
                .map(Entrada::getEvento)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<GetEntradaDto> obtenerEntradasFuturasUsuario(User usuario) {
        LocalDateTime ahora = LocalDateTime.now();
        List<Entrada> entradasFuturas = entradaRepository.findByUsuarioAndEventoFechaYHoraAfter(usuario, ahora);
        return entradasFuturas.stream()
                .map(e -> GetEntradaDto.from(e, mailService))
                .toList();
    }

    public List<GetEntradaDto> obtenerEntradasPasadasUsuario(User usuario) {
        LocalDateTime ahora = LocalDateTime.now();
        List<Entrada> entradasPasadas = entradaRepository.findByUsuarioAndEventoFechaYHoraBefore(usuario, ahora);
        return entradasPasadas.stream()
                .map(e -> GetEntradaDto.from(e, mailService))
                .toList();
    }

    public User updateUserInfo(UUID userId, EditUserInfoDto editUserInfoDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        // Actualizar los campos del usuario
        user.setNombre(editUserInfoDto.nombre());
        user.setApellidos(editUserInfoDto.apellidos());
        user.setCorreo(editUserInfoDto.correo());

        // Actualizar el equipo favorito si se proporciona un ID
        if (editUserInfoDto.equipoFavoritoId() != null) {
            Equipo equipo = equipoRepository.findById(editUserInfoDto.equipoFavoritoId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Equipo no encontrado"));
            user.setEquipoFavorito(equipo);
        }

        return userRepository.save(user);
    }

    public User updateUserPassword(UUID userId, EditUserPasswordDto editUserPasswordDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        // Verificar que la contraseña antigua es correcta
        if (!passwordEncoder.matches(editUserPasswordDto.oldPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Contraseña antigua incorrecta");
        }

        // Actualizar la contraseña
        user.setPassword(passwordEncoder.encode(editUserPasswordDto.newPassword()));

        return userRepository.save(user);
    }


    public List<User> search(List<SearchCriteria> searchCriteriaList) {

        UserSpecificationBuilder userSpecificationBuilder
                = new UserSpecificationBuilder(searchCriteriaList);

        Specification<User> where = userSpecificationBuilder.build();

        return userRepository.findAll(where);
    }

    public void removeFavoriteTeam(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el usuario con id " + userId));
        user.setEquipoFavorito(null);
        userRepository.save(user);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByCorreo(String correo) {
        return userRepository.existsByCorreo(correo);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User disableUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        
        if (user.getRoles().contains(UserRole.ADMIN)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No se puede deshabilitar un usuario administrador");
        }
        
        user.setEnabled(false);
        return userRepository.save(user);
    }

    @Transactional
    public User enableUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        
        user.setEnabled(true);
        return userRepository.save(user);
    }

}
