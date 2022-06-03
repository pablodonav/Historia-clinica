/**
 * ProxyPaciente.java
 * Adnana Catrinel Dragut
 * v2.0 07/05/2022.
 * 
 */
package modelo.clasesProxys;

import java.util.ArrayList;
import java.util.List;
import static modelo.clasesProxys.Comms.conectado;

/**
 * Clase que contiene los métodos para enviar solicitudes 
 * y obtener información del servidor relacionados con una vacuna.
 * 
 */
public class ProxyVacuna extends Comms {
    public static String PROPIEDAD_NUEVA_VACUNA = "Nueva Vacuna";
    private static ProxyVacuna instancia = null; // es singleton
    
    /**
     * Devuelve la instancia de la clase ProxyVacuna.
     *
     * @return Comms
     */
    public static synchronized ProxyVacuna getInstance() {
        if (instancia == null) {
            instancia = new ProxyVacuna();
        }
        return instancia;
    }
    
    /**
     * Obtiene todas las vacunas disponibles en el sistema para ser administradas
     * 
     * @return String
     * @throws Exception 
     */
    public String obtenerVacunasDisponibles() throws Exception{
        if (! conectado){
            return null;
        }
        
        List<String> resultados =  new ArrayList<>();
        
        PrimitivaComunicacion respuesta = 
                cliente.enviarSolicitud(PrimitivaComunicacion.OBTENER_VACUNAS_DISPONIBLES, 
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
     * Añadir una nueva vacuna al paciente pasado por parámetro
     * 
     * @param _jsonVacuna
     * @param _idPaciente
     * @throws Exception 
     */
    public void anyadirVacunaAPaciente(String _jsonVacuna, String _idPaciente) throws Exception{
        if (! conectado){
            return;
        }
                
        List<String> resultados =  new ArrayList<>();
        
        String parametros = _jsonVacuna + "\n" + _idPaciente;
        System.out.println("Json vacuna enviado: " + _jsonVacuna);
        
        PrimitivaComunicacion respuesta = 
        cliente.enviarSolicitud(PrimitivaComunicacion.NUEVA_VACUNA_PACIENTE, 
                                tiempoEsperaServidor,
                                parametros,
                                resultados);
    }
}
