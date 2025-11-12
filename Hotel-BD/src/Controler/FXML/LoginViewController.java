/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controler.FXML;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.GlobalUI;

/**
 * FXML Controller class
 *
 * @author dil-j
 */
public class LoginViewController implements Initializable {

    @FXML
    private Button btnIniciar;
    @FXML
    private PasswordField txtcontra;
    @FXML
    private TextField txtusuario;
    @FXML
    private Label mensaje;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void iniciarSesion(ActionEvent event) throws SQLException, IOException {
        GlobalUI system=new GlobalUI();
        boolean isCorrect=system.validarLogin(txtusuario.getText(), txtcontra.getText(),txtusuario, txtcontra, mensaje);
        if(isCorrect==true){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Dashboard.fxml"));
        Parent root = loader.load();

        // Crear nueva escena y stage
        Stage nuevaVentana = new Stage();
        nuevaVentana.setScene(new Scene(root));
        nuevaVentana.setTitle("Menú Principal");
        nuevaVentana.show();

        // Cerrar la ventana actual (login)
        Stage ventanaActual = (Stage) ((Node) event.getSource()).getScene().getWindow();
        ventanaActual.close();
        }
            
    }
    
}
