/**
 * CitaDAOImpl.java
 * Pablo Doñate Navarro
 * v1.0 02/04/2022.
 */
package modelo.clasesDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import modelo.clasesDTOs.CitaDTO;

/**
 * Clase que contiene las funciones necesarias
 *  para realizar las operaciones CRUD de una cita.
 * 
 */
public class CitaDAOImpl implements CitaDAO {
    
    private PreparedStatement stmt_add;
    private PreparedStatement stmt_del;
    private PreparedStatement stmt_upd;
    private PreparedStatement stmt_getAll;
    private PreparedStatement stmt_getCit;
    private PreparedStatement stmt_getAllSanit;
    private PreparedStatement stmt_getAllPac;

    private Connection connection;
    
    private static final String INSERT = "INSERT INTO CITA(descripcion, sala, centro, localidad, hora, fecha, nss_pac, dni_sanit) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
    
    /**
     *  Crea una cita, donde define la estructura
     *  de sus operaciones.
     *
     * @param _conn
     * @throws java.sql.SQLException
     */
    public CitaDAOImpl(Connection _conn) throws SQLException {
        this.connection = _conn;
        this.stmt_add = _conn.prepareStatement(INSERT);
    }
 
    /**
     * Crea una nueva cita.
     * 
     * @param _cita
     * @return
     * @throws SQLException 
     */
    @Override
    public boolean addCita(CitaDTO _cita) throws SQLException {
        stmt_add.setString(1, _cita.getDescripcion());
        stmt_add.setString(2, _cita.getSala());
        stmt_add.setString(3, _cita.getCentro());
        stmt_add.setString(4, _cita.getLocalidad());
        stmt_add.setTime(5, _cita.getHora());
        stmt_add.setDate(6, _cita.getFecha());
        stmt_add.setString(7, _cita.getNss_pac());
        stmt_add.setString(8, _cita.getDni_sanit());
        
        return stmt_add.executeUpdate() > 0;
    }

    /**
     * Elimina una cita, pasándole el código de la misma.
     * 
     * @param _codigo
     * @return
     * @throws SQLException 
     */
    @Override
    public boolean deleteCita(String _codigo) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * Modifica una cita existente, pasándole
     * la cita con los campos modificados.
     * 
     * @param _cita
     * @return
     * @throws SQLException 
     */
    @Override
    public boolean updateCita(CitaDTO _cita) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * Obtiene una cita dado su identificador.
     * 
     * @param _codigo
     * @return
     * @throws SQLException 
     */
    @Override
    public CitaDTO getCita(String _codigo) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * Obtiene el listado completo de citas.
     * 
     * @return
     * @throws SQLException 
     */
    @Override
    public List<CitaDTO> getCitas() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * Obtiene el listado completo de 
     * citas de un paciente.
     * 
     * @param _nss
     * @return
     * @throws SQLException 
     */
    @Override
    public List<CitaDTO> getCitasPaciente(String _nss) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * Obtiene el listado completo
     * de citas de un sanitario.
     * 
     * @param _dni
     * @return
     * @throws SQLException 
     */
    @Override
    public List<CitaDTO> getCitasSanitario(String _dni) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
