package com.igianetti.sgp.domain.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "tipo_personal")
@Audited // También auditamos la tabla de catálogo.
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoPersonal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", nullable = false, unique = true)
    private String nombre;

    // Nota: La relación @OneToMany con Empleado se omite aquí para mantener
    // la entidad de catálogo limpia, priorizando la legibilidad y evitando
    // problemas de persistencia bidireccional innecesarios en el lado 'TipoPersonal'.
}
