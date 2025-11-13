/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Date;

/**
 *
 * @author Usuario
 */
public class mdlHuesped {
    public String cedula;
    public String nombre;
    public String paterno;
    public String materno;
    public String direccion;
    public Date fechanac;
    public String estadocivil;
    public String tHuesped;
    public String telefono;
    public int idprocedencia;
    public int idprofesion;

    public mdlHuesped() {
    }

    public mdlHuesped(String cedula, String nombre, String paterno, String materno, String direccion, Date fechanac, String estadocivil, String tHuesped, String telefono, int idprocedencia, int idprofesion) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.paterno = paterno;
        this.materno = materno;
        this.direccion = direccion;
        this.fechanac = fechanac;
        this.estadocivil = estadocivil;
        this.tHuesped = tHuesped;
        this.telefono = telefono;
        this.idprocedencia = idprocedencia;
        this.idprofesion = idprofesion;
    }


  



    

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Date getFechanac() {
        return fechanac;
    }

    public void setFechanac(Date fechanac) {
        this.fechanac = fechanac;
    }

    public String getEstadocivil() {
        return estadocivil;
    }

    public void setEstadocivil(String estadocivil) {
        this.estadocivil = estadocivil;
    }

    public String gettHuesped() {
        return tHuesped;
    }

    public void settHuesped(String tHuesped) {
        this.tHuesped = tHuesped;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getIdprocedencia() {
        return idprocedencia;
    }

    public void setIdprocedencia(int idprocedencia) {
        this.idprocedencia = idprocedencia;
    }

    public int getIdprofesion() {
        return idprofesion;
    }

    public void setIdprofesion(int idprofesion) {
        this.idprofesion = idprofesion;
    }

    public int getId() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
