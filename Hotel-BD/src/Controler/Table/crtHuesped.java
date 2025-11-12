/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controler.Table;

import com.mysql.jdbc.Connection;
import hotel.bd.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Usuario
 */
public class crtHuesped {
    public int contarTotal(){
        int total=0;
        String sql="SELECT COUNT(nombre)AS total FROM huesped";
        try {
            Connection con=Conexion.conectar();
            PreparedStatement ps =con.prepareStatement(sql);
            ResultSet rs= ps.executeQuery();
            while(rs.next()){
                total=rs.getInt("total");
            }
            System.out.println("correcto");
            con.close();
            
        } catch (Exception e) {
            System.out.println("error al contar");
        }
        return total;
    }
}
