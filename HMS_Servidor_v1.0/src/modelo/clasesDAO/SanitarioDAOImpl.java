/**
 * SanitarioDAOImpl.java
 * Pablo Doñate Navarro
 * v1.0 02/04/2022.
 */
package modelo.clasesDAO;

import modelo.clasesDTOs.SanitarioDTO;
import java.util.List;
import java.sql.*;

/**
 * Clase que contiene las funciones necesarias
 *  para realizas las operaciones CRUD de un sanitario
 * 
 */
public class SanitarioDAOImpl implements SanitarioDAO {
    private PreparedStatement stmt_add;
    private PreparedStatement stmt_del;
    private PreparedStatement stmt_upd;
    private PreparedStatement stmt_getAll;
    private PreparedStatement stmt_getSan;

    private static final String INSERT = "INSERT INTO SANITARIO(dni, nombre, apellido1, apellido2, telefono, puesto_trabajo) VALUES(?, ?, ?, ?, ?, ?)";
    private static final String DELETE = "DELETE FROM SANITARIO WHERE dni=?";
    private static final String UPDATE = "UPDATE SANITARIO SET %s WHERE dni=\"%s\"";
    private static final String FIND_ALL = "SELECT * FROM SANITARIO";
    private static final String FIND_SANITARIO = "SELECT * FROM SANITARIO WHERE dni=?";
    
    private Connection connection;

    /**
     *  Crea un SanitarioDAOImpl, donde define la estructura
     *  de sus operaciones.
     * 
     * @param _conn
     * @throws java.sql.SQLException
     */
    public SanitarioDAOImpl(Connection _conn) throws SQLException {
        this.connection = _conn;
        this.stmt_add = _conn.prepareStatement(INSERT);
        this.stmt_del = _conn.prepareStatement(DELETE);
        this.stmt_upd = _conn.prepareStatement(UPDATE);
        this.stmt_getAll = _conn.prepareStatement(FIND_ALL);
        this.stmt_getSan = _conn.prepareStatement(FIND_SANITARIO);
    }

    @Override
    public boolean addSanitario(SanitarioDTO _sanitario) throws SQLException {
        stmt_add.setString(1, _sanitario.getDni());
        stmt_add.setString(2, _sanitario.getNombre());
        stmt_add.setString(3, _sanitario.getApellido1());
        stmt_add.setString(4, _sanitario.getApellido2());
        stmt_add.setInt(5, _sanitario.getTelefono());
        stmt_add.setString(6, _sanitario.getPuestoTrabajo());

        return stmt_add.executeUpdate() > 0;
    }

    @Override
    public boolean deleteSanitario(String _dni) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean updateSanitario(SanitarioDTO _sanitario) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public SanitarioDTO getSanitario(String _dni) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<SanitarioDTO> getSanitarios() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
