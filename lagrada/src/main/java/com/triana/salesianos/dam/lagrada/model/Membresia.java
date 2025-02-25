package com.triana.salesianos.dam.lagrada.model;

import jakarta.persistence.*;
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
@Table(name = "membresia")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_membresia", discriminatorType = DiscriminatorType.STRING)
public class Membresia {

    @Id
    @GeneratedValue
    private UUID id;

    private String nombre;
    private Double precio;
    private String descripcion;

    @Enumerated(EnumType.STRING)
    private TipoMembresia tipo;

    @OneToMany(mappedBy = "membresia")
    @ToString.Exclude
    private List<User> usuarios;
}
