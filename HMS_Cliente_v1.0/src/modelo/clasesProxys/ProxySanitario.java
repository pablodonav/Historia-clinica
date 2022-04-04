/**
 * ProxySanitario.java
 * Adnana Catrinel Dragut
 * v1.0 02/04/2022.
 * 
 */

package modelo.clasesProxys;

import com.google.gson.Gson;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que contiene los métodos para enviar solicitudes 
 * y obtener respuestas del servidor relacionados con un sanitario.
 * 
 */
public class ProxySanitario extends Comms{
    public static String PROPIEDAD_DAR_ALTA_SANITARIO = "Dar Alta Sanitario"; 
    
    /**
     * Crea ProxySanitario.
     * 
     */
    public ProxySanitario() {
    }
    
    /**
     *  Añade nuevo observador de las mesas.
     * 
     */     
    public void nuevoObservador(PropertyChangeListener observador) {
        this.observadores.addPropertyChangeListener(observador);
    } 
    
    /**
     *  Eliminar observador de las mesas.
     * 
     */     
    public void eliminarObservador(PropertyChangeListener observador) {
        this.observadores.removePropertyChangeListener(observador);
    } 
    
    public void darAltaSanitario(String _jsonSanitario) throws Exception{
        System.out.println(conectado);
        if (! conectado){
            return;
        }
        
        List<String> resultados =  new ArrayList<>();
        
        PrimitivaComunicacion respuesta = 
        cliente.enviarSolicitud(PrimitivaComunicacion.DAR_ALTA_SANITARIO, 
                                tiempoEsperaServidor,
                                _jsonSanitario,
                                resultados);
    }
    
    public void darAltaSanitarioTest(String _jsonSanitario) throws Exception{        
        String respuestaJson = "";
        Gson gson = new Gson();
       
        if (_jsonSanitario == null ||
            _jsonSanitario.isBlank() ||
            _jsonSanitario.isEmpty()) {
            return;
        }
        
        observadores.firePropertyChange(PROPIEDAD_DAR_ALTA_SANITARIO, null, _jsonSanitario);  
        return;
    }
    
    /**
     * Obtiene el sanitario que posee el dni pasado por parámetro,
     * o null en caso de que el dni no corresponda con ningún sanitario
     * de la base de datos
     * 
     * @param _dniSanitario
     * @return String
     * @throws Exception 
     */
    public String obtenerSanitario(String _dniSanitario) throws Exception{
        if (! conectado){
            return null;
        }
        
        List<String> resultados =  new ArrayList<>();
        
        PrimitivaComunicacion respuesta = 
                cliente.enviarSolicitud(PrimitivaComunicacion.VERIFICAR_USUARIO, 
                                        tiempoEsperaServidor,
                                        _dniSanitario,
                                        resultados);
        if (resultados.isEmpty() || 
                respuesta.equals(PrimitivaComunicacion.NOK.toString())){
            return null;
        } else {
            return resultados.get(0);
        }
    }
    
    /**
     *  Recibe del servidor el resultado de dar de alta
     *  un nuevo sanitario.
     * 
     */
    private boolean solicitudServidorDarAltaSanitario(
            String propiedad, List<String> resultados)
            throws IOException {
        String sanitarioJsonToReceive = resultados.get(0);
        
        if (sanitarioJsonToReceive == null ||
            sanitarioJsonToReceive.isBlank() ||
            sanitarioJsonToReceive.isEmpty()) {
            return false;
        }
        observadores.firePropertyChange(propiedad, null, sanitarioJsonToReceive);  
        return true;
    }
        
    /**
     * Recibe solicitudes del servidor
     * 
     * @param solicitud
     * @param resultados
     * @return boolean
     * @throws IOException 
     */
    @Override
    public boolean solicitudServidorProducida(PrimitivaComunicacion solicitud, 
            List<String> resultados) throws IOException {
        if (resultados.isEmpty()) {
            return false;
        } 
      
        switch(solicitud) {
            case DAR_ALTA_SANITARIO:
                return solicitudServidorDarAltaSanitario(
                    PROPIEDAD_DAR_ALTA_SANITARIO, resultados);
            default:
                return false;
        }   
    }
}
