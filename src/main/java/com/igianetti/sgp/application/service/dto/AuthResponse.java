package com.igianetti.sgp.application.service.dto;

// Un record inmutable para devolver el token.
public record AuthResponse(
        String token
) {}
