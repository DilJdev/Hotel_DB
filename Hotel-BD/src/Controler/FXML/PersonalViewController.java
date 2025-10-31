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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mdlpersonal mostrar=new mdlpersonal();
        crtpersonal crt=new crtpersonal();
        
         colID.setCellValueFactory(new PropertyValueFactory<>("idpersonal"));
         colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
         colCedula.setCellValueFactory(new PropertyValueFactory<>("cedulaidentidad"));
         conPaterno.setCellValueFactory(new PropertyValueFactory<>("paterno"));
         colMaterno.setCellValueFactory(new PropertyValueFactory<>("materno"));
         colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaingreso"));
         colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
         colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
         colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
         
         
        
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
//             actualizarTabla();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
