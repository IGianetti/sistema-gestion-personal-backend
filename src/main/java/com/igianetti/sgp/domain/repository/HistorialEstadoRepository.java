package com.igianetti.sgp.domain.repository;


import com.igianetti.sgp.domain.model.HistorialEstado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistorialEstadoRepository extends JpaRepository<HistorialEstado, Long> {

    // Aquí podríamos añadir métodos para obtener el historial de un empleado específico.
    // List<HistorialEstado> findByEmpleadoId(Long empleadoId);
}