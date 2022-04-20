/**
 * EpisodioAtencionDTO.java
 * Pablo Doñate Navarro
 * v1.0 10/04/2022.
 */
package modelo.clasesDTOs;

import com.google.gson.Gson;
import java.sql.Date;

/**
 * Clase que contiene los atributos y getter-setter de un episodio de atención.
 * 
 */
public class EpisodioAtencionDTO {
    private int id;
    private Date fecha;
    private String motivo;
    private String diagnostico;
    private String nss_pac;
    
    /**
     * Crea un Episodio de Atención.
     * 
     * @param id
     * @param fecha
     * @param motivo
     * @param diagnostico
     * @param nss_pac 
     */
    public EpisodioAtencionDTO(int id, Date fecha, String motivo, String diagnostico, String nss_pac) {
        this.id = id;
        this.fecha = fecha;
        this.motivo = motivo;
        this.diagnostico = diagnostico;
        this.nss_pac = nss_pac;
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
     * Obtiene el num. de la seguridad social
     * del paciente asociado al episodio.
     * 
     * @return 
     */
    public String getNss_pac() {
        return nss_pac;
    }

    @Override
    public String toString() {
        return "EpisodioAtencionDTO{" + "id=" + id + ", fecha=" + fecha 
                + ", motivo=" + motivo + ", diagnostico=" + diagnostico 
                + ", nss_pac=" + nss_pac + '}';
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
