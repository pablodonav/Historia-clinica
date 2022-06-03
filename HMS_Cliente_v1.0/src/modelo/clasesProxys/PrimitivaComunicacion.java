/**
 * PrimitivaComunicacion.java
 * Adnana Catrinel Dragut
 * v2.0 02/04/2022.
 *   
 */

package modelo.clasesProxys;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;


/**
 * Clase que contiene las primitivas de comunicación entre cliente-servidor.
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
    NUEVO_DIAGNOSTICO("new_diagnosis"),
    OBTENER_PACIENTES("get_patients"),
    OBTENER_EPISODIOS_PACIENTE("get_patient_episodes"),
    NUEVA_CITA("new_medical_appointment"),
    ELIMINAR_CITA("remove_medical_appointment"),
    OBTENER_CITAS_PACIENTE("get_medical_appointments"),
    NUEVA_VACUNA_PACIENTE("new_patient_vaccine"),
    OBTENER_VACUNAS_PACIENTE("get_patient_vaccines"),
    OBTENER_VACUNAS_DISPONIBLES("get_vaccines"),
    OBTENER_MEDICAMENTOS_DISPONIBLES("get_medicines"),
    NUEVO_MEDICAMENTO_PACIENTE("new_patient_medicine"),
    ELIMINAR_MEDICAMENTO_PACIENTE("remove_patient_medicine"),
    OBTENER_RECETA_PACIENTE("get_patient_medicines"),
    OBTENER_HISTORIA_PACIENTE("get_patient_medical_record");
    
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
