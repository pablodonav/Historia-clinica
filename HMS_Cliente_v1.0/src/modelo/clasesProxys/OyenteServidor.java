/**
 * OyenteServidor.java
 * Adnana Catrinel Dragut
 * v2.0 02/04/2022.
 *   
 */
package modelo.clasesProxys;

import java.io.IOException;
import java.util.List;

/**
 *  Interfaz de oyente para recibir solicitudes del servidor.
 * 
 */
public interface OyenteServidor {
   /**
    *  Llamado para notificar una solicitud del servidor.
    * 
    */ 
   public boolean solicitudServidorProducida(
           PrimitivaComunicacion solicitud, 
           List<String> resultados) throws IOException;
}