package com.triana.salesianos.dam.lagrada.service;

import com.triana.salesianos.dam.lagrada.dto.CreateEquipoDto;
import com.triana.salesianos.dam.lagrada.model.Equipo;
import com.triana.salesianos.dam.lagrada.repo.EquipoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EquipoService {

    private final EquipoRepository equipoRepository;

    public List<Equipo> findAll() {
        return equipoRepository.findAll();
    }

    public Equipo findById(UUID id) {
        return equipoRepository.findById(id).orElse(null);
    }

    // Crear equipo desde DTO
    public Equipo createEquipo(CreateEquipoDto dto) {
        Equipo equipo = new Equipo();
        equipo.setNombre(dto.nombre()); // Asignar valores desde el DTO
        return equipoRepository.save(equipo);
    }
}