package co.edu.sena.ipsvacunacionweb.servicio;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class ValidadorCitaTest {
    private final LocalDateTime ahora = LocalDateTime.of(2026, 10, 10, 9, 0);

    @Test
    void aceptaCitaFuturaConPacienteYVacuna() {
        assertDoesNotThrow(() -> ValidadorCita.validarDatos(
                1, "Influenza", ahora.plusDays(1), ahora));
    }

    @Test
    void rechazaFechaPasada() {
        assertThrows(IllegalArgumentException.class, () ->
                ValidadorCita.validarDatos(1, "Influenza", ahora.minusMinutes(1), ahora));
    }

    @Test
    void rechazaEstadoDesconocido() {
        assertThrows(IllegalArgumentException.class, () ->
                ValidadorCita.validarEstado("Pendiente"));
    }
}
