/**
 * CitaDTO.java
 * Pablo Doñate Navarro
 * v1.0 02/04/2022.
 */
package modelo.clasesDTOs;

import com.google.gson.Gson;
import java.sql.Date;
import java.sql.Time;

/**
 * Clase que contiene los atributos y getter-setter de una cita.
 * 
 */
public class CitaDTO {
    private int codigo;
    private String descripcion;
    private String sala;
    private String centro;
    private String localidad;
    private Time hora;
    private Date fecha;
    private String nss_pac;
    private String dni_sanit;

    /**
     * Crea una nueva cita.
     * 
     * @param codigo
     * @param descripcion
     * @param sala
     * @param centro
     * @param localidad
     * @param hora
     * @param fecha
     * @param nss_pac
     * @param dni_sanit 
     */
    public CitaDTO(int codigo, String descripcion, String sala, String centro, String localidad, Time hora, Date fecha, String nss_pac, String dni_sanit) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.sala = sala;
        this.centro = centro;
        this.localidad = localidad;
        this.hora = hora;
        this.fecha = fecha;
        this.nss_pac = nss_pac;
        this.dni_sanit = dni_sanit;
    }

    /**
     * Devuelve el identificador de la cita.
     * 
     * @return 
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Devuelve la descripcion
     * de la cita del paciente.
     * 
     * @return 
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Devuelve la sala del centro en la que
     * se llevará a cabo la cita.
     * 
     * @return 
     */
    public String getSala() {
        return sala;
    }

    /**
     * Devuelve el centro sanitario en el que
     * se llevará a cabo la cita.
     * 
     * @return 
     */
    public String getCentro() {
        return centro;
    }

    /**
     * Devuelve la localidad en la que
     * se llevará a cabo la cita.
     * 
     * @return 
     */
    public String getLocalidad() {
        return localidad;
    }

    /**
     * Devuelve la hora de la cita
     * del paciente.
     * 
     * @return 
     */
    public Time getHora() {
        return hora;
    }

    /**
     * Obtiene la fecha (día/mes/año)
     * de la cita del paciente.
     * 
     * @return 
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * Obtiene el num. de la seguridad social
     * del paciente asociado a la cita.
     * 
     * @return 
     */
    public String getNss_pac() {
        return nss_pac;
    }

    /**
     * Obtiene el dni del sanitario
     * asociado a la cita.
     * 
     * @return 
     */
    public String getDni_sanit() {
        return dni_sanit;
    }
    
    /**
     * Obtiene el toString del cita
     * 
     * @return String
     */
    @Override
    public String toString() {
        return "CitaDTO{" + "codigo=" + codigo + ", descripcion=" + descripcion + ", sala=" + sala + ", centro=" + centro + ", localidad=" + localidad + ", hora=" + hora + ", fecha=" + fecha + ", nss_pac=" + nss_pac + ", dni_sanit=" + dni_sanit + '}';
    }
    
    /**
     * Obtiene el JSON de la cita.
     * 
     * @return String
     */
    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
