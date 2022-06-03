/**
 * OyenteVista.java
 * Adnana Catrinel Dragut
 * v2.0 02/04/2022.  
 */
package control;


/**
 *  Interfaz de oyente para recibir eventos de la interfaz de usuario.
 * 
 */
public interface OyenteVista {
    public enum Evento {DAR_ALTA_SANITARIO, DAR_BAJA_SANITARIO, EDITAR_SANITARIO, 
        NUEVO_PACIENTE, NUEVO_EPISODIO, NUEVO_DIAGNOSTICO, NUEVA_CITA, 
        ELIMINAR_CITA, NUEVA_VACUNA, NUEVO_MEDICAMENTO, ELIMINAR_MEDICAMENTO, SALIR }
  
    /**
     *  Llamado para notificar un evento de la interfaz de usuario.
     * 
     */ 
    public void eventoProducido(Evento evento, Object obj);
}
