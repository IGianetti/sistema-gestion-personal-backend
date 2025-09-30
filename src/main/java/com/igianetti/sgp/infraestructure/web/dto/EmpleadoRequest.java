package com.igianetti.sgp.infraestructure.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

/**
 * Record inmutable para la creación y actualización de datos de Empleado.
 */
public record EmpleadoRequest(
        @NotBlank(message = "El nombre es obligatorio")
        String nombre,

        @NotBlank(message = "El apellido es obligatorio")
        String apellido,

        Integer edad,

        @NotNull(message = "La fecha de ingreso es obligatoria")
        @PastOrPresent(message = "La fecha de ingreso no puede ser futura")
        LocalDate fechaIngreso,

        @NotBlank(message = "La ficha censal es obligatoria")
        String fichaCensal,

        @NotBlank(message = "El DNI es obligatorio")
        String dni,

        @NotBlank(message = "El salario es obligatorio")
        String salario,

        String direccion,

        @NotNull(message = "El tipo de personal es obligatorio")
        Integer tipoPersonalId
) {
    // Java genera automáticamente: constructor, getters, equals(), hashCode(), y toString().
}