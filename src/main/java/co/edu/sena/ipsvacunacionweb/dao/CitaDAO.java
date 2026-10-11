package co.edu.sena.ipsvacunacionweb.dao;

import co.edu.sena.ipsvacunacionweb.conexion.ConexionBD;
import co.edu.sena.ipsvacunacionweb.modelo.Cita;
import co.edu.sena.ipsvacunacionweb.servicio.ValidadorCita;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CitaDAO {
    public List<Cita> listar() throws SQLException {
        String sql = "SELECT c.id_cita,c.id_paciente,CONCAT(p.nombre,' ',p.apellido) paciente,c.vacuna,c.fecha_hora,c.estado FROM cita c JOIN paciente p ON p.id_paciente=c.id_paciente ORDER BY c.fecha_hora DESC";
        List<Cita> citas = new ArrayList<>();
        try (Connection cn = ConexionBD.obtenerConexion(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Cita c = new Cita(); c.setIdCita(rs.getInt("id_cita")); c.setIdPaciente(rs.getInt("id_paciente"));
                c.setPaciente(rs.getString("paciente")); c.setVacuna(rs.getString("vacuna"));
                c.setFechaHora(rs.getTimestamp("fecha_hora").toLocalDateTime()); c.setEstado(rs.getString("estado")); citas.add(c);
            }
        }
        return citas;
    }
    public void crear(Cita c) throws SQLException {
        ValidadorCita.validarDatos(c.getIdPaciente(), c.getVacuna(),
                c.getFechaHora(), java.time.LocalDateTime.now());
        String sql = "INSERT INTO cita(id_paciente,vacuna,fecha_hora,estado) VALUES(?,?,?,'Programada')";
        try (Connection cn = ConexionBD.obtenerConexion(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1,c.getIdPaciente()); ps.setString(2,c.getVacuna()); ps.setTimestamp(3,Timestamp.valueOf(c.getFechaHora())); ps.executeUpdate();
        }
    }
    public void cambiarEstado(int id, String estado) throws SQLException {
        ValidadorCita.validarEstado(estado);
        try (Connection cn = ConexionBD.obtenerConexion(); PreparedStatement ps = cn.prepareStatement("UPDATE cita SET estado=? WHERE id_cita=?")) { ps.setString(1,estado); ps.setInt(2,id); ps.executeUpdate(); }
    }
}
