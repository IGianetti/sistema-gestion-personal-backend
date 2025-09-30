package com.igianetti.sgp.domain.repository;

import com.igianetti.sgp.domain.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

// Heredamos de JpaRepository (CRUD básico) y RevisionRepository (para Envers).
public interface EmpleadoRepository extends JpaRepository<Empleado, Long>,
        RevisionRepository<Empleado, Long, Integer> {

    /**
     * Ejemplo de metodo de consulta personalizado:
     * Buscar empleado por su ficha censal única.
     */
    Empleado findByFichaCensal(String fichaCensal);
}
