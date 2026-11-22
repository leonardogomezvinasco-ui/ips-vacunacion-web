package co.edu.sena.ipsvacunacionweb.modelo;

import java.time.LocalDateTime;

public class RegistroVacunacion {
    private int idRegistro, idCita, idPaciente, idVacuna, idPersonal, numeroDosis;
    private String paciente, vacuna, personal, lote, observaciones;
    private LocalDateTime fechaAplicacion;
    public int getIdRegistro(){return idRegistro;} public void setIdRegistro(int v){idRegistro=v;}
    public int getIdCita(){return idCita;} public void setIdCita(int v){idCita=v;}
    public int getIdPaciente(){return idPaciente;} public void setIdPaciente(int v){idPaciente=v;}
    public int getIdVacuna(){return idVacuna;} public void setIdVacuna(int v){idVacuna=v;}
    public int getIdPersonal(){return idPersonal;} public void setIdPersonal(int v){idPersonal=v;}
    public int getNumeroDosis(){return numeroDosis;} public void setNumeroDosis(int v){numeroDosis=v;}
    public String getPaciente(){return paciente;} public void setPaciente(String v){paciente=v;}
    public String getVacuna(){return vacuna;} public void setVacuna(String v){vacuna=v;}
    public String getPersonal(){return personal;} public void setPersonal(String v){personal=v;}
    public String getLote(){return lote;} public void setLote(String v){lote=v;}
    public String getObservaciones(){return observaciones;} public void setObservaciones(String v){observaciones=v;}
    public LocalDateTime getFechaAplicacion(){return fechaAplicacion;} public void setFechaAplicacion(LocalDateTime v){fechaAplicacion=v;}
}
