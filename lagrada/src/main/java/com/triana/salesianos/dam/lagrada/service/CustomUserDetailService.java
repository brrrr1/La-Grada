package com.triana.salesianos.dam.lagrada.service;

import com.triana.salesianos.dam.lagrada.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findFirstByUsername(username)
                .map(user -> {
                    if (!user.isEnabled()) {
                        throw new DisabledException("Tu cuenta está deshabilitada. Por favor, contacta con el administrador.");
                    }
                    return user;
                })
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
    }
}