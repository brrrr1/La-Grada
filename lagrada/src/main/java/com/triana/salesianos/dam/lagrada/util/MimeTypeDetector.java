package com.triana.salesianos.dam.lagrada.util;

import org.springframework.core.io.Resource;


public interface MimeTypeDetector {

    String getMimeType(Resource resource);

}