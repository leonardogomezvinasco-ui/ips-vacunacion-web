package co.edu.sena.ipsvacunacionweb.servicio;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ValidadorVacunaTest {
    @Test
    void aceptaCeroDosis() {
        assertDoesNotThrow(() -> ValidadorVacuna.validar("Influenza", 0));
    }

    @Test
    void rechazaDosisNegativas() {
        assertThrows(IllegalArgumentException.class, () ->
                ValidadorVacuna.validar("Influenza", -1));
    }

    @Test
    void rechazaNombreVacío() {
        assertThrows(IllegalArgumentException.class, () ->
                ValidadorVacuna.validar(" ", 5));
    }
}
