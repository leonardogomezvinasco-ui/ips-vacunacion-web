package co.edu.sena.ipsvacunacionweb.dao;

import co.edu.sena.ipsvacunacionweb.conexion.ConexionBD;
import co.edu.sena.ipsvacunacionweb.modelo.PersonalSalud;
import java.sql.*;
import java.util.*;

public class PersonalSaludDAO {
    private static final String COLUMNAS = "id_personal,tipo_documento,numero_documento,nombres,apellidos,registro_profesional,cargo,telefono,activo";
    public List<PersonalSalud> listar(boolean soloActivos) throws SQLException {
        List<PersonalSalud> lista = new ArrayList<>();
        String sql = "SELECT " + COLUMNAS + " FROM personal_salud" + (soloActivos ? " WHERE activo=TRUE" : "") + " ORDER BY apellidos,nombres";
        try(Connection cn=ConexionBD.obtenerConexion(); PreparedStatement ps=cn.prepareStatement(sql); ResultSet rs=ps.executeQuery()) {
            while(rs.next()) { PersonalSalud p=new PersonalSalud(); p.setIdPersonal(rs.getInt("id_personal")); p.setTipoDocumento(rs.getString("tipo_documento")); p.setNumeroDocumento(rs.getString("numero_documento")); p.setNombres(rs.getString("nombres")); p.setApellidos(rs.getString("apellidos")); p.setRegistroProfesional(rs.getString("registro_profesional")); p.setCargo(rs.getString("cargo")); p.setTelefono(rs.getString("telefono")); p.setActivo(rs.getBoolean("activo")); lista.add(p); }
        }
        return lista;
    }
    public PersonalSalud buscarPorId(int id) throws SQLException {
        try(Connection cn=ConexionBD.obtenerConexion();PreparedStatement ps=cn.prepareStatement("SELECT "+COLUMNAS+" FROM personal_salud WHERE id_personal=?")) {
            ps.setInt(1,id);try(ResultSet rs=ps.executeQuery()){if(!rs.next())return null;PersonalSalud p=new PersonalSalud();p.setIdPersonal(rs.getInt("id_personal"));p.setTipoDocumento(rs.getString("tipo_documento"));p.setNumeroDocumento(rs.getString("numero_documento"));p.setNombres(rs.getString("nombres"));p.setApellidos(rs.getString("apellidos"));p.setRegistroProfesional(rs.getString("registro_profesional"));p.setCargo(rs.getString("cargo"));p.setTelefono(rs.getString("telefono"));p.setActivo(rs.getBoolean("activo"));return p;}
        }
    }
    public void crear(PersonalSalud p) throws SQLException {
        validar(p);
        String sql="INSERT INTO personal_salud(tipo_documento,numero_documento,nombres,apellidos,registro_profesional,cargo,telefono) VALUES(?,?,?,?,?,?,?)";
        try(Connection cn=ConexionBD.obtenerConexion(); PreparedStatement ps=cn.prepareStatement(sql)) { ps.setString(1,p.getTipoDocumento().trim()); ps.setString(2,p.getNumeroDocumento().trim()); ps.setString(3,p.getNombres().trim()); ps.setString(4,p.getApellidos().trim()); ps.setString(5,p.getRegistroProfesional().trim()); ps.setString(6,p.getCargo().trim()); ps.setString(7,p.getTelefono()); ps.executeUpdate(); }
    }
    public void cambiarActivo(int id, boolean activo) throws SQLException {
        try(Connection cn=ConexionBD.obtenerConexion()) {
            cn.setAutoCommit(false);
            try(PreparedStatement ps=cn.prepareStatement("UPDATE personal_salud SET activo=? WHERE id_personal=?")) {
                ps.setBoolean(1,activo); ps.setInt(2,id);
                if(ps.executeUpdate()!=1) throw new IllegalArgumentException("No existe el registro de personal seleccionado");
                if(!activo) try(PreparedStatement cuentas=cn.prepareStatement("UPDATE usuario SET activo=FALSE WHERE id_personal=?")){cuentas.setInt(1,id);cuentas.executeUpdate();}
                cn.commit();
            } catch(SQLException|RuntimeException e){cn.rollback();throw e;} finally {cn.setAutoCommit(true);}
        }
    }
    public void actualizar(PersonalSalud p) throws SQLException {
        if(p.getIdPersonal()<=0)throw new IllegalArgumentException("Seleccione un registro de personal válido");
        validar(p);
        String sql="UPDATE personal_salud SET tipo_documento=?,numero_documento=?,nombres=?,apellidos=?,registro_profesional=?,cargo=?,telefono=? WHERE id_personal=?";
        try(Connection cn=ConexionBD.obtenerConexion();PreparedStatement ps=cn.prepareStatement(sql)){ps.setString(1,p.getTipoDocumento().trim());ps.setString(2,p.getNumeroDocumento().trim());ps.setString(3,p.getNombres().trim());ps.setString(4,p.getApellidos().trim());ps.setString(5,p.getRegistroProfesional().trim());ps.setString(6,p.getCargo().trim());ps.setString(7,p.getTelefono());ps.setInt(8,p.getIdPersonal());if(ps.executeUpdate()!=1)throw new IllegalArgumentException("No se encontró el registro de personal");}
    }
    private void validar(PersonalSalud p) {
        if(p.getTipoDocumento()==null||p.getTipoDocumento().isBlank()||p.getNumeroDocumento()==null||p.getNumeroDocumento().isBlank()||p.getNombres()==null||p.getNombres().isBlank()||p.getApellidos()==null||p.getApellidos().isBlank()||p.getRegistroProfesional()==null||p.getRegistroProfesional().isBlank()||p.getCargo()==null||p.getCargo().isBlank()) throw new IllegalArgumentException("Complete documento, nombres, apellidos, registro profesional y cargo");
    }
}
