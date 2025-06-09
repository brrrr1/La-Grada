package com.triana.salesianos.dam.lagrada.controller;

import com.triana.salesianos.dam.lagrada.dto.FileResponse;
import com.triana.salesianos.dam.lagrada.model.FileMetadata;
import com.triana.salesianos.dam.lagrada.service.StorageService;
import com.triana.salesianos.dam.lagrada.util.MimeTypeDetector;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Archivos", description = "Controlador para gestionar la subida y descarga de archivos")
public class FileController {

    private final StorageService storageService;
    private final MimeTypeDetector mimeTypeDetector;
    
    @Operation(summary = "Sube un archivo al servidor",
            description = "Permite subir un archivo al servidor y devuelve la información del archivo subido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Archivo subido correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FileResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                        "id": "123e4567-e89b-12d3-a456-426614174000",
                                        "name": "imagen.jpg",
                                        "size": 1024,
                                        "type": "image/jpeg",
                                        "uri": "http://localhost:8080/download/123e4567-e89b-12d3-a456-426614174000"
                                    }
                                    """))),
            @ApiResponse(responseCode = "400",
                    description = "Error al subir el archivo",
                    content = @Content)
    })
    @PostMapping("/upload")
    public ResponseEntity<?> upload(
            @Parameter(description = "Archivo a subir", required = true)
            @RequestPart("file") MultipartFile file) {

        FileResponse response = uploadFile(file);

        return ResponseEntity.created(URI.create(response.uri())).body(response);
    }

    private FileResponse uploadFile(MultipartFile multipartFile) {
        FileMetadata fileMetadata = storageService.store(multipartFile);

        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(fileMetadata.getId())
                .toUriString();

        fileMetadata.setURL(uri);

        return FileResponse.builder()
                .id(fileMetadata.getId())
                .name(fileMetadata.getFilename())
                .size(multipartFile.getSize())
                .type(multipartFile.getContentType())
                .uri(uri)
                .build();
    }

    @Operation(summary = "Descarga un archivo del servidor",
            description = "Permite descargar un archivo previamente subido usando su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Archivo descargado correctamente",
                    content = @Content(mediaType = "application/octet-stream")),
            @ApiResponse(responseCode = "404",
                    description = "Archivo no encontrado",
                    content = @Content)
    })
    @GetMapping("/download/{id:.+}")
    public ResponseEntity<Resource> getFile(
            @Parameter(description = "ID del archivo a descargar", example = "123e4567-e89b-12d3-a456-426614174000")
            @PathVariable String id) {
        Resource resource = storageService.loadAsResource(id);

        String mimeType = mimeTypeDetector.getMimeType(resource);

        return ResponseEntity.status(HttpStatus.OK)
                .header("Content-Type", mimeType)
                .body(resource);
    }

}