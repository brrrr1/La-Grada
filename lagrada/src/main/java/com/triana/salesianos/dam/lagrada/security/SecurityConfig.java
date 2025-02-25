package com.triana.salesianos.dam.lagrada.security;

import com.triana.salesianos.dam.lagrada.security.exceptionhandling.JwtAccessDeniedHandler;
import com.triana.salesianos.dam.lagrada.security.exceptionhandling.JwtAuthenticationEntryPoint;
import com.triana.salesianos.dam.lagrada.security.jwt.access.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;
    private final JwtAccessDeniedHandler accessDeniedHandler;

    @Bean
    AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {

        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        AuthenticationManager authenticationManager =
                authenticationManagerBuilder.authenticationProvider(authenticationProvider())
                        .build();

        return authenticationManager;
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider p = new DaoAuthenticationProvider();

        p.setUserDetailsService(userDetailsService);
        p.setPasswordEncoder(passwordEncoder);
        return p;

    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf -> csrf.disable());
        http.cors(Customizer.withDefaults());
        http.sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.exceptionHandling(excepz -> excepz
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
        );
        /*http.authorizeHttpRequests(authz -> authz
                .requestMatchers(HttpMethod.POST, "/auth/register", "/auth/login", "/auth/refresh/token","activate/account", "/error").permitAll()
                .requestMatchers("/me/admin").hasRole("ADMIN")
                .requestMatchers("/me", "/auth/logout").authenticated()
                .requestMatchers("/h2-console/**").permitAll()
                .anyRequest().authenticated());*/

        http.authorizeHttpRequests(authz -> authz
                .requestMatchers(HttpMethod.POST, "/auth/register", "/auth/login", "/auth/refresh/token", "/activate/account", "/error").permitAll()
                .requestMatchers("/me/admin").hasRole("ADMIN") // Solo los admin pueden acceder
                .requestMatchers("/me", "/auth/logout").authenticated() // Solo los usuarios autenticados pueden acceder
                .requestMatchers("/h2-console/**").permitAll() // Permite el acceso al H2 console
                .requestMatchers(HttpMethod.POST, "/user/choose-favorite-team/{teamId}", "/user/change-favorite-team/{newTeamId}", "/user/buy-ticket/{eventId}")
                .authenticated() // Solo los usuarios autenticados pueden comprar entradas, cambiar equipo favorito
                .requestMatchers("/user/edit-info", "/user/edit-password").authenticated() // Usuarios autenticados pueden editar info y contraseña (PUT)
                .requestMatchers("/user/favorite-team-events", "/eventos-futuros", "/eventos-pasados")
                .authenticated() // Solo los usuarios autenticados pueden ver estos eventos
                .requestMatchers("/equipo/**").hasRole("ADMIN") // Solo los administradores pueden crear, editar y borrar equipos (POST, PUT, DELETE)
                .requestMatchers(HttpMethod.GET, "/equipo/**").permitAll() // Todos pueden ver los equipos (GET)
                .requestMatchers("/evento/**").hasRole("ADMIN") // Solo los admin pueden crear, editar y borrar eventos (POST, PUT, DELETE)
                .requestMatchers(HttpMethod.GET, "/evento/proximos").permitAll() // Todos pueden ver los eventos futuros (GET)
                .anyRequest().authenticated()); // El resto de las peticiones requieren autenticación





        //Provisional para que se pueda acceder a t0d0
        /*http.authorizeHttpRequests(authz -> authz
                .anyRequest().permitAll());*/




        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);


        http.headers(headers ->
                headers.frameOptions(frameOptions -> frameOptions.disable()));

        return http.build();
    }






}
