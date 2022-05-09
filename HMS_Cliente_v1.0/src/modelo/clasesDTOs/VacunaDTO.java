/**
 * VacunaDTO.java
 * Adnana Catrinel Dragut
 * v1.0 02/04/2022.
 * 
 */
package modelo.clasesDTOs;

import com.google.gson.Gson;

/**
 * Clase que contiene los atributos y getter-setter de una Vacuna.
 * 
 */
public class VacunaDTO {
    private int codigo;
    private String nombre;

    /**
     * Crea una Vacuna
     * 
     * @param _codigo
     * @param _nombre 
     */
    public VacunaDTO(int _codigo, String _nombre) {
        this.codigo = _codigo;
        this.nombre = _nombre;
    }

    /**
     * Obtiene el codigo de una vacuna
     * 
     * @return int
     */
    public int getCodigo() {
        return codigo;
    }

    /**
     * Asigna el codigo de una vacuna
     * 
     * @param _codigo 
     */
    public void setCodigo(int _codigo) {
        this.codigo = _codigo;
    }

    /**
     * Obtiene el nombre de una vacuna
     * 
     * @return String
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Asigna el nombre a una vacuna
     * 
     * @param _nombre 
     */
    public void setNombre(String _nombre) {
        this.nombre = _nombre;
    }
    
    /**
     * Obtiene el toString de la vacuna
     * 
     * @return String
     */
    @Override
    public String toString() {
        return codigo + " " + nombre;
    }  
    
    /**
     * Obtiene el JSON de la vacuna
     * 
     * @return String
     */
    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
