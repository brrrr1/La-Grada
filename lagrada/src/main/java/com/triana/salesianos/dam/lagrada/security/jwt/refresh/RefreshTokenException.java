package com.triana.salesianos.dam.lagrada.security.jwt.refresh;

import com.triana.salesianos.dam.lagrada.security.exceptionhandling.JwtException;

public class RefreshTokenException extends JwtException {
    public RefreshTokenException(String s) {
        super(s);
    }
}
