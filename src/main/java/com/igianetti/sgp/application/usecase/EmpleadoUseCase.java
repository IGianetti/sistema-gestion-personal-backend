package com.igianetti.sgp.application.usecase;

import com.igianetti.sgp.application.service.EmpleadoService;
import com.igianetti.sgp.domain.model.Empleado;
import com.igianetti.sgp.domain.model.EstadoEmpleado;
import com.igianetti.sgp.domain.model.HistorialEstado;
import com.igianetti.sgp.domain.model.TipoPersonal;
import com.igianetti.sgp.domain.repository.EmpleadoRepository;
import com.igianetti.sgp.domain.repository.HistorialEstadoRepository;
import com.igianetti.sgp.util.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor // Genera un constructor con todos los campos 'final' (Inyección de Dependencias)
public class EmpleadoUseCase implements EmpleadoService {

    // Dependencia del Repositorio de Dominio
    private final EmpleadoRepository empleadoRepository;
    private final HistorialEstadoRepository historialEstadoRepository; // Necesario para la auditoría de estado

    // --- Métodos CRUD ---

    @Override
    @Transactional // Garantiza que la operación de persistencia se ejecute como una sola transacción
    public Empleado crearEmpleado(Empleado empleado) {
        // Aquí podríamos añadir validaciones complejas (ej: ficha censal única)

        // Asignar estado inicial: siempre ACTIVO al crear
        if (empleado.getEstado() == null) {
            empleado.setEstado(EstadoEmpleado.ACTIVO);
        }

        return empleadoRepository.save(empleado);
    }

    @Override
    @Transactional(readOnly = true) // Optimización para operaciones de solo lectura
    public Optional<Empleado> obtenerEmpleadoPorId(Long id) {
        return empleadoRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Empleado> obtenerTodosLosEmpleados() {
        return empleadoRepository.findAll();
    }

    @Override
    @Transactional
    public Empleado actualizarEmpleado(Long id, Empleado empleadoDetalles) {
        Empleado empleadoExistente = empleadoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado con ID: " + id));

        // Actualización de atributos (ejemplo: solo permitimos cambiar dirección y tipo)
        // La lógica de cómo se actualizan los campos debe ser MUY precisa aquí.
        empleadoExistente.setNombre(empleadoDetalles.getNombre());
        empleadoExistente.setApellido(empleadoDetalles.getApellido());
        empleadoExistente.setDireccion(empleadoDetalles.getDireccion());
        // ... otros campos no sensibles

        // ¡Cuidado! El estado y el salario deben tener flujos de actualización controlados.

        return empleadoRepository.save(empleadoExistente);
    }

    @Override
    @Transactional
    public void eliminarEmpleado(Long id) {
        if (!empleadoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Empleado no encontrado con ID: " + id);
        }
        empleadoRepository.deleteById(id);
    }

    // --- Métodos de Reglas de Negocio ---

    @Override
    @Transactional
    public Empleado cambiarEstado(Long empleadoId, EstadoEmpleado nuevoEstado) {
        Empleado empleado = empleadoRepository.findById(empleadoId)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado con ID: " + empleadoId));

        EstadoEmpleado estadoAnterior = empleado.getEstado();

        // 1. Regla de negocio: Si no hay cambio, salir.
        if (estadoAnterior == nuevoEstado) {
            return empleado;
        }

        // --- Lógica de Seguridad FALTANTE ---
        // Aquí obtendríamos el ID del usuario autenticado (del JWT en Spring Security)
        // Por ahora, usamos un placeholder. ESTO SERÁ IMPLEMENTADO EN LA CAPA DE SEGURIDAD.
        Long currentUserId = 1L; // <<-- PLACEHOLDER DE USUARIO AUTENTICADO

        // 2. Registrar la Auditoría de Estado
        HistorialEstado h = new HistorialEstado(empleado, estadoAnterior, nuevoEstado, currentUserId);
        historialEstadoRepository.save(h);

        // 3. Actualizar el estado del empleado
        empleado.setEstado(nuevoEstado);

        return empleadoRepository.save(empleado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Empleado> obtenerEmpleadosPorTipo(TipoPersonal tipoPersonal) {
        // Implementación con un metodo custom si fuera necesario,
        // pero JPA puede resolver esto si la relación es bidireccional.
        return empleadoRepository.findAll(); // Placeholder, se mejorará
    }
}