/**
 * ProxySanitario.java
 * Adnana Catrinel Dragut
 * v1.0 02/04/2022.
 * 
 */

package modelo.clasesProxys;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import modelo.clasesDTOs.SanitarioDTO;

/**
 * Clase que contiene los métodos para enviar solicitudes 
 * y obtener información del servidor relacionados con un sanitario.
 * 
 */
public class ProxySanitario extends Comms{
    public static String PROPIEDAD_DAR_ALTA_SANITARIO = "Dar Alta Sanitario";
    public static String PROPIEDAD_DAR_BAJA_SANITARIO = "Dar Baja Sanitario";
    public static String PROPIEDAD_EDITAR_SANITARIO = "Editar Sanitario";
    private static ProxySanitario instancia = null; // es singleton
    
    /**
     * Devuelve la instancia de la clase ProxySanitario.
     *
     * @return ProxySanitario
     */
    public static synchronized ProxySanitario getInstance() {
        if (instancia == null) {
            instancia = new ProxySanitario();
        }
        return instancia;
    }
    
    /**
     * Da de alta un nuevo sanitario con los atributos almacenados en 
     * el json pasado por parámetro
     * 
     * @param _jsonSanitario
     * @throws Exception 
     */
    public void darAltaSanitario(String _jsonSanitario) throws Exception{
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
    
    /**
     * Da de baja al sanitario al que le pertenece el dni
     * pasado por parámetro
     * 
     * @param _dniSanitario
     * @throws Exception 
     */
    public void darBajaSanitario(String _dniSanitario) throws Exception{
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
        
    /**
     * Edita la información de un sanitario existente y cuyos 
     * datos modificados se encuentran en el json pasado por parámetro
     * 
     * @param _jsonSanitario
     * @throws Exception 
     */
    public void editarSanitario(String _jsonSanitario) throws Exception{        
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
        sanitarios.add(new SanitarioDTO("Ana", "Prueba1", "Prueba1", "x1111", 1111, "a@gmail.com", "1234", "Médico"));
        sanitarios.add(new SanitarioDTO("Borja", "Prueba2", "Prueba2", "x2222", 2222, "a@gmail.com", "1234", "otros"));
        sanitarios.add(new SanitarioDTO("Maria", "Prueba3", "Prueba3", "x1234", 1234, "a@gmail.com", "1234", "Médico"));
        
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
}
