package com.igianetti.sgp.application.service;


import com.igianetti.sgp.domain.model.Empleado;
import com.igianetti.sgp.domain.model.EstadoEmpleado;
import com.igianetti.sgp.domain.model.TipoPersonal;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EmpleadoService {

    /**
     * Crea un nuevo empleado en el sistema.
     * @param empleado El objeto Empleado con los datos a guardar.
     * @return El Empleado persistido con su ID generado.
     */
    Empleado crearEmpleado(Empleado empleado);

    /**
     * Busca un empleado por su ID.
     * @param id El ID único del empleado.
     * @return Un Optional que contiene el Empleado si existe.
     */
    Optional<Empleado> obtenerEmpleadoPorId(Long id);

    /**
     * Obtiene una lista de todos los empleados.
     * @return Lista de empleados.
     */
    List<Empleado> obtenerTodosLosEmpleados();

    /**
     * Actualiza la información de un empleado existente.
     * @param id El ID del empleado a actualizar.
     * @param empleadoDetalles El objeto Empleado con los nuevos datos.
     * @return El Empleado actualizado.
     */
    Empleado actualizarEmpleado(Long id, Empleado empleadoDetalles);

    /**
     * Elimina lógicamente (o físicamente, dependiendo de la política) a un empleado.
     * Por ahora, definimos eliminación física.
     * @param id El ID del empleado a eliminar.
     */
    void eliminarEmpleado(Long id);

    // --- Métodos de Reglas de Negocio Específicas ---

    /**
     * Cambia el estado laboral del empleado (Activo, Licencia, Vacaciones).
     * Esto debería disparar un registro en HistorialEstado (auditoría).
     * @param empleadoId El ID del empleado.
     * @param nuevoEstado El nuevo estado a asignar.
     * @return El Empleado con el estado actualizado.
     */
    Empleado cambiarEstado(Long empleadoId, EstadoEmpleado nuevoEstado);

    /**
     * Obtiene una lista de empleados por su tipo de personal (Médico, Chofer, etc.).
     */
    List<Empleado> obtenerEmpleadosPorTipo(TipoPersonal tipoPersonal);
}