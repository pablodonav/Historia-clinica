/**
 * ServidorHospital.java
 * Pablo Doñate Navarro
 * v2.5 06/05/2022.
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
import modelo.clasesDTOs.VacunaPacienteDTO;

/**
 * Servidor de Hospital.
 *   
 */
public class ServidorHospital implements Runnable {
    public static String ERROR_CONEXION_HOSPITAL = 
        "Closed hospital connection"; 
    private static String FORMATO_FECHA_CONEXION = 
        "kk:mm:ss EEE d MMM yy";
    private static String SOLICITUD = "Se ha recibido solicitud: ";
    
    private ServidorSanitarios servidorSanitarios;
    private Socket socket;
    private BufferedReader entrada; 
    private PrintWriter salida;
    private Gson gson;
    
    /**
     * Construye el servidor de hospital.
     * 
     * @param _servidorSanitarios
     * @param _socket
     * @throws IOException 
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
     * Atiende las solicitudes de un sanitario.
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
                    
                case OBTENER_MEDICAMENTOS_DISPONIBLES:
                    obtenerMedicamentosDisponibles();
                    break;
                    
                case OBTENER_CITAS_PACIENTE:
                    obtenerCitasPaciente();
                    break;
                    
                case ELIMINAR_CITA:
                    eliminarCitaPaciente();
                    break;
                    
                case NUEVA_VACUNA_PACIENTE:
                    nuevaVacunaPaciente();
                    break;
                    
                case OBTENER_VACUNAS_PACIENTE:
                    obtenerVacunasPaciente();
                    break;
                    
                case OBTENER_VACUNAS_DISPONIBLES:
                    obtenerVacunasDisponibles();
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
     * @param _primitivaComunicacion
     * @param _identificador
     * @throws IOException 
     */
    private void mostrarIdConexion(
            PrimitivaComunicacion _primitivaComunicacion, 
            String _identificador)throws IOException {        
        
        System.out.println(SOLICITUD + 
            _primitivaComunicacion.toString() + " " + 
            _identificador + "\n");
    }
    
    /**
     * Conecta hospital push.
     * 
     * @throws IOException
     * @throws InterruptedException 
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
     * Desconecta hospital push.
     * 
     * @throws IOException 
     */  
    private void desconectarPushHospital() throws IOException {
        String idConexion = entrada.readLine();    
        
        ConexionPushHospital conexionPushHospital = 
               servidorSanitarios.obtenerConexionPushHospital(
                    idConexion);
        
        if (servidorSanitarios.eliminarConexionPushHospital(
                idConexion)) {
            salida.println(PrimitivaComunicacion.OK);
        
            System.out.println(SOLICITUD +
                PrimitivaComunicacion.DESCONECTAR_PUSH + " " +
                conexionPushHospital.toString() + "\n");
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
        System.out.println(SOLICITUD + PrimitivaComunicacion.DAR_ALTA_SANITARIO);
        
        PrimitivaComunicacion respuesta = PrimitivaComunicacion.NOK;
        
        String sanitarioJSON = entrada.readLine();
        
        System.out.println("Info de entrada: " + sanitarioJSON);
        
        SanitarioDTO sanitario = gson.fromJson(sanitarioJSON, SanitarioDTO.class);
        
        if (servidorSanitarios.añadirSanitario(sanitario)) {
            respuesta = PrimitivaComunicacion.OK;
        }
        
        System.out.println("Salida: " + respuesta + "\n");
        
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
        System.out.println(SOLICITUD + PrimitivaComunicacion.DAR_BAJA_SANITARIO);
        
        PrimitivaComunicacion respuesta = PrimitivaComunicacion.NOK;
        
        String dni = entrada.readLine();
        
        System.out.println("Info de entrada: " + "Dni - " + dni);
        
        if (servidorSanitarios.eliminarSanitario(dni)) {
            respuesta = PrimitivaComunicacion.OK;
        }
        
        System.out.println("Salida: " + respuesta + "\n");
        
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
        System.out.println(SOLICITUD + PrimitivaComunicacion.EDITAR_SANITARIO);
        
        PrimitivaComunicacion respuesta = PrimitivaComunicacion.NOK;
        
        String sanitarioJSON = entrada.readLine();
        SanitarioDTO sanitario = gson.fromJson(sanitarioJSON, SanitarioDTO.class);
        
        System.out.println("Info de entrada: " + sanitarioJSON);
        
        if (servidorSanitarios.editarSanitario(sanitario)) {
            respuesta = PrimitivaComunicacion.OK;
        }
        
        System.out.println("Salida: " + respuesta + "\n");
        
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
        System.out.println(SOLICITUD + PrimitivaComunicacion.OBTENER_SANITARIOS);
        
        salida.println(PrimitivaComunicacion.OBTENER_SANITARIOS);  
        
        String sanitariosJSON = servidorSanitarios.obtenerSanitarios();
      
        if (sanitariosJSON != null) {
            salida.println(sanitariosJSON);
            System.out.println("Salida: " + sanitariosJSON + "\n");
        } 
        else {
            salida.println(PrimitivaComunicacion.NOK.toString());
            System.out.println("Salida: " + PrimitivaComunicacion.NOK.toString() + "\n");
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
        System.out.println(SOLICITUD + PrimitivaComunicacion.VERIFICAR_USUARIO);
        
        PrimitivaComunicacion respuesta = PrimitivaComunicacion.NOK;
        
        String usuarioJSON = entrada.readLine();
        UsuarioDTO usuario = gson.fromJson(usuarioJSON, UsuarioDTO.class);
        
        System.out.println("Info de entrada: " + usuarioJSON);
        
        UsuarioDTO usuarioRes = servidorSanitarios.verificarUsuario(usuario);
        
        if (usuarioRes != null) {
            salida.println(PrimitivaComunicacion.VERIFICAR_USUARIO.toString());
            salida.println(usuario.toJson());
            System.out.println("Salida: " + usuarioRes + "\n");
        } else {
            salida.println(PrimitivaComunicacion.USUARIO_NO_ENCONTRADO.toString());
            System.out.println("Salida: " + PrimitivaComunicacion.USUARIO_NO_ENCONTRADO.toString() + "\n");
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
        System.out.println(SOLICITUD + PrimitivaComunicacion.NUEVO_PACIENTE);
        
        PrimitivaComunicacion respuesta = PrimitivaComunicacion.NOK;
        
        String pacienteJSON = entrada.readLine();
        PacienteDTO paciente = gson.fromJson(pacienteJSON, PacienteDTO.class);
        
        System.out.println("Info de entrada: " + pacienteJSON);
        
        if (servidorSanitarios.añadirPaciente(paciente)) {
            respuesta = PrimitivaComunicacion.OK;
        }
        
        System.out.println(respuesta + "\n");
        
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
        System.out.println(SOLICITUD + PrimitivaComunicacion.NUEVO_EPISODIO);
        
        PrimitivaComunicacion respuesta = PrimitivaComunicacion.NOK;
        
        String episodioAtencionJSON = entrada.readLine();
        String nss_received = entrada.readLine();
        
        System.out.println("Info de entrada: " + episodioAtencionJSON + "Nss - " + nss_received);

        EpisodioAtencionDTO episodio = gson.fromJson(episodioAtencionJSON, EpisodioAtencionDTO.class);
        
        if (servidorSanitarios.nuevoEpisodio(episodio, nss_received)) {
            respuesta = PrimitivaComunicacion.OK;
        }
        
        System.out.println(respuesta + "\n");
        
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
        System.out.println(SOLICITUD + PrimitivaComunicacion.NUEVA_CITA);
        
        PrimitivaComunicacion respuesta = PrimitivaComunicacion.NOK;
        
        String citaJSON = entrada.readLine();
        String nss_pac = entrada.readLine();
        
        System.out.println("Info de entrada: " + citaJSON + "Nss - " + nss_pac);
        
        CitaDTO cita = gson.fromJson(citaJSON, CitaDTO.class);
        
        if (servidorSanitarios.nuevaCita(cita, nss_pac)) {
            respuesta = PrimitivaComunicacion.OK;
        }
        
        System.out.println(respuesta + "\n"); 
        
        salida.println(respuesta);
        
        cerrarConexion();
    }
    
    /**
     * Obtiene las citas de un paciente.
     * 
     * @throws IOException
     * @throws SQLException 
     */
    private void obtenerCitasPaciente() throws IOException, SQLException {
        System.out.println(SOLICITUD + PrimitivaComunicacion.OBTENER_CITAS_PACIENTE);
        
        salida.println(PrimitivaComunicacion.OBTENER_CITAS_PACIENTE);  
        
        String nss_pac = entrada.readLine();
        
        System.out.println("Info de entrada: Nss - " + nss_pac);
        
        String citasJSON = servidorSanitarios.obtenerCitasPaciente(nss_pac);
        
        if (citasJSON != null) {
            salida.println(citasJSON); 
            System.out.println("Salida: " + citasJSON + "\n");
        } 
        else {
            salida.println(PrimitivaComunicacion.NOK.toString());
            System.out.println("Salida: " + PrimitivaComunicacion.NOK.toString() + "\n");
        }
        
        cerrarConexion();  
    }
    
    /**
     * Elimina la cita de un paciente.
     * Si lo puede eliminar, devuelve OK.
     * En caso contrario, devuelve NOK.
     * 
     * @throws IOException
     * @throws SQLException 
     */
    private void eliminarCitaPaciente() throws IOException, SQLException {
        System.out.println(SOLICITUD + PrimitivaComunicacion.ELIMINAR_CITA);
        
        PrimitivaComunicacion respuesta = PrimitivaComunicacion.NOK;
        
        String idCita = entrada.readLine();
        String nss_pac = entrada.readLine();
        
        System.out.println("Info de entrada: Nss - " + nss_pac + " idCita - " + idCita);
        
        if (servidorSanitarios.eliminarCita(idCita)) {
            respuesta = PrimitivaComunicacion.OK;
        }
        
        System.out.println(respuesta + "\n");
        
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
        System.out.println(SOLICITUD + PrimitivaComunicacion.OBTENER_PACIENTES);
        
        salida.println(PrimitivaComunicacion.OBTENER_PACIENTES);  
        
        String pacientesJSON = servidorSanitarios.obtenerPacientes();
      
        if (pacientesJSON != null) {
            salida.println(pacientesJSON); 
            System.out.println("Salida: " + pacientesJSON + "\n");
        } 
        else {
            salida.println(PrimitivaComunicacion.NOK.toString());
            System.out.println("Salida: " + PrimitivaComunicacion.NOK.toString() + "\n");
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
        System.out.println(SOLICITUD + PrimitivaComunicacion.OBTENER_EPISODIOS_PACIENTE);
        
        salida.println(PrimitivaComunicacion.OBTENER_EPISODIOS_PACIENTE);
        
        String nss_pac = entrada.readLine();
        
        System.out.println("Info de entrada: Nss - " + nss_pac);
        
        String episodiosJSON = servidorSanitarios.obtenerEpisodiosPaciente(nss_pac);
        
        if (episodiosJSON != null) {
            salida.println(episodiosJSON); 
            System.out.println("Salida: " + episodiosJSON + "\n");
        } 
        else {
            salida.println(PrimitivaComunicacion.NOK.toString());
            System.out.println("Salida: " + PrimitivaComunicacion.NOK.toString() + "\n");
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
        System.out.println(SOLICITUD + PrimitivaComunicacion.NUEVO_DIAGNOSTICO);
        
        PrimitivaComunicacion respuesta = PrimitivaComunicacion.NOK;
        
        String episodioJSON = entrada.readLine();
        String nss = entrada.readLine();
        
        System.out.println("Info de entrada: " + episodioJSON + " Nss - " + nss);
        
        EpisodioAtencionDTO episodio = gson.fromJson(episodioJSON, EpisodioAtencionDTO.class);
        
        if (servidorSanitarios.registrarDiagnostico(episodio, nss)) {
            respuesta = PrimitivaComunicacion.OK;
        }
        
        System.out.println(respuesta + "\n");
        
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
        System.out.println(SOLICITUD + PrimitivaComunicacion.NUEVO_MEDICAMENTO_PACIENTE);
        
        PrimitivaComunicacion respuesta = PrimitivaComunicacion.NOK;
        
        String medicamentoPacienteJSON = entrada.readLine();
        
        MedicamentoPacienteDTO medicamento = gson.fromJson(medicamentoPacienteJSON, MedicamentoPacienteDTO.class);
        
        if (servidorSanitarios.añadirMedicamento(medicamento)) {
            respuesta = PrimitivaComunicacion.OK;
        }
        
        System.out.println(respuesta + "\n");
        
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
        System.out.println(SOLICITUD + PrimitivaComunicacion.OBTENER_RECETA_PACIENTE);
        
        salida.println(PrimitivaComunicacion.OBTENER_RECETA_PACIENTE);
        
        String nss_pac = entrada.readLine();
        
        String recetaJSON = servidorSanitarios.obtenerRecetaPaciente(nss_pac);
        
        if (recetaJSON != null) {
            salida.println(recetaJSON); 
            System.out.println("Salida: " + recetaJSON + "\n");
        } 
        else {
            salida.println(PrimitivaComunicacion.NOK.toString());
            System.out.println("Salida: " + PrimitivaComunicacion.NOK.toString() + "\n");
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
        System.out.println(SOLICITUD + PrimitivaComunicacion.OBTENER_MEDICAMENTOS_DISPONIBLES);
        
        salida.println(PrimitivaComunicacion.OBTENER_MEDICAMENTOS_DISPONIBLES);
        
        String medicamentosJSON = servidorSanitarios.obtenerMedicamentosDisponibles();
        
        if (medicamentosJSON != null) {
            salida.println(medicamentosJSON); 
            System.out.println("Salida: " + medicamentosJSON + "\n");
        } 
        else {
            salida.println(PrimitivaComunicacion.NOK.toString());
            System.out.println("Salida: " + PrimitivaComunicacion.NOK.toString() + "\n");
        }
        
        cerrarConexion();    
    }
    
    /**
     * Recibe la vacuna, y 
     * añade esa vacuna a la historia
     * del paciente asociado.
     * 
     * @throws IOException
     * @throws SQLException 
     */
    private void nuevaVacunaPaciente() throws IOException, SQLException {
        System.out.println(SOLICITUD + PrimitivaComunicacion.NUEVA_VACUNA_PACIENTE);
        
        PrimitivaComunicacion respuesta = PrimitivaComunicacion.NOK;
        
        String vacunaPacienteJSON = entrada.readLine();
        
        VacunaPacienteDTO vacuna = gson.fromJson(vacunaPacienteJSON, VacunaPacienteDTO.class);
        
        if (servidorSanitarios.añadirVacuna(vacuna)) {
            respuesta = PrimitivaComunicacion.OK;
        }
        
        System.out.println(respuesta + "\n");
        
        salida.println(respuesta);
        
        cerrarConexion();  
    }
    
    /**
     * Método que obtiene la historia de vacunas de un paciente.
     * Si no encuentra, devuelve NOK.
     * En caso contrario, los devuelve.
     * 
     * @throws IOException
     * @throws SQLException 
     */
    private void obtenerVacunasPaciente() throws IOException, SQLException {
        System.out.println(SOLICITUD + PrimitivaComunicacion.OBTENER_VACUNAS_PACIENTE);
        
        salida.println(PrimitivaComunicacion.OBTENER_VACUNAS_PACIENTE);
        
        String nss_pac = entrada.readLine();
        
        String vacunasJSON = servidorSanitarios.obtenerVacunasPaciente(nss_pac);
        
        if (vacunasJSON != null) {
            salida.println(vacunasJSON); 
            System.out.println("Salida: " + vacunasJSON + "\n");
        } 
        else {
            salida.println(PrimitivaComunicacion.NOK.toString());
            System.out.println("Salida: " + PrimitivaComunicacion.NOK.toString() + "\n");
        }
        
        cerrarConexion();    
    }
    
    /**
     * Método que obtiene la lista de vacunas disponibles.
     * Si no encuentra, devuelve NOK.
     * En caso contrario, los devuelve.
     * 
     * @throws IOException
     * @throws SQLException 
     */
    private void obtenerVacunasDisponibles()throws IOException, SQLException {
        System.out.println(SOLICITUD + PrimitivaComunicacion.OBTENER_VACUNAS_DISPONIBLES);
        
        salida.println(PrimitivaComunicacion.OBTENER_VACUNAS_DISPONIBLES);
        
        String vacunasJSON = servidorSanitarios.obtenerVacunasDisponibles();
        
        if (vacunasJSON != null) {
            salida.println(vacunasJSON); 
            System.out.println("Salida: " + vacunasJSON + "\n");
        } 
        else {
            salida.println(PrimitivaComunicacion.NOK.toString());
            System.out.println("Salida: " + PrimitivaComunicacion.NOK.toString() + "\n");
        }
        
        cerrarConexion();    
    }
    
    /**
     * Cierra conexión.
     * 
     * @throws IOException 
     */
    private void cerrarConexion() throws IOException {
        entrada.close();
        salida.close();
        socket.close();      
    }
}
  
