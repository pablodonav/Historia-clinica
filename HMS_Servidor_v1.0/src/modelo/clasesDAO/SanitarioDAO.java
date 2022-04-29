/**
 * SanitarioDAO.java
 * Pablo Doñate Navarro
 * v1.0 29/04/2022.
 */
package modelo.clasesDAO;

import modelo.clasesDTOs.SanitarioDTO;
import java.sql.SQLException;
import java.util.List;

/**
 * Interfaz que define las cabereceras de las operaciones 
 *  CRUD de un sanitario
 * 
 */
public interface SanitarioDAO {
    
    public boolean addSanitario(SanitarioDTO _sanitario) throws SQLException;

    public boolean updateSanitario(SanitarioDTO _sanitario) throws SQLException;

    public SanitarioDTO getSanitario(String _dni) throws SQLException;

    public List<SanitarioDTO> getSanitarios() throws SQLException;
}
