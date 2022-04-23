/**
 * ProxyPaciente.java
 * Adnana Catrinel Dragut
 * v1.0 19/04/2022.
 * 
 */

package modelo.clasesProxys;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import modelo.clasesDTOs.PacienteDTO;

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
    
    public String obtenerPacientesTest() throws Exception{
        String respuestaJson = "";
        Gson gson = new Gson();
        
        List<PacienteDTO> pacientes = new ArrayList();
        pacientes.add(new PacienteDTO("A545455", "Ana", "Prueba1", "Prueba2", "dad,asdas,asda", 20, 60, 180));
        pacientes.add(new PacienteDTO("A545455", "Juan", "Prueba1", "Prueba2", "dad,asdas,asda", 20, 60, 180));
        pacientes.add(new PacienteDTO("A545455", "Jorge", "Prueba1", "Prueba2", "dad,asdas,asda", 20, 60, 180));
        pacientes.add(new PacienteDTO("A545455", "Maria", "Prueba1", "Prueba2", "dad,asdas,asda", 20, 60, 180));
        String sanitariosToSend = gson.toJson(pacientes);
    
        System.out.println("Enviados sanitarios: " + sanitariosToSend);
        
        for(PacienteDTO sanitario: pacientes){
            System.out.println(sanitario.toString());
        }
        
        if (sanitariosToSend.isEmpty()){
            return null;
        } else {
            return sanitariosToSend;
        }
    }
    
}
