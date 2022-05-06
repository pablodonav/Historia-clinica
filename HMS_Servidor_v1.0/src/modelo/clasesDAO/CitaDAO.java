/**
 * CitaDAO.java
 * Pablo Doñate Navarro
 * v2.5 06/05/2022.
 */
package modelo.clasesDAO;

import java.sql.SQLException;
import java.util.List;
import modelo.clasesDTOs.CitaDTO;

/**
 * Interfaz que define las cabereceras de las operaciones 
 *  CRUD de una cita.
 * 
 */
public interface CitaDAO {
    public boolean addCita(CitaDTO _cita, String nss_pac) throws SQLException;
    
    public int getNewIndex() throws SQLException;

    public boolean deleteCita(String _codigo) throws SQLException;

    public boolean updateCita(CitaDTO _cita, String nss_pac) throws SQLException;

    public CitaDTO getCita(String _codigo) throws SQLException;

    public List<CitaDTO> getCitas() throws SQLException;
    
    public List<CitaDTO> getCitasPaciente(String _nss) throws SQLException;
    
    public List<CitaDTO> getCitasSanitario(String _dni) throws SQLException;
}