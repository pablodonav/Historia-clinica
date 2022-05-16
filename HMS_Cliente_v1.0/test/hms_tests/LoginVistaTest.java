/**
 * LoginVistaTest.java
 * Adnana Catrinel Dragut
 * v2.0 15/05/2022.
 * 
 */

package hms_tests;

import modelo.clasesProxys.Comms;
import org.junit.Before;
import org.junit.Test;
import vista.LoginVista;
import static org.junit.Assert.*;

/**
 * Clase que se encarga de verificar el funcionamiento
 * de los métodos básicos de la clase LoginVista.
 * 
 */
public class LoginVistaTest {
    private Comms commsTest;
    private LoginVista loginVistaTest;
    
    private String ERROR_SINTAXIS_EMAIL =  
        "Sintaxis de correo electrónico errónea.\n" +
        "Vuelva a introducir las credenciales.";
        
    /**
     * Inicialización de las variables necesarias para realizar las pruebas.
     * 
     */
    @Before
    public void setUp() {
        this.commsTest = new Comms();
        this.loginVistaTest = new LoginVista(null, commsTest, "1");
        this.loginVistaTest.setVisible(false);
    }

    /**
     * Comprueba el correcto funcionamiento del método "sintaxisCorreoCorrecta()"
     * utilizando para ello un correo válido.
     * 
     */
    @Test
    public void testSintaxisCorreoCorrectaCaso1(){
        System.out.println("Test 1 - sintaxisCorreoCorrecta()");
        String email = "a@gmail.com";
        boolean expectedOutput = true;
        boolean result = loginVistaTest.sintaxisCorreoCorrecta(email);
        assertEquals(expectedOutput, result);
    }
    
    /**
     * Comprueba el correcto funcionamiento del método "sintaxisCorreoCorrecta()"
     * utilizando para ello un correo inválido.
     * 
     */
    @Test
    public void testSintaxisCorreoCorrectaCaso2(){
        System.out.println("Test 2 - sintaxisCorreoCorrecta()");
        String email = "a@.com";
        boolean expectedOutput = false;
        boolean result = loginVistaTest.sintaxisCorreoCorrecta(email);
        assertEquals(expectedOutput, result);
    }
    
    /**
     * Comprueba el correcto funcionamiento de la excepción que debe saltar para 
     * notificar un fallo en la sintaxis de un correo electrónico.
     * 
     * @throws Exception 
     */
    @Test
    public void testExpectedException() throws Exception {
        System.out.println("Test 1 - testExpectedException()");
        Throwable exception = assertThrows(Exception.class, () -> loginVistaTest.crearJsonUsuarioAVerificar());
        assertEquals(ERROR_SINTAXIS_EMAIL, exception.getMessage());
    }
}
