/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controler.Table;

import Model.mdlHabitacion;
import com.mysql.jdbc.Connection;
import hotel.bd.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 *
 * @author Usuario
 */
public class crtHabitacion {

    public static boolean insertarHabitacion(int numHabitacion, mdlHabitacion habitacion) {
        String sql = "INSERT INTO habitacion (numhabitacion, estado, idtipohabitacion) VALUES (?, ?, ?)";
        boolean corect = false;
        try (Connection con = Conexion.conectar();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, numHabitacion);                     // Número de habitación
            ps.setString(2, habitacion.getEstado());        // Estado desde el objeto mdlHabitacion
            ps.setInt(3, habitacion.getIdtipohabitacion()); // Id del tipo de habitación

            int filas = ps.executeUpdate();
            return corect;

        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
        return corect;
    }

    public static ObservableList<mdlHabitacion> obtenerHabitaciones() {
        ObservableList<mdlHabitacion> lista = FXCollections.observableArrayList();
        String sql = "SELECT numhabitacion, estado, idtipohabitacion FROM habitacion";

        try (Connection con = Conexion.conectar();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                mdlHabitacion habitacion = new mdlHabitacion(); // Constructor vacío

                // Asignamos los valores usando setters
                habitacion.setNumhabitacion(rs.getInt("numhabitacion"));
                habitacion.setEstado(rs.getString("estado"));
                habitacion.setIdtipohabitacion(rs.getInt("idtipohabitacion"));

                lista.add(habitacion); // Agregamos el objeto a la lista
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista; // Devolvemos el ObservableList
    }

    public static ObservableList<mdlHabitacion> obtenerHabitacionesPorTipo(int idTipo) {
        ObservableList<mdlHabitacion> lista = FXCollections.observableArrayList();
        String sql = "SELECT numhabitacion, estado, idtipohabitacion "
                + "FROM habitacion "
                + "WHERE idtipohabitacion = ?";

        try (Connection con = Conexion.conectar();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idTipo); // Parámetro exacto

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    mdlHabitacion habitacion = new mdlHabitacion(); // Constructor vacío

                    habitacion.setNumhabitacion(rs.getInt("numhabitacion"));
                    habitacion.setEstado(rs.getString("estado"));
                    habitacion.setIdtipohabitacion(rs.getInt("idtipohabitacion"));

                    lista.add(habitacion);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista; // ObservableList filtrado
    }

    public static ObservableList<Integer> obtenerNumerosHabitacionPorTipo(int idTipo) {
        ObservableList<Integer> lista = FXCollections.observableArrayList();
        String sql = "SELECT numhabitacion FROM habitacion WHERE idtipohabitacion = ?";

        try (Connection con = Conexion.conectar();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idTipo);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(rs.getInt("numhabitacion"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public double obtenerPrecioPorTipo(int idTipo) {
        double precio = 0.0;
        Connection con = Conexion.conectar();
        try {
            String sql = "SELECT precio FROM tipohabitacion WHERE idtipohabitacion = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idTipo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                precio = rs.getDouble("precio");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return precio;
    }

    public static ObservableList<Integer> obtenerNumerosHabitacion(int idTipo) {
        ObservableList<Integer> lista = FXCollections.observableArrayList();
        String sql = "SELECT numhabitacion FROM habitacion WHERE idtipohabitacion = ? AND estado = 'Libre'";

        try (Connection con = Conexion.conectar();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idTipo);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(rs.getInt("numhabitacion"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public int contarHabitacionesLibres() {
        int total = 0;
        String sql = "SELECT COUNT(*) AS total FROM habitacion WHERE estado = 'Libre'";
        try {
            Connection con = Conexion.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt("total");
            }
            System.out.println("Habitaciones libres contadas correctamente");
            con.close();
        } catch (Exception e) {
            System.out.println("Error al contar habitaciones libres: " + e.getMessage());
        }
        return total;
    }
    
    public int contarHabitacionesOcupadas() {
    int total = 0;
    String sql = "SELECT COUNT(*) AS total FROM habitacion WHERE estado = 'Ocupado'";
    try {
        Connection con = Conexion.conectar();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            total = rs.getInt("total");
        }
        System.out.println("Habitaciones ocupadas contadas correctamente");
        con.close();
    } catch (Exception e) {
        System.out.println("Error al contar habitaciones ocupadas: " + e.getMessage());
    }
    return total;
}
    
    public int contarTotalHabitaciones() {
    int total = 0;
    String sql = "SELECT COUNT(*) AS total FROM habitacion";
    try {
        Connection con = Conexion.conectar();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            total = rs.getInt("total");
        }
        System.out.println("Total habitaciones contadas correctamente");
        con.close();
    } catch (Exception e) {
        System.out.println("Error al contar total habitaciones: " + e.getMessage());
    }
    return total;
}
}
