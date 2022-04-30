/**
 * ProxyEpisodio.java
 * Adnana Catrinel Dragut
 * v1.0 21/04/2022.
 * 
 */
package modelo.clasesProxys;

import java.util.ArrayList;
import java.util.List;
import static modelo.clasesProxys.Comms.conectado;

/**
 * Clase que contiene los métodos para enviar solicitudes 
 * y obtener información del servidor relacionados con un episodio de atención.
 * 
 */
public class ProxyEpisodio extends Comms{
    private static ProxyEpisodio instancia = null; // es singleton
    public static String PROPIEDAD_NUEVO_EPISODIO = "Nuevo Episodio";
    public static String PROPIEDAD_NUEVO_DIAGNOSTICO = "Nuevo Diagnostico";
    
    /**
     * Devuelve la instancia de la clase ProxyEpisodio.
     *
     * @return Comms
     */
    public static synchronized ProxyEpisodio getInstance() {
        if (instancia == null) {
            instancia = new ProxyEpisodio();
        }
        return instancia;
    }
    
    /**
     * Registra al paciente con idPaciente un nuevo episodio que posee
     * los atributos almacenados en el json pasado por parámetro
     * 
     * @param _jsonNuevoEpisodio
     * @param idPaciente
     * @throws Exception 
     */
    public void registrarNuevoEpisodio(String _jsonNuevoEpisodio, String idPaciente) throws Exception{
        if (! conectado){
            return;
        }
        
        List<String> resultados =  new ArrayList<>();
        String parametros = _jsonNuevoEpisodio + "\n" + 
            idPaciente;
        
        PrimitivaComunicacion respuesta = 
        cliente.enviarSolicitud(PrimitivaComunicacion.NUEVO_EPISODIO, 
                                tiempoEsperaServidor,
                                parametros,
                                resultados);
    }
    
    /**
     * Añade un diagnóstico al episodio del paciente pasados como parámetros
     * 
     * @param _jsonEpisodioConDiagnostico
     * @param idPaciente
     * @throws Exception 
     */
    public void anyadirDiagnostico(String _jsonEpisodioConDiagnostico, String idPaciente) throws Exception{
        if (! conectado){
            return;
        }
        
        List<String> resultados =  new ArrayList<>();
        String parametros = _jsonEpisodioConDiagnostico + "\n" + 
            idPaciente;
        
        PrimitivaComunicacion respuesta = 
        cliente.enviarSolicitud(PrimitivaComunicacion.NUEVO_DIAGNOSTICO, 
                                tiempoEsperaServidor,
                                parametros,
                                resultados);
    }
}
