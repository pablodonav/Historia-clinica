/**
 * PacienteDAOImpl.java
 * Pablo Doñate Navarro
 * v2.6 09/05/2022.
 */
package modelo.clasesDAO;

import java.util.List;
import java.sql.*;
import java.util.ArrayList;
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
    private static final String DELETE = "DELETE FROM PACIENTE WHERE nss=?";
    private static final String UPDATE = "UPDATE PACIENTE SET nombre=?, apellido1=?, apellido2=?, alergias=?, edad=?, altura=?, peso=? WHERE nss=?";
    private static final String FIND_ALL = "SELECT * FROM PACIENTE";
    
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
        this.stmt_del = _conn.prepareStatement(DELETE);
        this.stmt_getAll = _conn.prepareStatement(FIND_ALL);
        this.stmt_upd = _conn.prepareStatement(UPDATE);
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
        stmt_del.setString(1, _nss);

        return stmt_del.executeUpdate() > 0;
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
        stmt_upd.setString(1, _paciente.getNombre());
        stmt_upd.setString(2, _paciente.getApellido1());
        stmt_upd.setString(3, _paciente.getApellido2());
        stmt_upd.setString(4, _paciente.getAlergias());
        stmt_upd.setInt(5, _paciente.getEdad());
        stmt_upd.setDouble(6, _paciente.getAltura());
        stmt_upd.setDouble(7, _paciente.getPeso());
        stmt_upd.setString(8, _paciente.getNss());

        return stmt_upd.executeUpdate() > 0;
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
        PacienteDTO paciente = null;

        stmt_getPac.setString(1, _nss);
        ResultSet rs = stmt_getPac.executeQuery();

        while (rs.next()) {
            String nss = rs.getString("nss");
            String nombre = rs.getString("nombre");
            String apellido1 = rs.getString("apellido1");
            String apellido2 = rs.getString("apellido2");
            String alergias = rs.getString("alergias");
            int edad = rs.getInt("edad");
            Double peso = rs.getDouble("peso");
            Double altura = rs.getDouble("altura");

            paciente = new PacienteDTO(nss, nombre, apellido1, apellido2, alergias, edad, altura, peso);
        }
        return paciente;
    }

    /**
     * Obtiene la colección de pacientes de la DB.
     * 
     * @return
     * @throws SQLException 
     */
    @Override
    public List<PacienteDTO> getPacientes() throws SQLException {
        PacienteDTO paciente = null;

        ResultSet rsPacientes = stmt_getAll.executeQuery();
        List<PacienteDTO> pacientes = new ArrayList<PacienteDTO>();

        while (rsPacientes.next()) {
            String nss = rsPacientes.getString("nss");
            String nombre = rsPacientes.getString("nombre");
            String apellido1 = rsPacientes.getString("apellido1");
            String apellido2 = rsPacientes.getString("apellido2");
            String alergias = rsPacientes.getString("alergias");
            int edad = rsPacientes.getInt("edad");
            Double peso = rsPacientes.getDouble("peso");
            Double altura = rsPacientes.getDouble("altura");

            paciente = new PacienteDTO(nss, nombre, apellido1, apellido2, alergias, edad, altura, peso);
            pacientes.add(paciente);
        }

        return pacientes;
    }
}
