package com.igianetti.sgp.infraestructure.controller;

import com.igianetti.sgp.application.service.AuthService;
import com.igianetti.sgp.application.service.dto.AuthRequest;
import com.igianetti.sgp.application.service.dto.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Endpoint para manejar la solicitud de inicio de sesión.
     * @param request El objeto AuthRequest (username y password)
     * @return ResponseEntity con el AuthResponse (token JWT)
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        // La lógica de autenticación y generación de token se maneja en el servicio.
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}
