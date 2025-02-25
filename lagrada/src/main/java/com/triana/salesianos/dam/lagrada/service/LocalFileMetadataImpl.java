package com.triana.salesianos.dam.lagrada.service;

import com.triana.salesianos.dam.lagrada.model.AbstractFileMetadata;
import com.triana.salesianos.dam.lagrada.model.FileMetadata;
import lombok.experimental.SuperBuilder;


@SuperBuilder
public class LocalFileMetadataImpl extends AbstractFileMetadata {

    public static FileMetadata of(String filename) {
        return LocalFileMetadataImpl.builder()
                .id(filename)
                .filename(filename)
                .build();
    }

}