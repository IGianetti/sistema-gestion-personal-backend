package com.igianetti.sgp.infraestructure.web.dto;

import com.igianetti.sgp.domain.model.EstadoEmpleado;

import java.time.LocalDate;

/**
 * Record inmutable para la respuesta de datos de Empleado.
 */
public record EmpleadoResponse(
        Long id,
        String nombre,
        String apellido,
        Integer edad,
        LocalDate fechaIngreso,
        EstadoEmpleado estado,
        String fichaCensal,
        String tipoPersonalNombre
) {
    // Datos sensibles (DNI, Salario) excluidos intencionalmente.
}