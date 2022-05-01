/**
 * EpisodioAtencionDAOImpl.java
 * Pablo Doñate Navarro
 * v2.4 01/05/2022.
 */
package modelo.clasesDAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
    private PreparedStatement stmt_getCount;

    private static final String INSERT = "INSERT INTO EPISODIO_DE_ATENCION(id, fecha, motivo, diagnostico, nss_pac) VALUES(?, ?, ?, ?, ?)";
    private static final String DELETE = "DELETE FROM EPISODIO_DE_ATENCION WHERE id=? AND nss_pac=?";
    private static final String UPDATE = "UPDATE EPISODIO_DE_ATENCION SET diagnostico=? WHERE id=? AND nss_pac=?";
    private static final String GET_EPISODIO = "SELECT * FROM EPISODIO_DE_ATENCION WHERE nss_pac=? AND id=?";
    private static final String GET_ALL = "SELECT * FROM EPISODIO_DE_ATENCION WHERE nss_pac=?";
    private static final String GET_COUNT = "SELECT COUNT(*) FROM EPISODIO_DE_ATENCION";
    
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
        this.stmt_getCount = _conn.prepareStatement (GET_COUNT);
    }

    /**
     * Añade un nuevo episodio a la DB.
     * 
     * @param _episodio
     * @param _nss
     * @return
     * @throws SQLException 
     */
    @Override
    public boolean addEpisodio(EpisodioAtencionDTO _episodio, String _nss) throws SQLException {
        
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = simpleDateFormat.format(_episodio.getFecha());
       
        stmt_add.setInt(1, _episodio.getId());
        stmt_add.setDate(2, java.sql.Date.valueOf(formattedDate));
        stmt_add.setString(3, _episodio.getMotivo());
        stmt_add.setString(4, _episodio.getDiagnostico());
        stmt_add.setString(5, _nss);
        
        return stmt_add.executeUpdate() > 0;
    }
    
    /**
     * Devuelve el el valor del indice del próximo episodio a insertar.
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

        return stmt_del.executeUpdate() > 0;
    }

    /**
     * Modifica el episodio de un paciente.Para ello,
     *  se pasa el episodio con los campos modificados.
     * 
     * @param _episodio
     * @param _nss
     * @return
     * @throws SQLException 
     */
    @Override
    public boolean updateEpisodio(EpisodioAtencionDTO _episodio, String _nss) throws SQLException {
        stmt_upd.setString(1, _episodio.getDiagnostico());
        stmt_upd.setInt(2, _episodio.getId());
        stmt_upd.setString(3, _nss);

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
            Date fec = rs.getDate("fecha");
            String motivo = rs.getString("motivo");
            String diagnostico = rs.getString("diagnostico");
            String nss_pac = rs.getString("nss_pac");

            java.util.Date fecha = new java.util.Date(fec.getYear(), fec.getMonth(), fec.getDay());
            
            episodio = new EpisodioAtencionDTO(id, fecha, motivo, diagnostico);
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

        stmt_getAll.setString(1, _nss);
        
        ResultSet rs = stmt_getAll.executeQuery();
        List<EpisodioAtencionDTO> episodios = new ArrayList<EpisodioAtencionDTO>();

        while (rs.next()) {
            int id = rs.getInt("id");
            Date fec = rs.getDate("fecha");
            String motivo = rs.getString("motivo");
            String diagnostico = rs.getString("diagnostico");
            String nss_pac = rs.getString("nss_pac");
            
            java.util.Date fecha = new java.util.Date(fec.getYear(), fec.getMonth(), fec.getDay());

            episodio = new EpisodioAtencionDTO(id, fecha, motivo, diagnostico);
            episodios.add(episodio);
        }

        return episodios;
    }
    
}
