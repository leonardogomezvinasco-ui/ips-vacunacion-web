package co.edu.sena.ipsvacunacionweb.modelo;

import java.io.Serializable;

public final class UsuarioSesion implements Serializable {
    private final int idUsuario;
    private final String nombreUsuario, rol, nombrePersonal;
    private final Integer idPersonal;
    private final boolean activo;

    public UsuarioSesion(int idUsuario, String nombreUsuario, String rol,
                         Integer idPersonal, String nombrePersonal) {
        this(idUsuario,nombreUsuario,rol,idPersonal,nombrePersonal,true);
    }
    public UsuarioSesion(int idUsuario, String nombreUsuario, String rol,
                         Integer idPersonal, String nombrePersonal, boolean activo) {
        this.idUsuario=idUsuario; this.nombreUsuario=nombreUsuario; this.rol=rol;
        this.idPersonal=idPersonal; this.nombrePersonal=nombrePersonal; this.activo=activo;
    }
    public int getIdUsuario(){return idUsuario;}
    public String getNombreUsuario(){return nombreUsuario;}
    public String getRol(){return rol;}
    public Integer getIdPersonal(){return idPersonal;}
    public String getNombrePersonal(){return nombrePersonal;}
    public boolean isActivo(){return activo;}
    public boolean tieneRol(String... roles) { for(String permitido:roles) if(permitido.equals(rol)) return true; return false; }
}
