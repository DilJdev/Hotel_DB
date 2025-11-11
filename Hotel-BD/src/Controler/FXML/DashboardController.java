/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controler.FXML;

import com.mysql.jdbc.Connection;
import hotel.bd.Conexion;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import utils.GlobalUI;

/**
 * FXML Controller class
 *
 * @author Lab3
 */
public class DashboardController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private AnchorPane menupane;
    @FXML
    private Button btnCerrarSesion;
    @FXML
    private AnchorPane bar;
    @FXML
    private Button btnmenu;
    
    private boolean menuVisible = true;
    @FXML
    private AnchorPane panelesContainer;
    @FXML
    private Label lblsucceful;
    @FXML
    private Label lblerror;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        GlobalUI pref=new GlobalUI();
        pref.AjustarTamaño(rootPane);
        Connection conection = Conexion.conectar();
        if(conection != null){
            pref.showNotification(lblsucceful, lblerror, true);
        }else{
            pref.showNotification(lblsucceful, lblerror, false);
        }
    }   

    @FXML
    private void showPanelPersonal(ActionEvent event) {
        try {
            // Cargar el nuevo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/PersonalView.fxml"));
            Parent nuevoPanel = loader.load();

            // Limpiar el contenedor y agregar el nuevo panel
            panelesContainer.getChildren().setAll(nuevoPanel);

            // Ajustar el nuevo panel al tamaño del contenedor
            AnchorPane.setTopAnchor(nuevoPanel, 0.0);
            AnchorPane.setBottomAnchor(nuevoPanel, 0.0);
            AnchorPane.setLeftAnchor(nuevoPanel, 0.0);
            AnchorPane.setRightAnchor(nuevoPanel, 0.0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void MostrarMenu(ActionEvent event) {
        TranslateTransition animacion = new TranslateTransition(Duration.millis(300), menupane);

        if (menuVisible) {
            // Ocultar: deslizar hacia la izquierda
            animacion.setToX(-200);
            animacion.setOnFinished(e -> {
                menupane.setPrefWidth(0);
                AnchorPane.setLeftAnchor(bar, 0.0);
                AnchorPane.setLeftAnchor(panelesContainer, 0.0);
                bar.setPrefWidth(rootPane.getWidth()); // O el ancho que quieras para la barra
            });
            btnmenu.setText("☰");
        } else {
            // Mostrar: deslizar hacia la derecha
            animacion.setToX(0);
            animacion.setOnFinished(e -> {
                menupane.setPrefWidth(200);
                AnchorPane.setLeftAnchor(bar, 200.0);
                AnchorPane.setLeftAnchor(panelesContainer, 200.0);
                bar.setPrefWidth(rootPane.getWidth() - 200);
            });
            btnmenu.setText("✖");
        }
        menuVisible = !menuVisible;
        animacion.play();
    }

    @FXML
    private void cerrarSesion(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/LoginView.fxml"));
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

    @FXML
    private void showPanelHabitacion(ActionEvent event) throws IOException {
        try {
            // Cargar el nuevo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/HabitacionView.fxml"));
            Parent nuevoPanel = loader.load();

            // Limpiar el contenedor y agregar el nuevo panel
            panelesContainer.getChildren().setAll(nuevoPanel);

            // Ajustar el nuevo panel al tamaño del contenedor
            AnchorPane.setTopAnchor(nuevoPanel, 0.0);
            AnchorPane.setBottomAnchor(nuevoPanel, 0.0);
            AnchorPane.setLeftAnchor(nuevoPanel, 0.0);
            AnchorPane.setRightAnchor(nuevoPanel, 0.0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
}
