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
@Table(name = "entrada")
public class Entrada {

    @Id @GeneratedValue
    private UUID id;

    private Long usuarioId;
    private Long eventoId;
}
