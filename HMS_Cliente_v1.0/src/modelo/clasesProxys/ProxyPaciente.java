/**
 * ProxyPaciente.java
 * Adnana Catrinel Dragut
 * v1.0 19/04/2022.
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
    
}
