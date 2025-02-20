package com.triana.salesianos.dam.lagrada.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
@DiscriminatorValue("EQUIPO")
public class MembresiaEquipo extends Membresia {

    @ManyToOne
    private Equipo equipo;
    private int membresiasRestantes;
}
