/**
 * SanitarioDAOImpl.java
 * Pablo Doñate Navarro
 * v2.4 01/05/2022.
 */
package modelo.clasesDAO;

import modelo.clasesDTOs.SanitarioDTO;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;

/**
 * Clase que contiene las funciones necesarias
 *  para realizar las operaciones CRUD de un sanitario
 * 
 */
public class SanitarioDAOImpl implements SanitarioDAO {
    private PreparedStatement stmt_add;
    private PreparedStatement stmt_del;
    private PreparedStatement stmt_upd;
    private PreparedStatement stmt_getAll;
    private PreparedStatement stmt_getSan;

    private static final String INSERT = "INSERT INTO SANITARIO(dni, nombre, apellido1, apellido2, telefono, puesto_trabajo) VALUES(?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE SANITARIO SET nombre=?, apellido1=?, apellido2=?, telefono=?, puesto_trabajo=? WHERE dni=?";
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
     * Modifica un sanitario existente de la DB.
     * 
     * @param _sanitario
     * @return
     * @throws SQLException 
     */
    @Override
    public boolean updateSanitario(SanitarioDTO _sanitario) throws SQLException {
        stmt_upd.setString(1, _sanitario.getNombre());
        stmt_upd.setString(2, _sanitario.getApellido1());
        stmt_upd.setString(3, _sanitario.getApellido2());
        stmt_upd.setInt(4, _sanitario.getTelefono());
        stmt_upd.setString(5, _sanitario.getPuestoTrabajo());
        stmt_upd.setString(6, _sanitario.getDni());

        return stmt_upd.executeUpdate() > 0;
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
        SanitarioDTO sanitario = null;

        stmt_getSan.setString(1, _dni);
        ResultSet rs = stmt_getSan.executeQuery();

        while (rs.next()) {
            String nombre = rs.getString("nombre");
            String apellido1 = rs.getString("apellido1");
            String apellido2 = rs.getString("apellido2");
            String dni = rs.getString("dni");
            int telefono = rs.getInt("telefono");
            String correo = rs.getString("correo");
            String contraseña = rs.getString("contraseña");
            String puesto = rs.getString("puesto_trabajo");

            sanitario = new SanitarioDTO(nombre, apellido1, apellido2, dni, telefono, correo, contraseña, puesto);
        }
        return sanitario;
    }

    /**
     * Obtiene la colección de sanitarios de la DB.
     * 
     * @return
     * @throws SQLException 
     */
    @Override
    public List<SanitarioDTO> getSanitarios() throws SQLException {
        SanitarioDTO sanitario = null;

        ResultSet rsSanitarios = stmt_getAll.executeQuery();
        List<SanitarioDTO> sanitarios = new ArrayList<SanitarioDTO>();

        while (rsSanitarios.next()) {
            String dni = rsSanitarios.getString("dni");
            String nombre = rsSanitarios.getString("nombre");
            String apellido1 = rsSanitarios.getString("apellido1");
            String apellido2 = rsSanitarios.getString("apellido2");
            int telefono = rsSanitarios.getInt("telefono");
            String puesto_trabajo = rsSanitarios.getString("puesto_trabajo");

            sanitario = new SanitarioDTO(nombre, apellido1, apellido2, dni, telefono, null, null, puesto_trabajo);
            sanitarios.add(sanitario);
        }

        return sanitarios;
    }
}
