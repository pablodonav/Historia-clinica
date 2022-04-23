/**
 * EpisodioDeAtencionDTO.java
 * Adnana Catrinel Dragut
 * v1.0 21/04/2022.
 * 
 */
package modelo.clasesDTOs;

import com.google.gson.Gson;
import java.sql.Date;

/**
 * Clase que contiene los atributos y getter-setter de un EpisodioDeAtencion.
 * 
 */
public class EpisodioDeAtencionDTO {
    private int id;
    private Date fecha;
    private String motivo = null;
    private String diagnostico = null;

    /**
     *
     * @param _id
     * @param _fecha
     * @param _motivo
     */
    public EpisodioDeAtencionDTO(Date _fecha, String _motivo) {
        this.fecha = _fecha;
        this.motivo = _motivo;
    }

    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param _id
     */
    public void setId(int _id) {
        this.id = _id;
    }

    /**
     *
     * @return
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     *
     * @param _fecha
     */
    public void setFecha(Date _fecha) {
        this.fecha = _fecha;
    }

    /**
     *
     * @return
     */
    public String getMotivo() {
        return motivo;
    }

    /**
     *
     * @param _motivo
     */
    public void setMotivo(String _motivo) {
        this.motivo = _motivo;
    }

    /**
     *
     * @return
     */
    public String getDiagnostico() {
        return diagnostico;
    }

    /**
     *
     * @param _diagnostico
     */
    public void setDiagnostico(String _diagnostico) {
        this.diagnostico = _diagnostico;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return id + " " + fecha + " " + motivo + " " + diagnostico;
    }
    
    /**
     * Obtiene el JSON del episodio de atención
     * 
     * @return String
     */
    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    } 
}
