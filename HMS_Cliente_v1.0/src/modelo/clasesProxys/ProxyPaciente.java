/**
 * ProxyPaciente.java
 * Adnana Catrinel Dragut
 * v2.0 19/04/2022.
 * 
 */

package modelo.clasesProxys;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que contiene los métodos para enviar solicitudes 
 * y obtener información del servidor relacionados con un paciente.
 * 
 */
public class ProxyPaciente extends Comms{
    public static String PROPIEDAD_NUEVO_PACIENTE = "Nuevo Paciente";
    private static ProxyPaciente instancia = null; // es singleton
    
    /**
     * Devuelve la instancia de la clase ProxyPaciente.
     *
     * @return Comms
     */
    public static synchronized ProxyPaciente getInstance() {
        if (instancia == null) {
            instancia = new ProxyPaciente();
        }
        return instancia;
    }
    
    /**
     * Registra un nuevo paciente con los atributos almacenados en 
     * el json pasado por parámetro
     * 
     * @param _jsonPaciente
     * @throws Exception 
     */
    public void registrarNuevoPaciente(String _jsonPaciente) throws Exception{
        if (! conectado){
            return;
        }
        
        List<String> resultados =  new ArrayList<>();
        
        PrimitivaComunicacion respuesta = 
        cliente.enviarSolicitud(PrimitivaComunicacion.NUEVO_PACIENTE, 
                                tiempoEsperaServidor,
                                _jsonPaciente,
                                resultados);
    }
    
    /**
     * Obtiene todos los pacientes del sistema
     * 
     * @return String
     * @throws Exception 
     */
    public String obtenerPacientes() throws Exception{
        if (! conectado){
            return null;
        }
        
        List<String> resultados =  new ArrayList<>();
        
        PrimitivaComunicacion respuesta = 
                cliente.enviarSolicitud(PrimitivaComunicacion.OBTENER_PACIENTES, 
                                        tiempoEsperaServidor,
                                        null,
                                        resultados);
        if (resultados.isEmpty() || 
                respuesta.equals(PrimitivaComunicacion.NOK.toString())){
            return null;
        } else {
            return resultados.get(0);
        }
    }
    
    /**
     * Obtiene todos los episodios de un paciente
     * 
     * @param _idPaciente
     * @return String
     * @throws Exception 
     */
    public String obtenerEpisodiosPaciente(String _idPaciente) throws Exception{
        if (! conectado){
            return null;
        }
        
        List<String> resultados =  new ArrayList<>();
        
        PrimitivaComunicacion respuesta = 
                cliente.enviarSolicitud(PrimitivaComunicacion.OBTENER_EPISODIOS_PACIENTE, 
                                        tiempoEsperaServidor,
                                        _idPaciente,
                                        resultados);
        if (resultados.isEmpty() || 
                respuesta.equals(PrimitivaComunicacion.NOK.toString())){
            return null;
        } else {
            return resultados.get(0);
        }
    }
    
    /**
     * Obtiene todas las citas de un paciente
     * 
     * @param _idPaciente
     * @return String
     * @throws Exception 
     */
    public String obtenerCitasPaciente(String _idPaciente) throws Exception{
        if (! conectado){
            return null;
        }
        
        List<String> resultados =  new ArrayList<>();
        
        PrimitivaComunicacion respuesta = 
                cliente.enviarSolicitud(PrimitivaComunicacion.OBTENER_CITAS_PACIENTE, 
                                        tiempoEsperaServidor,
                                        _idPaciente,
                                        resultados);
        if (resultados.isEmpty() || 
                respuesta.equals(PrimitivaComunicacion.NOK.toString())){
            return null;
        } else {
            return resultados.get(0);
        }
    }
    
    /**
     * Obtiene todas las vacunas de un paciente
     * 
     * @param _idPaciente
     * @return String
     * @throws Exception 
     */
    public String obtenerVacunasPaciente(String _idPaciente) throws Exception{
        if (! conectado){
            return null;
        }
        
        List<String> resultados =  new ArrayList<>();
        
        PrimitivaComunicacion respuesta = 
                cliente.enviarSolicitud(PrimitivaComunicacion.OBTENER_VACUNAS_PACIENTE, 
                                        tiempoEsperaServidor,
                                        _idPaciente,
                                        resultados);
        if (resultados.isEmpty() || 
                respuesta.equals(PrimitivaComunicacion.NOK.toString())){
            return null;
        } else {
            return resultados.get(0);
        }
    }
    
    /**
     * Obtiene todos los medicamentos de un paciente
     * 
     * @param _idPaciente
     * @return String
     * @throws Exception 
     */
    public String obtenerMedicamentosPaciente(String _idPaciente) throws Exception{
        if (! conectado){
            return null;
        }
        
        List<String> resultados =  new ArrayList<>();
        
        PrimitivaComunicacion respuesta = 
                cliente.enviarSolicitud(PrimitivaComunicacion.OBTENER_RECETA_PACIENTE, 
                                        tiempoEsperaServidor,
                                        _idPaciente,
                                        resultados);
        if (resultados.isEmpty() || 
                respuesta.equals(PrimitivaComunicacion.NOK.toString())){
            return null;
        } else {
            return resultados.get(0);
        }
    }
    
    /**
     * Obtiene la historia completa de un paciente
     * 
     * @param _idPaciente
     * @return String
     * @throws Exception 
     */
    public String obtenerHistoriaPaciente(String _idPaciente) throws Exception{
        if (! conectado){
            return null;
        }
        
        List<String> resultados =  new ArrayList<>();
        
        PrimitivaComunicacion respuesta = 
                cliente.enviarSolicitud(PrimitivaComunicacion.OBTENER_HISTORIA_PACIENTE, 
                                        tiempoEsperaServidor,
                                        _idPaciente,
                                        resultados);
        if (resultados.isEmpty() || 
                respuesta.equals(PrimitivaComunicacion.NOK.toString())){
            return null;
        } else {
            return resultados.get(0);
        }
    }
}
