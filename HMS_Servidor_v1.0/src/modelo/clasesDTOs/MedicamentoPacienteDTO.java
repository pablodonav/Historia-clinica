/**
 * MedicamentoPacienteDTO.java
 * Pablo Doñate Navarro
 * v2.6 09/05/2022.
 */
package modelo.clasesDTOs;

import com.google.gson.Gson;
import java.util.Date;

/**
 * Clase que contiene los atributos y getter-setter del 
 * medicamento de la receta de un paciente.
 * 
 */
public class MedicamentoPacienteDTO {
    private int id;
    private MedicamentoDTO medicamento;
    private Date fechaInicio;
    private Date fechaFin;
    /**
     * Crea un nuevo medicamento de un paciente.
     * 
     * @param id
     * @param medicamento
     * @param fecha_inicio
     * @param fecha_fin 
     */
    public MedicamentoPacienteDTO(int id, MedicamentoDTO medicamento, Date fecha_inicio, Date fecha_fin) {
        this.id = id;
        this.fechaInicio = fecha_inicio;
        this.fechaFin = fecha_fin;
        this.medicamento = medicamento;
    }

    /**
     * Devuelve el identificador del medicamento
     * de la receta del paciente.
     * 
     * @return 
     */
    public int getId() {
        return id;
    }

    /**
     * Define el identificador del medicamento de
     * la receta del paciente.
     * 
     * @param id 
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Devuelve la fecha de incio de tratamiento
     * del medicamento de la receta del paciente.
     * 
     * @return 
     */
    public Date getFecha_inicio() {
        return fechaInicio;
    }

    /**
     * Devuelve la fecha de fin de tratamiento
     * del medicamento de la receta del paciente.
     * 
     * @return 
     */
    public Date getFecha_fin() {
        return fechaFin;
    }

    /**
     * Devuelve el medicamento de la receta del paciente.
     * 
     * @return 
     */
    public MedicamentoDTO getMedicamento() {
        return medicamento;
    }
    
    /**
     * Obtiene el JSON del medicamento del paciente.
     * 
     * @return String
     */
    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
