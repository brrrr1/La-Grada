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
    public Equipo save(CreateEquipoDto createEquipoDto, MultipartFile file, MultipartFile file2) {
        FileMetadata fileMetadata = storageService.store(file);
        FileMetadata fileMetadata2 = storageService.store(file2);

        Equipo equipo = equipoRepository.save(
                Equipo.builder()
                        .nombre(createEquipoDto.getNombre())
                        .fotoEscudo(fileMetadata.getFilename())
                        .fotoFondo(fileMetadata2.getFilename())
                        .build()
        );
        return equipo;
    }

    @Transactional
    public Equipo update(UUID id, CreateEquipoDto updateEquipoDto, MultipartFile file, MultipartFile file2) {
        Equipo equipo = equipoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipo no encontrado"));

        // Actualizar nombre
        equipo.setNombre(updateEquipoDto.getNombre());

        // Actualizar imágenes solo si se proporcionan nuevas
        if (file != null && !file.isEmpty()) {
            FileMetadata fileMetadata = storageService.store(file);
            equipo.setFotoEscudo(fileMetadata.getFilename());
        }

        if (file2 != null && !file2.isEmpty()) {
            FileMetadata fileMetadata2 = storageService.store(file2);
            equipo.setFotoFondo(fileMetadata2.getFilename());
        }

        return equipoRepository.save(equipo);
    }
}