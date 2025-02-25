package com.triana.salesianos.dam.lagrada.util;

public record SearchCriteria(
        String key,
        String operation,
        Object value
) {
}