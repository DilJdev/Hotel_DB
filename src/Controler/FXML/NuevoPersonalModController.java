/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controler.FXML;

import Controler.Table.crtpersonal;
import Model.mdlpersonal;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Lab3
 */
public class NuevoPersonalModController implements Initializable {

    @FXML
    private TextField txtUsuario;
    @FXML
    private TextField txtAP;
    @FXML
    private TextField txtAM;
    @FXML
    private TextField txtCI;
    @FXML
    private TextField txtTel;
    @FXML
    private ComboBox<String> cbPersonal;
    @FXML
    private TextField txtDireccion;
    @FXML
    private TextField txtPassword;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnAceptar;
    @FXML
    private TextField txtNombre;
    @FXML
    private ComboBox<String> cbestado;
    @FXML
    private DatePicker fecha;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cbPersonal.getItems().clear();
        cbPersonal.getItems().addAll("Gerente", "Administracion", "Recepcionista");
        
        cbestado.getItems().clear();
        cbestado.getItems().addAll("Activo", "Inactivo");
        


    }    

    @FXML
    private void cancelar(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void aceptar(ActionEvent event) {
        mdlpersonal insertar=new mdlpersonal();
        crtpersonal execute=new crtpersonal();
        
        insertar.setNombre(txtNombre.getText());
        insertar.setPaterno(txtAP.getText());
        insertar.setMaterno(txtAM.getText());
        insertar.setCedulaidentidad(txtCI.getText());
        insertar.setTelefono(txtTel.getText());
        insertar.setDireccion(txtDireccion.getText());
        insertar.setEstado(cbestado.getValue());
        insertar.setFechaingreso(Date.valueOf(fecha.getValue()));
        insertar.setTipperonal(cbPersonal.getValue());
        insertar.setUsuario(txtUsuario.getText());
        insertar.setPassword(txtPassword.getText());
        
        execute.guardarPersonal(insertar);
    }
    
}
