/**
 * ProxySanitario.java
 * Adnana Catrinel Dragut
 * v1.0 02/04/2022.
 * 
 */

package modelo.clasesProxys;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import modelo.clasesDTOs.SanitarioDTO;

/**
 * Clase que contiene los métodos para enviar solicitudes 
 * y obtener respuestas del servidor relacionados con un sanitario.
 * 
 */
public class ProxySanitario extends Comms{
    public static String PROPIEDAD_DAR_ALTA_SANITARIO = "Dar Alta Sanitario";
    public static String PROPIEDAD_DAR_BAJA_SANITARIO = "Dar Baja Sanitario";
    public static String PROPIEDAD_EDITAR_SANITARIO = "Editar Sanitario";
            
    
    /**
     * Da de alta un nuevo sanitario con los atributos almacenados en 
     * el json pasado por parámetro
     * 
     * @param _jsonSanitario
     * @throws Exception 
     */
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
        if (_jsonSanitario == null ||
            _jsonSanitario.isBlank() ||
            _jsonSanitario.isEmpty()) {
            return;
        }
        
        observadores.firePropertyChange(PROPIEDAD_DAR_ALTA_SANITARIO, null, _jsonSanitario);  
        return;
    }
    
    /**
     * Da de baja al sanitario al que le pertenece el dni
     * pasado por parámetro
     * 
     * @param _dniSanitario
     * @throws Exception 
     */
    public void darBajaSanitario(String _dniSanitario) throws Exception{
        System.out.println(conectado);
        if (! conectado){
            return;
        }
        
        List<String> resultados =  new ArrayList<>();
        
        PrimitivaComunicacion respuesta = 
        cliente.enviarSolicitud(PrimitivaComunicacion.DAR_BAJA_SANITARIO, 
                                tiempoEsperaServidor,
                                _dniSanitario,
                                resultados);
    }
    
    public void darBajaSanitarioTest(String _dniSanitario) throws Exception{        
            if (_dniSanitario == null ||
                _dniSanitario.isBlank() ||
                _dniSanitario.isEmpty()) {
                return;
            }

            System.out.println("Dni sanitario seleccionado: " + _dniSanitario);
            observadores.firePropertyChange(PROPIEDAD_DAR_BAJA_SANITARIO, null, _dniSanitario);  
            return;
    }
    
    /**
     * Edita la información de un sanitario existente y cuyos 
     * datos modificados se encuentran en el json pasado por parámetro
     * 
     * @param _jsonSanitario
     * @throws Exception 
     */
    public void editarSanitario(String _jsonSanitario) throws Exception{        
        System.out.println(conectado);
        if (! conectado){
            return;
        }
        
        List<String> resultados =  new ArrayList<>();
        
        PrimitivaComunicacion respuesta = 
        cliente.enviarSolicitud(PrimitivaComunicacion.EDITAR_SANITARIO, 
                                tiempoEsperaServidor,
                                _jsonSanitario,
                                resultados);
    }
    
    public void editarSanitarioTest(String _jsonSanitario) throws Exception{        
            if (_jsonSanitario == null ||
                _jsonSanitario.isBlank() ||
                _jsonSanitario.isEmpty()) {
                return;
            }
            Gson gson = new Gson();
            SanitarioDTO userTest = gson.fromJson(_jsonSanitario, SanitarioDTO.class);
            
            System.out.println("Sanitario editado: " + userTest);
            observadores.firePropertyChange(PROPIEDAD_EDITAR_SANITARIO, null, _jsonSanitario);  
            return;
    }
       
    /**
     * Obtiene todos los sanitarios dados de alta en el sistema
     * 
     * @return String
     * @throws Exception 
     */
    public String obtenerSanitarios() throws Exception{
        if (! conectado){
            return null;
        }
        
        List<String> resultados =  new ArrayList<>();
        
        PrimitivaComunicacion respuesta = 
                cliente.enviarSolicitud(PrimitivaComunicacion.OBTENER_SANITARIOS, 
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
    
    public String obtenerSanitariosTest() throws Exception{
        String respuestaJson = "";
        Gson gson = new Gson();
        
        List<SanitarioDTO> sanitarios = new ArrayList();
        sanitarios.add(new SanitarioDTO("Ana", "Prueba1", "Prueba1", "x1111", 1111, "a@gmail.com", "1234", "otros"));
        sanitarios.add(new SanitarioDTO("Borja", "Prueba2", "Prueba2", "x2222", 2222, "a@gmail.com", "1234", "otros"));
        sanitarios.add(new SanitarioDTO("Maria", "Prueba3", "Prueba3", "x1234", 1234, "a@gmail.com", "1234", "otros"));
        
        String sanitariosToSend = gson.toJson(sanitarios);
    
        System.out.println("Enviados sanitarios: " + sanitariosToSend);
        
        for(SanitarioDTO sanitario: sanitarios){
            System.out.println(sanitario.toString());
        }
        
        if (sanitariosToSend.isEmpty()){
            return null;
        } else {
            return sanitariosToSend;
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
     *  Recibe del servidor el resultado de dar de baja
     *  un sanitario existente en el sistema.
     * 
     */
    private boolean solicitudServidorDarBajaSanitario(
            String propiedad, List<String> resultados)
            throws IOException {
        String dniSanitario = resultados.get(0);
        
        if (dniSanitario == null ||
            dniSanitario.isBlank() ||
            dniSanitario.isEmpty()) {
            return false;
        }
        observadores.firePropertyChange(propiedad, null, dniSanitario);  
        return true;
    }
    
    /**
     *  Recibe del servidor el resultado de editar
     *  un sanitario existente en el sistema.
     * 
     */
    private boolean solicitudServidorEditarSanitario(
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
            case DAR_BAJA_SANITARIO:
                return solicitudServidorDarBajaSanitario(
                    PROPIEDAD_DAR_BAJA_SANITARIO, resultados);
            case EDITAR_SANITARIO:
                return solicitudServidorEditarSanitario(
                    PROPIEDAD_EDITAR_SANITARIO, resultados);           
            default:
                return false;
        }   
    }
}
