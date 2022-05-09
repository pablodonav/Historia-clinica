/**
 * ProxyPaciente.java
 * Adnana Catrinel Dragut
 * v1.0 19/04/2022.
 * 
 */

package modelo.clasesProxys;

import com.google.gson.Gson;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import modelo.clasesDTOs.CitaPacienteDTO;
import modelo.clasesDTOs.EpisodioDeAtencionDTO;
import modelo.clasesDTOs.MedicamentoDTO;
import modelo.clasesDTOs.MedicamentoPacienteDTO;
import modelo.clasesDTOs.PacienteDTO;
import modelo.clasesDTOs.Ubicacion;
import modelo.clasesDTOs.VacunaDTO;
import modelo.clasesDTOs.VacunaPacienteDTO;

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
        
        for(PacienteDTO paciente: pacientes){
            System.out.println(paciente.toString());
        }
        
        if (sanitariosToSend.isEmpty()){
            return null;
        } else {
            return sanitariosToSend;
        }
    }
    
    /**
     * Obtiene todos los episodios de un paciente
     * 
     * @param _idPaciente
     * @return String
     * @throws Exception 
     */
    public String obtenerEpisodiosPaciente(String _idPaciente) throws Exception{
        if (! conectado){
            return null;
        }
        
        List<String> resultados =  new ArrayList<>();
        
        PrimitivaComunicacion respuesta = 
                cliente.enviarSolicitud(PrimitivaComunicacion.OBTENER_EPISODIOS_PACIENTE, 
                                        tiempoEsperaServidor,
                                        _idPaciente,
                                        resultados);
        if (resultados.isEmpty() || 
                respuesta.equals(PrimitivaComunicacion.NOK.toString())){
            return null;
        } else {
            return resultados.get(0);
        }
    }
    
    public String obtenerEpisodiosPacienteTest(String _idPaciente) throws Exception{
        String respuestaJson = "";
        Gson gson = new Gson();
       
        String oldstring = "2011-01-18 18:30:78.99";
        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(oldstring);

        List<EpisodioDeAtencionDTO> episodios = new ArrayList();
        episodios.add(new EpisodioDeAtencionDTO(1, date, "Dolor garganta y tos", "Resfriado común"));
        episodios.add(new EpisodioDeAtencionDTO(2, date, "Dolor garganta y tos", ""));
        episodios.add(new EpisodioDeAtencionDTO(3, date, "Dolor garganta y tos", "Resfriado común"));
        episodios.add(new EpisodioDeAtencionDTO(4, date, "Dolor garganta y tos", "Resfriado común"));
        episodios.add(new EpisodioDeAtencionDTO(5, date, "Dolor garganta y tos", ""));
        String episodiosToSend = gson.toJson(episodios);
    
        System.out.println("Enviados episodios: " + episodiosToSend);
        
        for(EpisodioDeAtencionDTO episodio: episodios){
            System.out.println(episodio.toString());
        }
        
        if (episodiosToSend.isEmpty()){
            return null;
        } else {
            return episodiosToSend;
        }
    }
    
    /**
     * Obtiene todas las citas de un paciente
     * 
     * @param _idPaciente
     * @return String
     * @throws Exception 
     */
    public String obtenerCitasPaciente(String _idPaciente) throws Exception{
        if (! conectado){
            return null;
        }
        
        List<String> resultados =  new ArrayList<>();
        
        PrimitivaComunicacion respuesta = 
                cliente.enviarSolicitud(PrimitivaComunicacion.OBTENER_CITAS_PACIENTE, 
                                        tiempoEsperaServidor,
                                        _idPaciente,
                                        resultados);
        if (resultados.isEmpty() || 
                respuesta.equals(PrimitivaComunicacion.NOK.toString())){
            return null;
        } else {
            return resultados.get(0);
        }
    }
    
    public String obtenerCitasPacienteTest(String _idPaciente) throws Exception{
        String respuestaJson = "";
        Gson gson = new Gson();
       
        String oldstring = "2011-01-18";
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(oldstring);
        Time tiempo1 = Time.valueOf(LocalTime.of(10, 30));
        Time tiempo2 = Time.valueOf(LocalTime.of(13, 45));
        
        Ubicacion ubicacion1 = new Ubicacion("Teruel", "Hospital Obisco Polanco" , "12 A");
        Ubicacion ubicacion2 = new Ubicacion("Zaragoza", "Hospital Miguel Servet" , "103 B");
        
        
        List<CitaPacienteDTO> citas = new ArrayList();
        citas.add(new CitaPacienteDTO(1, "x1234", ubicacion1, date, tiempo1, "Prueba 1 "));
        citas.add(new CitaPacienteDTO(2, "x1111", ubicacion2, date, tiempo2, "Prueba 2 "));
        citas.add(new CitaPacienteDTO(3, "x2222", ubicacion2, date, tiempo2, "Prueba 3 "));
        citas.add(new CitaPacienteDTO(4, "x3333", ubicacion1, date, tiempo1, "Prueba 4 "));
        String citasToSend = gson.toJson(citas);
    
        System.out.println("Enviados episodios: " + citasToSend);
        
        for(CitaPacienteDTO cita: citas){
            System.out.println(cita.toString());
        }
        
        if (citasToSend.isEmpty()){
            return null;
        } else {
            return citasToSend;
        }
    }
    
    /**
     * Obtiene todas las vacunas de un paciente
     * 
     * @param _idPaciente
     * @return String
     * @throws Exception 
     */
    public String obtenerVacunasPaciente(String _idPaciente) throws Exception{
        if (! conectado){
            return null;
        }
        
        List<String> resultados =  new ArrayList<>();
        
        PrimitivaComunicacion respuesta = 
                cliente.enviarSolicitud(PrimitivaComunicacion.OBTENER_VACUNAS_PACIENTE, 
                                        tiempoEsperaServidor,
                                        _idPaciente,
                                        resultados);
        if (resultados.isEmpty() || 
                respuesta.equals(PrimitivaComunicacion.NOK.toString())){
            return null;
        } else {
            return resultados.get(0);
        }
    }
    
    public String obtenerVacunasPacienteTest(String _idPaciente) throws Exception{
        String respuestaJson = "";
        Gson gson = new Gson();
       
        String oldstring = "2011-01-18";
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(oldstring);
        
        VacunaDTO vacuna1 = new VacunaDTO(1111, "Prueba1");
        VacunaDTO vacuna2 = new VacunaDTO(1111, "Prueba2");
        
        List<VacunaPacienteDTO> vacunas = new ArrayList();
        vacunas.add(new VacunaPacienteDTO(vacuna1, date));
        vacunas.add(new VacunaPacienteDTO(vacuna2, date));
        String vacunasToSend = gson.toJson(vacunas);
    
        System.out.println("Vacunas enviadas: " + vacunasToSend);
        
        for(VacunaPacienteDTO vacuna: vacunas){
            System.out.println(vacuna.toString());
        }
        
        if (vacunasToSend.isEmpty()){
            return null;
        } else {
            return vacunasToSend;
        }
    }
    
    /**
     * Obtiene todos los medicamentos de un paciente
     * 
     * @param _idPaciente
     * @return String
     * @throws Exception 
     */
    public String obtenerMedicamentosPaciente(String _idPaciente) throws Exception{
        if (! conectado){
            return null;
        }
        
        List<String> resultados =  new ArrayList<>();
        
        PrimitivaComunicacion respuesta = 
                cliente.enviarSolicitud(PrimitivaComunicacion.OBTENER_RECETA_PACIENTE, 
                                        tiempoEsperaServidor,
                                        _idPaciente,
                                        resultados);
        if (resultados.isEmpty() || 
                respuesta.equals(PrimitivaComunicacion.NOK.toString())){
            return null;
        } else {
            return resultados.get(0);
        }
    }
    
    public String obtenerMedicamentosPacienteTest(String _idPaciente) throws Exception{
        String respuestaJson = "";
        Gson gson = new Gson();
       
        String oldstring1 = "2022-01-18";
        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(oldstring1);
        String oldstring2 = "2022-03-12";
        Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(oldstring2);
        
        MedicamentoDTO medicamento1 = new MedicamentoDTO(1111, "Prueba 1");
        MedicamentoDTO medicamento2 = new MedicamentoDTO(2222, "Prueba 2");
        
        List<MedicamentoPacienteDTO> medicamentos = new ArrayList();
        medicamentos.add(new MedicamentoPacienteDTO(1, medicamento1, date1, date2));
        medicamentos.add(new MedicamentoPacienteDTO(2, medicamento2, date1, date2));
        String medicamentosToSend = gson.toJson(medicamentos);
    
        System.out.println("Medicamentos enviados: " + medicamentosToSend);
        
        for(MedicamentoPacienteDTO medicamento: medicamentos){
            System.out.println(medicamento.toString());
        }
        
        if (medicamentosToSend.isEmpty()){
            return null;
        } else {
            return medicamentosToSend;
        }
    }
}
