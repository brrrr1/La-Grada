package com.triana.salesianos.dam.lagrada.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "usuario")
public class User {

    @Id @GeneratedValue
    private UUID id;

    private String nombre;
    private String apellidos;
    private String correo;
    private String password;
    private Equipo equipoFavorito;
    private Membresia membresia;
    private List<Evento> eventos;
    private UserRole role;


}
