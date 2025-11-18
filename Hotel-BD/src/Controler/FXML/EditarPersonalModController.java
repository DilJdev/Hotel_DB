package Controler.FXML;

import Controler.Table.crtpersonal;
import Model.mdlpersonal;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import utils.GlobalUI;

public class EditarPersonalModController implements Initializable {

    @FXML
    private TextField txtUsuario;
    @FXML
    private TextField txtAP;
    @FXML
    private TextField txtAM;
    @FXML
    private TextField txtCI;
    @FXML
    private TextField txtTel;
    @FXML
    private ComboBox<String> cbPersonal;
    @FXML
    private TextField txtDireccion;
    @FXML
    private TextField txtPassword;
    @FXML
    private Button btnCancelar;
    @FXML
    private Button btnGuardar;
    @FXML
    private Button btnEliminar;
    @FXML
    private TextField txtNombre;
    @FXML
    private ComboBox<String> cbestado;
    @FXML
    private DatePicker fecha;
    
    @FXML
    private AnchorPane anchorPane;

    private mdlpersonal personalSeleccionado;
    private GlobalUI globalUI;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbPersonal.getItems().clear();
        cbPersonal.getItems().addAll("Gerente", "Administracion", "Recepcionista");

        cbestado.getItems().clear();
        cbestado.getItems().addAll("Activo", "Inactivo");

        globalUI = new GlobalUI();
        globalUI.AjustarTamano2(anchorPane);
    }

    public void setPersonalSeleccionado(mdlpersonal personal) {
        this.personalSeleccionado = personal;
        if (personal != null) {
            txtNombre.setText(personal.getNombre());
            txtAP.setText(personal.getPaterno());
            txtAM.setText(personal.getMaterno());
            txtCI.setText(personal.getCedulaidentidad());
            txtTel.setText(personal.getTelefono());
            txtDireccion.setText(personal.getDireccion());
            fecha.setValue(personal.getFechaingreso().toLocalDate());
            cbestado.setValue(personal.getEstado());
            cbPersonal.setValue(personal.getTipperonal());
            txtUsuario.setText(personal.getUsuario());
            txtPassword.setText(personal.getPassword());
        }
    }

    @FXML
    private void cancelar(ActionEvent event) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void guardar(ActionEvent event) {
        if (personalSeleccionado == null) return;

        String nombre = txtNombre.getText();
        String paterno = txtAP.getText();
        String materno = txtAM.getText();
        String ci = txtCI.getText();
        String tel = txtTel.getText();
        String dir = txtDireccion.getText();
        String usuario = txtUsuario.getText();
        String pass = txtPassword.getText();
        String tipo = cbPersonal.getValue();
        String estado = cbestado.getValue();
        java.time.LocalDate fIng = fecha.getValue();

        boolean valido = globalUI.validarCamposPersonal(
                nombre, paterno, materno, ci, tel, dir,
                usuario, pass, tipo, estado, fIng
        );

        globalUI.marcarErroresPersonal(
                nombre, paterno, materno, ci, tel, dir,
                usuario, pass, tipo, estado, fIng,
                txtNombre, txtAP, txtAM, txtCI, txtTel,
                txtDireccion, txtUsuario, txtPassword,
                cbPersonal, cbestado, fecha
        );

        if (!valido) return;

        personalSeleccionado.setNombre(nombre);
        personalSeleccionado.setPaterno(paterno);
        personalSeleccionado.setMaterno(materno);
        personalSeleccionado.setCedulaidentidad(ci);
        personalSeleccionado.setTelefono(tel);
        personalSeleccionado.setDireccion(dir);
        personalSeleccionado.setEstado(estado);
        personalSeleccionado.setFechaingreso(Date.valueOf(fIng));
        personalSeleccionado.setTipperonal(tipo);
        personalSeleccionado.setUsuario(usuario);
        personalSeleccionado.setPassword(pass);

        crtpersonal crt = new crtpersonal();
        boolean ok = crt.actualizarPersonal(personalSeleccionado);

        if (ok) {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Éxito");
            a.setHeaderText("Personal actualizado");
            a.setContentText("Los datos del personal se actualizaron correctamente.");
            a.showAndWait();
            cancelar(null);
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("No se pudo actualizar");
            a.setContentText("Ocurrió un error al actualizar el personal.");
            a.showAndWait();
        }
    }

    @FXML
    private void eliminar(ActionEvent event) {
        if (personalSeleccionado == null) return;

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmar eliminación");
        confirm.setHeaderText("¿Eliminar personal?");
        confirm.setContentText("Esta acción no se puede deshacer.");

        if (confirm.showAndWait().orElse(ButtonType.CANCEL) != ButtonType.OK) {
            return;
        }

        crtpersonal crt = new crtpersonal();
        boolean ok = crt.eliminarPersonalPorId(personalSeleccionado.getIdpersonal());

        if (ok) {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Éxito");
            a.setHeaderText("Personal eliminado");
            a.setContentText("El registro de personal fue eliminado.");
            a.showAndWait();
            cancelar(null);
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Error");
            a.setHeaderText("No se pudo eliminar");
            a.setContentText("Ocurrió un error al eliminar el personal.");
            a.showAndWait();
        }
    }
}
