/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.bd;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Lab3
 */
public class Conexion {
    public Conexion() {
    }
    
    public static Connection conectar() {
        String servidor="jdbc:mysql://localhost:3306/hotel_db";
        String usuario="root";
        String contrasenia="";
        try {
            Connection cn = (Connection) DriverManager.getConnection(servidor,usuario,contrasenia);
            System.out.println("Conexion exitosa");
            
            return cn;
        } catch (SQLException e) {
            System.out.println("Error de conexión " + e);
        }
        return null;
    }
}
