/**
 * TestServidor.java
 * Pablo Doñate Navarro
 * 
 */
import com.google.gson.Gson;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import modelo.DataBaseControl;
import modelo.clasesDTOs.CitaDTO;
import modelo.clasesDTOs.EpisodioAtencionDTO;
import modelo.clasesDTOs.HistoriaPacienteDTO;
import modelo.clasesDTOs.MedicamentoDTO;
import modelo.clasesDTOs.MedicamentoPacienteDTO;
import modelo.clasesDTOs.PacienteDTO;
import modelo.clasesDTOs.SanitarioDTO;
import modelo.clasesDTOs.UbicacionDTO;
import modelo.clasesDTOs.UsuarioDTO;
import modelo.clasesDTOs.VacunaDTO;
import modelo.clasesDTOs.VacunaPacienteDTO;

import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 * Clase que contiene métodos de JUnit para realizar pruebas.
 * 
 */
public class TestServidor {
    private DataBaseControl database;
    
    /**
     * Función que se ejecuta correctamente si se ha podido conectar a la DB.
     * En caso contrario, saltará excepción.
     * 
     * @throws SQLException
     */
    @Before
    public void testConnectDB() throws SQLException  {
        database = DataBaseControl.getInstance();
    }
    
    /**
     * Función que verifica si dado un usuario, exsite en la DB.
     * Se realizan tres pruebas diferentes:
     *  - Usuario encontrado, pero es Sanitario, no admin.
     *  - Usuario encontrado, es admin.
     *  - Usuario no encontrado.
     * 
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    @Test
    public void testVerificarUsuario() throws SQLException, ClassNotFoundException {
        UsuarioDTO usuarioCorrectoSan = new UsuarioDTO("123456789X", "antlopg@gmail.com", "81dc9bdb52d04dc20036dbd8313ed055");
        UsuarioDTO usuarioCorrectoAdm = new UsuarioDTO("199A", "admin@hsw.com", "81dc9bdb52d04dc20036dbd8313ed055");
        UsuarioDTO usuarioIncorrectoSan = new UsuarioDTO("12345678", "ant@gmail.com", "123456789");
        
        assertTrue("UsuarioDTO{correoElectronico=antlopg@gmail.com,contraseña=81dc9bdb52d04dc20036dbd8313ed055, admin=false}".equals(database.verificarUsuario(usuarioCorrectoSan).toString()));
        assertTrue("UsuarioDTO{correoElectronico=admin@hsw.com,contraseña=81dc9bdb52d04dc20036dbd8313ed055, admin=true}".equals(database.verificarUsuario(usuarioCorrectoAdm).toString()));
        assertTrue(null == database.verificarUsuario(usuarioIncorrectoSan));
    }
    
    /**
     * Función que verifica si se ha podido dar de alta un nuevo sanitario.
     * Para ello, antes se añadirá en la db como usuario. Luego se le especializará
     *  en sanitario.
     * 
     * @throws SQLException 
     */
    @Test
    public void testAñadirNuevoSanitario() throws SQLException {
        SanitarioDTO sanitario = new SanitarioDTO("Manolo", "García", "Martínez", "18123598X", 625174985, "manologar@gmail.com", "154876", "Médico");
       
        assertTrue(true == database.addUsuario(sanitario.getDni(), sanitario.getEmail(), sanitario.getContraseña()));
        assertTrue(true == database.addSanitario(sanitario));
    }
    
    /**
     * Función que modifica un sanitario existente.
     * Para ello, en la prueba se ha modificado el primer apellido.
     * Se ha verificado que se modifica correctamente el sanitario.
     * 
     * @throws SQLException 
     */
    @Test
    public void testEditarSanitarioExistente() throws SQLException {
        SanitarioDTO sanitarioModif = new SanitarioDTO("Manolo", "Pérez", "Martínez", "18123598X", 625174985, "manologar@gmail.com", "154876", "Médico");
        assertTrue(true == database.editarSanitario(sanitarioModif));
    }
    
    /**
     * Función que verifica si se ha podido añadir un nuevo paciente.
     * 
     * @throws SQLException 
     */
    @Test
    public void testAñadirNuevoPaciente() throws SQLException {
        PacienteDTO paciente = new PacienteDTO("256047A", "Alejandro", "Domingo", "Navarro", "Ácido clavulánico", 18, 1.87, 78.53);
        assertTrue(true == database.addPaciente(paciente));
    }
    
    /**
     * Función que verifica la asignación de un nuevo episodio de atención
     * al paciente creado en la función de test anterior.
     * 
     * @throws SQLException
     * @throws ParseException 
     */
    @Test
    public void testAñadirEpisodioPaciente() throws SQLException, ParseException {
        String string_date = "2022-05-20";
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        
        Date fecha = f. parse(string_date);
        
        EpisodioAtencionDTO episodio = new EpisodioAtencionDTO(10, fecha, "Alejandro ha acudido en consecuencia de dolor de cabeza.", "El paciente ha sido positivo en COVID-19");
        assertTrue(true == database.nuevoEpisodio(episodio, "256047A"));
    }
    
    /**
     * Función que verifica la asignación de una nueva cita
     * al paciente creado en la función de test anterior con el
     * sanitario creado en otra función de test.
     * 
     * @throws SQLException
     * @throws ParseException 
     */
    @Test
    public void testAñadirCitaPaciente() throws SQLException, ParseException {
        String string_date = "2022-05-20";
        String string_hora = "13:15";
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat h = new SimpleDateFormat("HH:mm");
        
        Date fecha = f. parse(string_date);
        Date hora = h.parse(string_hora);
        
        UbicacionDTO ubicacion = new UbicacionDTO("Teruel", "Hospital Obispo Polanco", "14A");
        CitaDTO cita = new CitaDTO(10, "18123598X", ubicacion, fecha, hora, "El paciente tiene cita con su médico de cabecera.");
        
        assertTrue(true == database.nuevaCita(cita, "256047A"));
    }
    
    /**
     * Función que verifica la inserción de un medicamento
     * en la receta del paciente creado en una función de test anterior.
     * 
     * @throws SQLException
     * @throws ParseException 
     */
    @Test
    public void testAñadirMedicamentoPaciente() throws SQLException, ParseException {
        String string_date_1 = "2022-05-20";
        String string_date_2 = "2022-05-27";
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        
        Date fechaInicio = f. parse(string_date_1);
        Date fechaFin = f. parse(string_date_2);
        
        MedicamentoDTO medicamento = new MedicamentoDTO(1, "Paracetamol");
        MedicamentoPacienteDTO medicamentoPaciente = new MedicamentoPacienteDTO(10, medicamento, fechaInicio, fechaFin);
        assertTrue(true == database.añadirMedicamentoAPaciente(medicamentoPaciente, "256047A"));
    }
    
     /**
     * Función que verifica la inserción de una vacuna
     * en la historia del paciente creado en una función de test anterior.
     * 
     * @throws SQLException
     * @throws ParseException 
     */
    @Test
    public void testAñadirVacunaPaciente() throws SQLException, ParseException {
        String string_date = "2022-05-20";
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        
        Date fecha = f. parse(string_date);
        
        VacunaDTO vacuna = new VacunaDTO(1, "Hepatitis B");
        VacunaPacienteDTO vacunaPaciente = new VacunaPacienteDTO(10, vacuna, fecha);
        assertTrue(true == database.añadirVacunaAPaciente(vacunaPaciente, "256047A"));
    }
    
    /**
     * Función que obtiene la historia completa de un paciente.
     * Para ello, se compara la historia que yo considero que debo de obtener, con
     * la historia devuelta por la DB.
     * La historia que creo yo, 
     * 
     * @throws SQLException 
     */
    @Test
    public void testObtenerHistoriaCompletaPaciente() throws SQLException, ParseException {
        Gson gson = new Gson();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        
        String string_date_ep = "2022-05-20";
        String string_date_med_1 = "2022-05-20";
        String string_date_med_2 = "2022-05-27";
        String string_date_vac = "2022-05-20";
        
        Date fechaEp = f. parse(string_date_ep);
        Date fechaInicio = f. parse(string_date_med_1);
        Date fechaFin = f. parse(string_date_med_2);
        Date fechaVac = f. parse(string_date_vac);
        
        EpisodioAtencionDTO[] episodios = new EpisodioAtencionDTO[] {new EpisodioAtencionDTO(10, fechaEp, "Alejandro ha acudido en consecuencia de dolor de cabeza.", "El paciente ha sido positivo en COVID-19")};
        
        MedicamentoDTO medicamento = new MedicamentoDTO(1, "Paracetamol");
        MedicamentoPacienteDTO[] medicamentos = new MedicamentoPacienteDTO[] {new MedicamentoPacienteDTO(10, medicamento, fechaInicio, fechaFin)};
        
        VacunaDTO vacuna = new VacunaDTO(1, "Hepatitis B");
        VacunaPacienteDTO[] vacunas = new VacunaPacienteDTO[] {new VacunaPacienteDTO(10, vacuna, fechaVac)};
        
        HistoriaPacienteDTO historia = new HistoriaPacienteDTO(Arrays.asList(episodios), Arrays.asList(medicamentos), Arrays.asList(vacunas));
        HistoriaPacienteDTO historiaRecibida = gson.fromJson(database.obtenerHistoriaPaciente("256047A"), HistoriaPacienteDTO.class);
        
        assertTrue(historia.toJson().equals(historiaRecibida.toJson()));
    }
}
