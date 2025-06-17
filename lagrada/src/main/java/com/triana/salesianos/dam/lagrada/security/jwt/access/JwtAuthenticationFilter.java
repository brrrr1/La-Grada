package com.triana.salesianos.dam.lagrada.security.jwt.access;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.triana.salesianos.dam.lagrada.model.User;
import com.triana.salesianos.dam.lagrada.repo.UserRepository;
import com.triana.salesianos.dam.lagrada.security.exceptionhandling.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    //private final UserService userService;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final ObjectMapper objectMapper;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = getJwtAccessTokenFromRequest(request);

            if (StringUtils.hasText(token) && jwtService.validateAccessToken(token)) {
                UUID id = jwtService.getUserIdFromAccessToken(token);
                Optional<User> result = userRepository.findById(id);

                if (result.isPresent()) {
                    User user = result.get();
                    if (!user.isEnabled()) {
                        handleAuthenticationError(response, "Tu cuenta está deshabilitada. Por favor, contacta con el administrador.");
                        return;
                    }

                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            user,
                            null,
                            user.getAuthorities()
                    );

                    authenticationToken.setDetails(new WebAuthenticationDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        } catch (JwtException ex) {
            handleAuthenticationError(response, ex.getMessage());
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void handleAuthenticationError(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Map<String, Object> body = new HashMap<>();
        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        body.put("error", "Unauthorized");
        body.put("message", message);

        objectMapper.writeValue(response.getOutputStream(), body);
    }

    private String getJwtAccessTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}