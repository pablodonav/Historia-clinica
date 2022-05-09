/**
 * MedicamentoDAO.java
 * Pablo Doñate Navarro
 * v2.6 09/05/2022.
 */
package modelo.clasesDAO;

import java.sql.SQLException;
import java.util.List;
import modelo.clasesDTOs.MedicamentoDTO;
import modelo.clasesDTOs.MedicamentoPacienteDTO;

/**
 * Interfaz que define las cabereceras de las operaciones 
 *  CRUD de un medicamento.
 * 
 */
public interface MedicamentoDAO {
    public boolean addMedicineToPatient(MedicamentoPacienteDTO _medicamento, String _nss_pac) throws SQLException;
    
    public int getNewIndex() throws SQLException;

    public boolean deleteMedicineFromPatient(String _nss, int _id) throws SQLException;

    public boolean updateMedicineFromPatient(MedicamentoPacienteDTO _medicamento, String _nss_pac) throws SQLException;

    public List<MedicamentoPacienteDTO> getMedicamentosPaciente(String _nss) throws SQLException;
    
    public List<MedicamentoDTO> getMedicamentos() throws SQLException;
}
