/**
 * SanitarioDTO.java
 * Adnana Catrinel Dragut
 * v2.0 02/04/2022.
 * 
 */

package modelo.clasesDTOs;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

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
     * Obtiene un vector de Object con los atributos de la clase sanitario
     * 
     * @return Object[]
     */
    public Object[] toArray(){
        List<Object> object = new ArrayList();
        
        object.add(nombre);
        object.add(apellido1);
        object.add(apellido2);
        object.add(dni);
        object.add(telefono);
        object.add(correoElectronico);
        object.add(puestoTrabajo);
        
        return object.toArray();
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
    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
