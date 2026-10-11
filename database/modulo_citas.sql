USE ips_vacunacion;
CREATE TABLE IF NOT EXISTS cita (
  id_cita INT AUTO_INCREMENT PRIMARY KEY,
  id_paciente INT NOT NULL,
  vacuna VARCHAR(100) NOT NULL,
  fecha_hora DATETIME NOT NULL,
  estado ENUM('Programada','Atendida','Cancelada') NOT NULL DEFAULT 'Programada',
  CONSTRAINT fk_cita_paciente FOREIGN KEY (id_paciente) REFERENCES paciente(id_paciente),
  CONSTRAINT uq_cita_paciente_fecha UNIQUE (id_paciente, fecha_hora),
  INDEX idx_cita_fecha (fecha_hora)
);
