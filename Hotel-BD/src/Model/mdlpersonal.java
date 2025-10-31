/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Date;

/**
 *
 * @author Andres
 */
public class mdlpersonal {
    private int idpersonal;
    private String cedulaidentidad;
    private String nombre;
    private String paterno;
    private String materno;
    private Date fechaingreso;
    private String direccion;
    private String telefono;
    private String tipperonal;
    private String estado;
    private String usuario;
    private String password;

    public mdlpersonal() {
    }

    public mdlpersonal(int idpersonal, String cedulaidentidad, String nombre, String paterno, String materno,
                        Date fechaingreso, String direccion, String telefono, String tipperonal,
                        String estado, String usuario, String password) {
        this.idpersonal = idpersonal;
        this.cedulaidentidad = cedulaidentidad;
        this.nombre = nombre;
        this.paterno = paterno;
        this.materno = materno;
        this.fechaingreso = fechaingreso;
        this.direccion = direccion;
        this.telefono = telefono;
        this.tipperonal = tipperonal;
        this.estado = estado;
        this.usuario = usuario;
        this.password = password;
    }

    public mdlpersonal(int idpersonal, String cedulaidentidad, String nombre, String paterno, String materno, Date fechaingreso, String direccion, String telefono, String estado) {
        this.idpersonal = idpersonal;
        this.cedulaidentidad = cedulaidentidad;
        this.nombre = nombre;
        this.paterno = paterno;
        this.materno = materno;
        this.fechaingreso = fechaingreso;
        this.direccion = direccion;
        this.telefono = telefono;
        this.estado = estado;
    }

    public int getIdpersonal() {
        return idpersonal;
    }

    public void setIdpersonal(int idpersonal) {
        this.idpersonal = idpersonal;
    }

    public String getCedulaidentidad() {
        return cedulaidentidad;
    }

    public void setCedulaidentidad(String cedulaidentidad) {
        this.cedulaidentidad = cedulaidentidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPaterno() {
        return paterno;
    }

    public void setPaterno(String paterno) {
        this.paterno = paterno;
    }

    public String getMaterno() {
        return materno;
    }

    public void setMaterno(String materno) {
        this.materno = materno;
    }

    public Date getFechaingreso() {
        return fechaingreso;
    }

    public void setFechaingreso(Date fechaingreso) {
        this.fechaingreso = fechaingreso;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTipperonal() {
        return tipperonal;
    }

    public void setTipperonal(String tipperonal) {
        this.tipperonal = tipperonal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
