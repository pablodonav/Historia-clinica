/**
 * ProxyPaciente.java
 * Adnana Catrinel Dragut
 * v2.0 07/05/2022.
 * 
 */
package modelo.clasesProxys;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import modelo.clasesDTOs.VacunaDTO;
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
    
    public String obtenerVacunasDisponiblesTest() throws Exception{
        String respuestaJson = "";
        Gson gson = new Gson();
        
        List<VacunaDTO> vacunas = new ArrayList();
        VacunaDTO vacuna1 = new VacunaDTO(554436, "FENDRIX");
        VacunaDTO vacuna2 = new VacunaDTO(112223, "PFIZER");
        vacunas.add(vacuna1);
        vacunas.add(vacuna2);
        String vacunasToSend = gson.toJson(vacunas);
    
        System.out.println("Vacunas disponibles enviadas: " + vacunasToSend);
        
        for(VacunaDTO vacuna: vacunas){
            System.out.println(vacuna.toString());
        }
        
        if (vacunasToSend.isEmpty()){
            return null;
        } else {
            return vacunasToSend;
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
