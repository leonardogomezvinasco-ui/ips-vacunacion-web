package co.edu.sena.ipsvacunacionweb.dao;
import co.edu.sena.ipsvacunacionweb.conexion.ConexionBD;
import co.edu.sena.ipsvacunacionweb.modelo.Vacuna;
import co.edu.sena.ipsvacunacionweb.servicio.ValidadorVacuna;
import java.sql.*; import java.util.*;
public class VacunaDAO {
 public List<Vacuna> listar() throws SQLException {
  List<Vacuna> lista=new ArrayList<>();
  try(Connection c=ConexionBD.obtenerConexion(); PreparedStatement p=c.prepareStatement("SELECT * FROM vacuna ORDER BY nombre"); ResultSet r=p.executeQuery()) { while(r.next()){Vacuna v=new Vacuna();v.setIdVacuna(r.getInt("id_vacuna"));v.setNombre(r.getString("nombre"));v.setFabricante(r.getString("fabricante"));v.setDosisDisponibles(r.getInt("dosis_disponibles"));lista.add(v);} }
  return lista;
 }
 public void guardar(Vacuna v) throws SQLException {
  ValidadorVacuna.validar(v.getNombre(), v.getDosisDisponibles());
  try(Connection c=ConexionBD.obtenerConexion();PreparedStatement p=c.prepareStatement("INSERT INTO vacuna(nombre,fabricante,dosis_disponibles) VALUES(?,?,?) ON DUPLICATE KEY UPDATE fabricante=VALUES(fabricante), dosis_disponibles=VALUES(dosis_disponibles)")){p.setString(1,v.getNombre().trim());p.setString(2,v.getFabricante());p.setInt(3,v.getDosisDisponibles());p.executeUpdate();}
 }
}
