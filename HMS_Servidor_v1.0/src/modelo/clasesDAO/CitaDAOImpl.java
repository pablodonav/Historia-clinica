/**
 * CitaDAOImpl.java
 * Pablo Doñate Navarro
 * v1.0 29/04/2022.
 */
package modelo.clasesDAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
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
    private static final String DELETE = "DELETE FROM CITA WHERE codigo=?";
    private static final String UPDATE = "UPDATE CITA SET descripcion=?, sala=?, centro=?, localidad=?, hora=?, fecha=?, nss_pac=?, dni_sanit=? WHERE codigo=?";
    private static final String GET_CITA = "SELECT * FROM CITA WHERE codigo=?";
    private static final String GET_ALL = "SELECT * FROM CITA";
    private static final String GET_ALL_SANIT = "SELECT * FROM CITA WHERE dni_sanit=?";
    private static final String GET_ALL_PAC = "SELECT * FROM CITA WHERE nss_pac=?";
    
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
        this.stmt_getCit = _conn.prepareStatement(GET_CITA);
        this.stmt_del = _conn.prepareStatement(DELETE);
        this.stmt_getAll = _conn.prepareStatement(GET_ALL);
        this.stmt_upd = _conn.prepareStatement(UPDATE);
        this.stmt_getAllPac = _conn.prepareStatement(GET_ALL_PAC);
        this.stmt_getAllSanit = _conn.prepareStatement(GET_ALL_SANIT);
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
        stmt_del.setString(1, _codigo);

        return (stmt_del.executeUpdate() > 0);
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
        stmt_upd.setString(1, _cita.getDescripcion());
        stmt_upd.setString(2, _cita.getSala());
        stmt_upd.setString(3, _cita.getCentro());
        stmt_upd.setString(4, _cita.getLocalidad());
        stmt_upd.setTime(5, _cita.getHora());
        stmt_upd.setDate(6, _cita.getFecha());
        stmt_upd.setString(7, _cita.getNss_pac());
        stmt_upd.setString(8, _cita.getDni_sanit());
        stmt_upd.setInt(9, _cita.getCodigo());

        return stmt_upd.executeUpdate() > 0;
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
        CitaDTO cita = null;

        stmt_getCit.setString(1, _codigo);
        ResultSet rs = stmt_getCit.executeQuery();

        while (rs.next()) {
            int codigo = rs.getInt("codigo");
            String descripcion = rs.getString("descripcion");
            String sala = rs.getString("sala");
            String centro = rs.getString("centro");
            String localidad = rs.getString("localidad");
            Time hora = rs.getTime("hora");
            Date fecha = rs.getDate("fecha");
            String nss_pac = rs.getString("nss_pac");
            String dni_sanit = rs.getString("dni_sanit");

            cita = new CitaDTO(codigo, descripcion, sala, centro, localidad, hora, fecha, nss_pac, dni_sanit);
        }
        
        return cita;
    }

    /**
     * Obtiene el listado completo de citas.
     * 
     * @return
     * @throws SQLException 
     */
    @Override
    public List<CitaDTO> getCitas() throws SQLException {
        CitaDTO cita = null;

        ResultSet rs = stmt_getAll.executeQuery();
        List<CitaDTO> citas = new ArrayList<CitaDTO>();

        while (rs.next()) {
            int codigo = rs.getInt("codigo");
            String descripcion = rs.getString("descripcion");
            String sala = rs.getString("sala");
            String centro = rs.getString("centro");
            String localidad = rs.getString("localidad");
            Time hora = rs.getTime("hora");
            Date fecha = rs.getDate("fecha");
            String nss_pac = rs.getString("nss_pac");
            String dni_sanit = rs.getString("dni_sanit");

            cita = new CitaDTO(codigo, descripcion, sala, centro, localidad, hora, fecha, nss_pac, dni_sanit);
            citas.add(cita);
        }

        return citas;
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
        stmt_getAllPac.setString(1, _nss);
        ResultSet rs = stmt_getAllPac.executeQuery();
        List<CitaDTO> citas = new ArrayList<CitaDTO>();

        while (rs.next()) {
            int codigo = rs.getInt("codigo");
            String descripcion = rs.getString("descripcion");
            String sala = rs.getString("sala");
            String centro = rs.getString("centro");
            String localidad = rs.getString("localidad");
            Time hora = rs.getTime("hora");
            Date fecha = rs.getDate("fecha");
            String nss_pac = rs.getString("nss_pac");
            String dni_sanit = rs.getString("dni_sanit");

            CitaDTO cita = new CitaDTO(codigo, descripcion, sala, centro, localidad, hora, fecha, nss_pac, dni_sanit);
            citas.add(cita);
        }

        return citas;
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
        CitaDTO cita = null;

        stmt_getAllSanit.setString(1, _dni);
        ResultSet rs = stmt_getAllSanit.executeQuery();
        List<CitaDTO> citas = new ArrayList<CitaDTO>();

        while (rs.next()) {
            int codigo = rs.getInt("codigo");
            String descripcion = rs.getString("descripcion");
            String sala = rs.getString("sala");
            String centro = rs.getString("centro");
            String localidad = rs.getString("localidad");
            Time hora = rs.getTime("hora");
            Date fecha = rs.getDate("fecha");
            String nss_pac = rs.getString("nss_pac");
            String dni_sanit = rs.getString("dni_sanit");

            cita = new CitaDTO(codigo, descripcion, sala, centro, localidad, hora, fecha, nss_pac, dni_sanit);
            citas.add(cita);
        }

        return citas;
    }
    
}
