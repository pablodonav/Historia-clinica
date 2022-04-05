/**
 * UsuarioDTO.java
 * Adnana Catrinel Dragut
 * v1.0 28/03/2022.
 * 
 */
package modelo.clasesDTOs;

import com.google.gson.Gson;

/**
 * Clase que contiene los atributos y getter-setter de un Usuario.
 * 
 */
public class UsuarioDTO {
    protected String dni;
    protected String correoElectronico;
    protected String contraseña;
    private boolean admin;

    /**
     * Crea un Usuario
     * 
     * @param _correoElectronico
     * @param _contraseña
     */
    public UsuarioDTO(String _dni, String _correoElectronico, String _contraseña) {
        this.dni = _dni;
        this.correoElectronico = _correoElectronico;
        this.contraseña = _contraseña;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }
    
    /**
     * Obtiene el correo del usuario
     * 
     * @return String
     */
    public String getCorreoElectronico() {
        return correoElectronico;
    }

    /**
     * Asigna el correo del usuario
     * 
     * @param _correoElectronico
     */
    public void setCorreoElectronico(String _correoElectronico) {
        this.correoElectronico = _correoElectronico;
    }

    /**
     * Obtiene la contraseña del usuario
     * 
     * @return String
     */
    public String getContraseña() {
        return contraseña;
    }

    /**
     * Asigna la contraseña del usuario
     * 
     * @param _contraseña
     */
    public void setContraseña(String _contraseña) {
        this.contraseña = _contraseña;
    }

    /**
     * Devuelve verdadero si el usuario es el administrador, 
     * o falso en caso contrario
     * 
     * @return boolean
     */
    public boolean isAdmin() {
        return admin;
    }

    /**
     * Asigna verdadero si el usuario es administrador,
     * o falso en caso contrario
     * 
     * @param _esAdmin
     */
    public void setAdmin(boolean _esAdmin) {
        this.admin = _esAdmin;
    }

    /**
     * Obtiene el toString del usuario
     * 
     * @return String
     */
    @Override
    public String toString() {
        return "UsuarioDTO{" + "correoElectronico=" + correoElectronico + 
            "," + "contrase\u00f1a=" + contraseña + ", admin=" + admin + '}';
    }
    
    
    
    /**
     * Obtiene el JSON del usuario
     * 
     * @return String
     */
    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
