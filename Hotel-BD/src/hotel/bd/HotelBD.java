/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotel.bd;

import com.mysql.jdbc.Connection;
import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author dil-j
 */
public class HotelBD extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Connection conection = Conexion.conectar();
       FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Dashboard.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch();
    }
    
}
