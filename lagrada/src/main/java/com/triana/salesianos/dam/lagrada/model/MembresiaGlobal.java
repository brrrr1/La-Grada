package com.triana.salesianos.dam.lagrada.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("GLOBAL")
public class MembresiaGlobal extends Membresia {

}