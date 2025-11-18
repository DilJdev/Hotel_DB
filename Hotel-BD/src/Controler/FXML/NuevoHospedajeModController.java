/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controler.FXML;

import Controler.Table.crtHabitacion;
import Controler.Table.crtHospedaje;
import Controler.Table.crtHuesped;
import Controler.Table.crtTipoHabitacion;
import Controler.Table.crtpersonal;
import Model.mdlHospedaje;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import utils.GlobalUI;

/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class NuevoHospedajeModController implements Initializable {

    @FXML
    private DatePicker dpIngreso;
    @FXML
    private DatePicker dpSalida;
    @FXML
    private ComboBox<String> cbtipo;
    @FXML
    private TableView<Integer> tablah;
    @FXML
    private TableColumn<Integer, String> colnumero;
    @FXML
    private Label txttotal;
    @FXML
    private ComboBox<String> cbpersonal;
    @FXML
    private Label txtnum;
    double precio = 0;
    @FXML
    private TextField txthi;
    @FXML
    private TextField txths;
    @FXML
    private AnchorPane huesped;
    @FXML
    private ComboBox<String> cbhuesped;
    
    @FXML
    private AnchorPane anchorPane;
    
    private GlobalUI globalUI;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        crtTipoHabitacion crt = new crtTipoHabitacion();
        crtpersonal crtp = new crtpersonal();

        cbtipo.setItems(crt.obtenerTiposHabitacion());
        cbpersonal.setItems(crtp.obtenerPersonal());

        colnumero.setCellValueFactory(cellData
                -> new SimpleStringProperty(String.valueOf(cellData.getValue()))
        );
        dpIngreso.valueProperty().addListener((observable, oldValue, newValue) -> {
            calcularTotal();
        });

        dpSalida.valueProperty().addListener((observable, oldValue, newValue) -> {
            calcularTotal();
        });

        globalUI = new GlobalUI();
        globalUI.AjustarTamano2(anchorPane);
    }

    @FXML
    private void obtenerprecio(ActionEvent event) {
        crtTipoHabitacion crt = new crtTipoHabitacion();
        crtHabitacion crth = new crtHabitacion();
        precio = crt.obtenerPrecioPorId(cbtipo.getSelectionModel().getSelectedIndex() + 1);
        System.out.println(precio);
        tablah.setItems(crth.obtenerNumerosHabitacion(cbtipo.getSelectionModel().getSelectedIndex() + 1));

    }

    @FXML
    private void seleccionarHabitacion(MouseEvent event) {
        Integer habitacionSeleccionada = tablah.getSelectionModel().getSelectedItem();
        if (habitacionSeleccionada != null) {
            txtnum.setText(String.valueOf(habitacionSeleccionada));
        }
        calcularTotal();

    }

    private void calcularTotal() {
        if (dpIngreso.getValue() != null && dpSalida.getValue() != null && precio > 0) {
            // Calcular días entre fechas
            long dias = java.time.temporal.ChronoUnit.DAYS.between(
                    dpIngreso.getValue(),
                    dpSalida.getValue()
            );

            // Asegurar que sea mínimo 1 día
            if (dias < 1) {
                dias = 1;
            }

            // Calcular total
            double total = dias * precio;
            txttotal.setText(String.valueOf(total));

            System.out.println("Días: " + dias + ", Total: " + total);
        } else {
            txttotal.setText("0");
        }
    }

    @FXML
    private void insertar(ActionEvent event) {
        mdlHospedaje mdl = new mdlHospedaje();
        crtHospedaje crth = new crtHospedaje();
        crtpersonal crtp = new crtpersonal();
        crtHuesped crthu = new crtHuesped();
        crtHabitacion crthab = new crtHabitacion();

        java.time.LocalDate fi = dpIngreso.getValue();
        java.time.LocalDate fs = dpSalida.getValue();
        String hi = txthi.getText();
        String hs = txths.getText();
        String tipo = cbtipo.getSelectionModel().getSelectedItem();
        String num = txtnum.getText();
        String personalSel = cbpersonal.getSelectionModel().getSelectedItem();
        String totalTexto = txttotal.getText();

        boolean valido = globalUI.validarCamposNuevoHospedaje(
                fi, fs,
                hi, hs,
                tipo,
                num,
                personalSel,
                totalTexto
        );

        globalUI.marcarErroresNuevoHospedaje(
                fi, fs,
                hi, hs,
                tipo,
                num,
                personalSel,
                totalTexto,
                dpIngreso,
                dpSalida,
                txthi,
                txths,
                cbtipo,
                txtnum,
                cbpersonal,
                txttotal
        );

        if (!valido) return;

        mdl.setFechaingreso(Date.valueOf(fi));
        mdl.setFechasalida(Date.valueOf(fs));
        mdl.setHoraingreso(hi);
        mdl.setHorasalida(hs);
        mdl.setTotal(Double.valueOf(totalTexto));
        mdl.setIdpersonal(crtp.obtenerIdPersonal(personalSel));
        mdl.setIdnumh(Integer.valueOf(num));

        crth.añadirHospedaje(mdl);
        // Marcar habitación como ocupada
        crthab.actualizarEstado(Integer.valueOf(num), "Ocupado");
        
        // Cerrar modal
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();

        huesped.setVisible(true);
        cbhuesped.setItems(crthu.obtenerNombresHuesped());
    }

    @FXML
    private void añadirFinal(ActionEvent event) {
        crtHospedaje crth = new crtHospedaje();
        crtHuesped hu = new crtHuesped();
        String nombreH = cbhuesped.getSelectionModel().getSelectedItem();

        if (nombreH == null || nombreH.trim().isEmpty()) {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
            alert.setTitle("Seleccionar huésped");
            alert.setHeaderText("Falta seleccionar huésped");
            alert.setContentText("Debe seleccionar un huésped para asociarlo al hospedaje.");
            alert.showAndWait();
            return;
        }

        crth.insertarHospedajeHuesped(crth.obtenerUltimoIdHospedaje(), hu.obtenerIdHuesped(nombreH));
    }
}
