/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Lab3
 */
public class GlobalUI {
    
    public void AjustarTamaño(javafx.scene.layout.AnchorPane rootPane){
        Platform.runLater(() -> {
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.setResizable(false);
            javafx.geometry.Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

            double width = screenBounds.getWidth() * 0.90;
            double height = screenBounds.getHeight() * 0.90;
            stage.setWidth(width);
            stage.setHeight(height);

            stage.setX((screenBounds.getWidth() - width) / 2);
            stage.setY((screenBounds.getHeight() - height) / 2);
            
            
        });
    }
   
    public void showNotification(javafx.scene.control.Label lblsucceful, javafx.scene.control.Label lblerror, boolean res){
        if (res) {

            lblsucceful.setVisible(true);
            lblerror.setVisible(false);
            lblsucceful.setTranslateY(30); // empieza desde más abajo
            lblsucceful.setOpacity(1);     // aseguramos que esté visible

            // 🔼 Animación de subida (más lenta)
            TranslateTransition subir = new TranslateTransition(Duration.millis(1200), lblsucceful);
            subir.setToY(0);

            // ⏳ Pausa de 3 segundos
            PauseTransition pausa = new PauseTransition(Duration.seconds(3));

            // 🔽 Animación de bajada
            TranslateTransition bajar = new TranslateTransition(Duration.millis(600), lblsucceful);
            bajar.setToY(30); // vuelve a bajar

            // 🕶️ Ocultar después de bajar
            bajar.setOnFinished(e -> lblsucceful.setVisible(false));

            // 🔁 Secuencia: subir → pausa → bajar
            SequentialTransition secuencia = new SequentialTransition(subir, pausa, bajar);
            secuencia.play();
        } else {
            lblsucceful.setVisible(false);
            lblerror.setVisible(true);
            lblerror.setTranslateY(30); // empieza desde más abajo
            lblerror.setOpacity(1);     // aseguramos que esté visible

            // 🔼 Animación de subida (más lenta)
            TranslateTransition subir = new TranslateTransition(Duration.millis(1200), lblerror);
            subir.setToY(0);

            // ⏳ Pausa de 3 segundos
            PauseTransition pausa = new PauseTransition(Duration.seconds(3));

            // 🔽 Animación de bajada
            TranslateTransition bajar = new TranslateTransition(Duration.millis(600), lblerror);
            bajar.setToY(30); // vuelve a bajar

            // 🕶️ Ocultar después de bajar
            bajar.setOnFinished(e -> lblerror.setVisible(false));

            // 🔁 Secuencia: subir → pausa → bajar
            SequentialTransition secuencia = new SequentialTransition(subir, pausa, bajar);
            secuencia.play();
            
            

        }
    }
}
