package Model;

public class mdlHabitacion {
    private int numhabitacion;       // Nuevo atributo: número de habitación
    private String estado;
    private int idtipohabitacion;

    // Constructor vacío
    public mdlHabitacion() {
    }

    // Constructor con todos los atributos
    public mdlHabitacion(int numhabitacion, String estado, int idtipohabitacion) {
        this.numhabitacion = numhabitacion;
        this.estado = estado;
        this.idtipohabitacion = idtipohabitacion;
    }

    // Getters y Setters
    public int getNumhabitacion() {
        return numhabitacion;
    }

    public void setNumhabitacion(int numhabitacion) {
        this.numhabitacion = numhabitacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIdtipohabitacion() {
        return idtipohabitacion;
    }

    public void setIdtipohabitacion(int idtipohabitacion) {
        this.idtipohabitacion = idtipohabitacion;
    }
}
