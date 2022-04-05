/**
 * PacienteDTO.java
 * Pablo Doñate Navarro
 * v1.0 02/04/2022.
 */
package modelo.clasesDTOs;

/**
 * Clase que contiene los atributos y getter-setter de un paciente.
 * 
 */
public class PacienteDTO {
    private String nss;
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String alergias;
    private int edad;
    private double altura;
    private double peso;

    /**
     * Crea un PacienteDTO.
     *
     * @param _nss
     * @param _nombre
     * @param _apellido1
     * @param _apellido2
     * @param _alergias
     * @param _edad
     * @param _altura
     * @param _peso
     */
    public PacienteDTO(String _nss, String _nombre, String _apellido1, String _apellido2, 
            String _alergias, int _edad, double _altura, double _peso) {
        this.nss = _nss;
        this.nombre = _nombre;
        this.apellido1 = _apellido1;
        this.apellido2 = _apellido2;
        this.alergias = _alergias;
        this.edad = _edad;
        this.altura = _altura;
        this.peso = _peso;
    }
    
    /**
     * Devuelve el numero de la seguridad social de un paciente.
     * 
     * @return nss
     */
    public String getNss() {
        return nss;
    }
    
    /**
     * Devuelve el nombre de un paciente.
     * 
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Devuelve el primer apellido de un paciente.
     * 
     * @return apellido1
     */
    public String getApellido1() {
        return apellido1;
    }

    /**
     * Devuelve el segundo apellido de un paciente.
     * 
     * @return apellido2
     */
    public String getApellido2() {
        return apellido2;
    }

    /**
     * Devuelve las alergias de un paciente.
     * 
     * @return alergias
     */
    public String getAlergias() {
        return alergias;
    }

    /**
     * Devuelve la edad de un paciente.
     * 
     * @return edad
     */
    public int getEdad() {
        return edad;
    }

    /**
     * Devuelve la altura de un paciente.
     * 
     * @return altura
     */
    public double getAltura() {
        return altura;
    }

    /**
     * Devuelve el peso de un paciente.
     * 
     * @return peso
     */
    public double getPeso() {
        return peso;
    }
}
