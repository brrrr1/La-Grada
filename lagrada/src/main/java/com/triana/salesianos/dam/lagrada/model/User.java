package com.triana.salesianos.dam.lagrada.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "usuario")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nombre;
    private String apellidos;

    @NaturalId
    @Column(unique = true, updatable = false)
    private String username;

    private String correo;
    private String password;

    @ManyToOne
    @JoinColumn(name = "equipo_favorito_id")
    private Equipo equipoFavorito;

    @ManyToOne(optional = true)
    @JoinColumn(name = "membresia_id")
    private Membresia membresia;

    @OneToMany(mappedBy = "usuario")
    private List<Entrada> entradas;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<UserRole> roles;

    @Builder.Default
    private boolean enabled = false;

    private String activationToken;

    @Builder.Default
    private Instant createdAt = Instant.now();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> "ROLE_" + role)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }
}
