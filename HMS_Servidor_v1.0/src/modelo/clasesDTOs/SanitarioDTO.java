/**
 * SanitarioDTO.java
 * Pablo Doñate Navarro
 * v1.0 29/04/2022.
 */
package modelo.clasesDTOs;

import com.google.gson.Gson;

/**
 * Clase que contiene los atributos y getter-setter de un Sanitario.
 * 
 */
public class SanitarioDTO extends UsuarioDTO{
    private String nombre;
    private String apellido1;
    private String apellido2;
    private int telefono;
    private String puestoTrabajo;

    /**
     * Crea un Sanitario
     * 
     * @param _nombre
     * @param _apellido1
     * @param _apellido2
     * @param _dni
     * @param _telefono
     * @param _correoElectronico
     * @param _contraseña
     * @param _puestoTrabajo
     */
    public SanitarioDTO(String _nombre, String _apellido1, String _apellido2, 
            String _dni, int _telefono, String _correoElectronico, 
            String _contraseña, String _puestoTrabajo) {
        super(_dni, _correoElectronico, _contraseña);
        this.nombre = _nombre;
        this.apellido1 = _apellido1;
        this.apellido2 = _apellido2;
        this.telefono = _telefono;
        this.puestoTrabajo = _puestoTrabajo;
    }

    /**
     * Obtiene el nombre del sanitario
     * 
     * @return String
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el primer apellido del sanitario
     * 
     * @return String
     */
    public String getApellido1() {
        return apellido1;
    }

    /**
     * Obtiene el segundo apellido del sanitario
     * 
     * @return String
     */
    public String getApellido2() {
        return apellido2;
    }

    /**
     * Obtiene el teléfono del sanitario
     * 
     * @return String
     */
    public int getTelefono() {
        return telefono;
    }

    /**
     * Obtiene el puesto de trabajo del sanitario
     * 
     * @return String
     */
    public String getPuestoTrabajo() {
        return puestoTrabajo;
    }
    
    /**
     * Obtiene el toString del sanitario
     * 
     * @return String
     */
    @Override
    public String toString() {
        return nombre + " " + apellido1 + " " + apellido2 + " " + dni + " " + 
               telefono + " " + correoElectronico + " " + puestoTrabajo;
    }  
    
    /**
     * Obtiene el JSON del sanitario
     * 
     * @return String
     */
    @Override
    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    } 
}
