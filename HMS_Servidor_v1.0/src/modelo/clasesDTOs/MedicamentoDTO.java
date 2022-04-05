/**
 * MedicamentoDTO.java
 * Pablo Doñate Navarro
 * v1.0 02/04/2022.
 */
package modelo.clasesDTOs;

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
     * Sobreescribe toString.
     *
     */
    @Override
    public String toString(){
        String s = codigo + " " + nombre + "\n";
        return s;
    }
}
