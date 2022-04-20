/**
 * PacienteDAOImpl.java
 * Pablo Doñate Navarro
 * v1.0 02/04/2022.
 */
package modelo.clasesDAO;

import java.util.List;
import java.sql.*;
import modelo.clasesDTOs.PacienteDTO;

/**
 * Clase que contiene las funciones necesarias
 *  para realizar las operaciones CRUD de un paciente
 * 
 */
public class PacienteDAOImpl implements PacienteDAO {
    private PreparedStatement stmt_add;
    private PreparedStatement stmt_del;
    private PreparedStatement stmt_upd;
    private PreparedStatement stmt_getAll;
    private PreparedStatement stmt_getPac;

    private static final String FIND_PACIENTE = "SELECT * FROM PACIENTE WHERE nss=?";
    private static final String INSERT = "INSERT INTO PACIENTE(nss, nombre, apellido1, apellido2, alergias, edad, altura, peso) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
    
    private Connection connection;

    /**
     *  Crea un PacienteDAOImpl, donde define la estructura
     *  de sus operaciones.
     * 
     * @param _conn
     * @throws java.sql.SQLException
     */
    public PacienteDAOImpl(Connection _conn) throws SQLException {
        this.connection = _conn;
        this.stmt_add = _conn.prepareStatement(INSERT);
        this.stmt_getPac = _conn.prepareStatement(FIND_PACIENTE);
    }

    /**
     * Añade un nuevo paciente a la DB.
     * 
     * @param _paciente
     * @return
     * @throws SQLException 
     */
    @Override
    public boolean addPaciente(PacienteDTO _paciente) throws SQLException {
        int cuenta = 0;
        stmt_getPac.setString(1, _paciente.getNss());

        ResultSet rs = stmt_getPac.executeQuery();
        
        if (rs.next()) {
            cuenta = rs.getInt(1);
        }
        rs.close();
        
        if (cuenta == 0) {
            stmt_add.setString(1, _paciente.getNss());
            stmt_add.setString(2, _paciente.getNombre());
            stmt_add.setString(3, _paciente.getApellido1());
            stmt_add.setString(4, _paciente.getApellido2());
            stmt_add.setString(5, _paciente.getAlergias());
            stmt_add.setInt(6, _paciente.getEdad());
            stmt_add.setDouble(7, _paciente.getAltura());
            stmt_add.setDouble(8, _paciente.getPeso());

            return stmt_add.executeUpdate() > 0;
        } else {
            return false;
        }
    }

    /**
     * Borra un paciente existente de la DB.
     * 
     * @param _nss
     * @return
     * @throws SQLException 
     */
    @Override
    public boolean deletePaciente(String _nss) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * Modifica un paciente existente de la DB.
     * 
     * @param _paciente
     * @return
     * @throws SQLException 
     */
    @Override
    public boolean updatePaciente(PacienteDTO _paciente) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * Obtiene un paciente de la DB.
     * 
     * @param _nss
     * @return
     * @throws SQLException 
     */
    @Override
    public PacienteDTO getPaciente(String _nss) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * Obtiene la colección de pacientes de la DB.
     * 
     * @return
     * @throws SQLException 
     */
    @Override
    public List<PacienteDTO> getPacientes() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
