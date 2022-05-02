/**
 * ProxyCita.java
 * Adnana Catrinel Dragut
 * v1.0 29/04/2022.
 * 
 */
package modelo.clasesProxys;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que contiene los métodos para enviar solicitudes 
 * y obtener información del servidor relacionados con una cita.
 * 
 */
public class ProxyCitaPaciente extends Comms{
    public static String PROPIEDAD_NUEVA_CITA = "Nueva Cita";
    public static String PROPIEDAD_ELIMINAR_CITA = "Eliminar Cita";
    private static ProxyCitaPaciente instancia = null; // es singleton
    
    /**
     * Devuelve la instancia de la clase ProxyCitaPaciente.
     *
     * @return ProxyCitaPaciente
     */
    public static synchronized ProxyCitaPaciente getInstance() {
        if (instancia == null) {
            instancia = new ProxyCitaPaciente();
        }
        return instancia;
    }
    
    /**
     * Añadir una nueva cita al paciente pasado por parámetro
     * 
     * @param _jsonCita
     * @param _idPaciente
     * @throws Exception 
     */
    public void anyadirCita(String _jsonCita, String _idPaciente) throws Exception{
        if (! conectado){
            return;
        }
                
        List<String> resultados =  new ArrayList<>();
        
        String parametros = _jsonCita + "\n" + _idPaciente;
        
        PrimitivaComunicacion respuesta = 
        cliente.enviarSolicitud(PrimitivaComunicacion.NUEVA_CITA, 
                                tiempoEsperaServidor,
                                parametros,
                                resultados);
    }
    
    /**
     * Eliminar la cita de un paciente pasado por parámetro
     * 
     * @param _idCita
     * @param _idPaciente
     * @throws Exception 
     */
    public void eliminarCita(String _idCita, String _idPaciente) throws Exception{
        if (! conectado){
            return;
        }
        
        List<String> resultados =  new ArrayList<>();
        
        String parametros = _idCita + "\n" + _idPaciente;
        
        PrimitivaComunicacion respuesta = 
        cliente.enviarSolicitud(PrimitivaComunicacion.ELIMINAR_CITA, 
                                tiempoEsperaServidor,
                                parametros,
                                resultados);
    }
}
