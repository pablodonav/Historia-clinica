/**
 * SanitarioDTO.java
 * Adnana Catrinel Dragut
 * v1.0 02/04/2022.
 * 
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
    private String dni;
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
        super(_correoElectronico, _contraseña);
        this.nombre = _nombre;
        this.apellido1 = _apellido1;
        this.apellido2 = _apellido2;
        this.dni = _dni;
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
     * Asigna el nombre del sanitario
     * 
     * @param _nombre
     */
    public void setNombre(String _nombre) {
        this.nombre = _nombre;
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
     * Asigna el primer apellido del sanitario
     * 
     * @param _apellido1
     */
    public void setApellido1(String _apellido1) {
        this.apellido1 = _apellido1;
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
     * Asigna el segundo apellido del sanitario
     * 
     * @param _apellido2
     */
    public void setApellido2(String _apellido2) {
        this.apellido2 = _apellido2;
    }

    /**
     * Obtiene el dni del sanitario
     * 
     * @return String
     */
    public String getDni() {
        return dni;
    }

    /**
     * Asigna el dni del sanitario
     * 
     * @param _dni
     */
    public void setDni(String _dni) {
        this.dni = _dni;
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
     * Asigna el teléfono del sanitario
     * 
     * @param _telefono
     */
    public void setTelefono(int _telefono) {
        this.telefono = _telefono;
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
     * Asigna el puesto de trabajo del sanitario
     * 
     * @param _puestoTrabajo
     */
    public void setPuestoTrabajo(String _puestoTrabajo) {
        this.puestoTrabajo = _puestoTrabajo;
    }
    
    /**
     * Obtiene el toString del sanitario
     * 
     * @return String
     */
    @Override
    public String toString() {
        return "SanitarioDTO{" + "nombre=" + nombre + ", apellido1=" + apellido1 + ", apellido2=" + 
                apellido2 + ", dni=" + dni + ", telefono=" + telefono + ", correoElectronico=" + 
                correoElectronico + ", contrase\u00f1a=" + contraseña + ", puestoTrabajo=" + puestoTrabajo + '}';
    }  
    
    /**
     * Obtiene el JSON del sanitario
     * 
     * @return String
     */
    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
