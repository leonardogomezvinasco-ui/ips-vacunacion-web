package co.edu.sena.ipsvacunacionweb.dao;

import co.edu.sena.ipsvacunacionweb.conexion.ConexionBD;
import co.edu.sena.ipsvacunacionweb.modelo.UsuarioSesion;
import co.edu.sena.ipsvacunacionweb.seguridad.HashContrasena;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {
    public java.util.List<UsuarioSesion> listar() throws SQLException {
        java.util.List<UsuarioSesion> lista = new java.util.ArrayList<>();
        String sql = "SELECT u.id_usuario,u.nombre_usuario,u.rol,u.id_personal,u.activo,CONCAT(p.nombres,' ',p.apellidos) nombre_personal "
                + "FROM usuario u LEFT JOIN personal_salud p ON p.id_personal=u.id_personal ORDER BY u.nombre_usuario";
        try (Connection cn=ConexionBD.obtenerConexion(); PreparedStatement ps=cn.prepareStatement(sql); ResultSet rs=ps.executeQuery()) {
            while(rs.next()) { int id=rs.getInt("id_personal"); Integer personal=rs.wasNull()?null:id;
                lista.add(new UsuarioSesion(rs.getInt("id_usuario"),rs.getString("nombre_usuario"),rs.getString("rol"),personal,rs.getString("nombre_personal"),rs.getBoolean("activo"))); }
        }
        return lista;
    }
    public UsuarioSesion autenticar(String usuario, String contrasena) throws SQLException {
        String sql = "SELECT u.id_usuario,u.nombre_usuario,u.hash_contrasena,u.rol,u.id_personal,"
                + "CONCAT(p.nombres,' ',p.apellidos) nombre_personal FROM usuario u "
                + "LEFT JOIN personal_salud p ON p.id_personal=u.id_personal "
                + "WHERE u.nombre_usuario=? AND u.activo=TRUE AND (u.id_personal IS NULL OR p.activo=TRUE)";
        try (Connection cn = ConexionBD.obtenerConexion(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, usuario == null ? "" : usuario.trim());
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next() || !HashContrasena.verificar(contrasena, rs.getString("hash_contrasena"))) return null;
                int idPersonal = rs.getInt("id_personal");
                Integer personal = rs.wasNull() ? null : idPersonal;
                return new UsuarioSesion(rs.getInt("id_usuario"), rs.getString("nombre_usuario"),
                        rs.getString("rol"), personal, rs.getString("nombre_personal"));
            }
        }
    }

    public void crear(String nombreUsuario, String contrasena, String rol, Integer idPersonal) throws SQLException {
        if (nombreUsuario == null || !nombreUsuario.matches("[A-Za-z0-9._-]{3,60}"))
            throw new IllegalArgumentException("El usuario debe tener de 3 a 60 caracteres alfanuméricos, punto, guion o guion bajo");
        if (!java.util.Set.of("ADMINISTRADOR", "AUXILIAR", "PERSONAL_SALUD").contains(rol))
            throw new IllegalArgumentException("Seleccione un rol válido");
        if ("PERSONAL_SALUD".equals(rol) && idPersonal == null)
            throw new IllegalArgumentException("Seleccione el profesional asociado al usuario");
        String hash = HashContrasena.crear(contrasena);
        String sql = "INSERT INTO usuario(nombre_usuario,hash_contrasena,rol,id_personal) VALUES(?,?,?,?)";
        try (Connection cn = ConexionBD.obtenerConexion(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, nombreUsuario.trim()); ps.setString(2, hash); ps.setString(3, rol);
            if (idPersonal == null) ps.setNull(4, java.sql.Types.INTEGER); else ps.setInt(4, idPersonal);
            ps.executeUpdate();
        }
    }

    public void cambiarActivo(int idUsuario, boolean activo, int idEjecutor) throws SQLException {
        if(idUsuario==idEjecutor&&!activo) throw new IllegalArgumentException("No puede desactivar su propia cuenta activa");
        try(Connection cn=ConexionBD.obtenerConexion()) {
            cn.setAutoCommit(false);
            try {
                if(!activo) {
                    try(PreparedStatement p=cn.prepareStatement("SELECT rol,activo FROM usuario WHERE id_usuario=? FOR UPDATE")) {
                        p.setInt(1,idUsuario); try(ResultSet r=p.executeQuery()) {
                            if(!r.next()) throw new IllegalArgumentException("La cuenta seleccionada no existe");
                            if("ADMINISTRADOR".equals(r.getString("rol"))&&r.getBoolean("activo")) {
                                try(PreparedStatement n=cn.prepareStatement("SELECT COUNT(*) FROM usuario WHERE rol='ADMINISTRADOR' AND activo=TRUE");ResultSet c=n.executeQuery()) { c.next(); if(c.getInt(1)<=1) throw new IllegalArgumentException("Debe conservar al menos un administrador activo"); }
                            }
                        }
                    }
                }
                try(PreparedStatement p=cn.prepareStatement("UPDATE usuario SET activo=? WHERE id_usuario=?")){p.setBoolean(1,activo);p.setInt(2,idUsuario);if(p.executeUpdate()!=1)throw new IllegalArgumentException("La cuenta seleccionada no existe");}
                cn.commit();
            } catch(SQLException|RuntimeException e){cn.rollback();throw e;} finally {cn.setAutoCommit(true);}
        }
    }
}
