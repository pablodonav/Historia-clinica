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
     * Registra a un paciente un nuevo episodio con los atributos almacenados en 
     * el json pasado por parámetro
     * 
     * @param _jsonPaciente
     * @throws Exception 
     */
    public void registrarNuevoEpisodio(String _jsonPacienteConEpisodio) throws Exception{
        if (! conectado){
            return;
        }
        
        List<String> resultados =  new ArrayList<>();
        
        PrimitivaComunicacion respuesta = 
        cliente.enviarSolicitud(PrimitivaComunicacion.NUEVO_EPISODIO, 
                                tiempoEsperaServidor,
                                _jsonPacienteConEpisodio,
                                resultados);
    }
}
