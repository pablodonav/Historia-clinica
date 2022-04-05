/**
 * Comms.java
 * Pablo Doñate y Adnana Dragut (05/2021). 
 *   
 */

package modelo.clasesProxys;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Clase que contiene los métodos enviar y recibir solicitudes del servidor.
 * 
 */
public class ConexionPushHospital {
    private String URLServidor;
    private int puertoServidor;

    private Socket socket;
    private BufferedReader entrada;
    private PrintWriter salida;

    /**
     * Construye cliente.
     * 
     * @param _URLServidor
     * @param _puertoServidor 
     */
    public ConexionPushHospital(String _URLServidor, int _puertoServidor) {
        this.URLServidor = _URLServidor;
        this.puertoServidor = _puertoServidor;
    }

    /**
     * Envía solicitud al servidor.
     * 
     * @param solicitud
     * @param tiempoEspera
     * @param parametros
     * @throws IOException 
     */
    private synchronized void enviar(
            PrimitivaComunicacion solicitud,
            int tiempoEspera,
            String parametros) throws IOException {

        socket = new Socket(URLServidor, puertoServidor);
        socket.setSoTimeout(tiempoEspera);

        entrada = new BufferedReader(new InputStreamReader(
                socket.getInputStream()));

        // Autoflush!!
        salida = new PrintWriter(new BufferedWriter(
                new OutputStreamWriter(
                        socket.getOutputStream())), true);

        System.out.println(solicitud);
        salida.println(solicitud.toString());

        if (parametros != null) {
            salida.println(parametros);
        }
    }

    /**
     * Recibe respuesta servidor.
     * 
     * @param resultados
     * @throws Exception 
     */
    private void recibirRespuestaServidor(List<String> resultados) 
            throws Exception {          
        String linea = "";
    
        while ((linea = entrada.readLine()) != null) {
            resultados.add(linea);   
        }    
    }
  
    /**
     * Recibe solicitud servidor.
     * 
     * @param resultados
     * @throws Exception 
     */ 
    private void recibirSolicitudServidor(List<String> resultados) 
            throws Exception {      
    
        String resultado = entrada.readLine();
    
        while (! resultado.equals(
                PrimitivaComunicacion.FIN.toString())) {        
            resultados.add(resultado);        
            resultado = entrada.readLine();
        }        
    }  

    /**
     * Recibe respuesta o solicitud del servidor.
     * 
     * @param resultados
     * @param solicitudServidor
     * @return PrimitivaComunicacion
     * @throws Exception 
     */
    private synchronized PrimitivaComunicacion recibir(
            List<String> resultados, boolean solicitudServidor)
            throws Exception {

        // Esperamos solicitud servidor
        PrimitivaComunicacion respuesta = PrimitivaComunicacion.nueva(
                new Scanner(new StringReader(entrada.readLine())));

        // Servidor envía test conexión
        if (respuesta == PrimitivaComunicacion.TEST) {
            entrada.readLine();                     // Salta FIN
            salida.println(PrimitivaComunicacion.OK);
            return respuesta;
        }
            
        // En el resto de casos recibimos, si los hay, 
        // resultados en líneas sig.    
        resultados.clear();

        if (!solicitudServidor) {
            recibirRespuestaServidor(resultados);
        } else {
            recibirSolicitudServidor(resultados);
        }

        salida.println(PrimitivaComunicacion.OK);

        return respuesta;
    }

    /**
     * Recibe respuesta del servidor.
     * 
     * @param resultados
     * @return PrimitivaComunicacion
     * @throws Exception 
     */
    private synchronized PrimitivaComunicacion recibir(
            List<String> resultados) throws Exception {
        return recibir(resultados, false);
    }

    /**
     * Envía una solicitud al servidor devolviendo con resultados.
     * 
     * @param solicitud
     * @param tiempoEspera
     * @param parametros
     * @param resultados
     * @return PrimitivaComunicacion
     * @throws Exception 
     */
    public synchronized PrimitivaComunicacion enviarSolicitud(
            PrimitivaComunicacion solicitud,
            int tiempoEspera,
            String parametros,  List<String> resultados)
            throws Exception {

        enviar(solicitud, tiempoEspera, parametros);

        PrimitivaComunicacion respuesta = recibir(resultados);

        entrada.close();
        salida.close();
        socket.close();

        return respuesta;
    }

    /**
     * Envía una solicitud al servidor sin devolver resultados.
     * 
     * @param solicitud
     * @param tiempoEspera
     * @param parametros
     * @return PrimitivaComunicacion
     * @throws Exception 
     */
    public synchronized PrimitivaComunicacion enviarSolicitud(
            PrimitivaComunicacion solicitud,
            int tiempoEspera,
            String parametros) throws Exception {

        return enviarSolicitud(solicitud, tiempoEspera,
                parametros, new ArrayList<String>());
    }

    /**
     * Envía una solicitud long polling al servidor.
     * 
     * @param solicitud
     * @param tiempoEspera
     * @param parametros
     * @param oyenteServidor
     * @throws Exception 
     */
    public synchronized void enviarSolicitudLongPolling(
            PrimitivaComunicacion solicitud,
            int tiempoEspera,
            String parametros,
            OyenteServidor oyenteServidor) throws Exception {

        enviar(solicitud, tiempoEspera, parametros);

        List<String> resultados = new ArrayList<>();

        do {
            PrimitivaComunicacion respuesta = recibir(
                    resultados, true);

            if (respuesta != PrimitivaComunicacion.TEST) {
                if (oyenteServidor.solicitudServidorProducida(
                        respuesta, resultados)) {
                    salida.println(PrimitivaComunicacion.OK);
                } else {
                    salida.println(PrimitivaComunicacion.NOK);
                }
            }
        } while (true);
    }
}
