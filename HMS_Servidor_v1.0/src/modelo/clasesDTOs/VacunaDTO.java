/**
 * VacunaDTO.java
 * Pablo Doñate Navarro
 * v1.0 02/04/2022.
 */
package modelo.clasesDTOs;

/**
 * Clase que contiene los atributos y getter-setter de una vacuna.
 * 
 */
public class VacunaDTO {
    private int codigo;
    private String nombre;
    
    /**
     * Crea una vacuna.
     * 
     * @param _codigo
     * @param _nombre
     */
    public VacunaDTO(int _codigo, String _nombre) {
        this.codigo = _codigo;
        this.nombre = _nombre;
    }
    
    /**
     * Devuelve el codigo de una VacunaDTO.
     * 
     * @return codigo
     */
    public int getCodigo() {
        return codigo;
    }
    
    /**
     * Devuelve el nombre de una VacunaDTO.
     * 
     * @return nombre
     */
    public String getNombre() {
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

