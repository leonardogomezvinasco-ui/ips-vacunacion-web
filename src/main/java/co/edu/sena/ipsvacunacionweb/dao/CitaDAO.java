package co.edu.sena.ipsvacunacionweb.dao;

import co.edu.sena.ipsvacunacionweb.conexion.ConexionBD;
import co.edu.sena.ipsvacunacionweb.modelo.Cita;
import co.edu.sena.ipsvacunacionweb.modelo.Vacuna;
import co.edu.sena.ipsvacunacionweb.servicio.ValidadorCita;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CitaDAO {
    public List<Cita> listar() throws SQLException {
        String sql = "SELECT c.id_cita,c.id_paciente,c.id_vacuna,CONCAT(p.nombre,' ',p.apellido) paciente,COALESCE(v.nombre,c.vacuna) vacuna,c.fecha_hora,c.estado FROM cita c JOIN paciente p ON p.id_paciente=c.id_paciente LEFT JOIN vacuna v ON v.id_vacuna=c.id_vacuna ORDER BY c.fecha_hora DESC";
        List<Cita> citas = new ArrayList<>();
        try (Connection cn = ConexionBD.obtenerConexion(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Cita c = new Cita(); c.setIdCita(rs.getInt("id_cita")); c.setIdPaciente(rs.getInt("id_paciente"));
                c.setIdVacuna(rs.getInt("id_vacuna"));
                c.setPaciente(rs.getString("paciente")); c.setVacuna(rs.getString("vacuna"));
                c.setFechaHora(rs.getTimestamp("fecha_hora").toLocalDateTime()); c.setEstado(rs.getString("estado")); citas.add(c);
            }
        }
        return citas;
    }
    public List<Cita> listarPendientesAplicacion() throws SQLException {
        List<Cita> citas=new ArrayList<>();
        String sql="SELECT c.id_cita,c.id_paciente,c.id_vacuna,CONCAT(p.nombre,' ',p.apellido) paciente,v.nombre vacuna,c.fecha_hora,c.estado FROM cita c JOIN paciente p ON p.id_paciente=c.id_paciente JOIN vacuna v ON v.id_vacuna=c.id_vacuna WHERE c.estado='Programada' AND c.fecha_hora<=NOW() ORDER BY c.fecha_hora";
        try(Connection cn=ConexionBD.obtenerConexion();PreparedStatement ps=cn.prepareStatement(sql);ResultSet rs=ps.executeQuery()) { while(rs.next()){Cita c=new Cita();c.setIdCita(rs.getInt("id_cita"));c.setIdPaciente(rs.getInt("id_paciente"));c.setIdVacuna(rs.getInt("id_vacuna"));c.setPaciente(rs.getString("paciente"));c.setVacuna(rs.getString("vacuna"));c.setFechaHora(rs.getTimestamp("fecha_hora").toLocalDateTime());c.setEstado(rs.getString("estado"));citas.add(c);} }
        return citas;
    }
    public List<Vacuna> listarVacunasParaCita() throws SQLException {
        List<Vacuna> vacunas=new ArrayList<>();
        try(Connection cn=ConexionBD.obtenerConexion();PreparedStatement ps=cn.prepareStatement("SELECT id_vacuna,nombre,fabricante,dosis_disponibles FROM vacuna ORDER BY nombre");ResultSet rs=ps.executeQuery()) { while(rs.next()){Vacuna v=new Vacuna();v.setIdVacuna(rs.getInt("id_vacuna"));v.setNombre(rs.getString("nombre"));v.setFabricante(rs.getString("fabricante"));v.setDosisDisponibles(rs.getInt("dosis_disponibles"));vacunas.add(v);} }
        return vacunas;
    }
    public void crear(Cita c) throws SQLException {
        ValidadorCita.validarNuevaCita(c.getIdPaciente(), c.getIdVacuna(),
                c.getFechaHora(), java.time.LocalDateTime.now());
        String sql = "INSERT INTO cita(id_paciente,vacuna,id_vacuna,fecha_hora,estado) SELECT ?,nombre,id_vacuna,?,'Programada' FROM vacuna WHERE id_vacuna=? AND dosis_disponibles>0";
        try (Connection cn = ConexionBD.obtenerConexion(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1,c.getIdPaciente()); ps.setTimestamp(2,Timestamp.valueOf(c.getFechaHora())); ps.setInt(3,c.getIdVacuna());
            if(ps.executeUpdate()!=1) throw new IllegalArgumentException("Seleccione una vacuna del catálogo con dosis disponibles");
        }
    }
    public void cambiarEstado(int id, String estado) throws SQLException {
        ValidadorCita.validarEstado(estado);
        try (Connection cn = ConexionBD.obtenerConexion(); PreparedStatement ps = cn.prepareStatement("UPDATE cita SET estado=? WHERE id_cita=?")) { ps.setString(1,estado); ps.setInt(2,id); ps.executeUpdate(); }
    }
}
