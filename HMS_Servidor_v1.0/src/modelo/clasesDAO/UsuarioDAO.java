/**
 * UsuarioDAO.java
 * Pablo Doñate Navarro
 * v2.6 09/05/2022.
 */
package modelo.clasesDAO;

import modelo.clasesDTOs.UsuarioDTO;
import java.sql.SQLException;
import java.util.List;

/**
 * Interfaz que define las cabereceras de las operaciones 
 *  CRUD de un usuario
 * 
 */
public interface UsuarioDAO {
    
    public boolean addUsuario(UsuarioDTO _usuario) throws SQLException;
    
    public boolean addAdmin(String _dni) throws SQLException;

    public UsuarioDTO checkUser(UsuarioDTO _usuario) throws SQLException;
    
    public boolean existeAdmin(String _dniAdmin) throws SQLException;
    
    public boolean deleteUsuario(String _dni) throws SQLException;

    public boolean updateUsuario(UsuarioDTO _usuario) throws SQLException;

    public UsuarioDTO getUsuario(String _dni) throws SQLException;

    public List<UsuarioDTO> getUsuarios() throws SQLException;
}
