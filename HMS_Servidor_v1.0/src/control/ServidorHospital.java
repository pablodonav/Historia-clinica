/**
 * ServidorHospital.java
 * Pablo Doñate Navarro
 * v1.0 29/04/2022.
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
import modelo.clasesDTOs.CitaDTO;
import modelo.clasesDTOs.EpisodioAtencionDTO;
import modelo.clasesDTOs.MedicamentoPacienteDTO;
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
                    
                case OBTENER_SANITARIOS:
                    obtenerSanitarios();
                    break;
                    
                case NUEVO_PACIENTE:
                    añadirPaciente();
                    break;
                    
                case DAR_BAJA_SANITARIO:
                    eliminarSanitario();
                    break;
                    
                case NUEVO_EPISODIO:
                    nuevoEpisodio();
                    break;
                    
                case NUEVA_CITA:
                    nuevaCita();
                    break;
                    
                case OBTENER_PACIENTES:
                    obtenerPacientes();
                    break;
                    
                case OBTENER_EPISODIOS_PACIENTE:
                    obtenerEpisodiosPaciente();
                    break;
                       
                case NUEVO_DIAGNOSTICO:
                    registrarDiagnostico();
                    break;
                    
                case NUEVO_MEDICAMENTO_PACIENTE:
                    nuevoMedicamentoPaciente();
                    break;
                    
                case OBTENER_RECETA_PACIENTE:
                    obtenerRecetaPaciente();
                    break;
                    
                case OBTENER_MEDICAMENTOS:
                    obtenerMedicamentosDisponibles();
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
     * Elimina el sanitario cuyo dni es el que se recibe desde el cliente.
     * Si no se elimina, devuelve NOK.
     * En caso contrario, devuelve OK.
     * 
     * @throws IOException
     * @throws SQLException 
     */
    private void eliminarSanitario() throws IOException, SQLException {
        PrimitivaComunicacion respuesta = PrimitivaComunicacion.NOK;
        
        String dni = entrada.readLine();
        
        if (servidorSanitarios.eliminarSanitario(dni)) {
            respuesta = PrimitivaComunicacion.OK;
        }
        
        salida.println(respuesta);
        
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
    
    /**
     * Se obtienen los sanitarios existentes.
     * Si no se han obtenido, devuelve NOK. 
     * En caso contrario, los devuelve.
     * 
     * @throws IOException
     * @throws SQLException 
     */
    private void obtenerSanitarios() throws IOException, SQLException {
        salida.println(PrimitivaComunicacion.OBTENER_SANITARIOS);  
        
        String sanitariosJSON = servidorSanitarios.obtenerSanitarios();
      
        if (sanitariosJSON != null) {
            salida.println(sanitariosJSON); 
        } 
        else {
            salida.println(PrimitivaComunicacion.NOK.toString());
        }
        
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
     * Añade un nuevo paciente en el sistema.
     * 
     * @throws IOException
     * @throws SQLException 
     */
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
     * Registra un nuevo episodio en el sistema.
     * 
     * @throws IOException
     * @throws SQLException 
     */
    private void nuevoEpisodio() throws IOException, SQLException {
        PrimitivaComunicacion respuesta = PrimitivaComunicacion.NOK;
        
        String episodioAtencionJSON = entrada.readLine();
        String nss_received = entrada.readLine();
        System.out.println(episodioAtencionJSON);
        EpisodioAtencionDTO episodio = gson.fromJson(episodioAtencionJSON, EpisodioAtencionDTO.class);
        
        if (servidorSanitarios.nuevoEpisodio(episodio, nss_received)) {
            respuesta = PrimitivaComunicacion.OK;
        }
        
        salida.println(respuesta);
        
        cerrarConexion();
    }
    
    /**
     * Crea una nueva cita asociada a un paciente y a un sanitario.
     * 
     * @throws IOException
     * @throws SQLException 
     */
    private void nuevaCita() throws IOException, SQLException {
        PrimitivaComunicacion respuesta = PrimitivaComunicacion.NOK;
        
        String citaJSON = entrada.readLine();
        CitaDTO cita = gson.fromJson(citaJSON, CitaDTO.class);
        
        if (servidorSanitarios.nuevaCita(cita)) {
            respuesta = PrimitivaComunicacion.OK;
        }
        
        salida.println(respuesta);
        
        cerrarConexion();
    }
    
    /**
     * Obtiene los pacientes del sistema.
     * Si no encuentra, devuelve NOK.
     * En caso contrario, devuelve OK.
     * 
     * @throws IOException
     * @throws SQLException 
     */
    private void obtenerPacientes() throws IOException, SQLException {
        salida.println(PrimitivaComunicacion.OBTENER_PACIENTES);  
        
        String pacientesJSON = servidorSanitarios.obtenerPacientes();
      
        if (pacientesJSON != null) {
            salida.println(pacientesJSON); 
        } 
        else {
            salida.println(PrimitivaComunicacion.NOK.toString());
        }
        
        cerrarConexion();    
    }
    
    /**
     * Método que obtiene los episodios de un paciente.
     * Si no encuentra, devuelve NOK.
     * En caso contrario, los devuelve.
     * 
     * @throws IOException
     * @throws SQLException 
     */
    private void obtenerEpisodiosPaciente() throws IOException, SQLException {
        salida.println(PrimitivaComunicacion.OBTENER_EPISODIOS_PACIENTE);
        
        String nss_pac = entrada.readLine();
        
        String episodiosJSON = servidorSanitarios.obtenerEpisodiosPaciente(nss_pac);
        
        if (episodiosJSON != null) {
            salida.println(episodiosJSON); 
        } 
        else {
            salida.println(PrimitivaComunicacion.NOK.toString());
        }
        
        cerrarConexion();    
    }
    
    /**
     * Recibe el episodio modificado y el nss del paciente y lo modifica en la DB.
     * Si no se han podido modificar, notificará con un NOK.
     * En caso contrario, le mandará OK.
     * 
     * @throws IOException
     * @throws SQLException 
     */
    private void registrarDiagnostico() throws IOException, SQLException {
        PrimitivaComunicacion respuesta = PrimitivaComunicacion.NOK;
        
        String episodioJSON = entrada.readLine();
        String nss = entrada.readLine();
        
        EpisodioAtencionDTO episodio = gson.fromJson(episodioJSON, EpisodioAtencionDTO.class);
        
        if (servidorSanitarios.registrarDiagnostico(episodio, nss)) {
            respuesta = PrimitivaComunicacion.OK;
        }
        
        salida.println(respuesta);
        
        cerrarConexion();  
    }
    
    /**
     * Recibe el medicamento, y 
     * añade ese medicamento a la receta
     * del paciente asociado.
     * 
     * @throws IOException
     * @throws SQLException 
     */
    private void nuevoMedicamentoPaciente() throws IOException, SQLException {
        PrimitivaComunicacion respuesta = PrimitivaComunicacion.NOK;
        
        String medicamentoPacienteJSON = entrada.readLine();
        
        MedicamentoPacienteDTO medicamento = gson.fromJson(medicamentoPacienteJSON, MedicamentoPacienteDTO.class);
        
        if (servidorSanitarios.añadirMedicamento(medicamento)) {
            respuesta = PrimitivaComunicacion.OK;
        }
        
        salida.println(respuesta);
        
        cerrarConexion();  
    }
    
    /**
     * Método que obtiene la receta de un paciente.
     * Si no encuentra, devuelve NOK.
     * En caso contrario, los devuelve.
     * 
     * @throws IOException
     * @throws SQLException 
     */
    private void obtenerRecetaPaciente() throws IOException, SQLException {
        salida.println(PrimitivaComunicacion.OBTENER_RECETA_PACIENTE);
        
        String nss_pac = entrada.readLine();
        
        String recetaJSON = servidorSanitarios.obtenerRecetaPaciente(nss_pac);
        
        if (recetaJSON != null) {
            salida.println(recetaJSON); 
        } 
        else {
            salida.println(PrimitivaComunicacion.NOK.toString());
        }
        
        cerrarConexion();    
    }
    
    /**
     * Método que obtiene la lista de medicamentos disponibles.
     * Si no encuentra, devuelve NOK.
     * En caso contrario, los devuelve.
     * 
     * @throws IOException
     * @throws SQLException 
     */
    private void obtenerMedicamentosDisponibles()throws IOException, SQLException {
        salida.println(PrimitivaComunicacion.OBTENER_MEDICAMENTOS);
        
        String medicamentosJSON = servidorSanitarios.obtenerMedicamentosDisponibles();
        
        if (medicamentosJSON != null) {
            salida.println(medicamentosJSON); 
        } 
        else {
            salida.println(PrimitivaComunicacion.NOK.toString());
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
  
