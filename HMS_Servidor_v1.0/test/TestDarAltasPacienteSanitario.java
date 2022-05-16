/**
 * TestDarAltasPacienteSanitario.java
 * Pablo Doñate Navarro
 * 
 */
import java.sql.SQLException;
import modelo.DataBaseControl;
import modelo.clasesDTOs.PacienteDTO;
import modelo.clasesDTOs.SanitarioDTO;
import modelo.clasesDTOs.UsuarioDTO;

import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 * Clase que contiene métodos de JUnit para realizar pruebas.
 * 
 */
public class TestDarAltasPacienteSanitario {
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
        UsuarioDTO usuarioCorrectoAdm = new UsuarioDTO("987987987A", "admin@hsw.com", "81dc9bdb52d04dc20036dbd8313ed055");
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
}