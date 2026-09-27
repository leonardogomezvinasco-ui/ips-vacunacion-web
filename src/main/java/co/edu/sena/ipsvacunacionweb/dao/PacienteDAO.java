package co.edu.sena.ipsvacunacionweb.dao;

import co.edu.sena.ipsvacunacionweb.conexion.ConexionBD;
import co.edu.sena.ipsvacunacionweb.modelo.Paciente;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {

    private static final String INSERTAR_SQL = """
            INSERT INTO paciente (
                tipo_documento, numero_documento, nombre, apellido,
                telefono, direccion, ciudad, consentimiento, fecha_registro
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

    private static final String LISTAR_SQL = """
            SELECT id_paciente, tipo_documento, numero_documento, nombre,
                   apellido, telefono, direccion, ciudad, consentimiento,
                   fecha_registro
            FROM paciente
            ORDER BY id_paciente
            """;

    private static final String BUSCAR_POR_ID_SQL = """
            SELECT id_paciente, tipo_documento, numero_documento, nombre,
                   apellido, telefono, direccion, ciudad, consentimiento,
                   fecha_registro
            FROM paciente
            WHERE id_paciente = ?
            """;

    private static final String ACTUALIZAR_SQL = """
            UPDATE paciente
            SET tipo_documento = ?, numero_documento = ?, nombre = ?,
                apellido = ?, telefono = ?, direccion = ?, ciudad = ?,
                consentimiento = ?, fecha_registro = ?
            WHERE id_paciente = ?
            """;

    private static final String ELIMINAR_SQL = """
            DELETE FROM paciente
            WHERE id_paciente = ?
            """;

    public boolean insertar(Paciente paciente) {
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia =
                     conexion.prepareStatement(INSERTAR_SQL)) {

            asignarValoresPaciente(sentencia, paciente);
            return sentencia.executeUpdate() == 1;

        } catch (SQLException excepcion) {
            System.err.println("Error al insertar el paciente: "
                    + excepcion.getMessage());
            return false;
        }
    }

    public List<Paciente> listar() {
        List<Paciente> pacientes = new ArrayList<>();

        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia =
                     conexion.prepareStatement(LISTAR_SQL);
             ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {
                pacientes.add(crearPaciente(resultado));
            }

        } catch (SQLException excepcion) {
            System.err.println("Error al consultar los pacientes: "
                    + excepcion.getMessage());
        }

        return pacientes;
    }

    public Paciente buscarPorId(int idPaciente) {
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia =
                     conexion.prepareStatement(BUSCAR_POR_ID_SQL)) {

            sentencia.setInt(1, idPaciente);

            try (ResultSet resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    return crearPaciente(resultado);
                }
            }

        } catch (SQLException excepcion) {
            System.err.println("Error al buscar el paciente: "
                    + excepcion.getMessage());
        }

        return null;
    }

    public boolean actualizar(Paciente paciente) {
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia =
                     conexion.prepareStatement(ACTUALIZAR_SQL)) {

            asignarValoresPaciente(sentencia, paciente);
            sentencia.setInt(10, paciente.getIdPaciente());

            return sentencia.executeUpdate() == 1;

        } catch (SQLException excepcion) {
            System.err.println("Error al actualizar el paciente: "
                    + excepcion.getMessage());
            return false;
        }
    }

    public boolean eliminar(int idPaciente) {
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement sentencia =
                     conexion.prepareStatement(ELIMINAR_SQL)) {

            sentencia.setInt(1, idPaciente);
            return sentencia.executeUpdate() == 1;

        } catch (SQLException excepcion) {
            System.err.println("Error al eliminar el paciente: "
                    + excepcion.getMessage());
            return false;
        }
    }

    private void asignarValoresPaciente(
            PreparedStatement sentencia,
            Paciente paciente) throws SQLException {

        sentencia.setString(1, paciente.getTipoDocumento());
        sentencia.setString(2, paciente.getNumeroDocumento());
        sentencia.setString(3, paciente.getNombre());
        sentencia.setString(4, paciente.getApellido());
        sentencia.setString(5, paciente.getTelefono());
        sentencia.setString(6, paciente.getDireccion());
        sentencia.setString(7, paciente.getCiudad());
        sentencia.setBoolean(8, paciente.isConsentimiento());
        sentencia.setDate(9, Date.valueOf(paciente.getFechaRegistro()));
    }

    private Paciente crearPaciente(ResultSet resultado) throws SQLException {
        Paciente paciente = new Paciente();

        paciente.setIdPaciente(resultado.getInt("id_paciente"));
        paciente.setTipoDocumento(resultado.getString("tipo_documento"));
        paciente.setNumeroDocumento(
                resultado.getString("numero_documento"));
        paciente.setNombre(resultado.getString("nombre"));
        paciente.setApellido(resultado.getString("apellido"));
        paciente.setTelefono(resultado.getString("telefono"));
        paciente.setDireccion(resultado.getString("direccion"));
        paciente.setCiudad(resultado.getString("ciudad"));
        paciente.setConsentimiento(
                resultado.getBoolean("consentimiento"));
        paciente.setFechaRegistro(
                resultado.getDate("fecha_registro").toLocalDate());

        return paciente;
    }
}
