/**
 * Hospital.java
 * Adnana Catrinel Dragut
 * v1.0 02/04/2022.
 * 
 */

package control;

import modelo.clasesDTOs.Tupla;
import modelo.clasesProxys.Comms;
import modelo.clasesProxys.ProxyCitaPaciente;
import modelo.clasesProxys.ProxyEpisodio;
import modelo.clasesProxys.ProxyMedicamento;
import modelo.clasesProxys.ProxyPaciente;
import modelo.clasesProxys.ProxySanitario;
import modelo.clasesProxys.ProxyVacuna;
import vista.vistasUsuarioAdmin.EditarSanitarioVista;
import vista.vistasUsuarioSanitario.NuevoPacienteVista;
import vista.vistasUsuarioAdmin.NuevoSanitarioVista;
import vista.WelcomeVista;
import vista.vistasUsuarioSanitario.CitasPacienteVista;
import vista.vistasUsuarioSanitario.EpisodiosPacienteVista;
import vista.vistasUsuarioSanitario.NuevaCitaVista;
import vista.vistasUsuarioSanitario.NuevoEpisodioVista;
import vista.vistasUsuarioSanitario.RecetaElectronicaVista;
import vista.vistasUsuarioSanitario.RegistroVacunacionVista;

/**
 * Clase que recibe los eventos de vista y llama a los métodos 
 * correspondientes de modelo. 
 * 
 */
public class Hospital implements OyenteVista{
    public static String VERSION = "v3.1";
    public static String TITULO = "Hospital Management System";
    
    private Comms comms = null;
    private WelcomeVista welcomeVista;
    private ProxySanitario pxSanitario = null;
    private ProxyPaciente pxPaciente = null;
    private ProxyEpisodio pxEpisodio = null;
    private ProxyCitaPaciente pxCita = null;
    private ProxyVacuna pxVacuna = null;
    private ProxyMedicamento pxMedicamento = null;
    
    /**
     * Crea un Hospital.
     * 
     */
    public Hospital() {
        this.comms = new Comms();
        this.pxSanitario = ProxySanitario.getInstance();
        this.pxPaciente = ProxyPaciente.getInstance();
        this.pxEpisodio = ProxyEpisodio.getInstance();
        this.pxCita = ProxyCitaPaciente.getInstance();
        this.pxVacuna = ProxyVacuna.getInstance();
        this.pxMedicamento = ProxyMedicamento.getInstance();
        this.welcomeVista = new WelcomeVista(this, comms);
        
        comms.conectar();
    }
    
    /**
     * Recibe eventos de vista.
     * 
     */
    public void eventoProducido(Evento evento, Object obj) {
        switch(evento) {  
            case DAR_ALTA_SANITARIO:
                try {
                    pxSanitario.darAltaSanitario((String)obj);
                } catch (Exception ex) {
                    welcomeVista.mensajeDialogo(NuevoSanitarioVista.ERROR_DAR_ALTA_SANITARIO);   
                }
                break;
           
            case DAR_BAJA_SANITARIO:
                try {
                    pxSanitario.darBajaSanitario((String)obj);
                } catch (Exception ex) {
                    welcomeVista.mensajeDialogo(EditarSanitarioVista.ERROR_DAR_BAJA_SANITARIO); 
                }
                break;
                
            case EDITAR_SANITARIO:
                try {
                    pxSanitario.editarSanitario((String)obj);
                } catch (Exception ex) {
                    welcomeVista.mensajeDialogo(EditarSanitarioVista.ERROR_EDITAR_SANITARIO); 
                }
                break;
            
            case NUEVO_PACIENTE:
                try {
                    pxPaciente.registrarNuevoPaciente((String)obj);
                } catch (Exception ex) {
                    welcomeVista.mensajeDialogo(NuevoPacienteVista.ERROR_NUEVO_PACIENTE); 
                }
                break;
            
            case NUEVO_EPISODIO:
                try {
                    Tupla<String, String> tupla = (Tupla<String, String>)obj;
                    pxEpisodio.registrarNuevoEpisodio(tupla.a, tupla.b);
                } catch (Exception ex) {
                    welcomeVista.mensajeDialogo(NuevoEpisodioVista.ERROR_NUEVO_EPISODIO); 
                }
                break;
                
            case NUEVO_DIAGNOSTICO:
                try {
                    Tupla<String, String> tupla = (Tupla<String, String>)obj;
                    pxEpisodio.anyadirDiagnostico(tupla.a, tupla.b);
                } catch (Exception ex) {
                    welcomeVista.mensajeDialogo(EpisodiosPacienteVista.ERROR_EDITAR_EPISODIO); 
                }
                break;
                
            case NUEVA_CITA:
                try {
                    Tupla<String, String> tupla = (Tupla<String, String>)obj;
                    pxCita.anyadirCitaPacienteTest(tupla.a, tupla.b);
                } catch (Exception ex) {
                    welcomeVista.mensajeDialogo(NuevaCitaVista.ERROR_NUEVA_CITA); 
                }
                break;
                
            case ELIMINAR_CITA:
                try {
                    Tupla<String, String> tupla = (Tupla<String, String>)obj;
                    pxCita.eliminarCita(tupla.a, tupla.b);
                } catch (Exception ex) {
                    welcomeVista.mensajeDialogo(CitasPacienteVista.ERROR_ELIMINAR_CITA); 
                }
                break;
                
            case NUEVA_VACUNA:
                try {
                    Tupla<String, String> tupla = (Tupla<String, String>)obj;
                    pxVacuna.anyadirVacunaAPaciente(tupla.a, tupla.b);
                } catch (Exception ex) {
                    welcomeVista.mensajeDialogo(RegistroVacunacionVista.ERROR_NUEVA_VACUNA); 
                }
                break;
                
            case NUEVO_MEDICAMENTO:
                try {
                    Tupla<String, String> tupla = (Tupla<String, String>)obj;
                    pxMedicamento.anyadirMedicamentoAPaciente(tupla.a, tupla.b);
                } catch (Exception ex) {
                    welcomeVista.mensajeDialogo(RecetaElectronicaVista.ERROR_NUEVO_MEDICAMENTO); 
                }
                break;
                
            case ELIMINAR_MEDICAMENTO:
                try {
                    Tupla<String, String> tupla = (Tupla<String, String>)obj;
                    pxMedicamento.eliminarMedicamentoDePaciente(tupla.a, tupla.b);
                } catch (Exception ex) {
                    welcomeVista.mensajeDialogo(RecetaElectronicaVista.ERROR_ELIMINAR_MEDICAMENTO); 
                }
                break;

            case SALIR:
                try {
                    comms.desconectar();
                    System.exit(0);
                } catch (Exception ex) {
                    welcomeVista.mensajeDialogo(Comms.ERROR_DESCONEXION); 
                }
                break;
        }
    } 
        
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Hospital();   
    }
}
