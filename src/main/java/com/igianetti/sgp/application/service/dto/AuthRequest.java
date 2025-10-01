package com.igianetti.sgp.application.service.dto;

// Un record inmutable que solo requiere los campos para el login.
// No necesita Lombok, constructores, getters, ni setters.
public record AuthRequest(
        String username,
        String password
) {}