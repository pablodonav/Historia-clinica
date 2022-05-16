/**
 * TestAñadirMedicVacunEpCit.java
 * Pablo Doñate Navarro
 * 
 */
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import modelo.DataBaseControl;
import modelo.clasesDTOs.CitaDTO;
import modelo.clasesDTOs.EpisodioAtencionDTO;
import modelo.clasesDTOs.MedicamentoDTO;
import modelo.clasesDTOs.MedicamentoPacienteDTO;
import modelo.clasesDTOs.UbicacionDTO;
import modelo.clasesDTOs.VacunaDTO;
import modelo.clasesDTOs.VacunaPacienteDTO;

import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 * Clase que contiene métodos de JUnit para realizar pruebas.
 * 
 */
public class TestAñadirMedicVacunEpCit {
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
}
