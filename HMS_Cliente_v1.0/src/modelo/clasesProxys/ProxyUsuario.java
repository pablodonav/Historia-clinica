/**
 * ProxyUsuario.java
 * Adnana Catrinel Dragut
 * v1.0 28/03/2022.
 * 
 */

package modelo.clasesProxys;

import com.google.gson.Gson;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import modelo.clasesDTOs.UsuarioDTO;

/**
 * Clase que contiene los métodos para enviar solicitudes 
 * y obtener información del servidor relacionados con un usuario.
 * 
 */
public class ProxyUsuario extends Comms{

    /**
     * Crea ProxyUsuario.
     * 
     */
    public ProxyUsuario() {
        this.oyenteServidor = this;
        this.observadores = new PropertyChangeSupport(this); 
    }
    
    /**
     * Verifica si existe el usuario con las credenciales pasadas en formato
     * json, si existe el usuario devuelve un json con el tipo de usuario al 
     * que pertenecen las credenciales (admin o sanitario), sino existe 
     * devuelve null
     * 
     * @param _jsonUsuario
     * @return String
     * @throws Exception 
     */
    public String verificarUsuario(String _jsonUsuario) throws Exception{
        if (! conectado){
            return null;
        }
        
        List<String> resultados =  new ArrayList<>();
        PrimitivaComunicacion respuesta = cliente.enviarSolicitud(PrimitivaComunicacion.VERIFICAR_USUARIO, 
                                                                  tiempoEsperaServidor,
                                                                  _jsonUsuario,
                                                                  resultados);
        if (resultados.isEmpty() || 
                respuesta.equals(PrimitivaComunicacion.NOK.toString()) ||
                respuesta.equals(PrimitivaComunicacion.USUARIO_NO_ENCONTRADO.toString())){
            return null;
        } else {
            return resultados.get(0);
        }
    }
    
    public String verificarUsuarioTest(String _jsonUsuario) throws Exception{        
        String respuestaJson = "";
        Gson gson = new Gson();
        
        System.out.println(_jsonUsuario);
        UsuarioDTO userTest = gson.fromJson(_jsonUsuario, UsuarioDTO.class);
        userTest.setAdmin(false);
        
        respuestaJson = userTest.toJson();
        System.out.println(respuestaJson);

        if (respuestaJson.isEmpty()){
            return null;
        } else {
            return respuestaJson;
        }
    }
}
