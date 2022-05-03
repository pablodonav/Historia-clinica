/**
 * CitaDAOImpl.java
 * Pablo Doñate Navarro
 * v2.4 01/05/2022.
 */
package modelo.clasesDAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import modelo.clasesDTOs.CitaDTO;
import modelo.clasesDTOs.UbicacionDTO;

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
    private PreparedStatement stmt_getCount;

    private Connection connection;
    
    private static final String INSERT = "INSERT INTO CITA(descripcion, sala, centro, localidad, hora, fecha, nss_pac, dni_sanit) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String DELETE = "DELETE FROM CITA WHERE codigo=?";
    private static final String UPDATE = "UPDATE CITA SET descripcion=?, sala=?, centro=?, localidad=?, hora=?, fecha=?, nss_pac=?, dni_sanit=? WHERE codigo=?";
    private static final String GET_CITA = "SELECT * FROM CITA WHERE codigo=?";
    private static final String GET_ALL = "SELECT * FROM CITA";
    private static final String GET_ALL_SANIT = "SELECT * FROM CITA WHERE dni_sanit=?";
    private static final String GET_ALL_PAC = "SELECT * FROM CITA WHERE nss_pac=?";
    private static final String GET_COUNT = "SELECT COUNT(*) FROM CITA";
    
    /**
     * Crea una cita, donde define la estructura
     * de sus operaciones.
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
        this.stmt_getCount = _conn.prepareStatement(GET_COUNT);
    }
 
    /**
     * Crea una nueva cita.
     * 
     * @param _cita
     * @param _nss_pac
     * @return
     * @throws SQLException 
     */
    @Override
    public boolean addCita(CitaDTO _cita, String _nss_pac) throws SQLException {
        
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = simpleDateFormat.format(_cita.getFecha());
        
        SimpleDateFormat simpleHourFormat = new SimpleDateFormat("HH:mm:ss");
        String formattedHour = simpleHourFormat.format(_cita.getTiempo());
        
        stmt_add.setString(1, _cita.getDescripcion());
        stmt_add.setString(2, _cita.getUbicacion().getSala());
        stmt_add.setString(3, _cita.getUbicacion().getCentroHospitalario());
        stmt_add.setString(4, _cita.getUbicacion().getLocalidad());
        stmt_add.setTime(5, java.sql.Time.valueOf(formattedHour));
        stmt_add.setDate(6, java.sql.Date.valueOf(formattedDate));
        stmt_add.setString(7, _nss_pac);
        stmt_add.setString(8, _cita.getDniMedico());
        
        return stmt_add.executeUpdate() > 0;
    }
    
    /**
     * Devuelve el valor del indice de la próxima cita a insertar.
     * 
     * @return
     * @throws SQLException 
     */
    @Override
    public int getCount() throws SQLException {
        int cuenta = 0;
        
        ResultSet rs = stmt_getCount.executeQuery();

        while (rs.next()) {
            cuenta = rs.getInt("COUNT(*)");
        }
        
        cuenta += 1;
        
        return cuenta;
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
     * @param _nss_pac
     * @return
     * @throws SQLException 
     */
    @Override
    public boolean updateCita(CitaDTO _cita, String _nss_pac) throws SQLException {
        
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = simpleDateFormat.format(_cita.getFecha());
        
        SimpleDateFormat simpleHourFormat = new SimpleDateFormat("HH:mm:ss");
        String formattedHour = simpleHourFormat.format(_cita.getTiempo());
        
        stmt_upd.setString(1, _cita.getDescripcion());
        stmt_upd.setString(2, _cita.getUbicacion().getSala());
        stmt_upd.setString(3, _cita.getUbicacion().getCentroHospitalario());
        stmt_upd.setString(4, _cita.getUbicacion().getLocalidad());
        stmt_upd.setTime(5, java.sql.Time.valueOf(formattedHour));
        stmt_upd.setDate(6, java.sql.Date.valueOf(formattedDate));
        stmt_upd.setString(7, _nss_pac);
        stmt_upd.setString(8, _cita.getDniMedico());
        stmt_upd.setInt(9, _cita.getIdentificador());

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
            Date fec = rs.getDate("fecha");
            String nss_pac = rs.getString("nss_pac");
            String dni_sanit = rs.getString("dni_sanit");
            
            java.util.Date fecha = new java.util.Date(fec.getYear(), fec.getMonth(), fec.getDay());
            java.util.Date tiempo = new java.util.Date(2000, 11, 1, hora.getHours(), hora.getMinutes());

            UbicacionDTO ubicacion = new UbicacionDTO(localidad, centro, sala);
            cita = new CitaDTO(codigo, dni_sanit, ubicacion, fecha, tiempo, descripcion);
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
        ResultSet rs = stmt_getAll.executeQuery();
        List<CitaDTO> citas = new ArrayList<CitaDTO>();

        while (rs.next()) {
            int codigo = rs.getInt("codigo");
            String descripcion = rs.getString("descripcion");
            String sala = rs.getString("sala");
            String centro = rs.getString("centro");
            String localidad = rs.getString("localidad");
            Time hora = rs.getTime("hora");
            Date fec = rs.getDate("fecha");
            String nss_pac = rs.getString("nss_pac");
            String dni_sanit = rs.getString("dni_sanit");
            
            java.util.Date fecha = new java.util.Date(fec.getYear(), fec.getMonth(), fec.getDay());
            java.util.Date tiempo = new java.util.Date(2000, 11, 1, hora.getHours(), hora.getMinutes());

            UbicacionDTO ubicacion = new UbicacionDTO(localidad, centro, sala);
            CitaDTO cita = new CitaDTO(codigo, dni_sanit, ubicacion, fecha, tiempo, descripcion);
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
            Date fec = rs.getDate("fecha");
            String nss_pac = rs.getString("nss_pac");
            String dni_sanit = rs.getString("dni_sanit");
            
            java.util.Date fecha = new java.util.Date(fec.getYear(), fec.getMonth(), fec.getDay());
            java.util.Date tiempo = new java.util.Date(2000, 11, 1, hora.getHours(), hora.getMinutes());

            UbicacionDTO ubicacion = new UbicacionDTO(localidad, centro, sala);
            CitaDTO cita = new CitaDTO(codigo, dni_sanit, ubicacion, fecha, tiempo, descripcion);
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
            Date fec = rs.getDate("fecha");
            String nss_pac = rs.getString("nss_pac");
            String dni_sanit = rs.getString("dni_sanit");
            
            java.util.Date fecha = new java.util.Date(fec.getYear(), fec.getMonth(), fec.getDay());
            java.util.Date tiempo = new java.util.Date(2000, 11, 1, hora.getHours(), hora.getMinutes());

            UbicacionDTO ubicacion = new UbicacionDTO(localidad, centro, sala);
            CitaDTO cita = new CitaDTO(codigo, dni_sanit, ubicacion, fecha, tiempo, descripcion);
            citas.add(cita);
        }

        return citas;
    }
    
}
