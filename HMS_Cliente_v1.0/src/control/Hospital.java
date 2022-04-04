/**
 * Hospital.java
 * Adnana Catrinel Dragut
 * v1.0 02/04/2022.
 * 
 */

package control;

import modelo.clasesProxys.Comms;
import modelo.clasesProxys.ProxySanitario;
import vista.LoginVista;

/**
 * Clase que recibe los eventos de vista y llama a los métodos 
 * correspondientes de modelo. 
 * 
 */
public class Hospital implements OyenteVista{
    public static String VERSION = "v3.1";
    public static String TITULO = "Hospital Management System";
    
    private Comms comms = null;
    private LoginVista loginVista;
    private ProxySanitario pxSanitario = null;
    
    /**
     * Crea un Hospital.
     * 
     */
    public Hospital() {
        this.comms = new Comms();
        this.pxSanitario = new ProxySanitario();
        this.loginVista = new LoginVista(this, pxSanitario);
        //comms.conectar();
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
                    ex.printStackTrace();
                }
                break;

            case SALIR:
                System.exit(0);
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
