-- Ejecutar una sola vez sobre el esquema de pruebas (o ajustar USE para el esquema local).
-- La tabla paciente debe existir antes de aplicar esta migración.
USE ips_vacunacion_test;

CREATE TABLE personal_salud (
    id_personal INT AUTO_INCREMENT PRIMARY KEY,
    tipo_documento VARCHAR(20) NOT NULL,
    numero_documento VARCHAR(30) NOT NULL UNIQUE,
    nombres VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    registro_profesional VARCHAR(50) NOT NULL UNIQUE,
    cargo VARCHAR(80) NOT NULL,
    telefono VARCHAR(30),
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE usuario (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre_usuario VARCHAR(60) NOT NULL UNIQUE,
    hash_contrasena VARCHAR(255) NOT NULL,
    rol ENUM('ADMINISTRADOR','AUXILIAR','PERSONAL_SALUD') NOT NULL,
    id_personal INT NULL UNIQUE,
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_usuario_personal FOREIGN KEY (id_personal)
        REFERENCES personal_salud(id_personal)
);

-- El catálogo se asegura en el esquema QA. En producción se recomienda ejecutar
-- el mismo script en una copia de seguridad y cambiar el esquema de la primera línea.
CREATE TABLE IF NOT EXISTS vacuna (
    id_vacuna INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    fabricante VARCHAR(100),
    dosis_disponibles INT NOT NULL DEFAULT 0,
    CONSTRAINT chk_vacuna_dosis CHECK (dosis_disponibles >= 0)
);

ALTER TABLE cita
    ADD COLUMN id_vacuna INT NULL AFTER vacuna,
    ADD CONSTRAINT fk_cita_vacuna FOREIGN KEY (id_vacuna)
        REFERENCES vacuna(id_vacuna);

UPDATE cita c
JOIN vacuna v ON LOWER(TRIM(CONVERT(v.nombre USING utf8mb4))) COLLATE utf8mb4_unicode_ci =
                LOWER(TRIM(CONVERT(c.vacuna USING utf8mb4))) COLLATE utf8mb4_unicode_ci
SET c.id_vacuna = v.id_vacuna
WHERE c.id_vacuna IS NULL;

CREATE TABLE registro_vacunacion (
    id_registro INT AUTO_INCREMENT PRIMARY KEY,
    id_cita INT NOT NULL UNIQUE,
    id_paciente INT NOT NULL,
    id_vacuna INT NOT NULL,
    id_personal INT NOT NULL,
    fecha_aplicacion DATETIME NOT NULL,
    numero_dosis TINYINT UNSIGNED NOT NULL,
    lote VARCHAR(60) NOT NULL,
    observaciones VARCHAR(500),
    creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_registro_cita FOREIGN KEY (id_cita) REFERENCES cita(id_cita),
    CONSTRAINT fk_registro_paciente FOREIGN KEY (id_paciente) REFERENCES paciente(id_paciente),
    CONSTRAINT fk_registro_vacuna FOREIGN KEY (id_vacuna) REFERENCES vacuna(id_vacuna),
    CONSTRAINT fk_registro_personal FOREIGN KEY (id_personal) REFERENCES personal_salud(id_personal),
    CONSTRAINT chk_registro_numero_dosis CHECK (numero_dosis > 0),
    INDEX idx_registro_fecha (fecha_aplicacion),
    INDEX idx_registro_paciente (id_paciente)
);

-- Antes de crear el primer administrador, compilar y ejecutar de forma interactiva:
-- java -cp target/classes co.edu.sena.ipsvacunacionweb.seguridad.GenerarHash
-- Copiar el hash emitido en un INSERT propio. No se crea una contraseña por defecto.
