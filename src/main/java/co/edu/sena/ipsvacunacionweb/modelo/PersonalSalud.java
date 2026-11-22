package co.edu.sena.ipsvacunacionweb.modelo;

public class PersonalSalud {
    private int idPersonal;
    private String tipoDocumento, numeroDocumento, nombres, apellidos, registroProfesional, cargo, telefono;
    private boolean activo;
    public int getIdPersonal(){return idPersonal;} public void setIdPersonal(int v){idPersonal=v;}
    public String getTipoDocumento(){return tipoDocumento;} public void setTipoDocumento(String v){tipoDocumento=v;}
    public String getNumeroDocumento(){return numeroDocumento;} public void setNumeroDocumento(String v){numeroDocumento=v;}
    public String getNombres(){return nombres;} public void setNombres(String v){nombres=v;}
    public String getApellidos(){return apellidos;} public void setApellidos(String v){apellidos=v;}
    public String getRegistroProfesional(){return registroProfesional;} public void setRegistroProfesional(String v){registroProfesional=v;}
    public String getCargo(){return cargo;} public void setCargo(String v){cargo=v;}
    public String getTelefono(){return telefono;} public void setTelefono(String v){telefono=v;}
    public boolean isActivo(){return activo;} public void setActivo(boolean v){activo=v;}
}
