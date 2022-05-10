/**
 * HistoriaDTO.java
 * Adnana Catrinel Dragut
 * v1.0 09/05/2022.
 * 
 */
package modelo.clasesDTOs;

import com.google.gson.Gson;
import java.util.List;

/**
 * Clase que contiene los atributos y getter-setter de la historia de un paciente.
 * 
 */
public class HistoriaPacienteDTO {
    private List<EpisodioDeAtencionDTO> episodios;
    private List<MedicamentoPacienteDTO> medicamentos;
    private List<VacunaPacienteDTO> vacunas;

    /**
     * Crea la historia de un paciente
     * 
     * @param _episodios
     * @param _medicamentos
     * @param _vacunas 
     */
    public HistoriaPacienteDTO(List<EpisodioDeAtencionDTO> _episodios, 
            List<MedicamentoPacienteDTO> _medicamentos, List<VacunaPacienteDTO> _vacunas) {
        this.episodios = _episodios;
        this.medicamentos = _medicamentos;
        this.vacunas = _vacunas;
    }

    /**
     * Obtiene todos los episodios de un paciente
     * 
     * @return List<EpisodioDeAtencionDTO>
     */
    public List<EpisodioDeAtencionDTO> getEpisodios() {
        return episodios;
    }

    /**
     * Obtiene la receta electrónica con todos los medicamentos de un paciente
     * 
     * @return List<MedicamentoPacienteDTO>
     */
    public List<MedicamentoPacienteDTO> getMedicamentos() {
        return medicamentos;
    }

    /**
     * Obtiene todas las vacunas administradas a un paciente
     * 
     * @return List<VacunaPacienteDTO>
     */
    public List<VacunaPacienteDTO> getVacunas() {
        return vacunas;
    }
    
    /**
     * Obtiene el JSON del paciente
     * 
     * @return String
     */
    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
