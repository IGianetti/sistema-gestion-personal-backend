package com.igianetti.sgp.domain.repository;




import com.igianetti.sgp.domain.model.TipoPersonal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

public interface TipoPersonalRepository extends JpaRepository<TipoPersonal, Integer>,
        RevisionRepository<TipoPersonal, Integer, Integer> {

    /**
     * Buscar un tipo de personal por nombre (ej: "Médico").
     */
    TipoPersonal findByNombre(String nombre);
}
