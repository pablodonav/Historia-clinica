/**
 * PacienteDTO.java
 * Adnana Catrinel Dragut
 * v1.0 02/04/2022.
 * 
 */
package modelo.clasesDTOs;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que contiene los atributos y getter-setter de un Paciente.
 * 
 */
public class PacienteDTO {
    private String nss;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private int edad;
    private double peso;
    private double altura;
    private String alergias;
    private List<EpisodioDeAtencionDTO> episodios = new ArrayList<>();

    /**
     * Crea un Sanitario
     * 
     * @param _nss
     * @param _nombre
     * @param _apellido1
     * @param _apellido2
     * @param _alergias 
     * @param _edad
     * @param _peso
     * @param _altura
     */
    public PacienteDTO(String _nss, String _nombre, String _apellido1, String _apellido2, 
            String _alergias, int _edad, double _peso, double _altura) {
        this.nss = _nss;
        this.nombre = _nombre;
        this.apellido1 = _apellido1;
        this.apellido2 = _apellido2;
        this.edad = _edad;
        this.peso = _peso;
        this.altura = _altura;
        this.alergias = _alergias;
    }

    /**
     * Obtiene el nss del paciente
     * 
     * @return String
     */
    public String getNss() {
        return nss;
    }

    /**
     * Asigna el nss del paciente
     * 
     * @param _nss 
     */
    public void setNss(String _nss) {
        this.nss = _nss;
    }

    /**
     * Obtiene el nombre del paciente
     * 
     * @return String
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Asigna el nombre del paciente
     * 
     * @param _nombre 
     */
    public void setNombre(String _nombre) {
        this.nombre = _nombre;
    }

    
    /**
     * Obtiene el primer apellido del paciente
     * 
     * @return String
     */
    public String getApellido1() {
        return apellido1;
    }

    /**
     * Asigna el primer apellido del paciente
     * 
     * @param _apellido1 
     */
    public void setApellido1(String _apellido1) {
        this.apellido1 = _apellido1;
    }

    /**
     * Obtiene el segundo apellido del paciente
     * 
     * @return String
     */
    public String getApellido2() {
        return apellido2;
    }

    /**
     * Asigna el segundo apellido del paciente
     * 
     * @param _apellido2 
     */
    public void setApellido2(String _apellido2) {
        this.apellido2 = _apellido2;
    }
    
    /**
     * Obtiene la edad del paciente
     * 
     * @return int
     */
    public int getEdad() {
        return edad;
    }

    /**
     * Asigna la edad del paciente
     * 
     * @param _edad 
     */
    public void setEdad(int _edad) {
        this.edad = _edad;
    }

    /**
     * Obtiene el peso del paciente
     * 
     * @return double
     */
    public double getPeso() {
        return peso;
    }

    /**
     * Asigna el peso del paciente
     * 
     * @param _peso 
     */
    public void setPeso(double _peso) {
        this.peso = _peso;
    }

    /**
     * Obtiene la altura del paciente
     * 
     * @return double
     */
    public double getAltura() {
        return altura;
    }

    /**
     * Asigna la altura del paciente
     * 
     * @param _altura 
     */
    public void setAltura(double _altura) {
        this.altura = _altura;
    }

    /**
     * Obtiene las alergias del paciente
     * 
     * @return String
     */
    public String getAlergias() {
        return alergias;
    }

    /**
     * Asigna las alturas del paciente
     * 
     * @param _altura 
     */
    public void setAlergias(String _alergias) {
        this.alergias = _alergias;
    }
    
    /**
     * Añade un nuevo episodio al paciente
     * 
     * @param _episodio 
     */
    public void anyadirEpisodio(EpisodioDeAtencionDTO _episodio){
        episodios.add(_episodio); 
    }
    
    /**
     * Obtiene un vector de Object con atributos de la clase paciente
     * 
     * @return Object[]
     */
    public Object[] toArray(){
        List<Object> object = new ArrayList();
        
        object.add(nombre);
        object.add(apellido1);
        object.add(nss);
        object.add(edad);
        
        return object.toArray();
    }
    
    /**
     * Obtiene el toString del paciente
     * 
     * @return String
     */
    @Override
    public String toString() {
        return nombre + " " + apellido1 + " " + apellido2 + " " + edad + " " + 
               peso + " " + altura + " " + nss + " " + alergias;
    }  
    
    /**
     * Obtiene el JSON del paciente
     * 
     * @return String
     */
    public String toJson(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
