/**
 * PrimitivaComunicacion.java
 * Pablo Doñate y Adnana Dragut (05/2021). 
 *   
 */

package modelo.clasesProxys;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;


/**
 *  Primitiva de comunicación cliente-servidor.
 * 
 */
public enum PrimitivaComunicacion {  
    CONECTAR_PUSH("connect"), 
    DESCONECTAR_PUSH("disconnect"), 
    NUEVO_ID_CONEXION("new_id_conection"),
    TEST("test"),
    FIN("fin"),
    OK("ok"),
    NOK("nok"),
    VERIFICAR_USUARIO("check_user"),
    DAR_ALTA_SANITARIO("new_health_worker"),
    USUARIO_NO_ENCONTRADO("not_found_user"),
    OBTENER_SANITARIOS("get_health_workers"),
    EDITAR_SANITARIO("edit_health_worker"),
    DAR_BAJA_SANITARIO("remove_health_worker"),
    NUEVO_PACIENTE("new_patient"),
    NUEVO_EPISODIO("new_episode"),
    OBTENER_PACIENTES("get_patients");
    
    private String simbolo;

    /**
     *  Construye una primitiva.
     * 
     */   
    PrimitivaComunicacion(String simbolo) {
        this.simbolo = simbolo;   
    }  

    /**
     *  Obtiene sintaxis primitiva comunicación.
     *
     */  
    private static Pattern obtenerSintaxisPrimitiva() {
        String expresionRegular = "";
    
        for (PrimitivaComunicacion primitiva : 
            PrimitivaComunicacion.values()) {   
            expresionRegular = expresionRegular + 
                primitiva.toString() + "|";     
        }
    
        return Pattern.compile(expresionRegular);     
    } 
 
    /**
     *  Devuelve nueva primitiva de comunicación.
     *
     */  
    public static PrimitivaComunicacion nueva(Scanner scanner)
            throws InputMismatchException {
     
        String token = scanner.next(obtenerSintaxisPrimitiva());
   
        for (PrimitivaComunicacion primitiva : 
            PrimitivaComunicacion.values()) {
            if (token.equals(primitiva.toString())) {
                return primitiva;
            }
        }
        return NOK;
    }
  
    /**
     *  toString.
     *
     */  
    @Override
    public String toString() {
        return simbolo;    
    }
}
