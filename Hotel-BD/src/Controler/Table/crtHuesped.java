/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controler.Table;

import Model.HuespedHospedaje;
import Model.mdlHuesped;
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
public class crtHuesped {

    public int contarTotal() {
        int total = 0;
        String sql = "SELECT COUNT(nombre)AS total FROM huesped";
        try {
            Connection con = Conexion.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                total = rs.getInt("total");
            }
            System.out.println("correcto");
            con.close();

        } catch (Exception e) {
            System.out.println("error al contar");
        }
        return total;
    }

    public static boolean insertarHuesped(mdlHuesped modelo) {
        String sql = "INSERT INTO huesped (cedulaidentidad, nombre, paterno, materno, direccion, fechanacimiento, "
                + "estadocivil, tipohuesped, telefono, idprocedencia, idprofesion) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = Conexion.conectar();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, modelo.getCedula());
            ps.setString(2, modelo.getNombre());
            ps.setString(3, modelo.getPaterno());
            ps.setString(4, modelo.getMaterno());
            ps.setString(5, modelo.getDireccion());
            ps.setDate(6, modelo.getFechanac());
            ps.setString(7, modelo.getEstadocivil());
            ps.setString(8, modelo.gettHuesped());
            ps.setString(9, modelo.getTelefono());
            ps.setInt(10, modelo.getIdprocedencia());
            ps.setInt(11, modelo.getIdprofesion());

            int filas = ps.executeUpdate();
            return filas > 0;

        } catch (SQLException e) {
            System.out.println("fallo " + e.getMessage());
            return false;
        }
    }

    public static ObservableList<mdlHuesped> obtenerHuespedes() {
        ObservableList<mdlHuesped> lista = FXCollections.observableArrayList();
        String sql = "SELECT ididentificacionhuesped, cedulaidentidad, nombre, paterno, materno, "
                + "fechanacimiento, direccion, telefono, estadocivil FROM huesped";

        try (Connection con = Conexion.conectar();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                mdlHuesped huesped = new mdlHuesped(); // Usamos el constructor vacío

                // Asignamos los valores usando setters
                huesped.setCedula(rs.getString("cedulaidentidad"));
                huesped.setNombre(rs.getString("nombre"));
                huesped.setPaterno(rs.getString("paterno"));
                huesped.setMaterno(rs.getString("materno"));
                huesped.setFechanac(rs.getDate("fechanacimiento")); // Ajusta según el tipo Date
                huesped.setDireccion(rs.getString("direccion"));
                huesped.setTelefono(rs.getString("telefono"));
                huesped.setEstadocivil(rs.getString("estadocivil"));

                lista.add(huesped); // Agregamos el objeto a la lista
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public static ObservableList<mdlHuesped> buscarHuespedes(String filtro) {
    ObservableList<mdlHuesped> lista = FXCollections.observableArrayList();
    String sql = "SELECT cedulaidentidad, nombre, paterno, materno, "
               + "fechanacimiento, direccion, telefono, estadocivil "
               + "FROM huesped WHERE cedulaidentidad LIKE ?";

    try (Connection con = Conexion.conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, "%" + filtro + "%"); // Parámetro LIKE

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                mdlHuesped huesped = new mdlHuesped(); // Constructor vacío

                // Asignamos los valores usando setters
                huesped.setCedula(rs.getString("cedulaidentidad"));
                huesped.setNombre(rs.getString("nombre"));
                huesped.setPaterno(rs.getString("paterno"));
                huesped.setMaterno(rs.getString("materno"));
                huesped.setFechanac(rs.getDate("fechanacimiento"));
                huesped.setDireccion(rs.getString("direccion"));
                huesped.setTelefono(rs.getString("telefono"));
                huesped.setEstadocivil(rs.getString("estadocivil"));

                lista.add(huesped);
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return lista; // Devolvemos el ObservableList filtrado
}

    public ObservableList<String> obtenerNombresHuesped() {
    ObservableList<String> lista = FXCollections.observableArrayList();
    String sql = "SELECT nombre FROM huesped";
    
    try (Connection con = Conexion.conectar();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        
        while (rs.next()) {
            lista.add(rs.getString("nombre"));
        }
        System.out.println("Nombres de huéspedes cargados exitosamente");
        con.close();
    } catch (SQLException e) {
        System.out.println("Error al cargar nombres de huéspedes: " + e.getMessage());
    }
    
    return lista;
}
   public int obtenerIdHuesped(String nombre) {
    int id = 0;
    String sql = "SELECT ididentificacionhuesped FROM huesped WHERE nombre = ?";
    
    try (Connection con = Conexion.conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {
        
        ps.setString(1, nombre);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            id = rs.getInt("ididentificacionhuesped");
        }
        
    } catch (SQLException e) {
        System.out.println("Error al obtener ID huésped: " + e.getMessage());
    }
    
    return id;
}
   
   public ObservableList<HuespedHospedaje> obtenerHuespedesConHospedajes() {
    ObservableList<HuespedHospedaje> lista = FXCollections.observableArrayList();
    String sql = "SELECT h.nombre, th.descripcionhabitacion, hab.numhabitacion " +
                 "FROM huesped h " +
                 "INNER JOIN hospedajehuesped hh ON h.ididentificacionhuesped = hh.idhuesped " +
                 "INNER JOIN hospedaje hos ON hh.idhospedaje = hos.idhospedaje " +
                 "INNER JOIN habitacion hab ON hos.numhabitacion = hab.numhabitacion " +
                 "INNER JOIN tipohabitacion th ON hab.idtipohabitacion = th.idtipohabitacion";
    
    try (Connection con = Conexion.conectar();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        
        while (rs.next()) {
            HuespedHospedaje item = new HuespedHospedaje(
                rs.getString("nombre"),
                rs.getString("descripcionhabitacion"),
                rs.getInt("numhabitacion")
            );
            lista.add(item);
        }
        System.out.println("Datos cargados correctamente");
        
    } catch (SQLException e) {
        System.out.println("Error al cargar datos: " + e.getMessage());
    }
    
    return lista;
}
   
   public ObservableList<HuespedHospedaje> filtrarHuespedesPorNombre(String nombre) {
    ObservableList<HuespedHospedaje> lista = FXCollections.observableArrayList();
    String sql = "SELECT h.nombre, th.descripcionhabitacion, hab.numhabitacion " +
                 "FROM huesped h " +
                 "INNER JOIN hospedajehuesped hh ON h.ididentificacionhuesped = hh.idhuesped " +
                 "INNER JOIN hospedaje hos ON hh.idhospedaje = hos.idhospedaje " +
                 "INNER JOIN habitacion hab ON hos.numhabitacion = hab.numhabitacion " +
                 "INNER JOIN tipohabitacion th ON hab.idtipohabitacion = th.idtipohabitacion " +
                 "WHERE h.nombre LIKE ?";
    
    try (Connection con = Conexion.conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {
        
        ps.setString(1, "%" + nombre + "%");
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            HuespedHospedaje item = new HuespedHospedaje(
                rs.getString("nombre"),
                rs.getString("descripcionhabitacion"),
                rs.getInt("numhabitacion")
            );
            lista.add(item);
        }
        
    } catch (SQLException e) {
        System.out.println("Error al filtrar: " + e.getMessage());
    }
    
    return lista;
}
   
   public boolean eliminarHuespedHospedaje(String nombre, int numHabitacion) {
    String sql = "DELETE hh FROM hospedajehuesped hh " +
                 "INNER JOIN huesped h ON hh.idhuesped = h.ididentificacionhuesped " +
                 "INNER JOIN hospedaje hos ON hh.idhospedaje = hos.idhospedaje " +
                 "WHERE h.nombre = ? AND hos.numhabitacion = ?";
    
    try (Connection con = Conexion.conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {
        
        ps.setString(1, nombre);
        ps.setInt(2, numHabitacion);
        
        int filas = ps.executeUpdate();
        if (filas > 0) {
            System.out.println("Registro eliminado correctamente");
            return true;
        }
        
    } catch (SQLException e) {
        System.out.println("Error al eliminar: " + e.getMessage());
    }
    
    return false;
}
}
