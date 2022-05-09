/**
 * DataBaseControl.java
 * Pablo Doñate Navarro
 * v2.6 09/05/2022.
 */
package modelo;

import com.google.gson.Gson;
import java.sql.Connection;
import modelo.clasesDAO.SanitarioDAOImpl;
import modelo.clasesDTOs.SanitarioDTO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.clasesDAO.CitaDAOImpl;
import modelo.clasesDAO.EpisodioAtencionDAOImpl;
import modelo.clasesDAO.MedicamentoDAOImpl;
import modelo.clasesDAO.PacienteDAOImpl;
import modelo.clasesDAO.UsuarioDAOImpl;
import modelo.clasesDAO.VacunaDAOImpl;
import modelo.clasesDTOs.CitaDTO;
import modelo.clasesDTOs.EpisodioAtencionDTO;
import modelo.clasesDTOs.HistoriaPacienteDTO;
import modelo.clasesDTOs.MedicamentoDTO;
import modelo.clasesDTOs.MedicamentoPacienteDTO;
import modelo.clasesDTOs.PacienteDTO;
import modelo.clasesDTOs.UsuarioDTO;
import modelo.clasesDTOs.VacunaDTO;
import modelo.clasesDTOs.VacunaPacienteDTO;

/**
 * Clase que contiene las operaciones asociadas
 * a la DB.
 * 
 */
public class DataBaseControl {
    private static DataBaseControl instancia 
        = null; //Es singleton
    private Connection conexion;
    
    private SanitarioDAOImpl sanImpl;
    private UsuarioDAOImpl usImpl;
    private PacienteDAOImpl pacImpl;
    private EpisodioAtencionDAOImpl epImpl;
    private CitaDAOImpl citImpl;
    private MedicamentoDAOImpl medImpl;
    private VacunaDAOImpl vacImpl;
    
    /**
     * Crea la conexión con la DB.
     * 
     * @throws SQLException 
     */
    public DataBaseControl() throws SQLException {
    	connectDB();
    }
    
    /**
     * Devuelve la instancia de la clase
     *  -> Es singleton.
     * 
     * @return
     * @throws SQLException 
     */
    public synchronized static DataBaseControl getInstance() 
            throws SQLException {
        if(instancia == null) {
            instancia = new DataBaseControl();
        }

        return instancia;
    }
    
    /** 
     * Realiza la conexión con la base de datos.
     * 
     * @throws SQLException
     */
    public void connectDB() throws SQLException {
    	DataBase db = new DataBase();

        conexion = db.MySQLConnect();
        
        sanImpl = new SanitarioDAOImpl(conexion);
        usImpl = new UsuarioDAOImpl(conexion);
        pacImpl = new PacienteDAOImpl(conexion);
        citImpl = new CitaDAOImpl(conexion);
        epImpl = new EpisodioAtencionDAOImpl(conexion);
        medImpl = new MedicamentoDAOImpl(conexion);
        vacImpl = new VacunaDAOImpl(conexion);
    }
    
    public boolean existeAdmin(String _dniAdmin) throws SQLException {
        if (conexion.isClosed()) {
            connectDB();
        }
        
        if (usImpl.getUsuario(_dniAdmin) != null) {
            return usImpl.existeAdmin(_dniAdmin);
        }
        
        return false;
    }
    
    /**
     * Añade un nuevo usuario a la DB.
     * 
     * @param _dni
     * @param _email
     * @param _pwd
     * @return
     * @throws SQLException 
     */
    public boolean addUsuario(String _dni, String _email, String _pwd) throws SQLException {
        if (conexion.isClosed()) {
            connectDB();
        }

        UsuarioDTO usuario = new UsuarioDTO(_dni, _email, _pwd);
        
        return usImpl.addUsuario(usuario);
    }
    
    /**
     * Añade un nuevo administrador a la DB.
     * 
     * @param _dniAdmin
     * @return
     * @throws SQLException 
     */
    public boolean addAdmin(String _dniAdmin) throws SQLException {
        if (conexion.isClosed()) {
            connectDB();
        }
        
        return usImpl.addAdmin(_dniAdmin);
    }
    
    /**
     * Añade un nuevo sanitario a la DB.
     * 
     * @param _sanitario
     * @return
     * @throws SQLException 
     */
    public boolean addSanitario(SanitarioDTO _sanitario)
            throws SQLException {

        if (conexion.isClosed()) {
            connectDB();
        }

        return sanImpl.addSanitario(_sanitario);
    }
    
    /**
     * Añade un nuevo paciente a la DB.
     * 
     * @param _paciente
     * @return
     * @throws SQLException 
     */
    public boolean addPaciente(PacienteDTO _paciente)
            throws SQLException {

        if (conexion.isClosed()) {
            connectDB();
        }

        return pacImpl.addPaciente(_paciente);
    }
    
    /**
     * Método que verifica si existe un usuario en la DB.
     * Devuelve el usuario devuelto por la base de datos.
     * 
     * @param _usuario
     * @return
     * @throws SQLException 
     */
    public UsuarioDTO verificarUsuario(UsuarioDTO _usuario) throws SQLException {
        if (conexion.isClosed()) {
            connectDB();
        }
        
        return usImpl.checkUser(_usuario);
    }
    
    /**
     * Método que modifica un sanitario existente.
     * 
     * @param _sanitario
     * @return
     * @throws SQLException 
     */
    public boolean editarSanitario(SanitarioDTO _sanitario) throws SQLException {
        if (conexion.isClosed()) {
            connectDB();
        }
        
        UsuarioDTO usuario = new UsuarioDTO(_sanitario.getDni(), _sanitario.getEmail(), _sanitario.getContraseña());
        
        if (usImpl.updateUsuario(usuario)) {
            return sanImpl.updateSanitario(_sanitario);
        }
        
        return false;
    }
    
    /**
     * Método que elimina a un sanitario existente.
     * 
     * @param _dni
     * @return
     * @throws SQLException 
     */
    public boolean eliminarSanitario(String _dni) throws SQLException {
        if (conexion.isClosed()) {
            connectDB();
        }
        
        return usImpl.deleteUsuario(_dni);
    }
    
    /**
     * Método que obtiene todos los sanitarios de la DB.
     * 
     * @return
     * @throws SQLException 
     */
    public String obtenerSanitarios() throws SQLException {
        Gson gson = new Gson();
        
        List<SanitarioDTO> sanitarios = new ArrayList<>();
        if (conexion.isClosed()) {
            connectDB();
        }
        
        sanitarios = sanImpl.getSanitarios();
        
        
        for (SanitarioDTO san : sanitarios) {
            UsuarioDTO usuario = usImpl.getUsuario(san.getDni());
            san.setEmail(usuario.getEmail());
            san.setContraseña(usuario.getContraseña());
        }

        
        return gson.toJson(sanitarios);
    }
    
    /**
     * Método que crea un nuevo episodio de un paciente.
     * 
     * @param _episodio
     * @param _nss
     * @return
     * @throws SQLException 
     */
    public boolean nuevoEpisodio(EpisodioAtencionDTO _episodio, String _nss) throws SQLException {
        if (conexion.isClosed()) {
            connectDB();
        }
        
        return epImpl.addEpisodio(_episodio, _nss);
    }
    
    /**
     * Método que devuelve el índice que debe ocupar el nuevo episodio
     * 
     * @return
     * @throws SQLException 
     */
    public int obtenerIndiceNuevoEpisodio() throws SQLException {
        if (conexion.isClosed()) {
            connectDB();
        }
        
        return epImpl.getCount();
    }
    
    /**
     * Devuelve el indice de la nueva cita a añadir.
     * 
     * @return
     * @throws SQLException 
     */
    public int obtenerIndiceNuevaCita() throws SQLException {
        if (conexion.isClosed()) {
            connectDB();
        }
        
        return citImpl.getNewIndex();
    }
    
    /**
     * Método que crea una nueva cita de un paciente.
     * 
     * @param _cita
     * @param _nss_pac
     * @return
     * @throws SQLException 
     */
    public boolean nuevaCita(CitaDTO _cita, String _nss_pac) throws SQLException {
        if (conexion.isClosed()) {
            connectDB();
        }
        
        return citImpl.addCita(_cita, _nss_pac);
    }
    
    /**
     * Método que obtiene las citas de un paciente.
     * 
     * @param _nss
     * @return
     * @throws SQLException 
     */
    public String obtenerCitasPaciente(String _nss) throws SQLException {
        Gson gson = new Gson();
        
        List<CitaDTO> citas = new ArrayList<>();
        if (conexion.isClosed()) {
            connectDB();
        }
        
        citas = citImpl.getCitasPaciente(_nss);
       
        return gson.toJson(citas);
    }
    
    /**
     * Elimina la cita de un paciente.
     * 
     * @param _idCita
     * @return
     * @throws SQLException 
     */
    public boolean eliminarCita(String _idCita) throws SQLException {
        if (conexion.isClosed()) {
            connectDB();
        }
        
        return citImpl.deleteCita(_idCita);
    }
    
    /**
     * Método que obtiene todos los pacientes de la DB.
     * 
     * @return
     * @throws SQLException 
     */
    public String obtenerPacientes() throws SQLException {
        Gson gson = new Gson();
        
        List<PacienteDTO> pacientes = new ArrayList<>();
        if (conexion.isClosed()) {
            connectDB();
        }
        
        pacientes = pacImpl.getPacientes();

        return gson.toJson(pacientes);
    }
    
    /**
     * Devuelve el JSON del conjunto de episodios de un paciente.
     * 
     * @param _nss
     * @return
     * @throws SQLException 
     */
    public String obtenerEpisodiosPaciente(String _nss) throws SQLException {
        Gson gson = new Gson();
        
        List<EpisodioAtencionDTO> episodios = new ArrayList<>();
        if (conexion.isClosed()) {
            connectDB();
        }
        
        episodios = epImpl.getEpisodios(_nss);
       
        return gson.toJson(episodios);
    }
    
    /**
     * Devuelve cierto si se ha conseguido registrar el nuevo diagnostico del episodio
     *  del paciente.
     * 
     * @param _episodio
     * @param _nss
     * @return
     * @throws SQLException 
     */
    public boolean modificarEpisodio(EpisodioAtencionDTO _episodio, String _nss) throws SQLException {
        if (conexion.isClosed()) {
            connectDB();
        }
        
        return epImpl.updateEpisodio(_episodio, _nss);
    }
    
    /**
     * Método que añade un medicamento a la receta de un paciente.
     * 
     * @param _medicamento
     * @return
     * @throws SQLException 
     */
    public boolean añadirMedicamentoAPaciente(MedicamentoPacienteDTO _medicamento, String _nss_pac) throws SQLException {
        if (conexion.isClosed()) {
            connectDB();
        }
        
        return medImpl.addMedicineToPatient(_medicamento, _nss_pac);
    }
    
    /**
     * Método que elimina un medicamento de la receta de un paciente.
     * 
     * @param _id
     * @param _nss_pac
     * @return
     * @throws SQLException 
     */
    public boolean eliminarMedicamentoDePaciente(int _id, String _nss_pac) throws SQLException {
        if (conexion.isClosed()) {
            connectDB();
        }
        
        return medImpl.deleteMedicineFromPatient(_nss_pac, _id);
    }
    
    /**
     * Método que devuelve el índice que debe ocupar el nuevo medicamento en la receta del paciente.
     * 
     * @return
     * @throws SQLException 
     */
    public int obtenerIndiceNuevoMedicamento() throws SQLException {
        if (conexion.isClosed()) {
            connectDB();
        }
        
        return medImpl.getNewIndex();
    }
    
    /**
     * Devuelve la receta de un paciente en formate JSON.
     * 
     * @param _nss
     * @return
     * @throws SQLException 
     */
    public String obtenerRecetaPaciente(String _nss) throws SQLException {
        Gson gson = new Gson();
        
        List<MedicamentoPacienteDTO> receta = new ArrayList<>();
        if (conexion.isClosed()) {
            connectDB();
        }
        
        receta = medImpl.getMedicamentosPaciente(_nss);
       
        return gson.toJson(receta);
    }
    
    /**
     * Devuelve los medicamentos disponibles en formato JSON.
     * 
     * @return
     * @throws SQLException 
     */
    public String obtenerMedicamentosDisponibles() throws SQLException {
        Gson gson = new Gson();
        
        List<MedicamentoDTO> medicamentos = new ArrayList<>();
        if (conexion.isClosed()) {
            connectDB();
        }
        
        medicamentos = medImpl.getMedicamentos();
       
        return gson.toJson(medicamentos);
    }
    
    /**
     * Método que añade una vacuna a la historia de un paciente.
     * 
     * @param _vacuna
     * @param _nss_pac
     * @return
     * @throws SQLException 
     */
    public boolean añadirVacunaAPaciente(VacunaPacienteDTO _vacuna, String _nss_pac) throws SQLException {
        if (conexion.isClosed()) {
            connectDB();
        }
        
        return vacImpl.addVaccineToPatient(_vacuna, _nss_pac);
    }
    
    /**
     * Método que devuelve el índice que debe ocupar la nueva 
     * vacuna en la historia del paciente.
     * 
     * @return
     * @throws SQLException 
     */
    public int obtenerIndiceNuevaVacuna() throws SQLException {
        if (conexion.isClosed()) {
            connectDB();
        }
        
        return vacImpl.getNewIndex();
    }
    
    /**
     * Devuelve las vacunas de un paciente en formato JSON.
     * 
     * @param _nss
     * @return
     * @throws SQLException 
     */
    public String obtenerVacunasPaciente(String _nss) throws SQLException {
        Gson gson = new Gson();
        
        List<VacunaPacienteDTO> vacunas = new ArrayList<>();
        if (conexion.isClosed()) {
            connectDB();
        }
        
        vacunas = vacImpl.getVacunasPaciente(_nss);
       
        return gson.toJson(vacunas);
    }
    
    /**
     * Devuelve las vacunas disponibles en formato JSON.
     * 
     * @return
     * @throws SQLException 
     */
    public String obtenerVacunasDisponibles() throws SQLException {
        Gson gson = new Gson();
        
        List<VacunaDTO> vacunas = new ArrayList<>();
        if (conexion.isClosed()) {
            connectDB();
        }
        
        vacunas = vacImpl.getVacunas();
       
        return gson.toJson(vacunas);
    }
    
    public String obtenerHistoriaPaciente(String _nss) throws SQLException {
        List<EpisodioAtencionDTO> episodios = new ArrayList<>();
        List<MedicamentoPacienteDTO> medicamentos = new ArrayList<>();
        List<VacunaPacienteDTO> vacunas = new ArrayList<>();
        
        episodios = epImpl.getEpisodios(_nss);
        medicamentos = medImpl.getMedicamentosPaciente(_nss);
        vacunas = vacImpl.getVacunasPaciente(_nss);
        
        HistoriaPacienteDTO historiaPaciente = new HistoriaPacienteDTO(episodios, medicamentos, vacunas);
        
        return historiaPaciente.toJson();
    }
}
