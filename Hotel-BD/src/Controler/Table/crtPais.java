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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Usuario
 */
public class crtPais {
    public ObservableList<String> obtenerPais() {
        ObservableList<String> lista = FXCollections.observableArrayList();
        String sql = "SELECT descripcionpais FROM pais";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(rs.getString("descripcionpais"));
            }
            System.out.println("se mostro paises");
            con.close();
        } catch (SQLException e) {
            System.out.println("no se mostro");
        }

        return lista;
    }
    
    public static boolean agregarPais(String nombrePais) {
        String sql = "INSERT INTO pais (descripcionpais) VALUES (?)";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombrePais); // Asignamos el valor al parámetro
            int filas = ps.executeUpdate();

            return filas > 0; // true si se insertó al menos una fila

        } catch (SQLException e) {
            System.out.println(" no se añadio");
            return false;
        }
    }
    
    public ObservableList<String> obtenerProfesion() {
        ObservableList<String> lista = FXCollections.observableArrayList();
        String sql = "SELECT descripcionprofesion FROM profesion";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(rs.getString("descripcionprofesion"));
            }
            System.out.println("se mostro paises");
            con.close();
        } catch (SQLException e) {
            System.out.println("no se mostro");
        }

        return lista;
    }
    
     public static ObservableList<String> obtenerProcedenciasPorPais(int idPais) {
        ObservableList<String> lista = FXCollections.observableArrayList();
        String sql = "SELECT descripcionprocedencia FROM procedencia WHERE idpais = ?";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idPais); // asignamos el id del país al parámetro
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(rs.getString("descripcionprocedencia"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
    
     public static boolean insertarProcedencia(String descripcion, int idPais) {
        String sql = "INSERT INTO procedencia (descripcionprocedencia, idpais) VALUES (?, ?)";

        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, descripcion);
            ps.setInt(2, idPais);

            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
