/**
 * MedicamentoDAOImpl.java
 * Pablo Doñate Navarro
 * v2.5 06/05/2022.
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
import modelo.clasesDTOs.MedicamentoDTO;
import modelo.clasesDTOs.MedicamentoPacienteDTO;

/**
 * Clase que contiene las funciones necesarias
 * para realizar las operaciones CRUD de un medicamento.
 * 
 */
public class MedicamentoDAOImpl implements MedicamentoDAO {

    private PreparedStatement stmt_add;
    private PreparedStatement stmt_del;
    private PreparedStatement stmt_upd;
    private PreparedStatement stmt_getAll;
    private PreparedStatement stmt_getReceta;
    private PreparedStatement stmt_getNewIndex;

    private Connection connection;
    
    private static final String INSERT = "INSERT INTO MEDICAMENTO_PACIENTE (id, fecha_inicio, fecha_fin, nss_pac, codigo_medic) VALUES(?, ?, ?, ?, ?)";
    private static final String DELETE = "DELETE FROM MEDICAMENTO_PACIENTE WHERE id=? AND codigo_medic=? AND nss_pac=?";
    private static final String UPDATE = "UPDATE MEDICAMENTO_PACIENTE SET fecha_inicio=?, fecha_fin=? WHERE id=? AND codigo_medic=? AND nss_pac=?";
    private static final String GET_RECETA_PACIENTE = "SELECT * FROM MEDICAMENTO_PACIENTE WHERE nss_pac=?";
    private static final String GET_NEW_INDEX = "SELECT max(id) + 1 FROM MEDICAMENTO_PACIENTE";
    private static final String GET_MEDICAMENTOS = "SELECT * FROM MEDICAMENTO";
    
    public MedicamentoDAOImpl(Connection _conn) throws SQLException {
        this.connection = _conn;
        
        this.stmt_add = _conn.prepareStatement(INSERT);
        this.stmt_del = _conn.prepareStatement(DELETE);
        this.stmt_getReceta = _conn.prepareStatement(GET_RECETA_PACIENTE);
        this.stmt_getAll = _conn.prepareStatement(GET_MEDICAMENTOS);
        this.stmt_upd = _conn.prepareStatement(UPDATE);
        this.stmt_getNewIndex = _conn.prepareStatement (GET_NEW_INDEX);
    }

    /**
     * Añade un medicamento a la receta de un paciente.
     * 
     * @param _medicamento
     * @return
     * @throws SQLException 
     */
    @Override
    public boolean addMedicineToPatient(MedicamentoPacienteDTO _medicamento) throws SQLException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String fechaFormateadaInicio = simpleDateFormat.format(_medicamento.getFecha_inicio());
        String fechaFormateadaFin = simpleDateFormat.format(_medicamento.getFecha_fin());
       
        stmt_add.setInt(1, _medicamento.getId());
        stmt_add.setDate(2, java.sql.Date.valueOf(fechaFormateadaInicio));
        stmt_add.setDate(3, java.sql.Date.valueOf(fechaFormateadaFin));
        stmt_add.setString(4, _medicamento.getNss_pac());
        stmt_add.setInt(5, _medicamento.getCodigo_medic());
        
        return stmt_add.executeUpdate() > 0;
    }
    
    /**
     * Devuelve el valor del indice del próximo medicamento de paciente a insertar.
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
     * el medicamento "_codigo_medic" con numero "id" en la receta del paciente
     * con identificador "_nss".
     * 
     * @param _codigo_medic
     * @param _nss
     * @param _id
     * @return
     * @throws SQLException 
     */
    @Override
    public boolean deleteMedicineFromPatient(int _codigo_medic, String _nss, int _id) throws SQLException {
        stmt_del.setInt(1, _id);
        stmt_del.setInt(2, _codigo_medic);
        stmt_del.setString(3, _nss);
        
        return (stmt_del.executeUpdate() > 0);
    }

    /**
     * Devuelve cierto si ha conseguido modificar la fecha de inicio o fin del medicamento
     * de la receta de un paciente.
     * 
     * @param _medicamento
     * @return
     * @throws SQLException 
     */
    @Override
    public boolean updateMedicineFromPatient(MedicamentoPacienteDTO _medicamento) throws SQLException {
        
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String fechaFormateadaInicio = simpleDateFormat.format(_medicamento.getFecha_inicio());
        String fechaFormateadaFin = simpleDateFormat.format(_medicamento.getFecha_fin());
        
        stmt_upd.setDate(1, java.sql.Date.valueOf(fechaFormateadaInicio));
        stmt_upd.setDate(2, java.sql.Date.valueOf(fechaFormateadaFin));
        stmt_upd.setInt(3, _medicamento.getId());
        stmt_upd.setInt(4, _medicamento.getCodigo_medic());
        stmt_upd.setString(5, _medicamento.getNss_pac());

        return stmt_upd.executeUpdate() > 0;
    }

    /**
     * Devuelve la lista de medicamentos (Receta) de un paciente.
     * 
     * @param _nss
     * @return
     * @throws SQLException 
     */
    @Override
    public List<MedicamentoPacienteDTO> getMedicamentosPaciente(String _nss) throws SQLException {
        stmt_getReceta.setString(1, _nss);
        ResultSet rs = stmt_getReceta.executeQuery();
        List<MedicamentoPacienteDTO> receta = new ArrayList<MedicamentoPacienteDTO>();

        while (rs.next()) {
            int id = rs.getInt("id");
            Date fecha_inicio = rs.getDate("fecha_inicio");
            Date fecha_fin = rs.getDate("fecha_fin");
            String nss_pac = rs.getString("nss_pac");
            int codigo_medic = rs.getInt("codigo_medic");

            MedicamentoPacienteDTO medicamento = new MedicamentoPacienteDTO(id, fecha_inicio, fecha_fin, nss_pac, codigo_medic);
            receta.add(medicamento);
        }

        return receta;
    }
    
    /**
     * Devuelve la lista de medicamentos disponibles.
     * 
     * @return
     * @throws SQLException 
     */
    @Override
    public List<MedicamentoDTO> getMedicamentos() throws SQLException {
        ResultSet rs = stmt_getAll.executeQuery();
        List<MedicamentoDTO> medicamentos = new ArrayList<MedicamentoDTO>();

        while (rs.next()) {
            int codigo = rs.getInt("codigo");
            String nombre = rs.getString("nombre");

            MedicamentoDTO medicamento = new MedicamentoDTO(codigo, nombre);
            medicamentos.add(medicamento);
        }

        return medicamentos;
    }
}
