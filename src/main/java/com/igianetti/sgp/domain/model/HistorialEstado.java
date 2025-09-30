package com.igianetti.sgp.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "historial_estado")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistorialEstado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación N:1 con Empleado
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_empleado", nullable = false)
    private Empleado empleado;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_anterior", nullable = false)
    private EstadoEmpleado estadoAnterior;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_nuevo", nullable = false)
    private EstadoEmpleado estadoNuevo;

    @Column(name = "fecha_cambio", nullable = false)
    private LocalDateTime fechaCambio = LocalDateTime.now(); // Valor por defecto

    // Referencia al ID del Usuario que realizó el cambio (RRHH_ADMIN, JEFE_AREA)
    // Usaremos un Long que referenciará a la entidad Usuario (aún no creada).
    @Column(name = "fk_usuario_cambio", nullable = false)
    private Long fkUsuarioCambio;

    // --- Constructor para uso en el Use Case ---
    public HistorialEstado(Empleado empleado, EstadoEmpleado estadoAnterior, EstadoEmpleado estadoNuevo, Long fkUsuarioCambio) {
        this.empleado = empleado;
        this.estadoAnterior = estadoAnterior;
        this.estadoNuevo = estadoNuevo;
        this.fkUsuarioCambio = fkUsuarioCambio;
    }
}
