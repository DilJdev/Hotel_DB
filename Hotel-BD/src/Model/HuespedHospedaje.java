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
    
    public HuespedHospedaje(String nombre, String tipoHabitacion, int numHabitacion) {
        this.nombre = nombre;
        this.tipoHabitacion = tipoHabitacion;
        this.numHabitacion = numHabitacion;
    }
    
    // Getters
    public String getNombre() { return nombre; }
    public String getTipoHabitacion() { return tipoHabitacion; }
    public int getNumHabitacion() { return numHabitacion; }
    
    // Setters
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setTipoHabitacion(String tipoHabitacion) { this.tipoHabitacion = tipoHabitacion; }
    public void setNumHabitacion(int numHabitacion) { this.numHabitacion = numHabitacion; }
}