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
import java.sql.SQLException;
import javafx.beans.value.ObservableListValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Usuario
 */
public class crtTipoHabitacion {
    public ObservableList<String> obtenerTiposHabitacion() {
        ObservableList<String> lista = FXCollections.observableArrayList();
        String sql = "SELECT descripcionhabitacion FROM tipohabitacion";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(rs.getString("descripcionhabitacion"));
            }
            System.out.println("se mostro");
            con.close();
        } catch (SQLException e) {
            System.out.println("no se mostro");
        }

        return lista;
    }
    
    public static double obtenerPrecioHabitacion(int idHabitacion) {
    double precio = 0.0;
    String sql = "SELECT precio FROM tipohabitacion WHERE idtipohabitacion = ?";

    try (Connection con = Conexion.conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, idHabitacion);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                precio = rs.getDouble("precio");
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return precio;
}

    public static int obtenerCantidadPersonas(int idHabitacion) {
    int cantidad = 0;
    String sql = "SELECT numpersonas FROM tipohabitacion WHERE idtipohabitacion = ?";

    try (Connection con = Conexion.conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, idHabitacion);

        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                cantidad = rs.getInt("numpersonas");
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return cantidad;
}

    public double obtenerPrecioPorId(int idTipoHabitacion) {
    double precio = 0.0;
    String sql = "SELECT precio FROM tipohabitacion WHERE idtipohabitacion = ?";
    
    try (Connection con = Conexion.conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {
        
        ps.setInt(1, idTipoHabitacion);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            precio = rs.getDouble("precio");
        }
        System.out.println("Precio obtenido: " + precio);
        con.close();
    } catch (SQLException e) {
        System.out.println("Error al obtener precio: " + e.getMessage());
    }
    
    return precio;
}
    
}

