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
import java.util.List;
import modelo.clasesDTOs.EpisodioAtencionDTO;

/**
 *
 * @author pablo
 */
public class EpisodioAtencionDAOImpl implements EpisodioAtencionDAO {
    private PreparedStatement stmt_add;
    private PreparedStatement stmt_del;
    private PreparedStatement stmt_upd;
    private PreparedStatement stmt_getAll;
    private PreparedStatement stmt_getEp;

    private static final String INSERT = "INSERT INTO EPISODIO_DE_ATENCION(fecha, motivo, diagnostico, nss_pac) VALUES(?, ?, ?, ?)";
    
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

    @Override
    public boolean deleteEpisodio(String _id, String _nss) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean updateEpisodio(EpisodioAtencionDTO _episodio) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public EpisodioAtencionDTO getEpisodio(String _id, String _nss) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<EpisodioAtencionDTO> getEpisodios() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
