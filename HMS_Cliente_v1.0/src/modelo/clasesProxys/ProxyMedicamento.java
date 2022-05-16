/**
 * ProxyMedicamento.java
 * Adnana Catrinel Dragut
 * v2.0 07/05/2022.
 * 
 */
package modelo.clasesProxys;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import modelo.clasesDTOs.MedicamentoDTO;
import static modelo.clasesProxys.Comms.conectado;

/**
 * Clase que contiene los métodos para enviar solicitudes 
 * y obtener información del servidor relacionados con un medicamento.
 * 
 */
public class ProxyMedicamento extends Comms {
    public static String PROPIEDAD_NUEVO_MEDICAMENTO = "Nuevo Medicamento";
    public static String PROPIEDAD_ELIMINAR_MEDICAMENTO = "Eliminar Medicamento";
    private static ProxyMedicamento instancia = null; // es singleton
    
    /**
     * Devuelve la instancia de la clase ProxyMedicamento.
     *
     * @return Comms
     */
    public static synchronized ProxyMedicamento getInstance() {
        if (instancia == null) {
            instancia = new ProxyMedicamento();
        }
        return instancia;
    }
    
    /**
     * Obtiene todos los medicamentos disponibles en el sistema
     * 
     * @return String
     * @throws Exception 
     */
    public String obtenerMedicamentosDisponibles() throws Exception{
        if (! conectado){
            return null;
        }
        
        List<String> resultados =  new ArrayList<>();
        
        PrimitivaComunicacion respuesta = 
                cliente.enviarSolicitud(PrimitivaComunicacion.OBTENER_MEDICAMENTOS_DISPONIBLES, 
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
    
    public String obtenerMedicamentosDisponiblesTest() throws Exception{
        String respuestaJson = "";
        Gson gson = new Gson();
        
        List<MedicamentoDTO> medicamentos = new ArrayList();
        MedicamentoDTO medicamento1 = new MedicamentoDTO(1112233, "Prueba1");
        MedicamentoDTO medicamento2 = new MedicamentoDTO(2223334, "Prueba2");
        medicamentos.add(medicamento1);
        medicamentos.add(medicamento2);
        String medicamentosToSend = gson.toJson(medicamentos);
    
        System.out.println("Medicamentos disponibles enviados: " + medicamentosToSend);
        
        for(MedicamentoDTO medicamento: medicamentos){
            System.out.println(medicamento.toString());
        }
        
        if (medicamentosToSend.isEmpty()){
            return null;
        } else {
            return medicamentosToSend;
        }
    }
    
    /**
     * Añadir un nuevo medicamento a la receta del paciente pasado por parámetro
     * 
     * @param _jsonMedicamento
     * @param _idPaciente
     * @throws Exception 
     */
    public void anyadirMedicamentoAPaciente(String _jsonMedicamento, String _idPaciente) throws Exception{
        if (! conectado){
            return;
        }
                
        List<String> resultados =  new ArrayList<>();
        
        String parametros = _jsonMedicamento + "\n" + _idPaciente;
        System.out.println("json enviar: " + _jsonMedicamento);
        
        PrimitivaComunicacion respuesta = 
        cliente.enviarSolicitud(PrimitivaComunicacion.NUEVO_MEDICAMENTO_PACIENTE, 
                                tiempoEsperaServidor,
                                parametros,
                                resultados);
    }
    
    /**
     * Eliminar medicamento al que le pertenece el código
     * pasado por parámetro
     * 
     * @param _idMedicamento
     * @param _idPaciente
     * @throws Exception 
     */
    public void eliminarMedicamentoDePaciente(String _idMedicamento, String _idPaciente) throws Exception{
        if (! conectado){
            return;
        }
        
        List<String> resultados =  new ArrayList<>();
        
        String parametros = _idMedicamento + "\n" + _idPaciente;
        
        PrimitivaComunicacion respuesta = 
        cliente.enviarSolicitud(PrimitivaComunicacion.ELIMINAR_MEDICAMENTO_PACIENTE, 
                                tiempoEsperaServidor,
                                parametros,
                                resultados);
    }
}
