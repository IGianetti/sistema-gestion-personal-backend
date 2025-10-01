package com.igianetti.sgp.infraestructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {

    // Usaremos Algorithm de Auth0
    private Algorithm algorithm;

    // Inyectamos las propiedades y creamos el objeto Algorithm
    public JwtService(
            @Value("${application.security.jwt.secret-key}") String secretKey
    ) {
        // Auth0 usa una instancia de Algorithm para firmar y verificar.
        this.algorithm = Algorithm.HMAC256(secretKey);
    }

    // Tiempo de vida del token (inyectado)
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    // --- Métodos de Generación ---

    public String generateToken(UserDetails userDetails) {
        Instant now = Instant.now();
        Instant expiryDate = now.plusMillis(jwtExpiration);

        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(expiryDate))
                // Auth0 usa el objeto Algorithm ya creado
                .sign(algorithm);
    }

    // --- Métodos de Extracción y Validación ---

    public String extractUsername(String token) {
        try {
            // 1. Crear el verificador
            JWTVerifier verifier = JWT.require(algorithm).build();

            // 2. Decodificar y verificar
            DecodedJWT jwt = verifier.verify(token);

            // 3. Extraer el subject (username)
            return jwt.getSubject();

        } catch (JWTVerificationException exception) {
            // El token es inválido o expiró
            return null;
        }
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);

        // Simplemente verificamos el nombre de usuario.
        // La expiración y validez de la firma ya fueron chequeadas en extractUsername.
        return (username != null) && username.equals(userDetails.getUsername());
    }
}
