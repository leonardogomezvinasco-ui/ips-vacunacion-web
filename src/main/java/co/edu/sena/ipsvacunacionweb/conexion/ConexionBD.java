package co.edu.sena.ipsvacunacionweb.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConexionBD {

    // Conserva como valor predeterminado el esquema de QA que ya se usaba en este equipo.
    // Permite cambiar los datos de conexión sin editar o recompilar el código.
    private static final String URL = configuracion("IPS_DB_URL", "ips.db.url",
            "jdbc:mysql://127.0.0.1:3306/ips_vacunacion_test?useSSL=false&serverTimezone=America/Bogota");
    private static final String USUARIO = configuracion("IPS_DB_USER", "ips.db.user", "root");
    private static final String CONTRASENA = configuracion("IPS_DB_PASSWORD", "ips.db.password", "");

    private ConexionBD() {
    }

    private static String configuracion(String variable, String propiedad, String predeterminado) {
        String valor = System.getProperty(propiedad);
        if (valor == null || valor.isBlank()) valor = System.getenv(variable);
        return valor == null || valor.isBlank() ? predeterminado : valor;
    }

    public static Connection obtenerConexion() throws SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException excepcion) {
            throw new SQLException(
                "No se encontró el controlador JDBC de MySQL.",
                excepcion);
        }

    return DriverManager.getConnection(
            URL,
            USUARIO,
            CONTRASENA);
    }
}
