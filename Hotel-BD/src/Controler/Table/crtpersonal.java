/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controler.Table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Model.mdlpersonal;
import hotel.bd.Conexion;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Andres
 */
public class crtpersonal {

    public boolean guardarPersonal(mdlpersonal obj) {
        boolean response = false;
        com.mysql.jdbc.Connection con = Conexion.conectar();

        try {

            String sql = "INSERT INTO personal(cedulaidentidad, nombre, paterno, materno, fechaingreso, direccion, telefono, tipperonal, estado, usuario, password) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement sqlInsertar = con.prepareStatement(sql);

            sqlInsertar.setString(1, obj.getCedulaidentidad());
            sqlInsertar.setString(2, obj.getNombre());
            sqlInsertar.setString(3, obj.getPaterno());
            sqlInsertar.setString(4, obj.getMaterno());
            sqlInsertar.setDate(5, obj.getFechaingreso());
            sqlInsertar.setString(6, obj.getDireccion());
            sqlInsertar.setString(7, obj.getTelefono());
            sqlInsertar.setString(8, obj.getTipperonal());
            sqlInsertar.setString(9, obj.getEstado());
            sqlInsertar.setString(10, obj.getUsuario());
            sqlInsertar.setString(11, obj.getPassword());

            int filasAfectadas = sqlInsertar.executeUpdate();
            if (filasAfectadas > 0) {
                response = true;
                System.out.println("Datos guardados correctamente. Filas afectadas: " + filasAfectadas);
            }
            sqlInsertar.close();
        } catch (SQLException e) {
            System.out.println("Error al guardar los datos: " + e.getMessage());
        }

        return response;
    }

   public ObservableList<mdlpersonal> mostrarPersonal() {
    ObservableList<mdlpersonal> lista = FXCollections.observableArrayList();
    String sql = "SELECT idpersonal, cedulaidentidad, nombre, paterno, materno, fechaingreso, direccion, telefono, estado FROM personal";
    
       try {
           Connection con = Conexion.conectar();
           PreparedStatement ps =con.prepareStatement(sql);
           ResultSet rs= ps.executeQuery();
           
           while (rs.next()){
               mdlpersonal mdl=new mdlpersonal(
                   rs.getInt("idpersonal"),
                   rs.getString("cedulaidentidad"),
                   rs.getString("nombre"),
                   rs.getString("paterno"),
                   rs.getString("materno"),
                   rs.getDate("fechaingreso"),
                   rs.getString("direccion"),
                   rs.getString("telefono"),
                   rs.getString("estado")
               );
               lista.add(mdl);
               
               
               System.out.println("correcto");
           }
       } catch (Exception e) {
           System.out.println("no se mostro");
       }
       return lista;
}
   
public ObservableList<String> obtenerPersonal() {
    ObservableList<String> lista = FXCollections.observableArrayList();
    String sql = "SELECT nombre, paterno, materno FROM personal WHERE estado = 'Activo'";
    
    try (Connection con = Conexion.conectar();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        
        while (rs.next()) {
            String nombre = rs.getString("nombre");
            String paterno = rs.getString("paterno");
            String materno = rs.getString("materno");
            
            // Formato: "Nombre Paterno Materno"
            String nombreCompleto = nombre + " " + paterno + 
                                  (materno != null ? " " + materno : "");
            lista.add(nombreCompleto);
        }
        System.out.println("Personal cargado exitosamente");
        con.close();
    } catch (SQLException e) {
        System.out.println("Error al cargar personal: " + e.getMessage());
    }
    
    return lista;
}

public int obtenerIdPersonal(String nombreCompleto) {
    int id = 0;
    String sql = "SELECT idpersonal FROM personal WHERE CONCAT(nombre, ' ', paterno, ' ', IFNULL(materno, '')) = ?";
    
    try (Connection con = Conexion.conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {
        
        ps.setString(1, nombreCompleto.trim());
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            id = rs.getInt("idpersonal");
        }
        
    } catch (SQLException e) {
        System.out.println("Error al obtener ID personal: " + e.getMessage());
    }
    
    return id;
}
   
   public int contarTotal() {
        int total = 0;
        String sql = "SELECT COUNT(*) AS total FROM personal WHERE estado = 'Activo'";
        try {
            Connection con = Conexion.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt("total");
            }
            System.out.println("Personal activo contado correctamente");
            con.close();
        } catch (Exception e) {
            System.out.println("Error al contar personal activo: " + e.getMessage());
        }
        return total;
    }

    // Obtener nombre completo (Nombre + Paterno) a partir del usuario
    public String obtenerNombrePorUsuario(String usuario) {
        String nombreCompleto = "";
        String sql = "SELECT nombre, paterno FROM personal WHERE usuario = ? LIMIT 1";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, usuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String nombre = rs.getString("nombre");
                String paterno = rs.getString("paterno");
                nombreCompleto = nombre + (paterno != null ? " " + paterno : "");
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener nombre por usuario: " + e.getMessage());
        }
        return nombreCompleto;
    }

    // Actualizar un registro de personal completo por id
    public boolean actualizarPersonal(mdlpersonal obj) {
        boolean actualizado = false;
        String sql = "UPDATE personal SET cedulaidentidad = ?, nombre = ?, paterno = ?, materno = ?, fechaingreso = ?, direccion = ?, telefono = ?, tipperonal = ?, estado = ?, usuario = ?, password = ? WHERE idpersonal = ?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, obj.getCedulaidentidad());
            ps.setString(2, obj.getNombre());
            ps.setString(3, obj.getPaterno());
            ps.setString(4, obj.getMaterno());
            ps.setDate(5, obj.getFechaingreso());
            ps.setString(6, obj.getDireccion());
            ps.setString(7, obj.getTelefono());
            ps.setString(8, obj.getTipperonal());
            ps.setString(9, obj.getEstado());
            ps.setString(10, obj.getUsuario());
            ps.setString(11, obj.getPassword());
            ps.setInt(12, obj.getIdpersonal());
            actualizado = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar personal: " + e.getMessage());
        }
        return actualizado;
    }

    // Eliminar personal por id
    public boolean eliminarPersonalPorId(int idPersonal) {
        boolean eliminado = false;
        String sql = "DELETE FROM personal WHERE idpersonal = ?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPersonal);
            eliminado = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar personal: " + e.getMessage());
        }
        return eliminado;
    }

    // Búsqueda por CI o nombre para la vista de personal
    public ObservableList<mdlpersonal> buscarPersonal(String filtro) {
        ObservableList<mdlpersonal> lista = FXCollections.observableArrayList();
        if (filtro == null) filtro = "";
        String sql = "SELECT idpersonal, cedulaidentidad, nombre, paterno, materno, fechaingreso, direccion, telefono, estado " +
                     "FROM personal WHERE cedulaidentidad LIKE ? OR nombre LIKE ?";
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            String like = "%" + filtro + "%";
            ps.setString(1, like);
            ps.setString(2, like);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                mdlpersonal mdl = new mdlpersonal(
                        rs.getInt("idpersonal"),
                        rs.getString("cedulaidentidad"),
                        rs.getString("nombre"),
                        rs.getString("paterno"),
                        rs.getString("materno"),
                        rs.getDate("fechaingreso"),
                        rs.getString("direccion"),
                        rs.getString("telefono"),
                        rs.getString("estado")
                );
                lista.add(mdl);
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar personal: " + e.getMessage());
        }
        return lista;
    }
}
