/**
 * MedicamentoPacienteDTO.java
 * Pablo Doñate Navarro
 * v1.0 29/04/2022.
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
    private Date fecha_inicio;
    private Date fecha_fin;
    private String nss_pac;
    private int codigo_medic;

    /**
     * Crea un nuevo medicamento de un paciente.
     * 
     * @param id
     * @param fecha_inicio
     * @param fecha_fin
     * @param nss_pac
     * @param codigo_medic 
     */
    public MedicamentoPacienteDTO(int id, Date fecha_inicio, Date fecha_fin, String nss_pac, int codigo_medic) {
        this.id = id;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.nss_pac = nss_pac;
        this.codigo_medic = codigo_medic;
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
        return fecha_inicio;
    }

    /**
     * Devuelve la fecha de fin de tratamiento
     * del medicamento de la receta del paciente.
     * 
     * @return 
     */
    public Date getFecha_fin() {
        return fecha_fin;
    }

    /**
     * Devuelve el nss del paciente asociado.
     * 
     * @return 
     */
    public String getNss_pac() {
        return nss_pac;
    }

    /**
     * Devuelve el codigo del medicamento 
     * que está en la receta del paciente.
     * 
     * @return 
     */
    public int getCodigo_medic() {
        return codigo_medic;
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
