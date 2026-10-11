USE ips_vacunacion;
CREATE TABLE IF NOT EXISTS vacuna (
 id_vacuna INT AUTO_INCREMENT PRIMARY KEY,
 nombre VARCHAR(100) NOT NULL UNIQUE,
 fabricante VARCHAR(100),
 dosis_disponibles INT NOT NULL DEFAULT 0,
 CONSTRAINT chk_vacuna_dosis CHECK (dosis_disponibles >= 0)
);
