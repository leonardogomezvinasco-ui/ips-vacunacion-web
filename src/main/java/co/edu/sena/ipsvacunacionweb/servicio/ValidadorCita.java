package co.edu.sena.ipsvacunacionweb.servicio;

import java.time.LocalDateTime;
import java.util.Set;

/** Reglas de negocio de citas, separadas del acceso a la base de datos. */
public final class ValidadorCita {
    private static final Set<String> ESTADOS_PERMITIDOS =
            Set.of("Programada", "Atendida", "Cancelada");

    private ValidadorCita() { }

    public static void validarDatos(int idPaciente, String vacuna,
                                    LocalDateTime fechaHora,
                                    LocalDateTime ahora) {
        if (idPaciente <= 0) {
            throw new IllegalArgumentException("Seleccione un paciente válido");
        }
        if (vacuna == null || vacuna.isBlank()) {
            throw new IllegalArgumentException("Indique la vacuna");
        }
        if (fechaHora == null || ahora == null || !fechaHora.isAfter(ahora)) {
            throw new IllegalArgumentException("La fecha de la cita debe ser futura");
        }
    }

    public static void validarEstado(String estado) {
        if (!ESTADOS_PERMITIDOS.contains(estado)) {
            throw new IllegalArgumentException("Estado no válido");
        }
    }
}
