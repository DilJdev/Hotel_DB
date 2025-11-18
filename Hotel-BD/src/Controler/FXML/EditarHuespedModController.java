package Controler.FXML;

import Controler.Table.crtHuesped;
import Model.mdlHuesped;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import utils.GlobalUI;

public class EditarHuespedModController implements Initializable {

    @FXML private TextField txtNombre;
    @FXML private TextField txtPaterno;
    @FXML private TextField txtMaterno;
    @FXML private TextField txtCi;
    @FXML private javafx.scene.control.DatePicker dateNacimiento;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtDireccion;
    @FXML private ComboBox<String> cbTipo;
    @FXML private RadioButton rbSoltero;
    @FXML private RadioButton rbCasado;
    @FXML private ToggleGroup civil;
    
    @FXML private AnchorPane anchorPane;

    private mdlHuesped huespedSeleccionado;
    private crtHuesped crtHuespedController;
    private GlobalUI globalUI;
    private String ciOriginal;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        crtHuespedController = new crtHuesped();
        globalUI = new GlobalUI();
        globalUI.AjustarTamano2(anchorPane);
        cbTipo.getItems().clear();
        cbTipo.getItems().addAll("Nacional", "Extranjero");
        rbSoltero.setSelected(true);
    }

    public void setHuespedSeleccionado(mdlHuesped huesped) {
        this.huespedSeleccionado = huesped;
        if (huesped != null) {
            // Guardamos el CI original para usarlo en el WHERE
            ciOriginal = huesped.getCedula();
            txtNombre.setText(huesped.getNombre());
            txtPaterno.setText(huesped.getPaterno());
            txtMaterno.setText(huesped.getMaterno());
            txtCi.setText(huesped.getCedula());
            if (huesped.getFechanac() != null) {
                dateNacimiento.setValue(huesped.getFechanac().toLocalDate());
            }
            txtTelefono.setText(huesped.getTelefono());
            txtDireccion.setText(huesped.getDireccion());
            cbTipo.setValue(huesped.gettHuesped());
            if ("Casado".equalsIgnoreCase(huesped.getEstadocivil())) {
                rbCasado.setSelected(true);
            } else {
                rbSoltero.setSelected(true);
            }
        }
    }

    @FXML
    private void guardar(ActionEvent event) {
        try {
            if (huespedSeleccionado == null) return;

            // Validación usando GlobalUI
            String estadoCivil = rbSoltero.isSelected() ? "Soltero" : (rbCasado.isSelected() ? "Casado" : "");
            boolean valido = globalUI.validarCamposHuesped(
                    txtNombre.getText(),
                    txtPaterno.getText(),
                    txtMaterno.getText(),
                    txtCi.getText(),
                    dateNacimiento.getValue(),
                    txtTelefono.getText(),
                    cbTipo.getValue(),
                    estadoCivil
            );

            globalUI.marcarErroresHuesped(
                    txtNombre.getText(),
                    txtPaterno.getText(),
                    txtMaterno.getText(),
                    txtCi.getText(),
                    dateNacimiento.getValue(),
                    txtTelefono.getText(),
                    cbTipo.getValue(),
                    estadoCivil,
                    txtNombre,
                    txtPaterno,
                    txtMaterno,
                    txtCi,
                    dateNacimiento,
                    txtTelefono,
                    cbTipo,
                    civil
            );
            if (!valido) return;

            huespedSeleccionado.setNombre(txtNombre.getText());
            huespedSeleccionado.setPaterno(txtPaterno.getText());
            huespedSeleccionado.setMaterno(txtMaterno.getText());
            huespedSeleccionado.setCedula(txtCi.getText());
            huespedSeleccionado.setFechanac(Date.valueOf(dateNacimiento.getValue()));
            huespedSeleccionado.setTelefono(txtTelefono.getText());
            huespedSeleccionado.setDireccion(txtDireccion.getText());
            huespedSeleccionado.settHuesped(cbTipo.getValue());
            huespedSeleccionado.setEstadocivil(estadoCivil);

            crtHuespedController.actualizarHuesped(huespedSeleccionado, ciOriginal);
            cerrar();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No se pudo actualizar el huésped: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void eliminar(ActionEvent event) {
        try {
            if (huespedSeleccionado == null) return;
            // Usar CI original por si el usuario lo cambió en el formulario
            crtHuespedController.eliminarHuespedPorCedula(ciOriginal != null ? ciOriginal : huespedSeleccionado.getCedula());
            cerrar();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No se pudo eliminar el huésped: " + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void cancelar(ActionEvent event) {
        cerrar();
    }

    private void cerrar() {
        Stage stage = (Stage) txtNombre.getScene().getWindow();
        stage.close();
    }
}
