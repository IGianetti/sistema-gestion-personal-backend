package com.igianetti.sgp.application.service;

import com.igianetti.sgp.application.service.dto.AuthRequest;
import com.igianetti.sgp.application.service.dto.AuthResponse;
import com.igianetti.sgp.infraestructure.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UsuarioDetailsService userDetailsService;
    private final JwtService jwtService;

    public AuthResponse login(AuthRequest request) {
        // 1. Intenta autenticar al usuario
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(), // Usar el metodo accessor del Record
                        request.password()  // Usar el metodo accessor del Record
                )
        );

        // 2. Si la autenticación es exitosa, carga los detalles del usuario
        UserDetails user = userDetailsService.loadUserByUsername(request.username());

        // 3. Genera el JWT
        String token = jwtService.generateToken(user);

        // 4. Retorna el token al cliente (creación directa del Record)
        return new AuthResponse(token); // creación directa del Record


    }
}