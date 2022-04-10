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
import modelo.clasesDTOs.PacienteDTO;
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
    private static String SOLICITUD = "Request:";
    
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
                    
                case EDITAR_SANITARIO:
                    editarSanitario();
                    break;
                    
                case NUEVO_PACIENTE:
                    añadirPaciente();
                    break;
                    
            }  
        } catch (IOException | InterruptedException | SQLException | InputMismatchException e) {
            System.out.println(ERROR_CONEXION_HOSPITAL +
                ": " + e.toString());       
        }
    }
    
    /**
     * Muestra el identificador de conexión de cada sanitario conectado.
     * 
     */
    private void mostrarIdConexion(
            PrimitivaComunicacion _primitivaComunicacion, 
            String _identificador)throws IOException {        
        
        System.out.println(SOLICITUD + " " + 
            _primitivaComunicacion.toString() + " " + 
            _identificador);
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
        
        mostrarIdConexion(PrimitivaComunicacion.CONECTAR_PUSH, 
            idConexion);
        
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
        
        ConexionPushHospital conexionPushHospital = 
               servidorSanitarios.obtenerConexionPushHospital(
                    idConexion);
        
        if (servidorSanitarios.eliminarConexionPushHospital(
                idConexion)) {
            salida.println(PrimitivaComunicacion.OK);
        
            System.out.println(SOLICITUD + " " +
                PrimitivaComunicacion.DESCONECTAR_PUSH + " " +
                conexionPushHospital.toString());
        } else {
            salida.println(PrimitivaComunicacion.NOK);
        }
        cerrarConexion();
    } 
    
    /**
     * Da de alta un nuevo sanitario.
     * 
     * @throws IOException
     * @throws SQLException 
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
    
    /**
     * Verifica si el login de un usuario es correcto.
     * 
     * @throws IOException
     * @throws SQLException 
     */
    private void verificarUsuario() throws IOException, SQLException {
        PrimitivaComunicacion respuesta = PrimitivaComunicacion.NOK;
        UsuarioDTO usuarioRes;
        
        String usuarioJSON = entrada.readLine();
        UsuarioDTO usuario = gson.fromJson(usuarioJSON, UsuarioDTO.class);
        
        usuarioRes = servidorSanitarios.verificarUsuario(usuario);
        
        if (usuarioRes != null) {
            salida.println(PrimitivaComunicacion.VERIFICAR_USUARIO.toString());
            salida.println(usuario.toJson());
        } else {
            salida.println(PrimitivaComunicacion.USUARIO_NO_ENCONTRADO.toString());
        }
        
        cerrarConexion();
    }
    
    /**
     * Recibe el sanitario a modificar, y realiza los cambios.
     * Si no se han podido modificar, notificará con un NOK.
     * En caso contrario, le mandará OK.
     * 
     * @throws IOException
     * @throws SQLException 
     */
    private void editarSanitario() throws IOException, SQLException {
        PrimitivaComunicacion respuesta = PrimitivaComunicacion.NOK;
        
        String sanitarioJSON = entrada.readLine();
        SanitarioDTO sanitario = gson.fromJson(sanitarioJSON, SanitarioDTO.class);
        
        if (servidorSanitarios.editarSanitario(sanitario)) {
            respuesta = PrimitivaComunicacion.OK;
        }
        
        salida.println(respuesta);
        
        cerrarConexion();
    }
    
    private void añadirPaciente() throws IOException, SQLException {
        PrimitivaComunicacion respuesta = PrimitivaComunicacion.NOK;
        
        String pacienteJSON = entrada.readLine();
        PacienteDTO paciente = gson.fromJson(pacienteJSON, PacienteDTO.class);
        
        if (servidorSanitarios.añadirPaciente(paciente)) {
            respuesta = PrimitivaComunicacion.OK;
        }
        
        salida.println(respuesta);
        
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
  