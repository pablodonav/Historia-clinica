/**
 * CitaDTO.java
 * Pablo Doñate Navarro
 * v2.4 01/05/2022.
 */
package modelo.clasesDTOs;

import com.google.gson.Gson;
import java.util.Date;

/**
 * Clase que contiene los atributos y getter-setter de una cita.
 * 
 */
public class CitaDTO {
    private int identificador;
    private String dniMedico;
    private UbicacionDTO ubicacion;
    private Date fecha;
    private Date tiempo;
    private String descripcion;

    /**
     * Crea una Cita
     * 
     * @param _identificador
     * @param _dniMedico
     * @param _ubicacion
     * @param _fecha
     * @param _hora
     * @param _descripcion 
     */
    public CitaDTO(int _identificador, String _dniMedico, UbicacionDTO _ubicacion, Date _fecha, Date _hora, String _descripcion) {
        this.identificador = _identificador;
        this.dniMedico = _dniMedico;
        this.ubicacion = _ubicacion;
        this.fecha = _fecha;
        this.tiempo = _hora;
        this.descripcion = _descripcion;
    }

    /**
     * Obtiene el identificador de una cita
     * 
     * @return int
     */
    public int getIdentificador() {
        return identificador;
    }

    /**
     * Asigna el identificador de una cita
     * 
     * @param _identificador 
     */
    public void setIdentificador(int _identificador) {
        this.identificador = _identificador;
    }

    /**
     * Obtiene el dni del médico que atiende una cita
     * 
     * @return String
     */
    public String getDniMedico() {
        return dniMedico;
    }

    
    /**
     * Asigna un médico a una cita
     * 
     * @param _dniMedico 
     */
    public void setDniMedico(String _dniMedico) {
        this.dniMedico = _dniMedico;
    }
    
    /**
     * Obtiene la ubicación de una cita
     * 
     * @return Ubicacion
     */
    public UbicacionDTO getUbicacion() {
        return ubicacion;
    }

    /**
     * Asigna la ubicación de una cita
     * 
     * @param _ubicacion 
     */
    public void setUbicacion(UbicacionDTO _ubicacion) {
        this.ubicacion = _ubicacion;
    }

    /**
     * Obtiene la fecha de una cita
     * 
     * @return Date
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * Asigna la fecha de una cita
     * 
     * @param _fecha 
     */
    public void setFecha(Date _fecha) {
        this.fecha = _fecha;
    }

    /**
     * Obtiene la tiempo de una cita
     * 
     * @return Date
     */
    public Date getTiempo() {
        return tiempo;
    }

    /**
     * Asigna la tiempo de una cita
     * 
     * @param tiempo 
     */
    public void setTiempo(Date tiempo) {
        this.tiempo = tiempo;
    }

    /**
     * Obtiene la descripción de una cita
     * 
     * @return String
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Asigna la descripción de una cita
     * 
     * @param descripcion 
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    /**
     * Obtiene el toString de la cita
     * 
     * @return String
     */
    @Override
    public String toString() {
        return identificador + " " + dniMedico + " " + ubicacion + 
            " " + fecha + " " + tiempo + " " + descripcion;
    }  
    
    /**
     * Obtiene el JSON de la cita
     * 
     * @return String
     */
    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
