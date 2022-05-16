/**
 * Config.java
 * Adnana Catrinel Dragut
 * v2.0 28/03/2022.
 * 
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
    private static final String FICHERO_CONFIG = "config.properties";
    private static String COMENTARIO_CONFIG = 
        Hospital.VERSION + " configuración conexión Servidor";
    
    public static final String URL_SERVIDOR = "URLServidor";
    public static final String PUERTO_SERVIDOR = "puertoServidor"; 
    public static final String TIEMPO_ESPERA_LARGA_ENCUESTA = 
        "tiempoEsperaLargaEncuesta";
    public static final String TIEMPO_ESPERA_SERVIDOR = 
        "tiempoEsperaServidor";
    public static final String TIEMPO_REINTENTO_CONEXION_SERVIDOR = 
        "tiempoReintentoConexionServidor";
    
    private String URLServidor = "155.210.71.106";
    private int puertoServidor = 15000;
    private int tiempoEsperaLargaEncuesta = 0;
    private int tiempoEsperaServidor = 1000;
    private int tiempoReintentoConexionServidor = 10000;

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
            configuracion = new Properties();
            configuracion.load(new FileInputStream(FICHERO_CONFIG));

            URLServidor = configuracion.getProperty(URL_SERVIDOR);
            puertoServidor = Integer.parseInt(
                configuracion.getProperty(PUERTO_SERVIDOR));
            tiempoEsperaLargaEncuesta = Integer.parseInt(
                configuracion.getProperty(TIEMPO_ESPERA_LARGA_ENCUESTA));
            tiempoEsperaServidor = Integer.parseInt(configuracion.getProperty(TIEMPO_ESPERA_SERVIDOR));
            tiempoReintentoConexionServidor = Integer.parseInt(configuracion.getProperty(TIEMPO_REINTENTO_CONEXION_SERVIDOR));

        } catch (Exception e) {
            configuracion.setProperty(URL_SERVIDOR, URLServidor);
            configuracion.setProperty(PUERTO_SERVIDOR, 
                Integer.toString(puertoServidor));
            configuracion.setProperty(TIEMPO_ESPERA_LARGA_ENCUESTA, 
                Integer.toString(tiempoEsperaLargaEncuesta));
            configuracion.setProperty(TIEMPO_ESPERA_SERVIDOR, 
                Integer.toString(tiempoEsperaServidor));
            configuracion.setProperty(TIEMPO_REINTENTO_CONEXION_SERVIDOR, 
                Integer.toString(tiempoReintentoConexionServidor));
            
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
            configuracion.store(fichero, COMENTARIO_CONFIG);
            fichero.close();
        } catch(IOException e) {
            e.printStackTrace();
        } 
    }

    /**
     * Obtiene la url del servidor
     * 
     * @return String
     */
    public String getURLServidor() {
        return URLServidor;
    }

    /**
     * Asigna la url del servidor
     * 
     * @param _URLServidor
     */
    public void setURLServidor(String _URLServidor) {
        this.URLServidor = _URLServidor;
    }

    /**
     * Obtiene el puerto del servidor
     * 
     * @return int
     */
    public int getPuertoServidor() {
        return puertoServidor;
    }

    /**
     * Asigna el puerto del servidor
     * 
     * @param _puertoServidor
     */
    public void setPuertoServidor(int _puertoServidor) {
        this.puertoServidor = _puertoServidor;
    } 

    /**
     * Obtiene el tiempo de espera de larga encuesta
     * 
     * @return int
     */
    public int getTiempoEsperaLargaEncuesta() {
        return tiempoEsperaLargaEncuesta;
    }

    /**
     * Asigna el tiempo de espera de larga encuesta
     * 
     * @param _tiempoEsperaLargaEncuesta
     */
    public void setTiempoEsperaLargaEncuesta(int _tiempoEsperaLargaEncuesta) {
        this.tiempoEsperaLargaEncuesta = _tiempoEsperaLargaEncuesta;
    }

    /**
     * Obtiene el tiempo de espera del servidor
     * 
     * @return int
     */
    public int getTiempoEsperaServidor() {
        return tiempoEsperaServidor;
    }

    /**
     * Asigna el tiempo de espera del servidor
     * 
     * @param _tiempoEsperaServidor
     */
    public void setTiempoEsperaServidor(int _tiempoEsperaServidor) {
        this.tiempoEsperaServidor = _tiempoEsperaServidor;
    }

    /**
     * Obtiene el tiempo de reintento de conexion del servidor
     * 
     * @return int
     */
    public int getTiempoReintentoConexionServidor() {
        return tiempoReintentoConexionServidor;
    }

    /**
     * Asigna el tiempo de reintento de conexión del servidor
     * 
     * @param _tiempoReintentoConexionServidor
     */
    public void setTiempoReintentoConexionServidor(int _tiempoReintentoConexionServidor) {
        this.tiempoReintentoConexionServidor = _tiempoReintentoConexionServidor;
    } 
}
