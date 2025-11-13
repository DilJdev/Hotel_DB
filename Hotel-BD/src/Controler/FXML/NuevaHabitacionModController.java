/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controler.FXML;

import Controler.Table.crtHabitacion;
import Controler.Table.crtHuesped;
import Controler.Table.crtTipoHabitacion;
import Model.mdlHabitacion;
import com.sun.org.apache.bcel.internal.generic.AALOAD;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class NuevaHabitacionModController implements Initializable {

    @FXML
    private ComboBox<String> cbTipoH;
    private AnchorPane capacidad;
    @FXML
    private ComboBox<String> cbestado;
    @FXML
    private Label precio;
    @FXML
    private Label caps;
    @FXML
    private TextField txtnum;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        crtTipoHabitacion mostrar=new crtTipoHabitacion();
        cbTipoH.setItems(mostrar.obtenerTiposHabitacion());
        
        cbestado.getItems().clear();
        cbestado.getItems().addAll("Libre", "Mantenimiento");
        
        // TODO
    }    

    @FXML
    private void mostrar(ActionEvent event) {
        crtTipoHabitacion ver=new crtTipoHabitacion();
        
        precio.setText(String.valueOf(ver.obtenerPrecioHabitacion(cbTipoH.getSelectionModel().getSelectedIndex()+1)));
        caps.setText(String.valueOf(ver.obtenerCantidadPersonas(cbTipoH.getSelectionModel().getSelectedIndex()+1)));
    }

    @FXML
    private void insertar(ActionEvent event) {
        crtHabitacion controler=new crtHabitacion();
        mdlHabitacion insertar=new mdlHabitacion();
        
        insertar.setEstado(cbestado.getSelectionModel().getSelectedItem());
        insertar.setIdtipohabitacion(cbTipoH.getSelectionModel().getSelectedIndex()+1);
        insertar.setNumhabitacion(Integer.valueOf(txtnum.getText()));
        
        controler.insertarHabitacion(insertar.getNumhabitacion(), insertar);
    }
    
}
