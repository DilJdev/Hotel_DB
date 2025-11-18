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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import utils.GlobalUI;

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
    
    @FXML
    private AnchorPane anchorPane;

    private GlobalUI globalUI;

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
        globalUI = new GlobalUI();
        globalUI.AjustarTamano2(anchorPane);
    }    

    @FXML
    private void cancelar(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void aceptar(ActionEvent event) {
        try {
            String nombre = txtNombre.getText();
            String paterno = txtAP.getText();
            String materno = txtAM.getText();
            String ci = txtCI.getText();
            String tel = txtTel.getText();
            String dir = txtDireccion.getText();
            String usuario = txtUsuario.getText();
            String pass = txtPassword.getText();
            String tipo = cbPersonal.getValue();
            String estado = cbestado.getValue();
            java.time.LocalDate fIng = fecha.getValue();

            boolean valido = globalUI.validarCamposPersonal(
                    nombre, paterno, materno, ci, tel, dir,
                    usuario, pass, tipo, estado, fIng
            );

            globalUI.marcarErroresPersonal(
                    nombre, paterno, materno, ci, tel, dir,
                    usuario, pass, tipo, estado, fIng,
                    txtNombre, txtAP, txtAM, txtCI, txtTel,
                    txtDireccion, txtUsuario, txtPassword,
                    cbPersonal, cbestado, fecha
            );

            if (!valido) return;

            mdlpersonal insertar = new mdlpersonal();
            crtpersonal execute = new crtpersonal();

            insertar.setNombre(nombre);
            insertar.setPaterno(paterno);
            insertar.setMaterno(materno);
            insertar.setCedulaidentidad(ci);
            insertar.setTelefono(tel);
            insertar.setDireccion(dir);
            insertar.setEstado(estado);
            insertar.setFechaingreso(Date.valueOf(fIng));
            insertar.setTipperonal(tipo);
            insertar.setUsuario(usuario);
            insertar.setPassword(pass);

            execute.guardarPersonal(insertar);

            // cerrar modal
            Stage stage = (Stage) btnAceptar.getScene().getWindow();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
