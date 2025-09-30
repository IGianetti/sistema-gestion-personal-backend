package com.igianetti.sgp.infraestructure.web.mapper;

import com.igianetti.sgp.domain.model.Empleado;
import com.igianetti.sgp.domain.model.TipoPersonal;
import com.igianetti.sgp.infraestructure.web.dto.EmpleadoRequest;
import com.igianetti.sgp.infraestructure.web.dto.EmpleadoResponse;

public class EmpleadoMapper {

    /**
     * Convierte EmpleadoRequest a Empleado (Dominio Entity).
     */
    public static Empleado toEntity(EmpleadoRequest request, TipoPersonal tipoPersonal) {
        Empleado empleado = new Empleado();

        // Usando los métodos de acceso de Record (son del mismo nombre que el campo)
        empleado.setNombre(request.nombre()); // NOTA: request.getNombre() cambia a request.nombre()
        empleado.setApellido(request.apellido());
        empleado.setEdad(request.edad());
        empleado.setFechaIngreso(request.fechaIngreso());
        empleado.setFichaCensal(request.fichaCensal());

        // Asignamos datos sensibles
        empleado.setDni(request.dni());
        empleado.setSalario(request.salario());
        empleado.setDireccion(request.direccion());

        empleado.setTipoPersonal(tipoPersonal);

        return empleado;
    }

    /**
     * Convierte Empleado (Dominio Entity) a EmpleadoResponse.
     */
    public static EmpleadoResponse toResponse(Empleado empleado) {
        // Usamos el constructor canónico del Record
        return new EmpleadoResponse(
                empleado.getId(),
                empleado.getNombre(),
                empleado.getApellido(),
                empleado.getEdad(),
                empleado.getFechaIngreso(),
                empleado.getEstado(),
                empleado.getFichaCensal(),
                empleado.getTipoPersonal() != null ? empleado.getTipoPersonal().getNombre() : null
        );
    }
}
