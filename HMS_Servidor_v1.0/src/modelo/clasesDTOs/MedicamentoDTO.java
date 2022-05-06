/**
 * MedicamentoDTO.java
 * Pablo Doñate Navarro
 * v2.5 06/05/2022.
 */
package modelo.clasesDTOs;

import com.google.gson.Gson;

/**
 * Clase que contiene los atributos y getter-setter de un medicamento.
 * 
 */
public class MedicamentoDTO {
    private int codigo;
    private String nombre;
    
    /**
     * Crea un medicamento.
     * 
     * @param _codigo
     * @param _nombre
     */
    public MedicamentoDTO(int _codigo, String _nombre) {
        this.codigo = _codigo;
        this.nombre = _nombre;
    }
    
    /**
     * Devuelve el codigo de un medicamento.
     * 
     * @return codigo
     */
    public int obtenerCodigo() {
        return codigo;
    }
    
    /**
     * Devuelve el nombre de un medicamento.
     * 
     * @return nombre
     */
    public String obtenerNombre() {
        return nombre;
    }
    
    /**
     * Obtiene el toString del medicamento
     * 
     * @return String
     */
    @Override
    public String toString(){
        String s = codigo + " " + nombre + "\n";
        return s;
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
