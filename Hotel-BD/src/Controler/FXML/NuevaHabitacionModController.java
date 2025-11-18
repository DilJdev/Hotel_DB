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
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import utils.GlobalUI;

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
    
    @FXML
    private AnchorPane anchorPane;

    private GlobalUI globalUI;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        crtTipoHabitacion mostrar=new crtTipoHabitacion();
        cbTipoH.setItems(mostrar.obtenerTiposHabitacion());
        
        cbestado.getItems().clear();
        cbestado.getItems().addAll("Libre", "Mantenimiento");
        globalUI = new GlobalUI();
        globalUI.AjustarTamano2(anchorPane);
    }    

    @FXML
    private void mostrar(ActionEvent event) {
        crtTipoHabitacion ver=new crtTipoHabitacion();
        
        precio.setText(String.valueOf(ver.obtenerPrecioHabitacion(cbTipoH.getSelectionModel().getSelectedIndex()+1)));
        caps.setText(String.valueOf(ver.obtenerCantidadPersonas(cbTipoH.getSelectionModel().getSelectedIndex()+1)));
    }

    @FXML
    private void insertar(ActionEvent event) {
        try {
            String num = txtnum.getText();
            String tipo = cbTipoH.getSelectionModel().getSelectedItem();

            boolean valido = globalUI.validarCamposHabitacion(num, tipo);
            globalUI.marcarErroresHabitacion(num, tipo, txtnum, cbTipoH);
            if (!valido) return;

            crtHabitacion controler = new crtHabitacion();
            mdlHabitacion insertar = new mdlHabitacion();

            insertar.setEstado(cbestado.getSelectionModel().getSelectedItem());
            insertar.setIdtipohabitacion(cbTipoH.getSelectionModel().getSelectedIndex()+1);
            insertar.setNumhabitacion(Integer.valueOf(num));

            controler.insertarHabitacion(insertar.getNumhabitacion(), insertar);

            // Cerrar modal
            Stage stage = (Stage) txtnum.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
