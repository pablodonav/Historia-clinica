/**
 * OyenteVista.java
 * Pablo Doñate y Adnana Dragut (05/2021). 
 *   
 */
package control;


/**
 *  Interfaz de oyente para recibir eventos de la interfaz de usuario.
 * 
 */
public interface OyenteVista {
    public enum Evento {DAR_ALTA_SANITARIO, DAR_BAJA_SANITARIO, EDITAR_SANITARIO, NUEVO_PACIENTE, NUEVO_EPISODIO, SALIR }
  
    /**
     *  Llamado para notificar un evento de la interfaz de usuario.
     * 
     */ 
    public void eventoProducido(Evento evento, Object obj);
}
