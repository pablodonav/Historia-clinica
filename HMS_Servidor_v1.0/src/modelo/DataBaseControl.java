/**
 * DataBaseControl.java
 * Pablo Doñate Navarro
 * v1.0 02/04/2022.
 */
package modelo;

import modelo.clasesDAO.SanitarioDAOImpl;
import modelo.clasesDTOs.SanitarioDTO;
import java.sql.SQLException;
import java.sql.*;
import modelo.clasesDAO.PacienteDAOImpl;
import modelo.clasesDAO.UsuarioDAOImpl;
import modelo.clasesDTOs.PacienteDTO;
import modelo.clasesDTOs.UsuarioDTO;

/**
 * Clase que contiene las operaciones asociadas
 * a la DB.
 * 
 */
public class DataBaseControl {
    private static DataBaseControl instancia 
        = null; //Es singleton
    private Connection conexion;
    
    private SanitarioDAOImpl sanImpl;
    private UsuarioDAOImpl usImpl;
    private PacienteDAOImpl pacImpl;
    
    /**
     * Crea la conexión con la DB.
     * 
     * @throws SQLException 
     */
    public DataBaseControl() throws SQLException {
    	connectDB();
    }
    
    /**
     * Devuelve la instancia de la clase-
     *  -> Es singleton.
     * 
     * @return
     * @throws SQLException 
     */
    public synchronized static DataBaseControl getInstance() 
            throws SQLException {
        if(instancia == null) {
            instancia = new DataBaseControl();
        }

        return instancia;
    }
    
    /** 
     * Realiza la conexión con la base de datos.
     * 
     * @throws SQLException
     */
    public void connectDB() throws SQLException {
    	DataBase db = new DataBase();

        conexion = db.MySQLConnect();
        
        sanImpl = new SanitarioDAOImpl(conexion);
        usImpl = new UsuarioDAOImpl(conexion);
        pacImpl = new PacienteDAOImpl(conexion);
    }
    
    /**
     * Añade un nuevo usuario a la DB.
     * 
     * @param _dni
     * @param _email
     * @param _pwd
     * @return
     * @throws SQLException 
     */
    public boolean addUsuario(String _dni, String _email, String _pwd) throws SQLException {
        if (conexion.isClosed()) {
            connectDB();
        }

        UsuarioDTO usuario = new UsuarioDTO(_dni, _email, _pwd);
        
        return usImpl.addUsuario(usuario);
    }
    
    /**
     * Añade un nuevo sanitario a la DB.
     * 
     * @param _sanitario
     * @return
     * @throws SQLException 
     */
    public boolean addSanitario(SanitarioDTO _sanitario)
            throws SQLException {

        if (conexion.isClosed()) {
            connectDB();
        }

        return sanImpl.addSanitario(_sanitario);
    }
    
    /**
     * Añade un nuevo paciente a la DB.
     * 
     * @param _paciente
     * @return
     * @throws SQLException 
     */
    public boolean addPaciente(PacienteDTO _paciente)
            throws SQLException {

        if (conexion.isClosed()) {
            connectDB();
        }

        return pacImpl.addPaciente(_paciente);
    }
    
    /**
     * Método que verifica si existe un usuario en la DB.
     * Devuelve el usuario devuelto por la base de datos.
     * 
     * @param _usuario
     * @return
     * @throws SQLException 
     */
    public UsuarioDTO verificarUsuario(UsuarioDTO _usuario) throws SQLException {
        if (conexion.isClosed()) {
            connectDB();
        }
        
        return usImpl.checkUser(_usuario);
    }
    
    /**
     * Método que modifica un sanitario existente.
     * 
     * @param _sanitario
     * @return
     * @throws SQLException 
     */
    public boolean editarSanitario(SanitarioDTO _sanitario) throws SQLException {
        if (conexion.isClosed()) {
            connectDB();
        }
        
        UsuarioDTO usuario = new UsuarioDTO(_sanitario.getDni(), _sanitario.getEmail(), _sanitario.getContraseña());
        
        if (usImpl.updateUsuario(usuario)) {
            return sanImpl.updateSanitario(_sanitario);
        }
        
        return false;
    }
}