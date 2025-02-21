package com.triana.salesianos.dam.lagrada.service;

import com.triana.salesianos.dam.lagrada.model.Membresia;
import com.triana.salesianos.dam.lagrada.repo.MembresiaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MembresiaService {

    private final MembresiaRepository membresiaRepository;

    //Métodos para editar precio de una membresia, obtener todas, obtener una

    //Metodo para editar precio de una membresia
    public void editMembresiaPrice(UUID id, Double precio) {
        Membresia membresia = membresiaRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la membresia con id " + id));
        membresia.setPrecio(precio);
        membresiaRepository.save(membresia);
    }

    //Metodo para obtener todas las membresias
    public List<Membresia> findAll() {
        return membresiaRepository.findAll();
    }

    //Metodo para obtener una membresia
    public Membresia findById(UUID id) {
        return membresiaRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la membresia con id " + id));
    }


}
