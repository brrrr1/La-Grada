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


    //METODOS USUARIO

    //Metodo para que el usuario cambie su membresia por otra. Por ejemplo, si tiene una basica cambiarla por una plus o una total
    public void changeMembresia(UUID id, Membresia membresia) {
        Membresia membresiaActual = membresiaRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la membresia con id " + id));
        membresiaActual.setTipo(membresia.getTipo());
        membresiaActual.setPrecio(membresia.getPrecio());
        membresiaActual.setNombre(membresia.getNombre());
        membresiaActual.setDescripcion(membresia.getDescripcion());
        membresiaRepository.save(membresiaActual);
    }

    //Metodo para que el usuario cancele su membresia
    public void cancelMembresia(UUID id) {
        Membresia membresia = membresiaRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe la membresia con id " + id));
        membresiaRepository.delete(membresia);
    }

    



}
