/**
 * EpisodiosPacienteVistaTest.java
 * Adnana Catrinel Dragut
 * v2.0 15/05/2022.
 * 
 */

package hms_tests;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JTable;
import javax.swing.JTextField;
import modelo.clasesDTOs.EpisodioDeAtencionDTO;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import vista.vistasUsuarioSanitario.EpisodiosPacienteVista;

/**
 * Clase que se encarga de verificar el funcionamiento
 * de los métodos básicos de la clase EpisodiosPacienteVista.
 * 
 */
public class EpisodiosPacienteVistaTest {
    private JTable tabla_con_episodios;
    private EpisodiosPacienteVista episodiosPacienteVistaTest;
    private List<EpisodioDeAtencionDTO> episodios = new ArrayList<EpisodioDeAtencionDTO>();
    private JTextField diagnostico_input_field;
    
    /**
     * Inicialización de las variables necesarias para realizar las pruebas.
     * 
     */
    @Before
    public void setUp() throws ParseException {
        this.episodiosPacienteVistaTest = new EpisodiosPacienteVista();
        this.episodiosPacienteVistaTest.setVisible(false);
        this.diagnostico_input_field = new JTextField("Resfriado común");
        
        // Inicialización para testCrearJsonEpisodioEditadoCaso1
        String oldstring1 = "2022-05-15 18:30:78.99";
        Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(oldstring1);
        
        String oldstring2 = "2022-04-10 18:30:78.99";
        Date date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(oldstring2);
        
        String oldstring3 = "2020-12-01 18:30:78.99";
        Date date3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(oldstring3);
        
        episodios.add(new EpisodioDeAtencionDTO(1, date1, "Dolor de Garganta", "Resfriado común"));
        episodios.add(new EpisodioDeAtencionDTO(2, date2, "Fiebre y dolor muscular", "Covid"));
        episodios.add(new EpisodioDeAtencionDTO(3, date3, "Dolor de cabeza y tos", ""));
        
        String[][] tableData = {{"1", "2022-05-15", "Dolor de Garganta", "Resfriado común"},
                                {"2", "2022-04-10", "Fiebre y dolor muscular", "Covid"},
                                {"3", "2020-12-01", "Dolor de cabeza y tos", ""}};
        
        String[] tableColumn = {"Identificador", "Fecha", "Motivo/s", "Diagnóstico"};
        tabla_con_episodios = new JTable(tableData, tableColumn);
        episodiosPacienteVistaTest.setEpisodios(episodios);
        episodiosPacienteVistaTest.setDiagnostico_input_field(diagnostico_input_field);
        tabla_con_episodios.addRowSelectionInterval(0, 0);
        episodiosPacienteVistaTest.setTabla_con_episodios(tabla_con_episodios);
    }
    
    /**
     * Comprueba el correcto funcionamiento del método "crearJsonEpisodioEditado()".
     * 
     */
    @Test
    public void testCrearJsonEpisodioEditadoCaso1() throws Exception{
        System.out.println("Test 1 - testCrearJsonEpisodioEditadoCaso1()");
        String expectedOutput = "{\"id\":1,\"fecha\":\"May 15, 2022, 6:31:18 PM\"," +
            "\"motivo\":\"Dolor de Garganta\",\"diagnostico\":\"Resfriado común\"}";
        String result = episodiosPacienteVistaTest.crearJsonEpisodioEditado();
        assertEquals(expectedOutput, result);
    }
    
    /**
     * Comprueba el correcto funcionamiento del método "obtenerIndexEpisodioPaciente()".
     * 
     */
    @Test
    public void testObtenerIndexEpisodioPacienteCaso1(){
        System.out.println("Test 1 - testObtenerIndexEpisodioPacienteCaso1()");
        int expectedOutput = 2;
        int result = episodiosPacienteVistaTest.obtenerIndexEpisodioPaciente(3);
        assertEquals(expectedOutput, result);
    }
}
