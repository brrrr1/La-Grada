package com.triana.salesianos.dam.lagrada.service;

import com.triana.salesianos.dam.lagrada.dto.CreateUserRequest;
import com.triana.salesianos.dam.lagrada.error.ActivationExpiredException;
import com.triana.salesianos.dam.lagrada.model.User;
import com.triana.salesianos.dam.lagrada.model.UserRole;
import com.triana.salesianos.dam.lagrada.repo.MembresiaRepository;
import com.triana.salesianos.dam.lagrada.repo.UserRepository;
import com.triana.salesianos.dam.lagrada.util.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    //private final SendGridMailSender mailSender;
    //private final ResendMailSender mailSender;

    private final MembresiaRepository membresiaRepository;

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

        try {
            mailService.sendVerificationEmail(createUserRequest.correo(), user.getActivationToken());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Error al enviar el email de activación");
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

    //Metodo para comprar una membresia
    public void buyMembership(UUID userId, UUID membershipId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el usuario con id " + userId));
        user.setMembresia(membresiaRepository.findById(membershipId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la membresia con id " + membershipId)));
        userRepository.save(user);
    }



}