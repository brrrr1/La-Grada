package com.triana.salesianos.dam.lagrada.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("EQUIPO")
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class MembresiaEquipo extends Membresia {

    @ManyToOne
    private Equipo equipo;

    private int membresiasRestantes;
}
