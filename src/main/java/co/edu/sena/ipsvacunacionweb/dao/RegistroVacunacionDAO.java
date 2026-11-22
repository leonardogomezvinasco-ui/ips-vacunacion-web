package co.edu.sena.ipsvacunacionweb.dao;

import co.edu.sena.ipsvacunacionweb.conexion.ConexionBD;
import co.edu.sena.ipsvacunacionweb.modelo.RegistroVacunacion;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class RegistroVacunacionDAO {
    public List<RegistroVacunacion> listar() throws SQLException {
        String sql="SELECT r.id_registro,r.id_cita,r.id_paciente,r.id_vacuna,r.id_personal,r.fecha_aplicacion,r.numero_dosis,r.lote,r.observaciones,CONCAT(p.nombre,' ',p.apellido) paciente,v.nombre vacuna,CONCAT(s.nombres,' ',s.apellidos) personal FROM registro_vacunacion r JOIN paciente p ON p.id_paciente=r.id_paciente JOIN vacuna v ON v.id_vacuna=r.id_vacuna JOIN personal_salud s ON s.id_personal=r.id_personal ORDER BY r.fecha_aplicacion DESC";
        List<RegistroVacunacion> registros=new ArrayList<>();
        try(Connection cn=ConexionBD.obtenerConexion();PreparedStatement ps=cn.prepareStatement(sql);ResultSet rs=ps.executeQuery()) { while(rs.next()){RegistroVacunacion r=new RegistroVacunacion();r.setIdRegistro(rs.getInt("id_registro"));r.setIdCita(rs.getInt("id_cita"));r.setIdPaciente(rs.getInt("id_paciente"));r.setIdVacuna(rs.getInt("id_vacuna"));r.setIdPersonal(rs.getInt("id_personal"));r.setFechaAplicacion(rs.getTimestamp("fecha_aplicacion").toLocalDateTime());r.setNumeroDosis(rs.getInt("numero_dosis"));r.setLote(rs.getString("lote"));r.setObservaciones(rs.getString("observaciones"));r.setPaciente(rs.getString("paciente"));r.setVacuna(rs.getString("vacuna"));r.setPersonal(rs.getString("personal"));registros.add(r);} }
        return registros;
    }

    public void registrar(int idCita, int idPersonal, int numeroDosis, String lote,
                          String observaciones, LocalDateTime fechaAplicacion) throws SQLException {
        if(idCita<=0||idPersonal<=0||numeroDosis<=0||lote==null||lote.isBlank()||lote.length()>60)
            throw new IllegalArgumentException("Seleccione cita, personal y dosis; el lote es obligatorio (máximo 60 caracteres)");
        if(fechaAplicacion==null||fechaAplicacion.isAfter(LocalDateTime.now()))
            throw new IllegalArgumentException("La fecha de aplicación no puede estar en el futuro");
        try(Connection cn=ConexionBD.obtenerConexion()) {
            cn.setAutoCommit(false);
            try {
                int idPaciente, idVacuna; LocalDateTime fechaCita;
                String sqlCita="SELECT id_paciente,id_vacuna,fecha_hora,estado FROM cita WHERE id_cita=? FOR UPDATE";
                try(PreparedStatement ps=cn.prepareStatement(sqlCita)){ps.setInt(1,idCita);try(ResultSet rs=ps.executeQuery()){
                    if(!rs.next())throw new IllegalArgumentException("La cita seleccionada no existe");
                    if(!"Programada".equals(rs.getString("estado")))throw new IllegalArgumentException("Solo se puede registrar una cita programada");
                    if(rs.getObject("id_vacuna")==null)throw new IllegalArgumentException("La cita debe estar vinculada a una vacuna del catálogo");
                    idPaciente=rs.getInt("id_paciente");idVacuna=rs.getInt("id_vacuna");fechaCita=rs.getTimestamp("fecha_hora").toLocalDateTime();
                }}
                if(fechaCita.isAfter(fechaAplicacion))throw new IllegalArgumentException("La cita todavía no ha llegado a la fecha programada");
                try(PreparedStatement ps=cn.prepareStatement("SELECT activo FROM personal_salud WHERE id_personal=? FOR UPDATE")){ps.setInt(1,idPersonal);try(ResultSet rs=ps.executeQuery()){if(!rs.next()||!rs.getBoolean(1))throw new IllegalArgumentException("El profesional no existe o está inactivo");}}
                try(PreparedStatement ps=cn.prepareStatement("UPDATE vacuna SET dosis_disponibles=dosis_disponibles-1 WHERE id_vacuna=? AND dosis_disponibles>0")){ps.setInt(1,idVacuna);if(ps.executeUpdate()!=1)throw new IllegalArgumentException("No hay dosis disponibles para esta vacuna");}
                String sql="INSERT INTO registro_vacunacion(id_cita,id_paciente,id_vacuna,id_personal,fecha_aplicacion,numero_dosis,lote,observaciones) VALUES(?,?,?,?,?,?,?,?)";
                try(PreparedStatement ps=cn.prepareStatement(sql)){ps.setInt(1,idCita);ps.setInt(2,idPaciente);ps.setInt(3,idVacuna);ps.setInt(4,idPersonal);ps.setTimestamp(5,Timestamp.valueOf(fechaAplicacion));ps.setInt(6,numeroDosis);ps.setString(7,lote.trim());ps.setString(8,observaciones==null?null:observaciones.trim());ps.executeUpdate();}
                try(PreparedStatement ps=cn.prepareStatement("UPDATE cita SET estado='Atendida' WHERE id_cita=? AND estado='Programada'")){ps.setInt(1,idCita);if(ps.executeUpdate()!=1)throw new IllegalArgumentException("El estado de la cita cambió durante el registro");}
                cn.commit();
            } catch(SQLException|RuntimeException e) { cn.rollback(); throw e; }
            finally { cn.setAutoCommit(true); }
        }
    }
}
