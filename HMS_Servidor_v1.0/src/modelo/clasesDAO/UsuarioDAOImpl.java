/**
 * SanitarioDAOImpl.java
 * Pablo Doñate Navarro
 * v1.0 02/04/2022.
 */
package modelo.clasesDAO;

import modelo.clasesDTOs.UsuarioDTO;
import java.util.List;
import java.sql.*;

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
    private PreparedStatement stmt_verifyUs;
    private PreparedStatement stmt_getUs;
    private PreparedStatement stmt_getAdm;

    private static final String INSERT = "INSERT INTO USUARIO(dni, correo, contraseña) VALUES(?, ?, ?)";
    private static final String DELETE = "DELETE FROM USUARIO WHERE dni=?";
    private static final String UPDATE = "UPDATE USUARIO SET correo=?, contraseña=? WHERE dni=?";
    private static final String FIND_ALL = "SELECT * FROM USUARIO";
    private static final String VERIFY_USUARIO = "SELECT * FROM USUARIO WHERE dni=? AND correo=? AND contraseña=?";
    private static final String FIND_USUARIO = "SELECT * FROM USUARIO WHERE dni=?";
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
        this.stmt_verifyUs = _conn.prepareStatement(VERIFY_USUARIO);
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
        int cuenta = 0;
        stmt_getAdm.setString(1, _usuario.getDni());

        ResultSet rs = stmt_getAdm.executeQuery();
        if (rs.next()) {
            cuenta = rs.getInt(1);
        }
        rs.close();
        
        if (cuenta == 0) {
            stmt_add.setString(1, _usuario.getDni());
            stmt_add.setString(2, _usuario.getEmail());
            stmt_add.setString(3, _usuario.getContraseña());
            return stmt_add.executeUpdate() > 0;
        } else {
            return false;
        }
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
        
        stmt_verifyUs.setString(1, _usuario.getDni());
        stmt_verifyUs.setString(2, _usuario.getEmail());
        stmt_verifyUs.setString(3, _usuario.getContraseña());

        ResultSet rs = stmt_verifyUs.executeQuery();
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
        stmt_del.setString(1, _dni);

        if (stmt_del.executeUpdate() > 0) {
            return true;
        }

        return false;
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
        stmt_upd.setString(1, _usuario.getEmail());
        stmt_upd.setString(2, _usuario.getContraseña());
        stmt_upd.setString(3, _usuario.getDni());

        return stmt_upd.executeUpdate() > 0;
    }

    @Override
    public UsuarioDTO getUsuario(String _dni) throws SQLException {
        UsuarioDTO usuario = null;

        stmt_getUs.setString(1, _dni);
        ResultSet rs = stmt_getUs.executeQuery();

        while (rs.next()) {
            String dni = rs.getString("dni");
            String correo = rs.getString("correo");
            String contraseña = rs.getString("contraseña");

            usuario = new UsuarioDTO(dni, correo, contraseña);
        }
        return usuario;
    }

    @Override
    public List<UsuarioDTO> getUsuarios() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
