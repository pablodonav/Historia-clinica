/**
 * PrimitivaComunicacion.java
 * Pablo Doñate Navarro
 * v2.5 06/05/2022.
 */
package control;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Primitiva de comunicación cliente-servidor.
 * 
 * @author pablo
 */
public enum PrimitivaComunicacion {  
    CONECTAR_PUSH("connect"), 
    DESCONECTAR_PUSH("disconnect"), 
    TEST("test"),
    FIN("fin"),
    OK("ok"),
    NOK("nok"),
    DAR_ALTA_SANITARIO("new_health_worker"),
    NUEVO_ID_CONEXION("new_id_conection"),
    VERIFICAR_USUARIO("check_user"),
    USUARIO_NO_ENCONTRADO("not_found_user"),
    EDITAR_SANITARIO("edit_health_worker"),
    NUEVO_PACIENTE("new_patient"),
    OBTENER_SANITARIOS("get_health_workers"),
    DAR_BAJA_SANITARIO("remove_health_worker"),
    NUEVO_EPISODIO("new_episode"),
    NUEVA_CITA("new_medical_appointment"),
    OBTENER_PACIENTES("get_patients"),
    OBTENER_EPISODIOS_PACIENTE("get_patient_episodes"),
    NUEVO_DIAGNOSTICO("new_diagnosis"),
    NUEVO_MEDICAMENTO_PACIENTE("new_patient_medicine"),
    OBTENER_RECETA_PACIENTE("get_patient_medicines"),
    OBTENER_MEDICAMENTOS_DISPONIBLES("get_medicines"),
    OBTENER_CITAS_PACIENTE("get_medical_appointments"),
    ELIMINAR_CITA("remove_medical_appointment"),
    NUEVA_VACUNA_PACIENTE("new_patient_vaccine"),
    OBTENER_VACUNAS_PACIENTE("get_patient_vaccines"),
    OBTENER_VACUNAS_DISPONIBLES("get_vaccines");
    
    private String simbolo;

    /**
     * Construye una primitiva.
     * 
     * @param simbolo 
     */  
    PrimitivaComunicacion(String simbolo) {
        this.simbolo = simbolo;   
    }  

    /**
     * Obtiene sintaxis primitiva comunicación.
     * 
     * @return 
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
     * @param scanner
     * @return 
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
