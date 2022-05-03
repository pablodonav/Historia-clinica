/**
 * Config.java
 * Pablo Doñate Navarro
 * v2.4 01/05/2022.
 */
package control;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Clase que contiene los métodos para cargar y
 * guardar información de un fichero.
 * 
 */
public class Config {
    private static Config instancia = null; // es singleton
    private Properties configuracion;
    
    private Properties propiedades; 
    private static final String FICHERO_CONFIG = 
        "config.properties";
    public static String VERSION = "Hospital Server 2.4";
    
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
    private static final String DNI_ADMIN = "adminID";
    private String dniAdmin = "199A";
    private static final String EMAIL_ADMIN = "adminMail";
    private String emailAdmin = "admin@hsw.com";
    private static final String PWD_ADMIN = "adminPWD";
    private String pwdAdmin = "81dc9bdb52d04dc20036dbd8313ed055";
    

    /**
     * Devuelve la instancia de la clase Config.
     *
     * @return Config
     */
    public static synchronized Config getInstance() {
        if (instancia == null) {
            instancia = new Config();
        }
        return instancia;
    }

    /**
     * Lee los parámetros de fichero,
     * si no existe algún valor se introduce uno por defecto.
     * 
     */
    public void load() {
        try {
            propiedades = new Properties();
            propiedades.load(new FileInputStream(FICHERO_CONFIG));

            numThreads = Integer.parseInt(
                    propiedades.getProperty(NUM_THREADS));
            puertoServidor = Integer.parseInt(
                    propiedades.getProperty(PUERTO_SERVIDOR));
            dniAdmin = propiedades.getProperty(DNI_ADMIN);
            emailAdmin = propiedades.getProperty(EMAIL_ADMIN);
            pwdAdmin = propiedades.getProperty(PWD_ADMIN);
            
        } catch (IOException | NumberFormatException e) {
            configuracion.setProperty(NUM_THREADS, 
                    Integer.toString(numThreads));
            configuracion.setProperty(PUERTO_SERVIDOR, 
                Integer.toString(puertoServidor));
            configuracion.setProperty(DNI_ADMIN, dniAdmin);
            configuracion.setProperty(EMAIL_ADMIN, emailAdmin);
            configuracion.setProperty(PWD_ADMIN, pwdAdmin);
            
            save();
        }
    }
    
    /**
     * Escribe valores en el fichero de configuración.
     * 
     */
    public void save() {
        try {
            FileOutputStream fichero = 
                new FileOutputStream(FICHERO_CONFIG);
            fichero.close();
        } catch(IOException e) {
            e.printStackTrace();
        } 
    }
    
    public static Config getInstancia() {
        return instancia;
    }

    public static void setInstancia(Config instancia) {
        Config.instancia = instancia;
    }

    public Properties getPropiedades() {
        return propiedades;
    }

    public void setPropiedades(Properties propiedades) {
        this.propiedades = propiedades;
    }

    public int getNumThreads() {
        return numThreads;
    }

    public void setNumThreads(int numThreads) {
        this.numThreads = numThreads;
    }

    public int getPuertoServidor() {
        return puertoServidor;
    }

    public void setPuertoServidor(int puertoServidor) {
        this.puertoServidor = puertoServidor;
    }

    public String getDniAdmin() {
        return dniAdmin;
    }

    public void setDniAdmin(String dniAdmin) {
        this.dniAdmin = dniAdmin;
    }

    public String getEmailAdmin() {
        return emailAdmin;
    }

    public void setEmailAdmin(String emailAdmin) {
        this.emailAdmin = emailAdmin;
    }

    public String getPwdAdmin() {
        return pwdAdmin;
    }

    public void setPwdAdmin(String pwdAdmin) {
        this.pwdAdmin = pwdAdmin;
    }
    
    
}
