/**
 * ServidorSanitarios.java
 * Pablo Doñate Navarro
 * v2.6 09/05/2022.
 */
package control;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import modelo.DataBaseControl;
import modelo.clasesDTOs.CitaDTO;
import modelo.clasesDTOs.EpisodioAtencionDTO;
import modelo.clasesDTOs.MedicamentoPacienteDTO;
import modelo.clasesDTOs.PacienteDTO;
import modelo.clasesDTOs.SanitarioDTO;
import modelo.clasesDTOs.UsuarioDTO;
import modelo.clasesDTOs.VacunaPacienteDTO;

/**
 * Clase asociada al servidor de sanitarios.
 * Será el encargado de recibir todas las conexiones de los sanitarios.
 * Para cada conexión, creará un ServidorHospital, que recibirá las peticiones
 * de cada sanitario.
 */
public class ServidorSanitarios extends Thread {
    private static int TIEMPO_TEST_CONEXIONES = 10 * 1000;
    public static int TIEMPO_ESPERA_CLIENTE = 1000;  
    
    private static String ERROR_EJECUCION_SERVIDOR = 
        "Error: Server running in ";   
    private static String ERROR_CREANDO_CONEXION_SANITARIO = 
        "Failed to create health worker connection";

    private Map<String, ConexionPushHospital> 
        conexionesPushSanitarios;
    private int numConexion = 0;
    private DataBaseControl database;
    
    private Config configuracion;

    /**
     * Construye el servidor de sanitarios.
     * 
     * @throws java.sql.SQLException
     */   
    public ServidorSanitarios() throws SQLException {
        this.configuracion = Config.getInstance();
        configuracion.load();
        database = DataBaseControl.getInstance();
        
        if ( ! database.existeAdmin(configuracion.getDniAdmin())) {
            database.addUsuario(configuracion.getDniAdmin(), configuracion.getEmailAdmin(), configuracion.getPwdAdmin());
            database.addAdmin(configuracion.getDniAdmin());
        }
        
        conexionesPushSanitarios = new ConcurrentHashMap<>();
        envioTestPeriodicosConexionesPushSanitarios();
        start();
    }
    
    /**
     * Envía tests periódicos para mantener lista conexiones
     * push con sanitarios.
     */
    private void envioTestPeriodicosConexionesPushSanitarios() {  
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                for(ConexionPushHospital conexionPushHospital :
                conexionesPushSanitarios.values()) {
                    try {
                        conexionPushHospital.enviarSolicitud(
                            PrimitivaComunicacion.TEST, 
                            TIEMPO_TEST_CONEXIONES);                       
                    } catch (IOException e1) {
                        System.out.println(
                            ServidorHospital.
                                ERROR_CONEXION_HOSPITAL + " " + 
                                conexionPushHospital.toString());

                        conexionesPushSanitarios.remove(
                            conexionPushHospital.
                                obtenerIdConexion());
                        try {
                            conexionPushHospital.cerrar(); 
                        } catch (IOException ex) {
                            // No hacemos nada, ya hemos
                            // cerrado conexión
                        }
                    }
                }
            }          
        }, TIEMPO_TEST_CONEXIONES, TIEMPO_TEST_CONEXIONES);              
    }
    
    /**
     * Ejecuta bucle espera conexiones.
     */
    @Override
    public void run() { 
        System.out.println(configuracion.VERSION);  
        try {
            ExecutorService poolThreads = 
                    Executors.newFixedThreadPool(configuracion.getNumThreads());
      
            ServerSocket serverSocket = 
                new ServerSocket(configuracion.getPuertoServidor());
      
            while(true) {
                Socket socket = serverSocket.accept();

                poolThreads.execute(
                    new ServidorHospital(this, socket));
            }      
      
        } catch (BindException e) {
            System.out.println(ERROR_EJECUCION_SERVIDOR + 
                configuracion.getPuertoServidor());
        } catch (IOException e) {
            System.out.println(ERROR_CREANDO_CONEXION_SANITARIO);
        }
    }
     
    /**
     * Genera nuevo identificador de conexión push hospital.
     * 
     * @return 
     */
    synchronized String generarIdConexionPushHospital() { 
        return String.valueOf(++numConexion); 
    }
    
    /**
     * Devuelve el numero de conexión.
     * 
     * @return 
     */
    synchronized String obtenerIdConexion() { 
        return String.valueOf(numConexion);
    }
    
    /**
     * Obtiene conexión push hospital por id conexión.
     * 
     * @param _idConexion
     * @return 
     */
    ConexionPushHospital obtenerConexionPushHospital (
            String _idConexion) {
        ConexionPushHospital conexionPushHospital = 
                conexionesPushSanitarios.get(_idConexion);
    
        return conexionPushHospital;
    }
    
    /**
     * Nueva conexión push hospital.
     * 
     * @param _conexionPushHospital
     */   
    synchronized void nuevaConexionPushHospital(
        ConexionPushHospital _conexionPushHospital) {
        
        conexionesPushSanitarios.put(
            _conexionPushHospital.obtenerIdConexion(), 
            _conexionPushHospital);  
    }
    
    /**
     * Elimina conexión push hospital.
     * 
     * @param _idConexion
     */   
    synchronized boolean eliminarConexionPushHospital(
            String _idConexion) throws IOException {
        ConexionPushHospital conexionPushHospital = 
                conexionesPushSanitarios.get(_idConexion);

        if (conexionPushHospital == null) {
          return false;
        }            

        conexionPushHospital.cerrar();
        conexionesPushSanitarios.remove(_idConexion); 

        return true;
    }
    
    /**
     * Método que añade un sanitario a la DB.
     * Para ello, primero crea un usuario, y luego 
     * lo registra como sanitario.
     * Finalmente, notifica a todos los sanitarios.
     * 
     * @param _sanitario
     * @return
     * @throws IOException
     * @throws SQLException 
     */
    synchronized boolean añadirSanitario(SanitarioDTO _sanitario) throws IOException, SQLException {
        if( ! database.addUsuario(_sanitario.getDni(), _sanitario.getEmail(), _sanitario.getContraseña())) {
            return false;
        }
        
        if( ! database.addSanitario(_sanitario)) {
            return false;
        }
        
        notificarSanitariosPush(PrimitivaComunicacion.DAR_ALTA_SANITARIO, 
                String.valueOf(_sanitario.toJson()));
        
        return true;
    }
    
    /**
     * Método que verifica si un usuario existe.
     * Si no existe, devolverá null.
     * Si existe, devolverá el usuario.
     * 
     * @param _usuario
     * @return
     * @throws IOException
     * @throws SQLException 
     */
    synchronized UsuarioDTO verificarUsuario(UsuarioDTO _usuario) throws IOException, SQLException {
        UsuarioDTO usuario;
        if ((usuario = database.verificarUsuario(_usuario)) == null) {
            return null;
        }
        
        return usuario;
    }
    
    /**
     * Método que modifica los atributos de un sanitario.
     * Notificará a todos los sanitarios del cambio, y
     * devolverá true en caso de haberse realizado
     * la modificación.
     * 
     * @param _sanitario
     * @return
     * @throws SQLException
     * @throws IOException 
     */
    synchronized boolean editarSanitario(SanitarioDTO _sanitario) 
            throws SQLException, IOException {
        if ( ! database.editarSanitario(_sanitario)) {
            return false;
        }
        
        notificarSanitariosPush(PrimitivaComunicacion.EDITAR_SANITARIO, 
                String.valueOf(_sanitario.toJson()));
        
        return true;
    }
    
    /**
     * Método que obtiene la lista de sanitarios 
     * registrados en el sistema.
     * 
     * @return
     */
    synchronized String obtenerSanitarios() throws SQLException {
        return database.obtenerSanitarios();
    }
    
    /**
     * Método que añade un paciente a la DB.
     * Para ello, primero crea un paciente.
     * Finalmente, notifica a todos los sanitarios activos.
     * 
     * @param _paciente
     * @return
     * @throws IOException
     * @throws SQLException 
     */
    synchronized boolean añadirPaciente(PacienteDTO _paciente) 
            throws IOException, SQLException {
        if( ! database.addPaciente(_paciente)) {
            return false;
        }
        
        notificarSanitariosPush(PrimitivaComunicacion.NUEVO_PACIENTE, 
                String.valueOf(_paciente.toJson()));
        
        return true;
    }
    
    /**
     * Método que elimina un sanitario del sistema.
     * 
     * @param _dni
     * @return
     * @throws IOException
     * @throws SQLException 
     */
    synchronized boolean eliminarSanitario(String _dni) 
            throws IOException, SQLException {
        if( ! database.eliminarSanitario(_dni)) {
            return false;
        }
        
        notificarSanitariosPush(PrimitivaComunicacion.DAR_BAJA_SANITARIO, 
                String.valueOf(_dni));
        
        return true;
    }
    
    /**
     * Método que crea un nuevo episodio de 
     * un paciente en el sistema.
     * 
     * @param _episodio
     * @param _nss
     * @return
     * @throws IOException
     * @throws SQLException 
     */
    synchronized boolean nuevoEpisodio(EpisodioAtencionDTO _episodio, String _nss) 
            throws IOException, SQLException {
        int cuenta = database.obtenerIndiceNuevoEpisodio();
        _episodio.setId(cuenta);
        
        if( ! database.nuevoEpisodio(_episodio, _nss)) {
            return false;
        }
        
        notificarSanitariosPush(PrimitivaComunicacion.NUEVO_EPISODIO, 
                String.valueOf(_episodio.toJson()));
        
        return true;
    }
    
    /**
     * Método que crea una nueva cita en el sistema.
     * 
     * @param _cita
     * @return
     * @throws IOException
     * @throws SQLException 
     */
    synchronized boolean nuevaCita(CitaDTO _cita, String _nss_pac) throws IOException, SQLException {
        int cuenta = database.obtenerIndiceNuevaCita();
        _cita.setIdentificador(cuenta);
        
        if( ! database.nuevaCita(_cita, _nss_pac)) {
            return false;
        }
        
        notificarSanitariosPush(PrimitivaComunicacion.NUEVA_CITA, 
                String.valueOf(_cita.toJson()));
        
        return true;
    }
    
    /**
     * Método que devuelve las citas de un paciente.
     * 
     * @param _nss
     * @return
     * @throws SQLException 
     */
    synchronized String obtenerCitasPaciente(String _nss) throws SQLException {
        return database.obtenerCitasPaciente(_nss);
    }
    
    /**
     * Elimina la cita de un paciente.
     * 
     * @param _idCita
     * @return
     * @throws IOException
     * @throws SQLException 
     */
    synchronized boolean eliminarCita(String _idCita) throws IOException, SQLException {
        if( ! database.eliminarCita(_idCita)) {
            return false;
        }
        
        notificarSanitariosPush(PrimitivaComunicacion.ELIMINAR_CITA, 
                String.valueOf(_idCita));
        
        return true;
    }
    
    /**
     * Método que obtiene la lista de pacientes 
     * registrados en el sistema.
     * 
     * @return
     */
    synchronized String obtenerPacientes() throws SQLException {
        return database.obtenerPacientes();
    }
    
    /**
     * Método que devuelve los episodios de un paciente.
     * 
     * @param nss
     * @return 
     */
    synchronized String obtenerEpisodiosPaciente(String _nss) throws SQLException {
        return database.obtenerEpisodiosPaciente(_nss);
    }
    
    /**
     * Devuelve cierto si se ha podido registrar el nuevo diagnostico 
     *  al episodio del paciente.
     * 
     * @return
     * @throws SQLException
     * @throws IOException 
     */
    synchronized boolean registrarDiagnostico(EpisodioAtencionDTO _episodio, String _nss) 
                throws SQLException, IOException {
        if ( ! database.modificarEpisodio(_episodio, _nss)) {
            return false;
        }
        
        notificarSanitariosPush(PrimitivaComunicacion.NUEVO_DIAGNOSTICO, 
                String.valueOf(_episodio.toJson()));
        
        return true;
    }
    
    /**
     * Devuelve cierto si se ha podido añadir un nuevo medicamento a la receta de
     * un paciente.
     * 
     * @param _medicamento
     * @return
     * @throws SQLException
     * @throws IOException 
     */
    synchronized boolean añadirMedicamento(MedicamentoPacienteDTO _medicamento, String _nss_pac) 
                throws SQLException, IOException {
        _medicamento.setId(database.obtenerIndiceNuevoMedicamento());
        
        if ( ! database.añadirMedicamentoAPaciente(_medicamento, _nss_pac)) {
            return false;
        }
        
        notificarSanitariosPush(PrimitivaComunicacion.NUEVO_MEDICAMENTO_PACIENTE, 
                String.valueOf(_medicamento.toJson()));
        
        return true;
    }
    
    /**
     * Devuelve cierto si se ha podido eliminar un medicamento de la receta de un paciente.
     * 
     * @param _id
     * @param _nss_pac
     * @return
     * @throws SQLException
     * @throws IOException 
     */
    synchronized boolean eliminarMedicamento(int _id, String _nss_pac) 
            throws SQLException, IOException {
        
        if ( ! database.eliminarMedicamentoDePaciente(_id, _nss_pac)) {
            return false;
        }
        
        notificarSanitariosPush(PrimitivaComunicacion.ELIMINAR_MEDICAMENTO_PACIENTE, 
                String.valueOf(_id));
        
        return true;
    }
    
    /**
     * Método que obtiene la receta electronica
     * de un paciente.
     * 
     * @param _nss
     * @return
     * @throws SQLException 
     */
    synchronized String obtenerRecetaPaciente(String _nss) throws SQLException {
        return database.obtenerRecetaPaciente(_nss);
    }
    
    /**
     * Método que devuelve la lista de 
     * medicamentos disponibles (dados de alta).
     * 
     * @return
     * @throws SQLException 
     */
    synchronized String obtenerMedicamentosDisponibles() throws SQLException {
        return database.obtenerMedicamentosDisponibles();
    }
    
    /**
     * Devuelve cierto si se ha podido añadir una nueva vacuna a la historia de
     * un paciente.
     * 
     * @param _vacuna
     * @return
     * @throws SQLException
     * @throws IOException 
     */
    synchronized boolean añadirVacuna(VacunaPacienteDTO _vacuna, String _nss_pac) 
                throws SQLException, IOException {
        _vacuna.setId(database.obtenerIndiceNuevaVacuna());
        
        if ( ! database.añadirVacunaAPaciente(_vacuna, _nss_pac)) {
            return false;
        }
        
        notificarSanitariosPush(PrimitivaComunicacion.NUEVA_VACUNA_PACIENTE, 
                String.valueOf(_vacuna.toJson()));
        
        return true;
    }
    
    /**
     * Método que obtiene la historia de vacunas
     * de un paciente.
     * 
     * @param _nss
     * @return
     * @throws SQLException 
     */
    synchronized String obtenerVacunasPaciente(String _nss) throws SQLException {
        return database.obtenerVacunasPaciente(_nss);
    }
    
    /**
     * Método que devuelve la lista de 
     * vacunas disponibles (dadas de alta).
     * 
     * @return
     * @throws SQLException 
     */
    synchronized String obtenerVacunasDisponibles() throws SQLException {
        return database.obtenerVacunasDisponibles();
    }
    
    /**
     * Método que devuelve la historia médica completa
     * de un paciente.
     * 
     * @param _nss
     * @return
     * @throws SQLException 
     */
    synchronized String obtenerHistoriaPaciente(String _nss) throws SQLException {
        return database.obtenerHistoriaPaciente(_nss);
    }
    
    /**
     * Notifica cambio hospital al resto de sanitarios.
     * 
     * @param _primitivaComunicacion
     * @param _parametros
     * @throws IOException 
     */
    private void notificarSanitariosPush(
            PrimitivaComunicacion _primitivaComunicacion, 
            String _parametros)
            throws IOException {
        for(ConexionPushHospital conexionPushHospital : 
                conexionesPushSanitarios.values()) {
            conexionPushHospital.enviarSolicitud(
                _primitivaComunicacion, 
                TIEMPO_ESPERA_CLIENTE, 
                _parametros);        
        }
    }
    
    /**
     * 
     * @param args
     * @throws SQLException 
     */
    public static void main(String[] args) throws SQLException {
        ServidorSanitarios servidorSanitarios = new ServidorSanitarios();
    }
}

