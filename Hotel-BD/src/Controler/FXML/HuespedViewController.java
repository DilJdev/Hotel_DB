/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controler.FXML;

import Controler.Table.crtHuesped;
import Model.mdlHuesped;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class HuespedViewController implements Initializable {

    @FXML
    private TableView<mdlHuesped> tablaPersonal;
    @FXML
    private TableColumn<mdlHuesped, String> colCedula;
    @FXML
    private TableColumn<mdlHuesped, String> colNombre;
    @FXML
    private TableColumn<mdlHuesped, String> conPaterno;
    @FXML
    private TableColumn<mdlHuesped, String> colMaterno;
    @FXML
    private TableColumn<mdlHuesped, String> colFecha;
    @FXML
    private TableColumn<mdlHuesped, String> colDireccion;
    @FXML
    private TableColumn<mdlHuesped, String> colTelefono;
    @FXML
    private TableColumn<mdlHuesped, String> colEstado;
    @FXML
    private Button btnnuevo;
    @FXML
    private TextField txtbuscar;
    @FXML
    private Button btnEditar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        colCedula.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getCedula()));

        colNombre.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getNombre()));

        conPaterno.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getPaterno()));

        colMaterno.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getMaterno()));

        colDireccion.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getDireccion()));

        colFecha.setCellValueFactory(cellData
                -> new SimpleStringProperty(
                        cellData.getValue().getFechanac() != null
                        ? cellData.getValue().getFechanac().toString()
                        : ""
                ));

        colEstado.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getEstadocivil()));

        colTelefono.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getTelefono()));
        
        crtHuesped ver=new crtHuesped();
        tablaPersonal.setItems(ver.obtenerHuespedes());

        // Habilitar botón Editar según selección
        tablaPersonal.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            btnEditar.setDisable(newSel == null);
        });
    }

    private void actualizarTabla() {
        crtHuesped ver = new crtHuesped();
        tablaPersonal.setItems(ver.obtenerHuespedes());
    }

    @FXML
    private void mostrarmodal(ActionEvent event) {
        try {
            // Cargar el archivo FXML del formulario
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/modales/NuevoHuespedMod.fxml"));
            Parent root = loader.load();

            // Crear una nueva ventana
            Stage modalStage = new Stage();
            modalStage.setTitle("Formulario");
            modalStage.setScene(new Scene(root));

            // Configurar como ventana modal
            modalStage.initModality(Modality.WINDOW_MODAL);
            modalStage.initOwner(btnnuevo.getScene().getWindow());

            // Mostrar el modal y esperar a que se cierre
            modalStage.showAndWait();
            actualizarTabla();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void buscar(KeyEvent event) {
        crtHuesped buscar=new crtHuesped();
        tablaPersonal.setItems(buscar.buscarHuespedes(txtbuscar.getText()));
    }

    @FXML
    private void editarHuesped(ActionEvent event) {
        mdlHuesped seleccionado = tablaPersonal.getSelectionModel().getSelectedItem();
        if (seleccionado == null) return;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/modales/EditarHuespedMod.fxml"));
            Parent root = loader.load();

            EditarHuespedModController controller = loader.getController();
            controller.setHuespedSeleccionado(seleccionado);

            Stage modalStage = new Stage();
            modalStage.setTitle("Editar Huésped");
            modalStage.setScene(new Scene(root));
            modalStage.initModality(Modality.WINDOW_MODAL);
            modalStage.initOwner(btnEditar.getScene().getWindow());
            modalStage.showAndWait();

            actualizarTabla();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
