/**
 * EpisodioAtencionDAO.java
 * Pablo Doñate Navarro
 * v2.5 06/05/2022.
 */
package modelo.clasesDAO;

import java.sql.SQLException;
import java.util.List;
import modelo.clasesDTOs.EpisodioAtencionDTO;

/**
 * Interfaz que define las cabereceras de las operaciones 
 *  CRUD de un episodio de atención.
 * 
 */
public interface EpisodioAtencionDAO {
    public boolean addEpisodio(EpisodioAtencionDTO _episodio, String _nss) throws SQLException;
    
    public int getCount() throws SQLException;

    public boolean deleteEpisodio(String _id, String _nss) throws SQLException;

    public boolean updateEpisodio(EpisodioAtencionDTO _episodio, String _nss) throws SQLException;

    public EpisodioAtencionDTO getEpisodio(String _id, String _nss) throws SQLException;

    public List<EpisodioAtencionDTO> getEpisodios(String _nss) throws SQLException;
}
