package com.igianetti.sgp.infraestructure.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true) // Permite asegurar métodos con @PreAuthorize
public class SecurityConfig {

    // 1. Define el codificador de contraseñas (BCrypt, estándar de la industria)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 2. Define el gestor de autenticación (necesario para el flujo de JWT)
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // 3. Configuración de CORS (para permitir peticiones desde React)
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Orígenes permitidos (localhost para desarrollo React)
        config.setAllowedOrigins(List.of("http://localhost:3000", "http://gestion-hospital.local:3000"));

        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        source.registerCorsConfiguration("/**", config); // Aplica CORS a todos los endpoints
        return new CorsFilter(source);
    }

    // 4. Cadena de Filtros de Seguridad
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Deshabilitar CSRF (Crucial para APIs sin estado)
                .csrf(AbstractHttpConfigurer::disable)

                // Gestión de Sesión: Sin estado (STATELESS) para usar JWT
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Definición de Reglas de Autorización
                .authorizeHttpRequests(authorize -> authorize
                        // Endpoint de Autenticación (Permitir acceso sin token)
                        .requestMatchers("/api/v1/auth/**").permitAll()

                        // Los demás endpoints requieren autenticación
                        .anyRequest().authenticated()
                );

        // Aquí se insertará el Filtro de JWT en la siguiente fase

        return http.build();
    }
}
