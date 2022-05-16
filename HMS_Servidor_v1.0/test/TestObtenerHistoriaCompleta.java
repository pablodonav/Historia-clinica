/**
 * TestObtenerHistoriaCompleta.java
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
import modelo.clasesDTOs.EpisodioAtencionDTO;
import modelo.clasesDTOs.HistoriaPacienteDTO;
import modelo.clasesDTOs.MedicamentoDTO;
import modelo.clasesDTOs.MedicamentoPacienteDTO;
import modelo.clasesDTOs.VacunaDTO;
import modelo.clasesDTOs.VacunaPacienteDTO;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 * Clase que contiene métodos de JUnit para realizar pruebas.
 * 
 */
public class TestObtenerHistoriaCompleta {
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
        
        EpisodioAtencionDTO[] episodios = new EpisodioAtencionDTO[] 
            {new EpisodioAtencionDTO(10, fechaEp, "Alejandro ha acudido en consecuencia de dolor de cabeza.", 
                    "El paciente ha sido positivo en COVID-19")};
        
        MedicamentoDTO medicamento = new MedicamentoDTO(1, "Paracetamol");
        MedicamentoPacienteDTO[] medicamentos = new MedicamentoPacienteDTO[] 
            {new MedicamentoPacienteDTO(10, medicamento, fechaInicio, fechaFin)};
        
        VacunaDTO vacuna = new VacunaDTO(1, "Hepatitis B");
        VacunaPacienteDTO[] vacunas = new VacunaPacienteDTO[] 
            {new VacunaPacienteDTO(10, vacuna, fechaVac)};
        
        HistoriaPacienteDTO historia = new HistoriaPacienteDTO(Arrays.asList(episodios), 
                Arrays.asList(medicamentos), Arrays.asList(vacunas));
        HistoriaPacienteDTO historiaRecibida = 
                gson.fromJson(database.obtenerHistoriaPaciente("256047A"), HistoriaPacienteDTO.class);
        
        assertTrue(historia.toJson().equals(historiaRecibida.toJson()));
    }
}
