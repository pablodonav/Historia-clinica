/**
 * Ubicacion.java
 * Adnana Catrinel Dragut
 * v2.0 29/04/2022.
 * 
 */
package modelo.clasesDTOs;

/**
 * Clase que contiene los atributos y getter-setter de una Ubicacion.
 * 
 */
public class Ubicacion {
    private String localidad = null;
    private String centroHospitalario = null;
    private String sala = null;

    /**
     * Crea una Ubicación
     * 
     * @param _localidad
     * @param _centro
     * @param _sala 
     */
    public Ubicacion(String _localidad, String _centro, String _sala) {
        this.localidad = _localidad;
        this.centroHospitalario = _centro;
        this.sala = _sala;
    }
    
    /**
     * Obtiene la localidad de una ubicación
     * 
     * @return String
     */
    public String getLocalidad() {
        return localidad;
    }

    /**
     * Asigna la localidad de una ubicación
     * 
     * @param _localidad 
     */
    public void setLocalidad(String _localidad) {
        this.localidad = _localidad;
    }

    /**
     * Obtiene el centro de una ubicación
     * 
     * @return String
     */
    public String getCentroHospitalario() {
        return centroHospitalario;
    }

    /**
     * Asigna el centro de una ubicación
     * 
     * @param _centroHospitalario 
     */
    public void setCentroHospitalario(String _centroHospitalario) {
        this.centroHospitalario = _centroHospitalario;
    }

    /**
     * Obtiene la sala de una ubicación
     * 
     * @return String
     */
    public String getSala() {
        return sala;
    }

    /**
     * Asigna la sala de una ubicación
     * 
     * @param _sala 
     */
    public void setSala(String _sala) {
        this.sala = _sala;
    }
    
    /**
     * Obtiene el toString de la ubicación
     * 
     * @return String
     */
    @Override
    public String toString() {
        return localidad + " " + centroHospitalario + " " + sala;
    }  
}
