package com.triana.salesianos.dam.lagrada.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;
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

    @Id
    @GeneratedValue
    private UUID id;

    private String nombre;
    private String descripcion;
    private LocalDateTime fechaYHora;

    @ManyToOne
    @JoinColumn(name = "equipo1_id")
    private Equipo equipo1;

    @ManyToOne
    @JoinColumn(name = "equipo2_id")
    private Equipo equipo2;

    private int entradasRestantes;
    private int entradasTotales;
    private Double precio;

    @Enumerated(EnumType.STRING)
    private TipoEvento tipoEvento; // Tipo de evento: COTIDIANO, IMPORTANTE, FINAL

    // Relación con entradas en lugar de ManyToMany con usuarios
    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Entrada> entradas;
}
