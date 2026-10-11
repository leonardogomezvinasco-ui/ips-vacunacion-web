package co.edu.sena.ipsvacunacionweb.servicio;

/** Reglas de negocio para registrar existencias de vacunas. */
public final class ValidadorVacuna {
    private ValidadorVacuna() { }

    public static void validar(String nombre, int dosisDisponibles) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre de la vacuna es obligatorio");
        }
        if (dosisDisponibles < 0) {
            throw new IllegalArgumentException("Las dosis no pueden ser negativas");
        }
    }
}
