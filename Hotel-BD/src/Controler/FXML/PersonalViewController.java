/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controler.FXML;

import Controler.Table.crtpersonal;
import Model.mdlpersonal;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Lab3
 */
public class PersonalViewController implements Initializable {

    @FXML
    private Button btnNuevo;
    @FXML
    private TableView<mdlpersonal> tablaPersonal;
    @FXML
    private TableColumn<mdlpersonal, String> colID;
    @FXML
    private TableColumn<mdlpersonal, String> colCedula;
    @FXML
    private TableColumn<mdlpersonal, String> colNombre;
    @FXML
    private TableColumn<mdlpersonal, String> conPaterno;
    @FXML
    private TableColumn<mdlpersonal, String> colMaterno;
    @FXML
    private TableColumn<mdlpersonal, String> colFecha;
    @FXML
    private TableColumn<mdlpersonal, String> colDireccion;
    @FXML
    private TableColumn<mdlpersonal, String> colTelefono;
    @FXML
    private TableColumn<mdlpersonal, String> colEstado;
    @FXML
    private TextField txtBuscarPersonal;
    @FXML
    private Button btnEditar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colID.setCellValueFactory(new PropertyValueFactory<>("idpersonal"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCedula.setCellValueFactory(new PropertyValueFactory<>("cedulaidentidad"));
        conPaterno.setCellValueFactory(new PropertyValueFactory<>("paterno"));
        colMaterno.setCellValueFactory(new PropertyValueFactory<>("materno"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaingreso"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        // Colores por estado
        colEstado.setCellFactory(col -> new TableCell<mdlpersonal, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    if ("Activo".equalsIgnoreCase(item)) {
                        setStyle("-fx-text-fill: green;");
                    } else if ("Inactivo".equalsIgnoreCase(item)) {
                        setStyle("-fx-text-fill: red;");
                    } else {
                        setStyle("");
                    }
                }
            }
        });

        actualizarTabla();

        // Botón Editar habilitado según selección
        tablaPersonal.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            btnEditar.setDisable(newSel == null);
        });
    }    

    private void actualizarTabla() {
        crtpersonal crt = new crtpersonal();
        tablaPersonal.setItems(crt.mostrarPersonal());
    }

    @FXML
    private void abrirModal(ActionEvent event) {
        try {
            // Cargar el archivo FXML del formulario
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/modales/NuevoPersonalMod.fxml"));
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
            actualizarTabla();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void buscar(KeyEvent event) {
        String filtro = txtBuscarPersonal.getText();
        crtpersonal crt = new crtpersonal();
        tablaPersonal.setItems(crt.buscarPersonal(filtro));
    }

    @FXML
    private void editarPersonal(ActionEvent event) {
        mdlpersonal seleccionado = tablaPersonal.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/modales/EditarPersonalMod.fxml"));
            Parent root = loader.load();

            EditarPersonalModController controller = loader.getController();
            controller.setPersonalSeleccionado(seleccionado);

            Stage modalStage = new Stage();
            modalStage.setTitle("Editar Personal");
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
