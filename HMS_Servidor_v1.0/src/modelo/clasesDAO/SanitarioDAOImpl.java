/**
 * SanitarioDAOImpl.java
 * Pablo Doñate Navarro
 * v1.0 02/04/2022.
 */
package modelo.clasesDAO;

import modelo.clasesDTOs.SanitarioDTO;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

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

    /**
     * Añade un nuevo sanitario a la DB.
     * 
     * @param _sanitario
     * @return
     * @throws SQLException 
     */
    @Override
    public boolean addSanitario(SanitarioDTO _sanitario) throws SQLException {
        int cuenta = 0;
        stmt_getSan.setString(1, _sanitario.getDni());

        ResultSet rs = stmt_getSan.executeQuery();
        if (rs.next()) {
            cuenta = rs.getInt(1);
        }
        rs.close();
        
        if (cuenta == 0) {
            stmt_add.setString(1, _sanitario.getDni());
            stmt_add.setString(2, _sanitario.getNombre());
            stmt_add.setString(3, _sanitario.getApellido1());
            stmt_add.setString(4, _sanitario.getApellido2());
            stmt_add.setInt(5, _sanitario.getTelefono());
            stmt_add.setString(6, _sanitario.getPuestoTrabajo());

            return stmt_add.executeUpdate() > 0;
        } else {
            return false;
        }
    }

    /**
     * Borra un sanitario existente de la DB.
     * 
     * @param _dni
     * @return
     * @throws SQLException 
     */
    @Override
    public boolean deleteSanitario(String _dni) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * Modifica un sanitario existente de la DB.
     * 
     * @param _sanitario
     * @return
     * @throws SQLException 
     */
    @Override
    public boolean updateSanitario(SanitarioDTO _sanitario) throws SQLException {
        List<String> atributos = Arrays.asList("dni", "nombre", "apellido1", "apellido2", "telefono", "puesto_trabajo");
        List<String> modified_param = new ArrayList<String>();

        for (String atributo : atributos) {
            if (("nombre".equals(atributo)) && (_sanitario.getNombre() != null)) {
                modified_param.add(String.format("%s=\"%s\"", atributo, _sanitario.getNombre()));
            }
            if (("apellido1".equals(atributo)) && (_sanitario.getApellido1() != null)) {
                modified_param.add(String.format("%s=\"%s\"", atributo, _sanitario.getApellido1()));
            }
            if (("apellido2".equals(atributo)) && (_sanitario.getApellido2() != null)) {
                modified_param.add(String.format("%s=\"%s\"", atributo, _sanitario.getApellido2()));
            }
            if (("telefono".equals(atributo)) && (_sanitario.getTelefono() != 0)) {
                modified_param.add(String.format("%s=\"%s\"", atributo, _sanitario.getTelefono()));
            }
            if (("puesto_trabajo".equals(atributo)) && (_sanitario.getPuestoTrabajo() != null)) {
                modified_param.add(String.format("%s=\"%s\"", atributo, _sanitario.getPuestoTrabajo()));
            }
        }

        String param = String.join(", ", modified_param);
        String sql = String.format(UPDATE, param, _sanitario.getDni());

        this.stmt_upd = connection.prepareStatement(sql);

        if (stmt_upd.executeUpdate() > 0) {
            return true;
        }

        return false;
    }

    /**
     * Obtiene un sanitario de la DB.
     * 
     * @param _dni
     * @return
     * @throws SQLException 
     */
    @Override
    public SanitarioDTO getSanitario(String _dni) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * Obtiene la colección de sanitarios de la DB.
     * 
     * @return
     * @throws SQLException 
     */
    @Override
    public List<SanitarioDTO> getSanitarios() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
