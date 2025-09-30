package com.igianetti.sgp.domain.repository;


import com.igianetti.sgp.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Metodo esencial para Spring Security: buscar por username
    Optional<Usuario> findByUsername(String username);
}
