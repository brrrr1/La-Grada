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

        /*http.authorizeHttpRequests(authz -> authz
                .requestMatchers(HttpMethod.GET, "/evento/proximos").permitAll() // Permitir acceso público a eventos futuros
                .requestMatchers(HttpMethod.GET, "/equipo/**").permitAll() // Permitir acceso público a equipos (todos y por ID)
                .requestMatchers(HttpMethod.POST, "/auth/register", "/auth/login", "/auth/refresh/token", "/activate/account", "/error").permitAll()
                .requestMatchers("/me/admin", "/search").hasRole("ADMIN")
                .requestMatchers("/me", "/auth/logout").authenticated()
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/user/choose-favorite-team/{teamId}", "/user/change-favorite-team/{newTeamId}", "/user/buy-ticket/{eventId}")
                .authenticated()
                .requestMatchers("/user/edit-info", "/user/edit-password").authenticated()
                .requestMatchers("/user/favorite-team-events", "/eventos-futuros", "/eventos-pasados").authenticated()
                .requestMatchers("/equipo/**").hasRole("ADMIN") // Solo los administradores pueden crear, editar y borrar equipos (POST, PUT, DELETE)
                .requestMatchers(HttpMethod.GET, "/equipo/**").permitAll() // Todos pueden ver los equipos (GET)
                .requestMatchers("/evento/**").hasRole("ADMIN")
                .requestMatchers("/upload/**", "/download/**").hasRole("ADMIN") // Restringir upload y download a ADMIN
                .anyRequest().authenticated());*/

        http.authorizeHttpRequests(authz -> authz
                // Permitir acceso público a Swagger UI y docs
                .requestMatchers(
                    "/swagger-ui.html",
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/api-docs/**"
                ).permitAll()
                // Permitir acceso público a descarga de imágenes
                .requestMatchers(HttpMethod.GET, "/download/**").permitAll()
                // Permitir acceso público a eventos de un equipo
                .requestMatchers(HttpMethod.GET, "/evento/equipo/**").permitAll()

                // Rutas públicas
                .requestMatchers(HttpMethod.GET, "/evento/proximos").permitAll()
                .requestMatchers(HttpMethod.GET, "/evento/proximos/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/evento/{id}").permitAll()
                .requestMatchers(HttpMethod.GET, "/evento/*").permitAll()
                .requestMatchers(HttpMethod.GET, "/equipo/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/auth/register", "/auth/login", "/auth/refresh/token", "/activate/account", "/error").permitAll()
                .requestMatchers(HttpMethod.GET, "/auth/check-username", "/auth/check-email").permitAll()
                .requestMatchers("/h2-console/**").permitAll()

                // Acceso restringido a ADMIN
                .requestMatchers("/me/admin", "/search").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/equipo/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/equipo/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/equipo/**").hasRole("ADMIN")
                .requestMatchers("/evento/**").hasRole("ADMIN")
                .requestMatchers("/upload/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/download/**").permitAll()

                // Rutas autenticadas para usuarios normales
                .requestMatchers("/me", "/auth/logout").authenticated()
                .requestMatchers(HttpMethod.POST, "/user/choose-favorite-team/**", "/user/change-favorite-team/**", "/user/buy-ticket/**").authenticated()
                .requestMatchers("/user/edit-info", "/user/edit-password").authenticated()
                .requestMatchers("/user/favorite-team-events", "/eventos-futuros", "/eventos-pasados").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/user/remove-favorite-team").authenticated()
                .requestMatchers("/user/entradas/futuras", "/user/entradas/pasadas").authenticated()

                // Cualquier otra ruta requiere autenticación
                .anyRequest().authenticated()
        );

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http.headers(headers ->
                headers.frameOptions(frameOptions -> frameOptions.disable()));

        return http.build();
    }
}
