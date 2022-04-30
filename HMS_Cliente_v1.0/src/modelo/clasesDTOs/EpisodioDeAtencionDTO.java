/**
 * EpisodioDeAtencionDTO.java
 * Adnana Catrinel Dragut
 * v1.0 21/04/2022.
 * 
 */
package modelo.clasesDTOs;

import com.google.gson.Gson;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
     * Crea un episodio de atención
     * 
     * @param _id
     * @param _fecha
     * @param _motivo
     * @param _diagnostico
     */
    public EpisodioDeAtencionDTO(int _id, Date _fecha, String _motivo, String _diagnostico) {
        this.id = _id;
        this.fecha = _fecha;
        this.motivo = _motivo;
        this.diagnostico = _diagnostico;
    }

    /**
     * Obtiene el id del episodio de atención
     * 
     * @return int
     */
    public int getId() {
        return id;
    }

    /**
     * Asigna el id del episodio de atención
     * @param _id
     */
    public void setId(int _id) {
        this.id = _id;
    }

    /**
     * Obtiene la fecha del episodio de atención
     * 
     * @return Date
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * Asigna la fecha del episodio de atención
     * 
     * @param _fecha
     */
    public void setFecha(Date _fecha) {
        this.fecha = _fecha;
    }

    /**
     * Obtiene el motivo del episodio de atención
     * 
     * @return String
     */
    public String getMotivo() {
        return motivo;
    }

    /**
     * Asigna el motivo del episodio de atención
     * 
     * @param _motivo
     */
    public void setMotivo(String _motivo) {
        this.motivo = _motivo;
    }

    /**
     * Obtiene el diagnóstico del episodio de atención
     * 
     * @return String
     */
    public String getDiagnostico() {
        return diagnostico;
    }

    /**
     * Asigna el diagnóstico del episodio de atención
     * 
     * @param _diagnostico
     */
    public void setDiagnostico(String _diagnostico) {
        this.diagnostico = _diagnostico;
    }

    /**
     * Obtiene un vector de Object con los atributos de la clase
     * episodio de atención
     * 
     * @return Object[]
     */
    public Object[] toArray(){
        List<Object> object = new ArrayList();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        String strFecha= formatter.format(fecha);  
        
        object.add(id);
        object.add(strFecha);
        object.add(motivo);
        object.add(diagnostico);
        
        return object.toArray();
    }
    
    /**
     * Obtiene el toString del episodio de atención
     * 
     * @return String
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
