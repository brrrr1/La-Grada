package com.triana.salesianos.dam.lagrada.service;

import com.triana.salesianos.dam.lagrada.service.StorageService;
import com.triana.salesianos.dam.lagrada.dto.CreateEquipoDto;
import com.triana.salesianos.dam.lagrada.model.Equipo;
import com.triana.salesianos.dam.lagrada.model.FileMetadata;
import com.triana.salesianos.dam.lagrada.repo.EquipoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EquipoService {

    private final StorageService storageService;

    private final EquipoRepository equipoRepository;

    public List<Equipo> findAll() {
        return equipoRepository.findAll();
    }

    public Equipo findById(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }
        return equipoRepository.findById(id).orElse(null);
    }

    // Crear equipo desde DTO
    public Equipo createEquipo(CreateEquipoDto dto) {
        Equipo equipo = new Equipo();
        equipo.setNombre(dto.getNombre()); // Asignar valores desde el DTO
        return equipoRepository.save(equipo);
    }

    @Transactional
    public Equipo save(CreateEquipoDto createEquipoDto, MultipartFile file) {
        FileMetadata fileMetadata = storageService.store(file);

        Equipo equipo = equipoRepository.save(
                Equipo.builder()
                        .nombre(createEquipoDto.getNombre())
                        .fotoEscudo(fileMetadata.getFilename())
                        .build()
        );
        return equipo;
    }
}