/**
 * EditarSanitarioVistaTest.java
 * Adnana Catrinel Dragut
 * v2.0 15/05/2022.
 * 
 */

package hms_tests;

import javax.swing.JTable;
import javax.swing.JTextField;
import modelo.clasesDTOs.SanitarioDTO;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import vista.vistasUsuarioAdmin.EditarSanitarioVista;

/**
 * Clase que se encarga de verificar el funcionamiento
 * de los métodos básicos de la clase EditarSanitarioVista.
 * 
 */
public class EditarSanitarioVistaTest{
    private EditarSanitarioVista editarSanitarioTest;
    private JTable tabla_con_sanitarios;
    private SanitarioDTO sanitarioTest;
    private JTextField _nombre_input_field_test;
    
    /**
     * Inicialización de las variables necesarias para realizar las pruebas.
     * 
     */
    @Before
    public void setUp() {
        this.editarSanitarioTest = new EditarSanitarioVista();
        this.tabla_con_sanitarios = new JTable();
        this._nombre_input_field_test = new JTextField();
        this.editarSanitarioTest.setVisible(false);
        this.sanitarioTest = new SanitarioDTO("Ana", "Test2", "Test2", "112233X", 987654321, "a@gmail.com", "4321", "Médico");
        
        // Inicialización para testObtenerDniSanitarioSeleccionadoCaso1
        String[][] tableData = {{"Juan", "Test1", "Test1", "123456X", "123456789", "j@gmail.com", "1234", "Médico"},
                                {"Ana", "Test2", "Test2", "112233X", "987654321", "a@gmail.com", "4321", "Médico"},
                                {"Maria", "Test3", "Test3", "112233X", "111222333", "m@gmail.com", "1111", "Otros"}};
        
        String[] tableColumn = {"Nombre", "Apellio1", "Apellido2", "DNI", "Teléfono", "Correo", "Puesto"};
        tabla_con_sanitarios = new JTable(tableData, tableColumn);
        editarSanitarioTest.setTabla_con_sanitarios(tabla_con_sanitarios);
    }
    
    /**
     * Comprueba el correcto funcionamiento del método "obtenerDniSanitarioSeleccionado()".
     * 
     */
    @Test
    public void testObtenerDniSanitarioSeleccionadoCaso1(){
        System.out.println("Test 1 - testObtenerDniSanitarioSeleccionadoCaso1()");
        String expectedOutput = "112233X";
        String result = editarSanitarioTest.obtenerDniSanitarioSeleccionado(1);
        assertEquals(expectedOutput, result);
    }
    
    /**
     * Comprueba el correcto funcionamiento del método "modificarNombreSanitario()",
     * pasándole para ello un nombre sin modificar de un sanitario.
     * 
     */
    @Test
    public void testModificarNombreSanitarioCaso1(){
        System.out.println("Test 1 - testModificarNombreSanitarioCaso1()");
        _nombre_input_field_test.setText("Ana");
        editarSanitarioTest.setNombre_input_field(_nombre_input_field_test);
        
        String expectedOutput = sanitarioTest.getNombre();
        System.out.println("Nombre sanitario antes de llamar al método de modificar --> " + sanitarioTest.getNombre());
        editarSanitarioTest.modificarNombreSanitario(sanitarioTest);
        System.out.println("Nombre sanitario después de llamar al método de modificar --> " + sanitarioTest.getNombre());
        String result = sanitarioTest.getNombre();
        assertEquals(expectedOutput, result);
    }
    
    /**
     * Comprueba el correcto funcionamiento del método "modificarNombreSanitario()",
     * pasándole para ello un nombre modificado de un sanitario.
     * 
     */
    @Test
    public void testModificarNombreSanitarioCaso2(){
        System.out.println("Test 2 - testModificarNombreSanitarioCaso1()");
        _nombre_input_field_test.setText("Ana Maria");
        editarSanitarioTest.setNombre_input_field(_nombre_input_field_test);
        
        String expectedOutput = "Ana Maria";
        System.out.println("Nombre sanitario antes de llamar al método de modificar --> " + sanitarioTest.getNombre());
        editarSanitarioTest.modificarNombreSanitario(sanitarioTest);
        System.out.println("Nombre sanitario después de llamar al método de modificar --> " + sanitarioTest.getNombre());
        String result = sanitarioTest.getNombre();
        assertEquals(expectedOutput, result);
    } 
}
