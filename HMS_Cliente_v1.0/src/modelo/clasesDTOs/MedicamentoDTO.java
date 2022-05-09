/**
 * MedicamentoDTO.java
 * Adnana Catrinel Dragut
 * v1.0 07/04/2022.
 * 
 */
package modelo.clasesDTOs;

import com.google.gson.Gson;

/**
 * Clase que contiene los atributos y getter-setter de un Medicamento.
 * 
 */
public class MedicamentoDTO {
    private int codigo;
    private String nombre;

    /**
     * Crea un medicamento
     * 
     * @param _codigo
     * @param _nombre 
     */
    public MedicamentoDTO(int _codigo, String _nombre) {
        this.codigo = _codigo;
        this.nombre = _nombre;
    }

    /**
     * Obtiene el codigo de un medicamento
     * 
     * @return int
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Asigna el código a un medicamento
     * 
     * @param _codigo 
     */
    public void setCodigo(int _codigo) {
        this.codigo = _codigo;
    }

    /**
     * Obtiene el nombre de un medicamento
     * 
     * @return String
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Asigna el nombre a un medicamento
     * 
     * @param _nombre 
     */
    public void setNombre(String _nombre) {
        this.nombre = _nombre;
    }
    
    /**
     * Obtiene el toString del medicamento
     * 
     * @return String
     */
    @Override
    public String toString() {
        return codigo + " " + nombre;
    }  
    
    /**
     * Obtiene el JSON del medicamento
     * 
     * @return String
     */
    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
