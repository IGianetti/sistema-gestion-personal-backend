-- V1__create_core_tables.sql

CREATE TABLE tipo_personal (
    id_tipo_personal SERIAL PRIMARY KEY,
    nombre VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE empleado (
    id BIGSERIAL PRIMARY KEY,
    fk_tipo_personal INTEGER NOT NULL REFERENCES tipo_personal(id_tipo_personal),
    ficha_censal VARCHAR(50) UNIQUE NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    edad INTEGER,
    fecha_ingreso DATE NOT NULL,
    estado VARCHAR(20) NOT NULL,
    -- Los campos cifrados se almacenan como texto
    dni TEXT,
    salario TEXT NOT NULL,
    direccion TEXT,

    -- Campos de auditoría implícitos para Envers
    -- Envers generará la tabla 'empleado_AUD' y los campos de revisión.
);

-- ... (otras tablas como licencia, documento, etc.)