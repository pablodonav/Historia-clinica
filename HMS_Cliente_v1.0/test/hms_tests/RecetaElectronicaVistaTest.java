/**
 * RecetaElectronicaVistaTest.java
 * Adnana Catrinel Dragut
 * v2.0 15/05/2022.
 * 
 */

package hms_tests;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import vista.vistasUsuarioSanitario.RecetaElectronicaVista;

/**
 * Clase que se encarga de verificar el funcionamiento
 * de los métodos básicos de la clase RecetaElectronicaVista.
 * 
 */
public class RecetaElectronicaVistaTest {
    private RecetaElectronicaVista recetaElectronicaVistaTest;
    
    /**
     * Inicialización de las variables necesarias para realizar las pruebas.
     * 
     */
    @Before
    public void setUp() {
        this.recetaElectronicaVistaTest = new RecetaElectronicaVista();
        this.recetaElectronicaVistaTest.setVisible(false);
    }
    
    /**
     * Comprueba el correcto funcionamiento del método "fechaFinPosteriorAFechaInicio()",
     * pasándole para ello una fecha fin anterior a la fecha de inicio.
     * 
     * @throws ParseException 
     */
    @Test
        public void testFechaFinPosteriorAFechaInicioCaso1() throws ParseException{
        System.out.println("Test 1 - testFechaFinPosteriorAFechaInicioCaso1()");
        
        String oldstring1 = "2022-05-15 18:30:78.99";
        Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(oldstring1);
        
        String oldstring2 = "2022-04-10 18:30:78.99";
        Date date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(oldstring2);
        
        boolean expectedOutput = false;
        boolean result = recetaElectronicaVistaTest.fechaFinPosteriorAFechaInicio(date1, date2);
        assertEquals(expectedOutput, result);
    }
    
    /**
     * Comprueba el correcto funcionamiento del método "fechaFinPosteriorAFechaInicio()",
     * pasándole para ello una fecha fin posterior a la fecha de inicio.
     * 
     * @throws ParseException 
     */
    @Test
    public void testFechaFinPosteriorAFechaInicioCaso2() throws ParseException{
        System.out.println("Test 2 - testFechaFinPosteriorAFechaInicioCaso2()");
        
        String oldstring1 = "2022-05-15 18:30:78.99";
        Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(oldstring1);
        
        String oldstring2 = "2022-07-10 18:30:78.99";
        Date date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(oldstring2);
        
        boolean expectedOutput = true;
        boolean result = recetaElectronicaVistaTest.fechaFinPosteriorAFechaInicio(date1, date2);
        assertEquals(expectedOutput, result);
    }
}
