package com.triana.salesianos.dam.lagrada.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "evento")
public class Evento {

    @Id @GeneratedValue
    private UUID id;

    private TipoEvento tipo;
    private String nombre;
    private String descripcion;
    private LocalDateTime fechaYHora;
    private Equipo equipo1;
    private Equipo equipo2;
    private int entradasRestantes;
    private int entradasTotales;
    private Double precio;


}
