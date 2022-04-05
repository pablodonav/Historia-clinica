/**
 * ServidorSanitarios.java
 * Pablo Doñate Navarro
 * v1.0 02/04/2022.
 */
package control;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import modelo.DataBaseControl;
import modelo.clasesDTOs.SanitarioDTO;
import modelo.clasesDTOs.UsuarioDTO;

/**
 * Clase asociada al servidor de sanitarios.
 * Será el encargado de recibir todas las conexiones de los sanitarios.
 * Para cada conexión, creará un ServidorHospital, que recibirá las peticiones
 * de cada sanitario.
 * 
 */
public class ServidorSanitarios extends Thread {
    private static int TIEMPO_TEST_CONEXIONES = 10 * 1000;
    public static int TIEMPO_ESPERA_CLIENTE = 1000;  

    private Map<String, ConexionPushHospital> 
        conexionesPushSanitarios;
    private int numConexion = 0;
    private DataBaseControl database;
    
     /** Configuración */  
    private Properties propiedades; 
    private static final String FICHERO_CONFIG = 
        "config.properties";
    public static String VERSION = "Hospital Server 1.0";
    
    private static String FICHERO_CONFIG_ERRONEO = 
        "Config file is wrong. Set default values";
    private static String ESPERANDO_SOLICITUD_SANITARIO = 
        "Waiting for health worker request...";
    private static String ERROR_EJECUCION_SERVIDOR = 
        "Error: Server running in ";   
    private static String ERROR_CREANDO_CONEXION_SANITARIO = 
            "Failed to create health worker connection";    

    private static final String NUM_THREADS = "threadsNumber";
    private int numThreads = 30;
    private static final String PUERTO_SERVIDOR = "serverPort";
    private int puertoServidor = 15000;

    /**
     *  Construye el servidor de sanitarios.
     * 
     * @throws java.sql.SQLException
     */   
    public ServidorSanitarios() throws SQLException {
        leerConfiguracion();
        database = DataBaseControl.getInstance();
        
        conexionesPushSanitarios = new ConcurrentHashMap<>();
        envioTestPeriodicosConexionesPushSanitarios();
        start();
    }
    
    /**
     *  Lee configuración.
     * 
     */ 
    private void leerConfiguracion() {
        try {
            propiedades = new Properties();
            propiedades.load(new FileInputStream(FICHERO_CONFIG));

            numThreads = Integer.parseInt(
                    propiedades.getProperty(NUM_THREADS));
            puertoServidor = Integer.parseInt(
                    propiedades.getProperty(PUERTO_SERVIDOR));
            
        } catch (IOException | NumberFormatException e) {
            System.out.println(FICHERO_CONFIG_ERRONEO);
            System.out.println(NUM_THREADS + " = " + numThreads);
            System.out.println(PUERTO_SERVIDOR + " = " + 
                puertoServidor);
        }
    }
    
    /**
     * Envía tests periódicos para mantener lista conexiones
     * push con sanitarios.
     *  
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
     *  Ejecuta bucle espera conexiones.
     * 
     */   
    @Override
    public void run() { 
        System.out.println(VERSION);  
        try {
            ExecutorService poolThreads = 
                    Executors.newFixedThreadPool(numThreads);
      
            ServerSocket serverSocket = 
                new ServerSocket(puertoServidor);
      
            while(true) {
                System.out.println(ESPERANDO_SOLICITUD_SANITARIO);  
                Socket socket = serverSocket.accept();

                poolThreads.execute(
                    new ServidorHospital(this, socket));
            }      
      
        } catch (BindException e) {
            System.out.println(ERROR_EJECUCION_SERVIDOR + 
                puertoServidor);
        } catch (IOException e) {
            System.out.println(ERROR_CREANDO_CONEXION_SANITARIO);
        }
    }
     
    /**
     *  Genera nuevo identificador de conexión push hospital.
     * 
     */
    synchronized String generarIdConexionPushHospital() { 
        return String.valueOf(++numConexion); 
    }
    
    /**
     * Devuelve el numero de conexión.
     * 
     */
    synchronized String obtenerIdConexion() { 
        return String.valueOf(numConexion);
    }
    
    /**
     *  Obtiene conexión push hospital por id conexión.
     * 
     */
    ConexionPushHospital obtenerConexionPushHospital (
            String _idConexion) {
        ConexionPushHospital conexionPushHospital = 
                conexionesPushSanitarios.get(_idConexion);
    
        if (conexionPushHospital != null) {
            return conexionPushHospital;
        }
    
        return null;
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
     *  Elimina conexión push hospital.
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
    
    synchronized UsuarioDTO verificarUsuario(UsuarioDTO _usuario) throws IOException, SQLException {
        UsuarioDTO usuario;
        if ((usuario = database.verificarUsuario(_usuario)) == null) {
            return null;
        }
        
        return usuario;
    }
    
    /**
     *  Notifica cambio hospital al resto de sanitarios.
     * 
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
     * @param args the command line arguments
     * @throws java.sql.SQLException
     */
    public static void main(String[] args) throws SQLException {
        ServidorSanitarios servidorSanitarios = new ServidorSanitarios();
    }
    
}

