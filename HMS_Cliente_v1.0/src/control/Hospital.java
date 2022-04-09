/**
 * Hospital.java
 * Adnana Catrinel Dragut
 * v1.0 02/04/2022.
 * 
 */

package control;

import modelo.clasesProxys.Comms;
import modelo.clasesProxys.ProxySanitario;
import vista.EditarSanitarioVista;
import vista.NuevoSanitarioVista;
import vista.WelcomeVista;

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
    
    /**
     * Crea un Hospital.
     * 
     */
    public Hospital() {
        this.comms = new Comms();
        this.pxSanitario = new ProxySanitario();
        this.welcomeVista = new WelcomeVista(this, comms, pxSanitario);
        
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

            case SALIR:
                try {
                    comms.desconectar();
                    System.exit(0);
                } catch (Exception ex) {
                    welcomeVista.mensajeDialogo(Comms.ERROR_CONEXION); 
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
