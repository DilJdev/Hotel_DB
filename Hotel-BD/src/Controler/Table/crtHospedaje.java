/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controler.Table;

import Model.mdlHospedaje;
import com.mysql.jdbc.Connection;
import hotel.bd.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Usuario
 */
public class crtHospedaje {

public void añadirHospedaje(mdlHospedaje hospedaje) {
    String sql = "INSERT INTO hospedaje (fechaingreso, fechasalida, horaingreso, horasalida, importetotal, idpersonal, numhabitacion) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
    
    try (Connection con = Conexion.conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {
        
        ps.setDate(1, hospedaje.getFechaingreso());
        if (hospedaje.getFechasalida() != null) {
            ps.setDate(2, hospedaje.getFechasalida());
        } else {
            ps.setNull(2, java.sql.Types.DATE);
        }
        ps.setString(3, hospedaje.getHoraingreso());
        ps.setString(4, hospedaje.getHorasalida() != null ? hospedaje.getHorasalida() : null);
        ps.setDouble(5, hospedaje.getTotal());
        ps.setInt(6, hospedaje.getIdpersonal());
        ps.setInt(7, hospedaje.getIdnumh());
        
        int filas = ps.executeUpdate();
        if (filas > 0) {
            System.out.println("Hospedaje insertado correctamente");
        }
        
    } catch (SQLException e) {
        System.out.println("Error al insertar hospedaje: " + e.getMessage());
    }
}
    
    public int obtenerUltimoIdHospedaje() {
    int id = 0;
    String sql = "SELECT MAX(idhospedaje) as ultimo_id FROM hospedaje";
    
    try (Connection con = Conexion.conectar();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        
        if (rs.next()) {
            id = rs.getInt("ultimo_id");
        }
        
    } catch (SQLException e) {
        System.out.println("Error al obtener último ID: " + e.getMessage());
    }
    
    return id;
}

public void insertarHospedajeHuesped(int idHospedaje, int idHuesped) {
    String sql = "INSERT INTO hospedajehuesped (idhospedaje, idhuesped) VALUES (?, ?)";
    
    try (Connection con = Conexion.conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {
        
        ps.setInt(1, idHospedaje);
        ps.setInt(2, idHuesped);
        
        int filas = ps.executeUpdate();
        if (filas > 0) {
            System.out.println("Huesped-Hospedaje insertado correctamente");
        }
        
    } catch (SQLException e) {
        System.out.println("Error al insertar huesped-hospedaje: " + e.getMessage());
    }
}

}
