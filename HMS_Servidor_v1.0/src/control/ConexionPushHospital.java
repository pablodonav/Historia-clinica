/**
 * ConexionPushHospital.java
 * Pablo Doñate Navarro
 * v1.0 29/04/2022.
 */
package control;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

 /**
  *  Conexión push con hospital.
  * 
  */
public class ConexionPushHospital {
    private String idConexion;  
    private Socket socket;
    private BufferedReader entrada;
    private PrintWriter salida;
    private CountDownLatch cierreConexion;
    
    /**
     *  Construye conexión push con hospital.
     * 
     */  
    ConexionPushHospital(String _idConexion, Socket _socket,
          CountDownLatch _cierreConexion) throws IOException {
        this.idConexion = _idConexion;
        this.socket = _socket;
        this.cierreConexion = _cierreConexion;

        entrada = new BufferedReader(
            new InputStreamReader(socket.getInputStream()));

        // Autoflush!!
        salida = new PrintWriter(new BufferedWriter(
            new OutputStreamWriter(socket.getOutputStream())), true); 
    }
    
    /**
     *  Obtiene identificador de conexión.
     * 
     */  
    String obtenerIdConexion() {
        return idConexion;
    }
    
    /**
     *  Envia solicitud.
     * 
     */
    private void enviar(PrimitivaComunicacion _solicitud,
            int _tiempoEspera, String _parametros) throws IOException {
        socket.setSoTimeout(_tiempoEspera);

        salida.println(_solicitud.toString());

        if (_parametros != null) {                 
            salida.println(_parametros);
        }

        salida.println(PrimitivaComunicacion.FIN);
    }
  
    /**
     *  Recibe respuesta.
     * 
     */
    private PrimitivaComunicacion recibir(List<String> _resultados) 
            throws IOException {
        PrimitivaComunicacion respuesta = PrimitivaComunicacion.NOK;

        String linea = entrada.readLine();
        if (linea != null) {
            respuesta = PrimitivaComunicacion.nueva(
                new Scanner(new StringReader(linea)));

            _resultados.clear();       

            while(entrada.ready()) {
                _resultados.add(entrada.readLine());   
            }      
        }
        
        return respuesta;     
    }
    
    /**
     *  Envía solicitud a hospital.
     * 
     */
    synchronized PrimitivaComunicacion enviarSolicitud(
        PrimitivaComunicacion _solicitud, int _tiempoEspera,
        String _parametros, List<String> _resultados) 
        throws IOException {
        
        enviar(_solicitud, _tiempoEspera, _parametros);
    
        return recibir(_resultados);
    }
  
    /**
     *  Envía solicitud a hospital sin esperar resultados.
     * 
     */
    synchronized PrimitivaComunicacion enviarSolicitud(
        PrimitivaComunicacion _solicitud, int _tiempoEspera,
        String _parametros) throws IOException {  
      
        return enviarSolicitud(_solicitud, 
            _tiempoEspera, _parametros, new ArrayList());
    }
    
    /**
     *  Envía solicitud a hospital sin parámetros y
     *  sin esperar resultados.
     * 
     */
    synchronized PrimitivaComunicacion enviarSolicitud(
        PrimitivaComunicacion _solicitud, int _tiempoEspera) 
        throws IOException {  
      
        return enviarSolicitud(_solicitud, 
            _tiempoEspera, null, new ArrayList());
    }
  
  
    /**
     *  Cierra la conexión.
     * 
     */  
    synchronized void cerrar() throws IOException {
        entrada.close();
        salida.close();
        socket.close(); 

        cierreConexion.countDown();
    }
  
    /**
     *  toString.
     * 
     */
    @Override
    public String toString() {
        return  idConexion;
    }
    
}
