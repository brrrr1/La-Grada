package com.triana.salesianos.dam.lagrada.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("GLOBAL")
@Getter
@Setter
@ToString(callSuper = true)
@AllArgsConstructor
public class MembresiaGlobal extends Membresia {
    // No es necesario definir un constructor aquí si usas @NoArgsConstructor de Lombok
}
