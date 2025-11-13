/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controler.FXML;

import Controler.Table.crtHabitacion;
import Controler.Table.crtTipoHabitacion;
import Model.mdlHabitacion;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class HabitacionViewController implements Initializable {

    @FXML
    private TableView<mdlHabitacion> tablaPersonal;
    @FXML
    private Button btnNuevo;
    @FXML
    private TableColumn<mdlHabitacion, String> colNumHabitacion;
    @FXML
    private TableColumn<mdlHabitacion, String> colEstado;
    @FXML
    private TableColumn<mdlHabitacion, String> colIdTipoHabitacion;
    @FXML
    private ComboBox<String> cbbuscar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        colNumHabitacion.setCellValueFactory(new PropertyValueFactory<>("numhabitacion"));
    colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
    colIdTipoHabitacion.setCellValueFactory(new PropertyValueFactory<>("idtipohabitacion"));
        crtHabitacion ver=new crtHabitacion();
    tablaPersonal.setItems(ver.obtenerHabitaciones());
    crtTipoHabitacion obtener=new crtTipoHabitacion();
        cbbuscar.setItems(obtener.obtenerTiposHabitacion());
    }    

    @FXML
    private void abrirModal(ActionEvent event) {
        try {
            // Cargar el archivo FXML del formulario
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/modales/NuevaHabitacionMod.fxml"));
            Parent root = loader.load();

            // Crear una nueva ventana
            Stage modalStage = new Stage();
            modalStage.setTitle("Formulario");
            modalStage.setScene(new Scene(root));

            // Configurar como ventana modal
            modalStage.initModality(Modality.WINDOW_MODAL);
            modalStage.initOwner(btnNuevo.getScene().getWindow());

            // Mostrar el modal y esperar a que se cierre
            modalStage.showAndWait();
//             actualizarTabla();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void buscar(ActionEvent event) {
        crtHabitacion buscar=new crtHabitacion();
        tablaPersonal.setItems(buscar.obtenerHabitacionesPorTipo(cbbuscar.getSelectionModel().getSelectedIndex()+1));
    }
    
}
