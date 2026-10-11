package co.edu.sena.ipsvacunacionweb.modelo;
public class Vacuna {
    private int idVacuna, dosisDisponibles; private String nombre, fabricante;
    public int getIdVacuna(){return idVacuna;} public void setIdVacuna(int v){idVacuna=v;}
    public int getDosisDisponibles(){return dosisDisponibles;} public void setDosisDisponibles(int v){dosisDisponibles=v;}
    public String getNombre(){return nombre;} public void setNombre(String v){nombre=v;}
    public String getFabricante(){return fabricante;} public void setFabricante(String v){fabricante=v;}
}
