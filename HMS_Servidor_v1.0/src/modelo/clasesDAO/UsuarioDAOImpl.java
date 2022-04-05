/**
 * SanitarioDAOImpl.java
 * Pablo Doñate Navarro
 * v1.0 02/04/2022.
 */
package modelo.clasesDAO;

import modelo.clasesDTOs.UsuarioDTO;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Clase que contiene las funciones necesarias
 *  para realizas las operaciones CRUD de un usuario
 * 
 */
public class UsuarioDAOImpl implements UsuarioDAO {
    private PreparedStatement stmt_add;
    private PreparedStatement stmt_del;
    private PreparedStatement stmt_upd;
    private PreparedStatement stmt_getAll;
    private PreparedStatement stmt_getUs;
    private PreparedStatement stmt_getAdm;

    private static final String INSERT = "INSERT INTO USUARIO(dni, correo, contraseña) VALUES(?, ?, ?)";
    private static final String DELETE = "DELETE FROM USUARIO WHERE dni=?";
    private static final String UPDATE = "UPDATE USUARIO SET %s WHERE dni=\"%s\"";
    private static final String FIND_ALL = "SELECT * FROM USUARIO";
    private static final String FIND_USUARIO = "SELECT * FROM USUARIO WHERE dni=? AND correo=? AND contraseña=?";
    private static final String FIND_ADMIN = "SELECT * FROM ADMINISTRADOR WHERE dni=?";
    
    private Connection connection;

    /**
     *  Crea un UsuarioDAOImpl, donde define la estructura
     *  de sus operaciones.
     *
     * @param _conn
     * @throws java.sql.SQLException
     */
    public UsuarioDAOImpl(Connection _conn) throws SQLException {
        this.connection = _conn;
        this.stmt_add = _conn.prepareStatement(INSERT);
        this.stmt_del = _conn.prepareStatement(DELETE);
        this.stmt_upd = _conn.prepareStatement(UPDATE);
        this.stmt_getAll = _conn.prepareStatement(FIND_ALL);
        this.stmt_getUs = _conn.prepareStatement(FIND_USUARIO);
        this.stmt_getAdm = _conn.prepareStatement(FIND_ADMIN);
    }

    /**
     * Añade un nuevo usuario a la DB.
     * 
     * @param _usuario
     * @return
     * @throws SQLException 
     */
    @Override
    public boolean addUsuario(UsuarioDTO _usuario) throws SQLException {
        stmt_add.setString(1, _usuario.getDni());
        stmt_add.setString(2, _usuario.getEmail());
        stmt_add.setString(3, _usuario.getContraseña());

        return stmt_add.executeUpdate() > 0;
    }
    
    /**
     * Comprueba si existe un usuario de la DB.
     * 
     * @param _usuario
     * @return
     * @throws SQLException 
     */
    @Override
    public UsuarioDTO checkUser(UsuarioDTO _usuario) throws SQLException {
        UsuarioDTO usuarioEncontrado = null;
        int cuenta = 0;
        
        stmt_getUs.setString(1, _usuario.getDni());
        stmt_getUs.setString(2, _usuario.getEmail());
        stmt_getUs.setString(3, _usuario.getContraseña());
        
        ResultSet rs = stmt_getUs.executeQuery();
        if (rs.next()) {
            cuenta = rs.getInt(1);
        }
        rs.close();
        
        if (cuenta > 0) {
            // Se ha encontrado un usuario con esas credenciales.
            //Se va a verificar si es un usuario administrador.
            cuenta = 0;
            usuarioEncontrado = _usuario;
            usuarioEncontrado.setAdmin(false);
            stmt_getAdm.setString(1, _usuario.getDni());
            rs = stmt_getAdm.executeQuery();
            if (rs.next()) {
                cuenta = rs.getInt(1);
            }
            rs.close();
            
            if (cuenta > 0) {
                //Se ha verificado que el usuario es admin.
                _usuario.setAdmin(true);
            }
        }
        
        return usuarioEncontrado;
    }

    @Override
    public boolean deleteUsuario(String _dni) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * Modifica un ususario existente de la DB.
     * 
     * @param _usuario
     * @return
     * @throws SQLException 
     */
    @Override
    public boolean updateUsuario(UsuarioDTO _usuario) throws SQLException {
        List<String> atributos = Arrays.asList("dni", "correo", "contraseña");
        List<String> modified_param = new ArrayList<String>();

        for (String atributo : atributos) {
            if (("correo".equals(atributo)) && (_usuario.getEmail() != null)) {
                modified_param.add(String.format("%s=\"%s\"", atributo, _usuario.getEmail()));
            }
            if (("contraseña".equals(atributo)) && (_usuario.getContraseña() != null)) {
                modified_param.add(String.format("%s=\"%s\"", atributo, _usuario.getContraseña()));
            }
        }

        String param = String.join(", ", modified_param);
        String sql = String.format(UPDATE, param, _usuario.getDni());

        this.stmt_upd = connection.prepareStatement(sql);

        if (stmt_upd.executeUpdate() > 0) {
            return true;
        }

        return false;
    }

    @Override
    public UsuarioDTO getUsuario(String _dni) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<UsuarioDTO> getUsuarios() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
