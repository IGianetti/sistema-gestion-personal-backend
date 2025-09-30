package com.igianetti.sgp.domain.model;

import com.igianetti.sgp.util.EncryptionConverter;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.envers.Audited;

import java.time.LocalDate;

@Entity
@Table(name = "empleado")
@Audited // Habilita Hibernate Envers para registrar cambios en esta entidad.
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- Atributos Comunes ---

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellido", nullable = false)
    private String apellido;

    @Column(name = "edad")
    private Integer edad;

    @Column(name = "fecha_ingreso", nullable = false)
    private LocalDate fechaIngreso;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoEmpleado estado;

    @Column(name = "ficha_censal", unique = true, nullable = false)
    private String fichaCensal;

    // --- Relaciones y Tipos ---

    // Relación N:1 con TipoPersonal (Médico, Chofer, etc.)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_tipo_personal", nullable = false)
    private TipoPersonal tipoPersonal;

    // --- Atributos Sensibles (Cifrados) ---

    // DNI (cifrado)
    @Convert(converter = EncryptionConverter.class)
    @Column(name = "dni", columnDefinition = "TEXT") // Usamos TEXT para almacenar el string cifrado
    private String dni;

    // Salario (cifrado)
    @Convert(converter = EncryptionConverter.class)
    @Column(name = "salario", columnDefinition = "TEXT")
    private String salario;

    // Dirección (cifrado)
    @Convert(converter = EncryptionConverter.class)
    @Column(name = "direccion", columnDefinition = "TEXT")
    private String direccion;

    // NOTA: Flyway deberá crear la tabla 'empleado_AUD' automáticamente.
}