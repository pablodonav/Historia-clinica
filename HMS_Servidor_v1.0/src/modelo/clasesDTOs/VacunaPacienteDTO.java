/**
 * VacunaPacienteDTO.java
 * Pablo Doñate Navarro
 * v2.5 06/05/2022.
 */
package modelo.clasesDTOs;

import com.google.gson.Gson;
import java.util.Date;

/**
 * Clase que contiene los atributos y getter-setter 
 * de las vacunas administradas a un paciente.
 * 
 */
public class VacunaPacienteDTO {
    private int id;
    private Date fecha;
    private String nss_pac;
    private int codigo_vac;

    /**
     * Crea un nuevo medicamento de un paciente.
     * 
     * @param id
     * @param fecha
     * @param codigo_vac
     * @param nss_pac 
     */
    public VacunaPacienteDTO(int id, Date fecha, String nss_pac, int codigo_vac) {
        this.id = id;
        this.fecha = fecha;
        this.nss_pac = nss_pac;
        this.codigo_vac = codigo_vac;
    }

    /**
     * Devuelve el identificador
     * de la vacuna administrada.
     * 
     * @return 
     */
    public int getId() {
        return id;
    }

    /**
     * Modifica el identificador 
     * de la vacuna administrada.
     * 
     * @param id 
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Devuelve la fecha en la que
     * fue administrada la vacuna.
     * 
     * @return 
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * Modifica la fecha en la que
     * ha sido administrada la vacuna.
     * 
     * @param fecha
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * Devuelve el identficador del paciente asociado.
     * 
     * @return 
     */
    public String getNss_pac() {
        return nss_pac;
    }

    /**
     * Modifica el identificador del paciente.
     * 
     * @param nss_pac 
     */
    public void setNss_pac(String nss_pac) {
        this.nss_pac = nss_pac;
    }

    /**
     * Devuelve el codigo de la vacuna.
     * 
     * @return 
     */
    public int getCodigo_vac() {
        return codigo_vac;
    }

    /**
     * Modifica el codigo de la vacuna.
     * 
     * @param codigo_vac
     */
    public void setCodigo_vac(int codigo_vac) {
        this.codigo_vac = codigo_vac;
    }
    
    /**
     * Obtiene el JSON de la vacuna del paciente.
     * 
     * @return String
     */
    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
