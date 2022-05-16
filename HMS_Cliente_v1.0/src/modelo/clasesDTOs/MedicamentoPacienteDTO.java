/**
 * MedicamentoPacienteDTO.java
 * Adnana Catrinel Dragut
 * v2.0 07/04/2022.
 * 
 */
package modelo.clasesDTOs;

import com.google.gson.Gson;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Clase que contiene los atributos y getter-setter de un MedicamentoPaciente.
 * 
 */
public class MedicamentoPacienteDTO {
    private int id;
    private MedicamentoDTO medicamento;
    private Date fechaInicio;
    private Date fechaFin;

    /**
     * Crea un MedicamentoPacienteDTO
     * 
     * @param _id
     * @param _medicamento
     * @param _fechaInicio
     * @param _fechaFin 
     */
    public MedicamentoPacienteDTO(int _id, MedicamentoDTO _medicamento, Date _fechaInicio, Date _fechaFin) {
        this.id = _id;
        this.medicamento = _medicamento;
        this.fechaInicio = _fechaInicio;
        this.fechaFin = _fechaFin;
    }

    /**
     * Obtiene el id del medicamento de un paciente
     * 
     * @return int
     */
    public int getId() {
        return id;
    }

    /**
     * Asigna el id a un medicamento de un paciente
     * 
     * @param _id 
     */
    public void setId(int _id) {
        this.id = _id;
    }
  
    /**
     * Obtiene el medicamento de un paciente
     * 
     * @return MedicamentoDTO
     */
    public MedicamentoDTO getMedicamento() {
        return medicamento;
    }

    /**
     * Asigna un medicamento a un paciente
     * 
     * @param _medicamento 
     */
    public void setMedicamento(MedicamentoDTO _medicamento) {
        this.medicamento = _medicamento;
    }

    /**
     * Obtiene la fecha de inicio del medicamento de un paciente
     * 
     * @return Date
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Asigna la fecha de inicio de un medicamento de un paciente
     * 
     * @param _fechaInicio 
     */
    public void setFechaInicio(Date _fechaInicio) {
        this.fechaInicio = _fechaInicio;
    }

    /**
     * Obtiene la fecha de fin del medicamento de un paciente
     * 
     * @return Date
     */
    public Date getFechaFin() {
        return fechaFin;
    }

    /**
     * Asigna la fecha de fin de un medicamento de un paciente
     * 
     * @param _fechaFin 
     */
    public void setFechaFin(Date _fechaFin) {
        this.fechaFin = _fechaFin;
    }
    
    /**
     * Obtiene un vector de Object con atributos de la clase medicamentoPaciente
     * 
     * @return Object[]
     */
    public Object[] toArray(){
        List<Object> object = new ArrayList();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
        String strFechaInicio = formatter.format(fechaInicio);
        String strFechaFin = formatter.format(fechaFin);
        
        object.add(medicamento.getCodigo());
        object.add(medicamento.getNombre());
        object.add(strFechaInicio);
        object.add(strFechaFin);
        
        return object.toArray();
    }
    
    /**
     * Obtiene el toString del medicamento de un paciente
     * 
     * @return String
     */
    @Override
    public String toString() {
        return medicamento + " " + fechaInicio + " " + fechaFin;
    }  
    
    /**
     * Obtiene el JSON del medicamentoPaciente
     * 
     * @return String
     */
    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
