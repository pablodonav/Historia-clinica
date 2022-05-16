/**
 * HistoriaPacienteDTO.java
 * Pablo Doñate Navarro
 * v2.6 09/05/2022.
 */
package modelo.clasesDTOs;

import com.google.gson.Gson;
import java.util.List;

/**
 * Clase que contiene los atributos de una historia de un paciente.
 * 
 */
public class HistoriaPacienteDTO {
    private List<EpisodioAtencionDTO> episodios;
    private List<MedicamentoPacienteDTO> medicamentos;
    private List<VacunaPacienteDTO> vacunas;

    /**
     * Crea la historia de un paciente.
     * 
     * @param episodios
     * @param medicamentos
     * @param vacunas 
     */
    public HistoriaPacienteDTO(List<EpisodioAtencionDTO> episodios, List<MedicamentoPacienteDTO> medicamentos, List<VacunaPacienteDTO> vacunas) {
        this.episodios = episodios;
        this.medicamentos = medicamentos;
        this.vacunas = vacunas;
    }
    
    /**
     * Obtiene el JSON de la historia del paciente.
     * 
     * @return String
     */
    public String toJson(){
        Gson gson = new Gson();
        
        return gson.toJson(this);
    }
}
