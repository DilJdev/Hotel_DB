/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Usuario
 */
public class HuespedHospedaje {
    private String nombre;
    private String tipoHabitacion;
    private int numHabitacion;
    private int idHospedaje;
    
    public HuespedHospedaje(String nombre, String tipoHabitacion, int numHabitacion) {
        this.nombre = nombre;
        this.tipoHabitacion = tipoHabitacion;
        this.numHabitacion = numHabitacion;
    }
    
    public HuespedHospedaje(String nombre, String tipoHabitacion, int numHabitacion, int idHospedaje) {
        this.nombre = nombre;
        this.tipoHabitacion = tipoHabitacion;
        this.numHabitacion = numHabitacion;
        this.idHospedaje = idHospedaje;
    }
    
    // Getters
    public String getNombre() { return nombre; }
    public String getTipoHabitacion() { return tipoHabitacion; }
    public int getNumHabitacion() { return numHabitacion; }
    public int getIdHospedaje() { return idHospedaje; }
    
    // Setters
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setTipoHabitacion(String tipoHabitacion) { this.tipoHabitacion = tipoHabitacion; }
    public void setNumHabitacion(int numHabitacion) { this.numHabitacion = numHabitacion; }
    public void setIdHospedaje(int idHospedaje) { this.idHospedaje = idHospedaje; }
}