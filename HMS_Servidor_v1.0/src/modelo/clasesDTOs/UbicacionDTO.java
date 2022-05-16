/**
 * UbicacionDTO.java
 * Pablo Doñate Navarro
 * v2.6 09/05/2022.
 * 
 */
package modelo.clasesDTOs;

import com.google.gson.Gson;

/**
 * Clase que contiene los atributos y getter-setter de una Ubicacion.
 * 
 */
public class UbicacionDTO {
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
    public UbicacionDTO(String _localidad, String _centro, String _sala) {
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
