package com.triana.salesianos.dam.lagrada.controller;

import com.triana.salesianos.dam.lagrada.service.MembresiaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class MembresiaController {

    private final MembresiaService membresiaService;

    // hacer el controlador, documentar los métodos con swagger, y añadir restricción de rol para los endpoint en el security config
    //Métodos para editar precio de una membresia, obtener todas, obtener una

    //Metodo para editar precio de una membresia
    @PutMapping("/membresia/edit/{id}/{precio}")
    public void editMembresiaPrice(UUID id, Double precio) {
        membresiaService.editMembresiaPrice(id, precio);
    }


    //Metodo para obtener todas las membresias
    @GetMapping("/membresia/all")
    public void findAll() {
        membresiaService.findAll();
    }

    //Metodo para obtener una membresia
    @GetMapping("/membresia/{id}")
    public void findById(UUID id) {
        membresiaService.findById(id);
    }


}
