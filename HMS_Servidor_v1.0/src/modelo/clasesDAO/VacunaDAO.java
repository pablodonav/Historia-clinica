/**
 * VacunaDAO.java
 * Pablo Doñate Navarro
 * v2.6 09/05/2022.
 */
package modelo.clasesDAO;

import java.sql.SQLException;
import java.util.List;
import modelo.clasesDTOs.VacunaDTO;
import modelo.clasesDTOs.VacunaPacienteDTO;

/**
 * Interfaz que define las cabereceras de las operaciones 
 *  CRUD de una vacuna.
 * 
 */
public interface VacunaDAO {
    public boolean addVaccineToPatient(VacunaPacienteDTO _vacuna, String _nss_pac) throws SQLException;
    
    public int getNewIndex() throws SQLException;

    public boolean deleteVaccineFromPatient(int _codigo_vac, String _nss, int _id) throws SQLException;

    public boolean updateVaccineFromPatient(VacunaPacienteDTO _vacuna, String _nss_pac) throws SQLException;

    public List<VacunaPacienteDTO> getVacunasPaciente(String _nss) throws SQLException;
    
    public List<VacunaDTO> getVacunas() throws SQLException;
}
