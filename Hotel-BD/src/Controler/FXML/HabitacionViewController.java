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
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
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
    @FXML
    private Button btnEditarHab;
    @FXML
    private RadioButton rbEstadoTodos;
    @FXML
    private RadioButton rbEstadoLibre;
    @FXML
    private RadioButton rbEstadoOcupado;
    @FXML
    private RadioButton rbEstadoMantenimiento;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colNumHabitacion.setCellValueFactory(new PropertyValueFactory<>("numhabitacion"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colIdTipoHabitacion.setCellValueFactory(new PropertyValueFactory<>("idtipohabitacion"));

        // Colores por estado
        colEstado.setCellFactory(col -> new TableCell<mdlHabitacion, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(item);
                    if ("Libre".equalsIgnoreCase(item)) {
                        setStyle("-fx-text-fill: green;");
                    } else if ("Ocupado".equalsIgnoreCase(item)) {
                        setStyle("-fx-text-fill: red;");
                    } else if ("Mantenimiento".equalsIgnoreCase(item)) {
                        setStyle("-fx-text-fill: orange;");
                    } else {
                        setStyle("");
                    }
                }
            }
        });

        // Crear grupo de toggles para estado
        ToggleGroup grpEstado = new ToggleGroup();
        rbEstadoTodos.setToggleGroup(grpEstado);
        rbEstadoLibre.setToggleGroup(grpEstado);
        rbEstadoOcupado.setToggleGroup(grpEstado);
        rbEstadoMantenimiento.setToggleGroup(grpEstado);

        // Cargar habitaciones
        actualizarTabla();

        // Cargar tipos con "Ver todos"
        crtTipoHabitacion obtener = new crtTipoHabitacion();
        javafx.collections.ObservableList<String> tipos = obtener.obtenerTiposHabitacion();
        tipos.add(0, "Ver todos");
        cbbuscar.setItems(tipos);
        cbbuscar.getSelectionModel().selectFirst();

        // Botón Editar habilitado según selección
        tablaPersonal.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            btnEditarHab.setDisable(newSel == null);
        });
    }    

    private void actualizarTabla() {
        crtHabitacion ver = new crtHabitacion();
        tablaPersonal.setItems(ver.obtenerHabitaciones());
        aplicarFiltros();
    }

    private void aplicarFiltros() {
        crtHabitacion crt = new crtHabitacion();
        javafx.collections.ObservableList<mdlHabitacion> base;

        int idxTipo = cbbuscar.getSelectionModel().getSelectedIndex();
        if (idxTipo <= 0) {
            base = crt.obtenerHabitaciones();
        } else {
            // porque 0 es "Ver todos"
            base = crt.obtenerHabitacionesPorTipo(idxTipo);
        }

        String filtroEstado = null;
        if (rbEstadoLibre.isSelected()) filtroEstado = "Libre";
        else if (rbEstadoOcupado.isSelected()) filtroEstado = "Ocupado";
        else if (rbEstadoMantenimiento.isSelected()) filtroEstado = "Mantenimiento";

        if (filtroEstado != null) {
            javafx.collections.ObservableList<mdlHabitacion> filtradas = FXCollections.observableArrayList();
            for (mdlHabitacion h : base) {
                if (filtroEstado.equalsIgnoreCase(h.getEstado())) {
                    filtradas.add(h);
                }
            }
            tablaPersonal.setItems(filtradas);
        } else {
            tablaPersonal.setItems(base);
        }
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
            actualizarTabla();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void buscar(ActionEvent event) {
        aplicarFiltros();
    }

    @FXML
    private void filtrarEstado(ActionEvent event) {
        aplicarFiltros();
    }

    @FXML
    private void editarHabitacion(ActionEvent event) {
        mdlHabitacion seleccionada = tablaPersonal.getSelectionModel().getSelectedItem();
        if (seleccionada == null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cambiar estado");
        alert.setHeaderText("Habitación " + seleccionada.getNumhabitacion());
        alert.setContentText("Selecciona el nuevo estado");

        ButtonType btnLibre = new ButtonType("Libre");
        ButtonType btnMantenimiento = new ButtonType("Mantenimiento");
        ButtonType btnCancelar = ButtonType.CANCEL;

        alert.getButtonTypes().setAll(btnLibre, btnMantenimiento, btnCancelar);

        Optional<ButtonType> result = alert.showAndWait();
        if (!result.isPresent() || result.get() == btnCancelar) return;

        String nuevoEstado = result.get() == btnLibre ? "Libre" : "Mantenimiento";

        crtHabitacion crt = new crtHabitacion();
        crt.actualizarEstado(seleccionada.getNumhabitacion(), nuevoEstado);

        actualizarTabla();
    }
}
