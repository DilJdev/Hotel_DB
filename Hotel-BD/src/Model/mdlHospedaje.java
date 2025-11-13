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
public class mdlHospedaje {
    public Date fechaingreso;
    public Date fechasalida;
    public String horaingreso;
    public String horasalida;
    public Double total;
    public int idpersonal;
    public int idnumh;

    public mdlHospedaje() {
    }

    public mdlHospedaje(Date fechaingreso, Date fechasalida, String horaingreso, String horasalida, Double total, int idpersonal, int idnumh) {
        this.fechaingreso = fechaingreso;
        this.fechasalida = fechasalida;
        this.horaingreso = horaingreso;
        this.horasalida = horasalida;
        this.total = total;
        this.idpersonal = idpersonal;
        this.idnumh = idnumh;
    }

    public Date getFechaingreso() {
        return fechaingreso;
    }

    public void setFechaingreso(Date fechaingreso) {
        this.fechaingreso = fechaingreso;
    }

    public Date getFechasalida() {
        return fechasalida;
    }

    public void setFechasalida(Date fechasalida) {
        this.fechasalida = fechasalida;
    }

    public String getHoraingreso() {
        return horaingreso;
    }

    public void setHoraingreso(String horaingreso) {
        this.horaingreso = horaingreso;
    }

    public String getHorasalida() {
        return horasalida;
    }

    public void setHorasalida(String horasalida) {
        this.horasalida = horasalida;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public int getIdpersonal() {
        return idpersonal;
    }

    public void setIdpersonal(int idpersonal) {
        this.idpersonal = idpersonal;
    }

    public int getIdnumh() {
        return idnumh;
    }

    public void setIdnumh(int idnumh) {
        this.idnumh = idnumh;
    }
    
    
    
}
