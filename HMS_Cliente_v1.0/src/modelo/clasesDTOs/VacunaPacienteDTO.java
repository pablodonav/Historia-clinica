/**
 * VacunaPacienteDTO.java
 * Adnana Catrinel Dragut
 * v1.0 02/04/2022.
 * 
 */
package modelo.clasesDTOs;

import com.google.gson.Gson;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Clase que contiene los atributos y getter-setter de una VacunaPaciente.
 * 
 */
public class VacunaPacienteDTO {
    private int id;
    private VacunaDTO vacuna;
    private Date fecha;

    /**
     * Crea una VacunaPacienteDTO
     * 
     * @param _vacuna
     * @param _fecha 
     */
    public VacunaPacienteDTO(VacunaDTO _vacuna, Date _fecha) {
        this.vacuna = _vacuna;
        this.fecha = _fecha;
    }
    
    /**
     * Obtiene el id de una vacuna de un paciente
     * 
     * @return int
     */
    public int getId() {
        return id;
    }

    /**
     * Asigna el id de una vacuna de un paciente
     * 
     * @param _id 
     */
    public void setId(int _id) {
        this.id = _id;
    }
    
    /**
     * Obtiene vacuna de un paciente
     * 
     * @return VacunaDTO
     */
    public VacunaDTO getVacuna() {
        return vacuna;
    }

    /**
     * Asigna una vacuna a un paciente
     * 
     * @param _vacuna 
     */
    public void setVacuna(VacunaDTO _vacuna) {
        this.vacuna = _vacuna;
    }

    /**
     * Obtiene la fecha de vacunación de una vacuna de un paciente
     * 
     * @return Date
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * Asigna la fecha de vacunación de una vacuna de un paciente
     * 
     * @param fecha 
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    /**
     * Obtiene un vector de Object con atributos de la clase vacuna
     * 
     * @return Object[]
     */
    public Object[] toArray(){
        List<Object> object = new ArrayList();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
        String strFecha= formatter.format(fecha);
        
        object.add(vacuna.getCodigo());
        object.add(vacuna.getNombre());
        object.add(strFecha);
        
        return object.toArray();
    }
    
    /**
     * Obtiene el toString de la vacunaPaciente
     * 
     * @return String
     */
    @Override
    public String toString() {
        return vacuna + " " + fecha;
    }  
    
    /**
     * Obtiene el JSON de la vacunaPaciente
     * 
     * @return String
     */
    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
