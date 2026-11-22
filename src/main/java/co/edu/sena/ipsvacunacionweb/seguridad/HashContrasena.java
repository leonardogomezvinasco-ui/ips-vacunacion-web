package co.edu.sena.ipsvacunacionweb.seguridad;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

/** Almacenamiento de contraseñas con PBKDF2 y sal aleatoria por usuario. */
public final class HashContrasena {
    private static final int ITERACIONES = 210_000;
    private static final int BITS = 256;
    private static final SecureRandom ALEATORIO = new SecureRandom();

    private HashContrasena() { }

    public static String crear(String contrasena) {
        if (contrasena == null || contrasena.length() < 12) {
            throw new IllegalArgumentException("Use una contraseña de al menos 12 caracteres");
        }
        byte[] sal = new byte[16];
        ALEATORIO.nextBytes(sal);
        byte[] hash = derivar(contrasena.toCharArray(), sal, ITERACIONES);
        return "pbkdf2-sha256$" + ITERACIONES + "$"
                + Base64.getEncoder().encodeToString(sal) + "$"
                + Base64.getEncoder().encodeToString(hash);
    }

    public static boolean verificar(String contrasena, String almacenado) {
        if (contrasena == null || almacenado == null) return false;
        try {
            String[] partes = almacenado.split("\\$", -1);
            if (partes.length != 4 || !"pbkdf2-sha256".equals(partes[0])) return false;
            byte[] sal = Base64.getDecoder().decode(partes[2]);
            byte[] hashEsperado = Base64.getDecoder().decode(partes[3]);
            byte[] hashActual = derivar(contrasena.toCharArray(), sal,
                    Integer.parseInt(partes[1]));
            if (hashActual.length != hashEsperado.length) return false;
            int diferencia = 0;
            for (int i = 0; i < hashActual.length; i++) diferencia |= hashActual[i] ^ hashEsperado[i];
            return diferencia == 0;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private static byte[] derivar(char[] clave, byte[] sal, int iteraciones) {
        try {
            PBEKeySpec spec = new PBEKeySpec(clave, sal, iteraciones, BITS);
            try {
                return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
                        .generateSecret(spec).getEncoded();
            } finally {
                spec.clearPassword();
                java.util.Arrays.fill(clave, '\0');
            }
        } catch (java.security.GeneralSecurityException e) {
            throw new IllegalStateException("No se pudo proteger la contraseña", e);
        }
    }
}
