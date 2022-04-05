/**
 * DataBase.java
 * Pablo Doñate Navarro
 * v1.0 02/04/2022.
 */
package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase que contiene los atributos de acceso
 * a la DB, y realiza la conexión.
 *
 */
public class DataBase {
    
    private Connection conexion = null;
    
    /**
     * Método que devuelve la conexión que realiza
     * con la DB.
     * 
     * @return 
     */
    public Connection MySQLConnect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String servidor = "jdbc:mysql://155.210.71.163:3306/historia_clinica";
            String usuario = "monty";
            String pass = "24292Bis-";
            
            // Se inicia la conexion
            conexion = DriverManager.getConnection(servidor, usuario, pass);
  
            return conexion;

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error en la conexión a la base de datos.");
            return null;
        }
    }
}