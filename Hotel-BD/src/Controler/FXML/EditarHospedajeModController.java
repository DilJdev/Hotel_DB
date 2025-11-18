package Controler.FXML;

import Model.HuespedHospedaje;
import Model.mdlHospedaje;
import Controler.Table.crtHospedaje;
import Controler.Table.crtHabitacion;
import Controler.Table.crtHuesped;
import Controler.Table.crtTipoHabitacion;
import utils.GlobalUI;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;
import java.time.LocalDate;
import java.sql.SQLException;

public class EditarHospedajeModController implements Initializable {

    @FXML
    private TextField txtNombreHuesped;
    
    @FXML
    private DatePicker dpFechaInicio;
    
    @FXML
    private DatePicker dpFechaFin;
    
    @FXML
    private ComboBox<String> cbHabitacion;
    
    @FXML
    private Button btnGuardar;
    
    @FXML
    private Button btnEliminar;
    
    @FXML
    private Button btnCancelar;
    
    @FXML
    private AnchorPane anchorPane;
    
    private HuespedHospedaje hospedajeSeleccionado;
    private crtHospedaje crtHospedajeController;
    private crtHabitacion crtHabitacionController;
    private crtHuesped crtHuespedController;
    private GlobalUI globalUI;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        crtHabitacionController = new crtHabitacion();
        crtHuespedController = new crtHuesped();
        crtHospedajeController = new crtHospedaje();
        globalUI = new GlobalUI();
        globalUI.AjustarTamano2(anchorPane);
        
        cargarHabitaciones();
    }
    
    public void setHospedajeSeleccionado(HuespedHospedaje hospedaje) {
        this.hospedajeSeleccionado = hospedaje;
        
        if (hospedaje != null) {
            txtNombreHuesped.setText(hospedaje.getNombre());
            cbHabitacion.setValue(hospedaje.getNumHabitacion() + " - " + hospedaje.getTipoHabitacion());
            
            // Aquí deberías cargar las fechas del hospedaje desde la base de datos
            // Por ahora usaremos fechas por defecto
            dpFechaInicio.setValue(LocalDate.now());
            dpFechaFin.setValue(LocalDate.now().plusDays(1));
        }
    }
    
    private void cargarHabitaciones() {
        try {
            ObservableList<String> habitaciones = FXCollections.observableArrayList();
            // Aquí deberías cargar las habitaciones disponibles desde la base de datos
            // Por ahora agregaremos algunas de ejemplo
            habitaciones.add("101 - Individual");
            habitaciones.add("102 - Doble");
            habitaciones.add("103 - Suite");
            habitaciones.add("201 - Individual");
            habitaciones.add("202 - Doble");
            
            cbHabitacion.setItems(habitaciones);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void guardarCambios(ActionEvent event) {
        try {
            if (hospedajeSeleccionado != null) {
                // Validación lógica y visual
                java.time.LocalDate fi = dpFechaInicio.getValue();
                java.time.LocalDate ff = dpFechaFin.getValue();
                String habSeleccionadaStr = cbHabitacion.getValue();
                String nuevoNombre = txtNombreHuesped.getText();

                boolean valido = globalUI.validarCamposHospedaje(
                        fi,
                        ff,
                        habSeleccionadaStr,
                        nuevoNombre
                );

                globalUI.marcarErroresHospedaje(
                        fi,
                        ff,
                        habSeleccionadaStr,
                        nuevoNombre,
                        dpFechaInicio,
                        dpFechaFin,
                        cbHabitacion,
                        txtNombreHuesped
                );

                if (!valido) return;

                // Crear un objeto mdlHospedaje con los datos actualizados
                mdlHospedaje hospedajeActualizado = new mdlHospedaje();
                hospedajeActualizado.setIdhospedaje(hospedajeSeleccionado.getIdHospedaje());
                hospedajeActualizado.setFechaingreso(java.sql.Date.valueOf(dpFechaInicio.getValue()));
                
                if (dpFechaFin.getValue() != null) {
                    hospedajeActualizado.setFechasalida(java.sql.Date.valueOf(dpFechaFin.getValue()));
                } else {
                    hospedajeActualizado.setFechasalida(null);
                }
                
                hospedajeActualizado.setHoraingreso("10:00"); // Valor por defecto
                hospedajeActualizado.setHorasalida(null); // Valor por defecto
                hospedajeActualizado.setTotal(0.0); // Valor por defecto

                // Valor del ComboBox en formato "101 - Tipo" -> nos quedamos con el número antes del guion
                String habSeleccionada = cbHabitacion.getValue();
                if (habSeleccionada != null && !habSeleccionada.trim().isEmpty()) {
                    String numHabStr = habSeleccionada.split(" ")[0];
                    hospedajeActualizado.setIdnumh(Integer.parseInt(numHabStr));
                } else {
                    throw new IllegalArgumentException("Debe seleccionar una habitación válida");
                }
                
                // 1) Actualizar nombre del huésped en la tabla huesped
                if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
                    crtHuespedController.actualizarNombrePorIdHospedaje(
                            hospedajeSeleccionado.getIdHospedaje(),
                            nuevoNombre.trim()
                    );
                }

                // 2) Actualizar datos del hospedaje en la tabla hospedaje
                crtHospedajeController.actualizarHospedaje(hospedajeActualizado);
                
                showAlert(Alert.AlertType.INFORMATION, "Éxito", "Hospedaje actualizado correctamente");
                cerrarModal();
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "No se pudo actualizar el hospedaje: " + e.getMessage());
        }
    }
    
    @FXML
    private void eliminarHospedaje(ActionEvent event) {
        try {
            Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmDialog.setTitle("Confirmar Eliminación");
            confirmDialog.setHeaderText("¿Está seguro de eliminar este hospedaje?");
            confirmDialog.setContentText("Esta acción no se puede deshacer");
            
            if (confirmDialog.showAndWait().get() == ButtonType.OK) {
                if (hospedajeSeleccionado != null) {
                    // Eliminar relaciones huésped-hospedaje primero
                    crtHospedajeController.eliminarHospedajeHuesped(hospedajeSeleccionado.getIdHospedaje());
                    // Luego eliminar el hospedaje
                    crtHospedajeController.eliminarHospedaje(hospedajeSeleccionado.getIdHospedaje());
                    
                    showAlert(Alert.AlertType.INFORMATION, "Éxito", "Hospedaje eliminado correctamente");
                    cerrarModal();
                }
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "No se pudo eliminar el hospedaje: " + e.getMessage());
        }
    }
    
    @FXML
    private void cancelar(ActionEvent event) {
        cerrarModal();
    }
    
    private void cerrarModal() {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }
    
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
