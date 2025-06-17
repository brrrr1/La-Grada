package com.triana.salesianos.dam.lagrada.config;

import com.triana.salesianos.dam.lagrada.security.exceptionhandling.AuthenticationExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
public class SecurityExceptionConfig {

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new AuthenticationExceptionHandler();
    }
} 