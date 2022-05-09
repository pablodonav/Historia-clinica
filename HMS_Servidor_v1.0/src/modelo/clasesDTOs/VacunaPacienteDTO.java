/**
 * VacunaPacienteDTO.java
 * Pablo Doñate Navarro
 * v2.6 09/05/2022.
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
    private VacunaDTO vacuna;
    private Date fecha;

    /**
     * Crea un nuevo medicamento de un paciente.
     * 
     * @param id
     * @param vacuna
     * @param fecha 
     */
    public VacunaPacienteDTO(int id, VacunaDTO vacuna, Date fecha) {
        this.id = id;
        this.fecha = fecha;
        this.vacuna = vacuna;
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
     * Devuelve la vacuna del paciente.
     * 
     * @return 
     */
    public VacunaDTO getVacuna() {
        return vacuna;
    }

    /**
     * Modifica la vacuna del paciente.
     * 
     * @param vacuna 
     */
    public void setVacuna(VacunaDTO vacuna) {
        this.vacuna = vacuna;
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
