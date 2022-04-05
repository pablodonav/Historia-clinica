/**
 * ServidorHospital.java
 * Pablo Doñate Navarro
 * v1.0 02/04/2022.
 */
package control;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.Socket;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import modelo.clasesDTOs.SanitarioDTO;
import modelo.clasesDTOs.UsuarioDTO;

/**
 * Servidor de Hospital.
 *   
 */
public class ServidorHospital implements Runnable {
    public static String ERROR_CONEXION_HOSPITAL = 
        "Closed hospital connection"; 
    private static String FORMATO_FECHA_CONEXION = 
        "kk:mm:ss EEE d MMM yy";
    
    private ServidorSanitarios servidorSanitarios;
    private Socket socket;
    private BufferedReader entrada; 
    private PrintWriter salida;
    private Gson gson;
    
    /**
     *  Construye el servidor de hospital.
     * 
     */
    ServidorHospital(ServidorSanitarios _servidorSanitarios,
            Socket _socket) throws IOException {
        this.servidorSanitarios = _servidorSanitarios;
        this.socket = _socket;  

        entrada = new BufferedReader(
            new InputStreamReader(socket.getInputStream()));

        // Autoflush!!
        salida = new PrintWriter(new BufferedWriter(
            new OutputStreamWriter(
                socket.getOutputStream())), true);  
        
        gson = new Gson();
    } 

    /**
     *  Atiende las solicitudes de un sanitario..
     * 
     */  
    @Override
    public void run() {
        try {        
            PrimitivaComunicacion solicitud = 
                PrimitivaComunicacion.nueva(
                    new Scanner(new StringReader(
                        entrada.readLine())));

            switch(solicitud) {
                case CONECTAR_PUSH:
                    conectarPushHospital();
                    break;

                case DESCONECTAR_PUSH:
                    desconectarPushHospital();
                    break;
                  
                case DAR_ALTA_SANITARIO:
                    darAltaSanitario();
                    break;
                    
                case VERIFICAR_USUARIO:
                    verificarUsuario();
                    break;
            }  
        } catch (IOException | InterruptedException | SQLException | InputMismatchException e) {
            System.out.println(ERROR_CONEXION_HOSPITAL +
                ": " + e.toString());       
        }
    }
    
    /**
     *  Conecta hospital push.
     * 
     */    
    private void conectarPushHospital() throws IOException,
        InterruptedException { 
        
        CountDownLatch cierreConexion = new CountDownLatch(1);
        String idConexion = 
            servidorSanitarios.generarIdConexionPushHospital();
        
        ConexionPushHospital conexionPushHospital = 
           new ConexionPushHospital(idConexion, socket, 
                cierreConexion);

        PrimitivaComunicacion respuesta = 
            conexionPushHospital.enviarSolicitud(
                PrimitivaComunicacion.NUEVO_ID_CONEXION,
                servidorSanitarios.TIEMPO_ESPERA_CLIENTE,
                idConexion);

        if (respuesta.equals(PrimitivaComunicacion.OK)) {    
            servidorSanitarios.nuevaConexionPushHospital(
                conexionPushHospital);

            // Esperamos hasta cierre conexión push agenda
            cierreConexion.await();
        } else {
            conexionPushHospital.cerrar();
        }
    }
    
    /**
     *  Desconecta hospital push.
     * 
     */    
    private void desconectarPushHospital() throws IOException {
        String idConexion = entrada.readLine();                     

        if (servidorSanitarios.eliminarConexionPushHospital(
                idConexion)) {
            salida.println(PrimitivaComunicacion.OK);
        } else {
            salida.println(PrimitivaComunicacion.NOK);
        }
        cerrarConexion();
    } 
    
    /**
     *  Da de alta un nuevo sanitario.
     *
     */
    private void darAltaSanitario() throws IOException, SQLException {
        PrimitivaComunicacion respuesta = PrimitivaComunicacion.NOK;
        
        String sanitarioJSON = entrada.readLine();
        SanitarioDTO sanitario = gson.fromJson(sanitarioJSON, SanitarioDTO.class);
        
        if (servidorSanitarios.añadirSanitario(sanitario)) {
            respuesta = PrimitivaComunicacion.OK;
        }
        
        salida.println(respuesta);
        
        cerrarConexion();
    }
    
    private void verificarUsuario() throws IOException, SQLException {
        PrimitivaComunicacion respuesta = PrimitivaComunicacion.NOK;
        UsuarioDTO usuarioRes;
        
        String usuarioJSON = entrada.readLine();
        UsuarioDTO usuario = gson.fromJson(usuarioJSON, UsuarioDTO.class);
        
        usuarioRes = servidorSanitarios.verificarUsuario(usuario);
        if (usuario != null) {
            salida.println(usuario);
        } else {
            salida.println(PrimitivaComunicacion.USUARIO_NO_ENCONTRADO);
        }
        
        cerrarConexion();
    }
    
    /**
     *  Cierra conexión.
     * 
     */
    private void cerrarConexion() throws IOException {
        entrada.close();
        salida.close();
        socket.close();      
    }
}
  
