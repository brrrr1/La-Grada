package com.triana.salesianos.dam.lagrada.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "equipo")
public class Equipo {

    @Id
    @GeneratedValue
    private UUID id;
    private String nombre;
    private String fotoEscudo;
    private String fotoFondo;
}
