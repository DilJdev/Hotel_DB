package Controler.Table;

import java.sql.*;
import Model.mdlHospedaje;
import hotel.bd.Conexion;

public class crtHospedaje {
    private Connection conexion;
    
    public crtHospedaje() {
        conexion = Conexion.conectar();
    }
    
    public void añadirHospedaje(mdlHospedaje hospedaje) {
    String sql = "INSERT INTO hospedaje (fechaingreso, fechasalida, horaingreso, horasalida, idpersonal, numhabitacion) VALUES (?, ?, ?, ?, ?, ?)";
    try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
        stmt.setDate(1, new java.sql.Date(hospedaje.getFechaingreso().getTime()));
        stmt.setDate(2, hospedaje.getFechasalida() != null ? new java.sql.Date(hospedaje.getFechasalida().getTime()) : null);
        stmt.setString(3, hospedaje.getHoraingreso());
        stmt.setString(4, hospedaje.getHorasalida());
        stmt.setInt(5, hospedaje.getIdpersonal());
        stmt.setInt(6, hospedaje.getIdnumh());   // campo Java, columna numhabitacion
        stmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    
  public void actualizarHospedaje(mdlHospedaje hospedaje) {
    String sql = "UPDATE hospedaje SET fechaingreso = ?, fechasalida = ?, horaingreso = ?, horasalida = ?, idpersonal = ?, numhabitacion = ? WHERE idhospedaje = ?";
    try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
        stmt.setDate(1, new java.sql.Date(hospedaje.getFechaingreso().getTime()));
        stmt.setDate(2, hospedaje.getFechasalida() != null ? new java.sql.Date(hospedaje.getFechasalida().getTime()) : null);
        stmt.setString(3, hospedaje.getHoraingreso());
        stmt.setString(4, hospedaje.getHorasalida());
        stmt.setInt(5, hospedaje.getIdpersonal());
        stmt.setInt(6, hospedaje.getIdnumh());   // campo Java
        stmt.setInt(7, hospedaje.getIdhospedaje());
        stmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    
    public void eliminarHospedaje(int idHospedaje) {
        String sql = "DELETE FROM hospedaje WHERE idhospedaje = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idHospedaje);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void eliminarHospedajeHuesped(int idHospedaje) {
        String sql = "DELETE FROM hospedajehuesped WHERE idhospedaje = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idHospedaje);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void eliminarHospedajeCompleto(int idHospedaje) {
        try {
            // Primero eliminar las relaciones con huéspedes
            eliminarHospedajeHuesped(idHospedaje);
            // Luego eliminar el hospedaje
            eliminarHospedaje(idHospedaje);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public int obtenerUltimoIdHospedaje() {
        String sql = "SELECT MAX(idhospedaje) FROM hospedaje";
        try (Statement stmt = conexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public void insertarHospedajeHuesped(int idHospedaje, int idHuesped) {
        String sql = "INSERT INTO hospedajehuesped (idhospedaje, idhuesped) VALUES (?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idHospedaje);
            stmt.setInt(2, idHuesped);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}