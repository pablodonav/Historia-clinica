/**
 * EpisodioAtencionDTO.java
 * Pablo Doñate Navarro
 * v1.0 01/05/2022.
 */
package modelo.clasesDTOs;

import com.google.gson.Gson;
import java.util.Date;

/**
 * Clase que contiene los atributos y getter-setter de un episodio de atención.
 * 
 */
public class EpisodioAtencionDTO {
    private int id;
    private Date fecha;
    private String motivo;
    private String diagnostico;
    
    /**
     * Crea un Episodio de Atención.
     * 
     * @param id
     * @param fecha
     * @param motivo
     * @param diagnostico 
     */
    public EpisodioAtencionDTO(int id, Date fecha, String motivo, String diagnostico) {
        this.id = id;
        this.fecha = fecha;
        this.motivo = motivo;
        this.diagnostico = diagnostico;
    }

    /**
     * Obtiene el identificador del episodio.
     * 
     * @return 
     */
    public int getId() {
        return id;
    }

    /**
     * Modifica el identificador del episodio.
     * 
     * @param _id 
     */
    public void setId(int _id) {
        this.id = _id;
    }
    
    /**
     * Obtiene la fecha del episodio.
     * 
     * @return 
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * Obtiene el motivo del episodio.
     * 
     * @return 
     */
    public String getMotivo() {
        return motivo;
    }

    /**
     * Obtiene el diagnostico del episodio.
     * 
     * @return 
     */
    public String getDiagnostico() {
        return diagnostico;
    }
    
    /**
     * Obtiene el toString del episodio.
     * 
     * @return String
     */
    @Override
    public String toString() {
        return "EpisodioAtencionDTO{" + "id=" + id + ", fecha=" + fecha 
                + ", motivo=" + motivo + ", diagnostico=" + diagnostico + '}';
    }
    
    /**
     * Obtiene el JSON del episodio
     * 
     * @return String
     */
    public String toJson(){
        Gson gson = new Gson();
        
        return gson.toJson(this);
    }
}
