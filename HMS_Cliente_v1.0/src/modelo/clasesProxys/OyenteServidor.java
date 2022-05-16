/**
 * OyenteServidor.java
 * Pablo Doñate y Adnana Dragut (05/2021). 
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