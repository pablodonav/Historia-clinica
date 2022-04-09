/**
 * Comms.java
 * Pablo Doñate y Adnana Dragut (05/2021). 
 *   
 */

package modelo.clasesProxys;

import control.Config;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.List;

/**
 * Clase que contiene los métodos para conectarse y desconectarse
 * del servidor.
 * 
 */
public class Comms implements OyenteServidor{
    private String URLservidor = "";
    private int puertoServidor = 0;
    private int tiempoEsperaLargaEncuesta = 0;
    protected int tiempoEsperaServidor = 0;
    private int tiempoReintentoConexionServidor = 0;
    
    protected OyenteServidor oyenteServidor;
    protected PropertyChangeSupport observadores;
        
    protected ConexionPushHospital cliente; 
    protected static boolean conectado = false;  
    private String idConexion;
    
    private Config configuracion;
    
    public static String PROPIEDAD_CONECTADO = "Conectar"; 
    
    /* Mensajes de Error */
    public static final String ERROR_CONEXION = 
            "No se ha podido conectar con el servidor.";

    /**
     * Crea Comms.
     * 
     */
    public Comms() {
        this.configuracion = Config.getInstance();
        this.oyenteServidor = this;
        this.observadores = new PropertyChangeSupport(this);    
        configuracion.load();
        this.URLservidor = configuracion.getURLServidor();
        this.puertoServidor = configuracion.getPuertoServidor();
        this.tiempoEsperaLargaEncuesta = configuracion.getTiempoEsperaLargaEncuesta();
        this.tiempoEsperaServidor = configuracion.getTiempoEsperaServidor();
        this.tiempoReintentoConexionServidor = configuracion.getTiempoReintentoConexionServidor();
        
        this.cliente = new ConexionPushHospital(URLservidor, puertoServidor);
    }
    
    /**
     * Conecta con servidor mediante long polling.
     *
     */
    public void conectar() {
        new Thread() {
            @Override
            public void run() {
                ConexionPushHospital cliente
                        = new ConexionPushHospital(URLservidor, puertoServidor);

                while (true) {
                    try {
                        cliente.enviarSolicitudLongPolling(
                                PrimitivaComunicacion.CONECTAR_PUSH,
                                tiempoEsperaLargaEncuesta,
                                null,
                                oyenteServidor);
                    } catch (Exception e) {
                        conectado = false;
                        observadores.firePropertyChange(
                            PROPIEDAD_CONECTADO, null, null);

                        // Volvemos a intentar conexión pasado un tiempo
                        try {
                            sleep(tiempoReintentoConexionServidor);
                        } catch (InterruptedException e2) {
                            // Propagamos a la máquina virtual
                            new RuntimeException();
                        }
                    }
                }
            }
        }.start();
    }
    
    /**
     *  Desconecta del servidor.
     * 
     */     
    public void desconectar() throws Exception {  
        if ( ! conectado) {
            return;
        }        
        configuracion.save();
        cliente.enviarSolicitud(PrimitivaComunicacion.DESCONECTAR_PUSH, 
            tiempoEsperaServidor, 
            idConexion);
    }

    /**
     *  Añade nuevo observador de las mesas.
     * 
     */     
    public void nuevoObservador(PropertyChangeListener observador) {
        this.observadores.addPropertyChangeListener(observador);
    } 
    
    /**
     *  Eliminar observador de las mesas.
     * 
     */     
    public void eliminarObservador(PropertyChangeListener observador) {
        this.observadores.removePropertyChangeListener(observador);
    } 
    
    /**
     * Recibe solicitud del servidor de nuevo idConexion.
     * 
     * @param resultados
     * @return boolean
     * @throws IOException 
     */
    private boolean solicitudServidorNuevoIdConexion(
            List<String> resultados) throws IOException {
        idConexion = resultados.get(0);
        
        if (idConexion == null) {
            return false;
        }
    
        conectado = true; 
    
        observadores.firePropertyChange(PROPIEDAD_CONECTADO, null, idConexion);  
        return true;
    }
    
    /**
     * Recibe solicitudes del servidor
     * 
     * @param solicitud
     * @param resultados
     * @return boolean
     * @throws IOException 
     */
    @Override
    public boolean solicitudServidorProducida(PrimitivaComunicacion solicitud, 
            List<String> resultados) throws IOException {
        if (resultados.isEmpty()) {
            return false;
        } 
      
        switch(solicitud) {
            case NUEVO_ID_CONEXION:
                return solicitudServidorNuevoIdConexion(resultados);
            default:
                return false;
        }   
    }

}
