/**
 * VacunaDAOImpl.java
 * Pablo Doñate Navarro
 * v2.6 09/05/2022.
 */
package modelo.clasesDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import modelo.clasesDTOs.VacunaDTO;
import modelo.clasesDTOs.VacunaPacienteDTO;

/**
 * Clase que contiene las funciones necesarias
 * para realizar las operaciones CRUD de una vacuna.
 * 
 */
public class VacunaDAOImpl implements VacunaDAO {

    private PreparedStatement stmt_add;
    private PreparedStatement stmt_del;
    private PreparedStatement stmt_upd;
    private PreparedStatement stmt_getAll;
    private PreparedStatement stmt_getVacunasPac;
    private PreparedStatement stmt_getNewIndex;
    private PreparedStatement stmt_getVacunaName;

    private Connection connection;
    
    private static final String INSERT = "INSERT INTO VACUNA_PACIENTE (id, fecha, nss_pac, codigo_vacuna) VALUES(?, ?, ?, ?)";
    private static final String DELETE = "DELETE FROM VACUNA_PACIENTE WHERE id=? AND codigo_vacuna=? AND nss_pac=?";
    private static final String UPDATE = "UPDATE VACUNA_PACIENTE SET fecha=? WHERE id=? AND codigo_vacuna=? AND nss_pac=?";
    private static final String GET_VACUNAS_PACIENTE = "SELECT * FROM VACUNA_PACIENTE WHERE nss_pac=?";
    private static final String GET_NEW_INDEX = "SELECT max(id) + 1 FROM VACUNA_PACIENTE";
    private static final String GET_VACUNAS = "SELECT * FROM VACUNA";
    private static final String GET_VACUNA = "SELECT nombre FROM VACUNA WHERE codigo=?";
    
    public VacunaDAOImpl(Connection _conn) throws SQLException {
        this.connection = _conn;
        
        this.stmt_add = _conn.prepareStatement(INSERT);
        this.stmt_del = _conn.prepareStatement(DELETE);
        this.stmt_getVacunasPac = _conn.prepareStatement(GET_VACUNAS_PACIENTE);
        this.stmt_getAll = _conn.prepareStatement(GET_VACUNAS);
        this.stmt_upd = _conn.prepareStatement(UPDATE);
        this.stmt_getNewIndex = _conn.prepareStatement(GET_NEW_INDEX);
        this.stmt_getVacunaName = _conn.prepareStatement(GET_VACUNA);
    }

    /**
     * Añade una vacuna al historial de un paciente.
     * 
     * @param _vacuna
     * @param _nss_pac
     * @return
     * @throws SQLException 
     */
    @Override
    public boolean addVaccineToPatient(VacunaPacienteDTO _vacuna, String _nss_pac) throws SQLException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String fechaFormateada = simpleDateFormat.format(_vacuna.getFecha());
       
        stmt_add.setInt(1, _vacuna.getId());
        stmt_add.setDate(2, java.sql.Date.valueOf(fechaFormateada));
        stmt_add.setString(3, _nss_pac);
        stmt_add.setInt(4, _vacuna.getVacuna().getCodigo());
        
        return stmt_add.executeUpdate() > 0;
    }
    
    /**
     * Devuelve el valor del indice de la próxima vacuna de paciente a administrar.
     * 
     * @return
     * @throws SQLException 
     */
    @Override
    public int getNewIndex() throws SQLException {
        int cuenta = 0;
        
        ResultSet rs = stmt_getNewIndex.executeQuery();

        while (rs.next()) {
            cuenta = rs.getInt(1);
        }
        
        if (cuenta == 0) {
            cuenta = 1;
        }
        
        return cuenta;
    }

    /**
     * Devuelve cierto si se ha conseguido borrar
     * la vacuna "_codigo_vac" con numero "id" en la historia del paciente
     * con identificador "_nss".
     * 
     * @param _codigo_vac
     * @param _nss
     * @param _id
     * @return
     * @throws SQLException 
     */
    @Override
    public boolean deleteVaccineFromPatient(int _codigo_vac, String _nss, int _id) throws SQLException {
        stmt_del.setInt(1, _id);
        stmt_del.setInt(2, _codigo_vac);
        stmt_del.setString(3, _nss);
        
        return (stmt_del.executeUpdate() > 0);
    }

    /**
     * Devuelve cierto si ha conseguido modificar la fecha de administracion
     * de la vacuna de un paciente.
     * 
     * @param _vacuna
     * @return
     * @throws SQLException 
     */
    @Override
    public boolean updateVaccineFromPatient(VacunaPacienteDTO _vacuna, String _nss_pac) throws SQLException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String fechaFormateada = simpleDateFormat.format(_vacuna.getFecha());
        
        stmt_upd.setDate(1, java.sql.Date.valueOf(fechaFormateada));
        stmt_upd.setInt(2, _vacuna.getId());
        stmt_upd.setInt(3, _vacuna.getVacuna().getCodigo());
        stmt_upd.setString(4, _nss_pac);

        return stmt_upd.executeUpdate() > 0;
    }

    /**
     * Devuelve la lista de vacunas (historial) de un paciente.
     * 
     * @param _nss
     * @return
     * @throws SQLException 
     */
    @Override
    public List<VacunaPacienteDTO> getVacunasPaciente(String _nss) throws SQLException {
        stmt_getVacunasPac.setString(1, _nss);
        ResultSet rs = stmt_getVacunasPac.executeQuery();
        List<VacunaPacienteDTO> vacunas = new ArrayList<VacunaPacienteDTO>();

        while (rs.next()) {
            int id = rs.getInt("id");
            Date fecha = rs.getDate("fecha");
            String nss_pac = rs.getString("nss_pac");
            int codigo_vac = rs.getInt("codigo_vacuna");
            String nombre_vac = "";
            
            stmt_getVacunaName.setInt(1, codigo_vac);
            ResultSet rsv = stmt_getVacunaName.executeQuery();
            
            while(rsv.next()) {
                nombre_vac = rsv.getString("nombre");
            }
   
            java.util.Date fec = new java.util.Date(fecha.getYear(), fecha.getMonth(), fecha.getDay());
            
            VacunaDTO vac = new VacunaDTO(codigo_vac, nombre_vac);
            VacunaPacienteDTO vacuna = new VacunaPacienteDTO(id, vac, fec);
            vacunas.add(vacuna);
        }

        return vacunas;
    }
    
    /**
     * Devuelve la lista de vacunas disponibles.
     * 
     * @return
     * @throws SQLException 
     */
    @Override
    public List<VacunaDTO> getVacunas() throws SQLException {
        ResultSet rs = stmt_getAll.executeQuery();
        List<VacunaDTO> vacunas = new ArrayList<VacunaDTO>();

        while (rs.next()) {
            int codigo = rs.getInt("codigo");
            String nombre = rs.getString("nombre");

            VacunaDTO vacuna = new VacunaDTO(codigo, nombre);
            vacunas.add(vacuna);
        }

        return vacunas;
    }
}
