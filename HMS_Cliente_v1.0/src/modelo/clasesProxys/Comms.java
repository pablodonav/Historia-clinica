/**
 * Comms.java
 * Pablo Doñate y Adnana Dragut (05/2021). 
 *   
 */

package modelo.clasesProxys;

import com.google.gson.Gson;
import control.Config;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.List;
import modelo.clasesDTOs.EpisodioDeAtencionDTO;
import modelo.clasesDTOs.SanitarioDTO;

/**
 * Clase que contiene los métodos para conectarse-desconectarse
 * del servidor y los métodos para capturar las solicitudes enviadas por el mismo.
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
    public static final String ERROR_DESCONEXION = 
            "No se ha podido realizar la desconexión del servidor.";

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
     * Desconecta del servidor.
     * 
     * @throws Exception 
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
     * Añade nuevo observador de las mesas.
     * 
     * @param _observador 
     */     
    public void nuevoObservador(PropertyChangeListener _observador) {
        this.observadores.addPropertyChangeListener(_observador);
    } 
    
    /**
     * Eliminar observador de las mesas.
     * 
     * @param _observador 
     */     
    public void eliminarObservador(PropertyChangeListener _observador) {
        this.observadores.removePropertyChangeListener(_observador);
    } 
    
    /**
     * Recibe solicitud del servidor de nuevo idConexion.
     * 
     * @param _propiedad
     * @param _resultados
     * @return
     * @throws IOException 
     */
    private boolean solicitudServidorNuevoIdConexion(
            String _propiedad, List<String> _resultados) throws IOException {
        idConexion = _resultados.get(0);
        
        if (idConexion == null) {
            return false;
        }
    
        conectado = true; 
    
        observadores.firePropertyChange(_propiedad, null, idConexion);  
        return true;
    }
    
    /**
     * Recibe del servidor el resultado de dar de alta
     * un nuevo sanitario.
     * 
     * @param _propiedad
     * @param _resultados
     * @return
     * @throws IOException 
     */
    private boolean solicitudServidorDarAltaSanitario(
            String _propiedad, List<String> _resultados)
            throws IOException {
        String sanitarioJsonToReceive = _resultados.get(0);
        
        if (sanitarioJsonToReceive == null ||
            sanitarioJsonToReceive.isBlank() ||
            sanitarioJsonToReceive.isEmpty()) {
            
            return false;
        }
        observadores.firePropertyChange(_propiedad, null, sanitarioJsonToReceive);  
        return true;
    }
    
    /**
     * Recibe del servidor el resultado de dar de baja
     * un sanitario existente en el sistema.
     * 
     * @param _propiedad
     * @param _resultados
     * @return
     * @throws IOException 
     */
    private boolean solicitudServidorDarBajaSanitario(
            String _propiedad, List<String> _resultados)
            throws IOException {
        String dniSanitario = _resultados.get(0);
        
        if (dniSanitario == null ||
            dniSanitario.isBlank() ||
            dniSanitario.isEmpty()) {
            
            return false;
        }
        observadores.firePropertyChange(_propiedad, null, dniSanitario);  
        return true;
    }
    
    /**
     *  Recibe del servidor el resultado de editar
     *  un sanitario existente en el sistema.
     * 
     * @param _propiedad
     * @param _resultados
     * @return
     * @throws IOException 
     */
    private boolean solicitudServidorEditarSanitario(
            String _propiedad, List<String> _resultados)
            throws IOException {
        String sanitarioJsonToReceive = _resultados.get(0);
        
        if (sanitarioJsonToReceive == null ||
            sanitarioJsonToReceive.isBlank() ||
            sanitarioJsonToReceive.isEmpty()) {
            
            return false;
        }
        observadores.firePropertyChange(_propiedad, null, sanitarioJsonToReceive);  
        return true;
    }
    
    /**
     * Recibe del servidor el resultado de añadir un nuevo paciente.
     * 
     * @param _propiedad
     * @param _resultados
     * @return
     * @throws IOException 
     */
    private boolean solicitudServidorNuevoPaciente(
            String _propiedad, List<String> _resultados)
            throws IOException {
        String pacienteJsonToReceive = _resultados.get(0);
        
        if (pacienteJsonToReceive == null ||
            pacienteJsonToReceive.isBlank() ||
            pacienteJsonToReceive.isEmpty()) {
            
            return false;
        }
        observadores.firePropertyChange(_propiedad, null, pacienteJsonToReceive);  
        return true;
    }
    
    /**
     * Recibe del servidor el resultado de añadir un nuevo episodio.
     * 
     * @param _propiedad
     * @param _resultados
     * @return
     * @throws IOException 
     */
    private boolean solicitudServidorNuevoEpisodio(
            String _propiedad, List<String> _resultados)
            throws IOException {
        String episodioJsonToReceive = _resultados.get(0);
        
        if (episodioJsonToReceive == null ||
            episodioJsonToReceive.isBlank() ||
            episodioJsonToReceive.isEmpty()) {
            
            return false;
        }

        observadores.firePropertyChange(_propiedad, null, episodioJsonToReceive);  
        return true;
    }
    
    /**
     * Recibe del servidor el resultado de añadir un nuevo diagnostico 
     * a un episodio de un paciente.
     * 
     * @param _propiedad
     * @param _resultados
     * @return
     * @throws IOException 
     */
    private boolean solicitudServidorNuevoDiagnostico(
            String _propiedad, List<String> _resultados)
            throws IOException {
        String episodioConDiagnosticoJsonToReceive = _resultados.get(0);
        
        if (episodioConDiagnosticoJsonToReceive == null ||
            episodioConDiagnosticoJsonToReceive.isBlank() ||
            episodioConDiagnosticoJsonToReceive.isEmpty()) {
            
            return false;
        }

        observadores.firePropertyChange(_propiedad, null, episodioConDiagnosticoJsonToReceive);  
        return true;
    }
    
    /**
     * Recibe del servidor el resultado de añadir una nueva cita 
     * a un paciente.
     * 
     * @param _propiedad
     * @param _resultados
     * @return
     * @throws IOException 
     */
    private boolean solicitudServidorNuevaCita(
            String _propiedad, List<String> _resultados)
            throws IOException {
        String citaJsonToReceive = _resultados.get(0);
        
        if (citaJsonToReceive == null ||
            citaJsonToReceive.isBlank() ||
            citaJsonToReceive.isEmpty()) {
            
            return false;
        }

        observadores.firePropertyChange(_propiedad, null, citaJsonToReceive);  
        return true;
    }
    
    /**
     * Recibe del servidor el resultado de eliminar una cita 
     * de un paciente.
     * 
     * @param _propiedad
     * @param _resultados
     * @return
     * @throws IOException 
     */
    private boolean solicitudServidorEliminarCita(
            String _propiedad, List<String> _resultados)
            throws IOException {
        String idCitaToReceive = _resultados.get(0);
        
        if (idCitaToReceive == null ||
            idCitaToReceive.isBlank() ||
            idCitaToReceive.isEmpty()) {
            
            return false;
        }

        observadores.firePropertyChange(_propiedad, null, idCitaToReceive);  
        return true;
    }
    
    /**
     * Recibe del servidor el resultado añadir una nueva vacuna
     * a un paciente.
     * 
     * @param _propiedad
     * @param _resultados
     * @return
     * @throws IOException 
     */
    private boolean solicitudServidorAnyadirVacunaPaciente(
            String _propiedad, List<String> _resultados)
            throws IOException {
        String vacunaJsonToReceive = _resultados.get(0);
        
        if (vacunaJsonToReceive == null ||
            vacunaJsonToReceive.isBlank() ||
            vacunaJsonToReceive.isEmpty()) {
            
            return false;
        }

        observadores.firePropertyChange(_propiedad, null, vacunaJsonToReceive);  
        return true;
    }
    
    /**
     * Recibe del servidor el resultado añadir un nuevo medicamento
     * a la receta de un paciente.
     * 
     * @param _propiedad
     * @param _resultados
     * @return
     * @throws IOException 
     */
    private boolean solicitudServidorAnyadirMedicamentoPaciente(
            String _propiedad, List<String> _resultados)
            throws IOException {
        String medicamentoJsonToReceive = _resultados.get(0);
        
        if (medicamentoJsonToReceive == null ||
            medicamentoJsonToReceive.isBlank() ||
            medicamentoJsonToReceive.isEmpty()) {
            
            return false;
        }

        observadores.firePropertyChange(_propiedad, null, medicamentoJsonToReceive);  
        return true;
    }
    
    /**
     * Recibe del servidor el resultado de eliminar un medicamento  
     * de la receta electrónica de un paciente.
     * 
     * @param _propiedad
     * @param _resultados
     * @return
     * @throws IOException 
     */
    private boolean solicitudServidorEliminarMedicamentoDePaciente(
            String _propiedad, List<String> _resultados)
            throws IOException {
        String idMedicamentoToReceive = _resultados.get(0);
        System.out.println(idMedicamentoToReceive);
        
        if (idMedicamentoToReceive == null ||
            idMedicamentoToReceive.isBlank() ||
            idMedicamentoToReceive.isEmpty()) {
            
            return false;
        }

        observadores.firePropertyChange(_propiedad, null, idMedicamentoToReceive);  
        return true;
    }
    
    public void darAltaSanitarioTest(String _jsonSanitario) throws Exception{               
        if (_jsonSanitario == null ||
            _jsonSanitario.isBlank() ||
            _jsonSanitario.isEmpty()) {
            return;
        }
        
        observadores.firePropertyChange(ProxySanitario.PROPIEDAD_DAR_ALTA_SANITARIO, null, _jsonSanitario);  
        return;
    }
    
    public void darBajaSanitarioTest(String _dniSanitario) throws Exception{        
            if (_dniSanitario == null ||
                _dniSanitario.isBlank() ||
                _dniSanitario.isEmpty()) {
                return;
            }

            System.out.println("Dni sanitario seleccionado: " + _dniSanitario);
            observadores.firePropertyChange(ProxySanitario.PROPIEDAD_DAR_BAJA_SANITARIO, null, _dniSanitario);  
            return;
    }
    
    public void editarSanitarioTest(String _jsonSanitario) throws Exception{        
            if (_jsonSanitario == null ||
                _jsonSanitario.isBlank() ||
                _jsonSanitario.isEmpty()) {
                return;
            }
            Gson gson = new Gson();
            SanitarioDTO userTest = gson.fromJson(_jsonSanitario, SanitarioDTO.class);
            
            System.out.println("Sanitario editado: " + userTest);
            observadores.firePropertyChange(ProxySanitario.PROPIEDAD_EDITAR_SANITARIO, null, _jsonSanitario);  
            return;
    }
    
    public void registrarNuevoPacienteTest(String _jsonPaciente) throws Exception{  
        if (_jsonPaciente == null ||
            _jsonPaciente.isBlank() ||
            _jsonPaciente.isEmpty()) {
            return;
        }
        
        System.out.println("jsonRecibido:" + _jsonPaciente);
        observadores.firePropertyChange(ProxyPaciente.PROPIEDAD_NUEVO_PACIENTE, null, _jsonPaciente);  
        return;
    }
    
    public void registrarNuevoEpisodioTest(String _jsonNuevoEpisodio, String idPaciente) throws Exception{  
        if (_jsonNuevoEpisodio == null ||
            _jsonNuevoEpisodio.isBlank() ||
            _jsonNuevoEpisodio.isEmpty()) {
            return;
        }
        
        System.out.println("jsonRecibido:" + _jsonNuevoEpisodio);
        Gson gson = new Gson();
        EpisodioDeAtencionDTO episodioDTO = gson.fromJson(_jsonNuevoEpisodio, EpisodioDeAtencionDTO.class);
        episodioDTO.setId(1);
        _jsonNuevoEpisodio = episodioDTO.toJson();
        
        observadores.firePropertyChange(ProxyEpisodio.PROPIEDAD_NUEVO_EPISODIO, null, _jsonNuevoEpisodio);  
        return;
    }
    
    public void anyadirDiagnosticoTest(String _jsonEpisodioConDiagnostico, String idPaciente) throws Exception{  
        if (_jsonEpisodioConDiagnostico == null ||
            _jsonEpisodioConDiagnostico.isBlank() ||
            _jsonEpisodioConDiagnostico.isEmpty()) {
            return;
        }
        
        System.out.println("jsonRecibido:" + _jsonEpisodioConDiagnostico);
        
        observadores.firePropertyChange(ProxyEpisodio.PROPIEDAD_NUEVO_DIAGNOSTICO, null, _jsonEpisodioConDiagnostico);  
        return;
    }
    
    public void anyadirCitaPacienteTest(String _jsonNuevaCita, String idPaciente) throws Exception{  
        if (_jsonNuevaCita == null ||
            _jsonNuevaCita.isBlank() ||
            _jsonNuevaCita.isEmpty()) {
            return;
        }
        
        System.out.println("jsonRecibido:" + _jsonNuevaCita);
        
        observadores.firePropertyChange(ProxyCitaPaciente.PROPIEDAD_NUEVA_CITA, null, _jsonNuevaCita);  
        return;
    }
    
    public void eliminarCitaPacienteTest(String _idCita, String _idPaciente) throws Exception{  
        if (_idCita == null ||
            _idCita.isBlank() ||
            _idCita.isEmpty()) {
            return;
        }
        
        System.out.println("idCita:" + _idCita);
        System.out.println("idPaciente:" + _idPaciente);
        
        observadores.firePropertyChange(ProxyCitaPaciente.PROPIEDAD_ELIMINAR_CITA, null, _idCita);  
        return;
    }
    
    public void anyadirVacunaPacienteTest(String _jsonNuevaVacuna, String idPaciente) throws Exception{  
        if (_jsonNuevaVacuna == null ||
            _jsonNuevaVacuna.isBlank() ||
            _jsonNuevaVacuna.isEmpty()) {
            return;
        }
        
        System.out.println("jsonRecibido:" + _jsonNuevaVacuna);
        
        observadores.firePropertyChange(ProxyVacuna.PROPIEDAD_NUEVA_VACUNA, null, _jsonNuevaVacuna);  
        return;
    }
    
    public void anyadirMedicamentoPacienteTest(String _jsonNuevoMedicamento, String idPaciente) throws Exception{  
        if (_jsonNuevoMedicamento == null ||
            _jsonNuevoMedicamento.isBlank() ||
            _jsonNuevoMedicamento.isEmpty()) {
            return;
        }
        
        System.out.println("jsonRecibido:" + _jsonNuevoMedicamento);
        
        observadores.firePropertyChange(ProxyMedicamento.PROPIEDAD_NUEVO_MEDICAMENTO, null, _jsonNuevoMedicamento);  
        return;
    }
    
    public void eliminarMedicamentoDePacienteTest(String _idMedicamento, String _idPaciente) throws Exception{  
        if (_idMedicamento == null ||
            _idMedicamento.isBlank() ||
            _idMedicamento.isEmpty()) {
            return;
        }
        
        System.out.println("codigoMedicamento:" + _idMedicamento);
        System.out.println("idPaciente:" + _idPaciente);
        
        observadores.firePropertyChange(ProxyMedicamento.PROPIEDAD_ELIMINAR_MEDICAMENTO, null, _idMedicamento);  
        return;
    }
    
    /**
     * Recibe solicitudes del servidor
     * 
     * @param _solicitud
     * @param _resultados
     * @return boolean
     * @throws IOException 
     */
    @Override
    public boolean solicitudServidorProducida(PrimitivaComunicacion _solicitud, 
            List<String> _resultados) throws IOException {
        if (_resultados.isEmpty()) {
            return false;
        } 
      
        switch(_solicitud) {
            case NUEVO_ID_CONEXION:
                return solicitudServidorNuevoIdConexion(PROPIEDAD_CONECTADO, _resultados);
            case DAR_ALTA_SANITARIO:
                return solicitudServidorDarAltaSanitario(
                    ProxySanitario.PROPIEDAD_DAR_ALTA_SANITARIO, _resultados);
            case DAR_BAJA_SANITARIO:
                return solicitudServidorDarBajaSanitario(
                    ProxySanitario.PROPIEDAD_DAR_BAJA_SANITARIO, _resultados);
            case EDITAR_SANITARIO:
                return solicitudServidorEditarSanitario(
                    ProxySanitario.PROPIEDAD_EDITAR_SANITARIO, _resultados);
            case NUEVO_PACIENTE:
                return solicitudServidorNuevoPaciente(
                    ProxyPaciente.PROPIEDAD_NUEVO_PACIENTE, _resultados);
            case NUEVO_EPISODIO:
                return solicitudServidorNuevoEpisodio(
                    ProxyEpisodio.PROPIEDAD_NUEVO_EPISODIO, _resultados);
            case NUEVO_DIAGNOSTICO:
                return solicitudServidorNuevoDiagnostico(
                    ProxyEpisodio.PROPIEDAD_NUEVO_DIAGNOSTICO, _resultados);
            case NUEVA_CITA:
                return solicitudServidorNuevaCita(
                    ProxyCitaPaciente.PROPIEDAD_NUEVA_CITA, _resultados);
            case ELIMINAR_CITA:
                return solicitudServidorEliminarCita(
                    ProxyCitaPaciente.PROPIEDAD_ELIMINAR_CITA, _resultados);
            case NUEVA_VACUNA_PACIENTE:
                return solicitudServidorAnyadirVacunaPaciente(
                    ProxyVacuna.PROPIEDAD_NUEVA_VACUNA, _resultados);     
            case NUEVO_MEDICAMENTO_PACIENTE:
                return solicitudServidorAnyadirMedicamentoPaciente(
                    ProxyMedicamento.PROPIEDAD_NUEVO_MEDICAMENTO, _resultados);
            case ELIMINAR_MEDICAMENTO_PACIENTE:
                return solicitudServidorEliminarMedicamentoDePaciente(
                    ProxyMedicamento.PROPIEDAD_ELIMINAR_MEDICAMENTO, _resultados);
            default:
                return false;
        }   
    }
}
