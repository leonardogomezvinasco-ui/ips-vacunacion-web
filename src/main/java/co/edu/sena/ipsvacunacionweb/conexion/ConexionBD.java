package co.edu.sena.ipsvacunacionweb.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConexionBD {

    private static final String URL =
            "jdbc:mysql://localhost:3306/ips_vacunacion"
            + "?useSSL=false&serverTimezone=America/Bogota";

    private static final String USUARIO = "root";
    private static final String CONTRASENA = "";

    private ConexionBD() {
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
