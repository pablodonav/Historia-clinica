/**
 * EpisodioAtencionDAOImpl.java
 * Pablo Doñate Navarro
 * v1.0 02/04/2022.
 */
package modelo.clasesDAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.clasesDTOs.EpisodioAtencionDTO;

/**
 * Clase que contiene las funciones necesarias
 *  para realizar las operaciones CRUD de un episodio de atencion.
 * 
 */
public class EpisodioAtencionDAOImpl implements EpisodioAtencionDAO {
    private PreparedStatement stmt_add;
    private PreparedStatement stmt_del;
    private PreparedStatement stmt_upd;
    private PreparedStatement stmt_getAll;
    private PreparedStatement stmt_getEp;

    private static final String INSERT = "INSERT INTO EPISODIO_DE_ATENCION(fecha, motivo, diagnostico, nss_pac) VALUES(?, ?, ?, ?)";
    private static final String DELETE = "DELETE FROM EPISODIO_DE_ATENCION WHERE id=? AND nss_pac=?";
    private static final String UPDATE = "UPDATE EPISODIO_DE_ATENCION SET fecha=?, motivo=?, diagnostico=? WHERE id=? AND nss_pac=?";
    private static final String GET_EPISODIO = "SELECT * FROM EPISODIO_DE_ATENCION WHERE nss_pac=? AND id=?";
    private static final String GET_ALL = "SELECT * FROM EPISODIO_DE_ATENCION";
    
    private Connection connection;

    /**
     *  Crea un EpisodioAtencionDAOImpl, donde define la estructura
     *  de sus operaciones.
     *
     * @param _conn
     * @throws java.sql.SQLException
     */
    public EpisodioAtencionDAOImpl(Connection _conn) throws SQLException {
        this.connection = _conn;
        this.stmt_add = _conn.prepareStatement(INSERT);
        this.stmt_getEp = _conn.prepareStatement(GET_EPISODIO);
        this.stmt_del = _conn.prepareStatement(DELETE);
        this.stmt_getAll = _conn.prepareStatement(GET_ALL);
        this.stmt_upd = _conn.prepareStatement(UPDATE);
    }

    /**
     * Añade un nuevo episodio a la DB.
     * 
     * @param _episodio
     * @return
     * @throws SQLException 
     */
    @Override
    public boolean addEpisodio(EpisodioAtencionDTO _episodio) throws SQLException {
        stmt_add.setDate(1, _episodio.getFecha());
        stmt_add.setString(2, _episodio.getMotivo());
        stmt_add.setString(3, _episodio.getDiagnostico());
        stmt_add.setString(4, _episodio.getNss_pac());
        
        return stmt_add.executeUpdate() > 0;
    }

    /**
     * Elimina el episodio de un paciente.
     * 
     * @param _id
     * @param _nss
     * @return
     * @throws SQLException 
     */
    @Override
    public boolean deleteEpisodio(String _id, String _nss) throws SQLException {
        stmt_del.setString(1, _id);
        stmt_del.setString(2, _nss);

        if (stmt_del.executeUpdate() > 0) {
            return true;
        }

        return false;
    }

    /**
     * Modifica el episodio de un paciente. Para ello,
     * se pasa el episodio con los campos modificados.
     * 
     * @param _episodio
     * @return
     * @throws SQLException 
     */
    @Override
    public boolean updateEpisodio(EpisodioAtencionDTO _episodio) throws SQLException {
        stmt_upd.setDate(1, _episodio.getFecha());
        stmt_upd.setString(2, _episodio.getMotivo());
        stmt_upd.setString(3, _episodio.getDiagnostico());
        stmt_upd.setInt(4, _episodio.getId());
        stmt_upd.setString(5, _episodio.getNss_pac());

        return stmt_upd.executeUpdate() > 0;
    }

    /**
     * Devuelve el episodio dado su identificador y nss del paciente asociado.
     * 
     * @param _id
     * @param _nss
     * @return
     * @throws SQLException 
     */
    @Override
    public EpisodioAtencionDTO getEpisodio(String _id, String _nss) throws SQLException {
        EpisodioAtencionDTO episodio = null;

        stmt_getEp.setString(1, _nss);
        stmt_getEp.setString(2, _id);
        ResultSet rs = stmt_getEp.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("id");
            Date fecha = rs.getDate("fecha");
            String motivo = rs.getString("motivo");
            String diagnostico = rs.getString("diagnostico");
            String nss_pac = rs.getString("nss_pac");

            episodio = new EpisodioAtencionDTO(id, fecha, motivo, diagnostico, nss_pac);
        }
        return episodio;
    }

    /**
     * Obtiene todos los episodios de un paciente.
     * 
     * @return
     * @throws SQLException 
     */
    @Override
    public List<EpisodioAtencionDTO> getEpisodios(String _nss) throws SQLException {
        EpisodioAtencionDTO episodio = null;

        ResultSet rs = stmt_getAll.executeQuery();
        List<EpisodioAtencionDTO> episodios = new ArrayList<EpisodioAtencionDTO>();

        while (rs.next()) {
            int id = rs.getInt("id");
            Date fecha = rs.getDate("fecha");
            String motivo = rs.getString("motivo");
            String diagnostico = rs.getString("diagnostico");
            String nss_pac = rs.getString("nss_pac");

            episodio = new EpisodioAtencionDTO(id, fecha, motivo, diagnostico, nss_pac);
            episodios.add(episodio);
        }

        return episodios;
    }
    
}
