/**
 * PacienteDAO.java
 * Pablo Doñate Navarro
 * v1.0 29/04/2022.
 */
package modelo.clasesDAO;

import java.sql.SQLException;
import java.util.List;
import modelo.clasesDTOs.PacienteDTO;

/**
 * Interfaz que define las cabereceras de las operaciones 
 *  CRUD de un paciente
 * 
 */
public interface PacienteDAO {
    public boolean addPaciente(PacienteDTO _sanitario) throws SQLException;

    public boolean deletePaciente(String _nss) throws SQLException;

    public boolean updatePaciente(PacienteDTO _paciente) throws SQLException;

    public PacienteDTO getPaciente(String _nss) throws SQLException;

    public List<PacienteDTO> getPacientes() throws SQLException;
}
