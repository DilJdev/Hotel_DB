/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controler.FXML;

import Controler.Table.crtHabitacion;
import Controler.Table.crtHospedaje;
import Controler.Table.crtHuesped;
import Controler.Table.crtpersonal;
import Model.HuespedHospedaje;
import com.mysql.jdbc.Connection;
import hotel.bd.Conexion;
import java.sql.PreparedStatement;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import utils.GlobalUI;

/**
 * FXML Controller class
 *
 * @author Lab3
 */
public class DashboardController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private AnchorPane menupane;
    @FXML
    private Button btnCerrarSesion;
    @FXML
    private AnchorPane bar;
    @FXML
    private Button btnmenu;
    
    private boolean menuVisible = true;
    @FXML
    private AnchorPane panelesContainer;
    @FXML
    private Label lblsucceful;
    @FXML
    private Label lblerror;
    @FXML
    private Label lblHuespedes;
    @FXML
    private Label lblhabitacionesLibres;
    @FXML
    private Label lblhabitacionesOcupadas;
    @FXML
    private Label lblPersonalActivo;
    @FXML
    private Label lblUsuario;
    @FXML
    private TableView<HuespedHospedaje> tablaHospedajes;
    @FXML
    private TableColumn<HuespedHospedaje, String> colNombreHuesped;
    @FXML
    private TableColumn<HuespedHospedaje, Integer> colNumHabitacion;
    @FXML
    private TableColumn<HuespedHospedaje, String> colTipoHabitacion;
    @FXML
    private Label lblDetNombre;
    @FXML
    private Label lblDetHabitacion;
    @FXML
    private Label lblDetTipo;
    @FXML
    private Label lblDetFechaIngreso;
    @FXML
    private Label lblDetFechaSalida;
    @FXML
    private Label lblDetNoches;
    @FXML
    private Button btnLiberarHabitacion;
    
    @FXML
    private TextField txtBuscarHuesped;
    @FXML
    private Button btnEditarHospedaje;

    private String usuarioLogin;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        GlobalUI pref=new GlobalUI();
        pref.AjustarTamano(rootPane);
        Connection conection = Conexion.conectar();
        if(conection != null){
            pref.showNotification(lblsucceful, lblerror, true);
        }else{
            pref.showNotification(lblsucceful, lblerror, false);
        }
        crtHuesped contar=new crtHuesped();
        lblHuespedes.setText(String.valueOf(contar.contarTotal()));
        
        crtHabitacion crth=new crtHabitacion();
        lblhabitacionesLibres.setText(String.valueOf(crth.contarHabitacionesLibres()));
        lblhabitacionesOcupadas.setText(String.valueOf(crth.contarHabitacionesOcupadas()));
        
        crtpersonal crtp = new crtpersonal();
        lblPersonalActivo.setText(String.valueOf(crtp.contarTotal()));

        // Si ya tenemos usuario asignado antes de initialize, actualizar label
        if (usuarioLogin != null && !usuarioLogin.isEmpty()) {
            actualizarNombreUsuario();
        }
        
        // Inicializar tabla y funcionalidades
        inicializarTablaHospedajes();
        configurarBusqueda();
        configurarSeleccionTabla();
        cargarDatosHospedajes();
    }   

    public void setUsuarioLogin(String usuario) {
        this.usuarioLogin = usuario;
        actualizarNombreUsuario();
    }

    private void actualizarNombreUsuario() {
        try {
            crtpersonal crtp = new crtpersonal();
            String nombre = crtp.obtenerNombrePorUsuario(usuarioLogin);
            if (nombre != null && !nombre.trim().isEmpty()) {
                lblUsuario.setText(nombre);
            } else {
                lblUsuario.setText(usuarioLogin);
            }
        } catch (Exception e) {
            lblUsuario.setText(usuarioLogin);
        }
    }
    
    private void inicializarTablaHospedajes() {
        colNombreHuesped.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNombre()));
        colNumHabitacion.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getNumHabitacion()).asObject());
        colTipoHabitacion.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTipoHabitacion()));
    }
    
    private void cargarDatosHospedajes() {
        crtHuesped huespedController = new crtHuesped();
        tablaHospedajes.setItems(huespedController.obtenerHuespedesConHospedajes());
    }
    
    private void configurarBusqueda() {
        txtBuscarHuesped.textProperty().addListener((observable, oldValue, newValue) -> {
            filtrarTabla(newValue);
        });
    }
    
    private void configurarSeleccionTabla() {
        tablaHospedajes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnEditarHospedaje.setDisable(newValue == null);
            // Actualizar panel de detalles
            if (newValue != null) {
                lblDetNombre.setText(newValue.getNombre());
                lblDetHabitacion.setText(String.valueOf(newValue.getNumHabitacion()));
                lblDetTipo.setText(newValue.getTipoHabitacion());
                btnLiberarHabitacion.setDisable(false);
                cargarDetallesHospedaje(newValue);
            } else {
                lblDetNombre.setText("-");
                lblDetHabitacion.setText("-");
                lblDetTipo.setText("-");
                lblDetFechaIngreso.setText("-");
                lblDetFechaSalida.setText("-");
                lblDetNoches.setText("-");
                btnLiberarHabitacion.setDisable(true);
            }
        });
    }

    private void cargarDetallesHospedaje(HuespedHospedaje item) {
        try {
            String sql = "SELECT fechaingreso, fechasalida FROM hospedaje WHERE idhospedaje = ?";
            Connection con = Conexion.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, item.getIdHospedaje());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                java.sql.Date fi = rs.getDate("fechaingreso");
                java.sql.Date fs = rs.getDate("fechasalida");
                if (fi != null) {
                    lblDetFechaIngreso.setText(fi.toString());
                } else {
                    lblDetFechaIngreso.setText("-");
                }
                if (fs != null) {
                    lblDetFechaSalida.setText(fs.toString());
                } else {
                    lblDetFechaSalida.setText("-");
                }

                // Calcular noches totales
                long noches = 0;
                if (fi != null) {
                    java.time.LocalDate ini = fi.toLocalDate();
                    java.time.LocalDate fin = (fs != null) ? fs.toLocalDate() : java.time.LocalDate.now();
                    noches = java.time.temporal.ChronoUnit.DAYS.between(ini, fin);
                    if (noches < 1) {
                        noches = 1;
                    }
                }
                lblDetNoches.setText(noches > 0 ? String.valueOf(noches) : "-");
            } else {
                lblDetFechaIngreso.setText("-");
                lblDetFechaSalida.setText("-");
                lblDetNoches.setText("-");
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void liberarHabitacion(ActionEvent event) {
        HuespedHospedaje seleccionado = tablaHospedajes.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            return;
        }

        try {
            // Marcar la habitación como Libre
            int numHab = seleccionado.getNumHabitacion();
            String sql = "UPDATE habitacion SET estado = 'Libre' WHERE numhabitacion = ?";
            Connection con = Conexion.conectar();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, numHab);
            ps.executeUpdate();
            con.close();

            // Refrescar métricas e información de tabla
            crtHabitacion crth = new crtHabitacion();
            lblhabitacionesLibres.setText(String.valueOf(crth.contarHabitacionesLibres()));
            lblhabitacionesOcupadas.setText(String.valueOf(crth.contarHabitacionesOcupadas()));

            cargarDatosHospedajes();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void filtrarTabla(String searchText) {
        crtHuesped huespedController = new crtHuesped();
        javafx.collections.ObservableList<HuespedHospedaje> todosLosHuespedes = huespedController.obtenerHuespedesConHospedajes();
        
        if (searchText == null || searchText.isEmpty()) {
            tablaHospedajes.setItems(todosLosHuespedes);
        } else {
            javafx.collections.ObservableList<HuespedHospedaje> huespedesFiltrados = FXCollections.observableArrayList();
            
            for (HuespedHospedaje huesped : todosLosHuespedes) {
                if (huesped.getNombre().toLowerCase().contains(searchText.toLowerCase())) {
                    huespedesFiltrados.add(huesped);
                }
            }
            
            tablaHospedajes.setItems(huespedesFiltrados);
        }
    }
    
    @FXML
    private void editarHospedaje(ActionEvent event) {
        HuespedHospedaje hospedajeSeleccionado = tablaHospedajes.getSelectionModel().getSelectedItem();
        
        if (hospedajeSeleccionado != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/modales/EditarHospedajeMod.fxml"));
                Parent root = loader.load();
                
                EditarHospedajeModController controller = loader.getController();
                controller.setHospedajeSeleccionado(hospedajeSeleccionado);
                
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Editar Hospedaje");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
                
                // Refrescar la tabla después de cerrar el modal
                cargarDatosHospedajes();
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    @FXML
    private void abrirNuevoHospedaje(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/modales/NuevoHospedajeMod.fxml"));
            Parent root = loader.load();
            
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Nuevo Hospedaje");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            
            // Refrescar la tabla después de cerrar el modal
            cargarDatosHospedajes();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void reabrirDashboard(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Dashboard.fxml"));
        Parent root = loader.load();

        Stage nuevaVentana = new Stage();
        nuevaVentana.setScene(new Scene(root));
        nuevaVentana.setTitle("Dashboard");
        nuevaVentana.show();

        Stage ventanaActual = (Stage) ((Node) event.getSource()).getScene().getWindow();
        ventanaActual.close();
    }

    @FXML
    private void showPanelPersonal(ActionEvent event) {
        try {
            // Cargar el nuevo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/PersonalView.fxml"));
            Parent nuevoPanel = loader.load();

            // Limpiar el contenedor y agregar el nuevo panel
            panelesContainer.getChildren().setAll(nuevoPanel);

            // Ajustar el nuevo panel al tamaño del contenedor
            AnchorPane.setTopAnchor(nuevoPanel, 0.0);
            AnchorPane.setBottomAnchor(nuevoPanel, 0.0);
            AnchorPane.setLeftAnchor(nuevoPanel, 0.0);
            AnchorPane.setRightAnchor(nuevoPanel, 0.0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void MostrarMenu(ActionEvent event) {
        TranslateTransition animacion = new TranslateTransition(Duration.millis(300), menupane);

        if (menuVisible) {
            // Ocultar: deslizar hacia la izquierda
            animacion.setToX(-200);
            animacion.setOnFinished(e -> {
                menupane.setPrefWidth(0);
                AnchorPane.setLeftAnchor(bar, 0.0);
                AnchorPane.setLeftAnchor(panelesContainer, 0.0);
                bar.setPrefWidth(rootPane.getWidth()); // O el ancho que quieras para la barra
            });
            btnmenu.setText("☰");
        } else {
            // Mostrar: deslizar hacia la derecha
            animacion.setToX(0);
            animacion.setOnFinished(e -> {
                menupane.setPrefWidth(200);
                AnchorPane.setLeftAnchor(bar, 200.0);
                AnchorPane.setLeftAnchor(panelesContainer, 200.0);
                bar.setPrefWidth(rootPane.getWidth() - 200);
            });
            btnmenu.setText("✖");
        }
        menuVisible = !menuVisible;
        animacion.play();
    }

    @FXML
    private void cerrarSesion(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/LoginView.fxml"));
        Parent root = loader.load();

        // Crear nueva escena y stage
        Stage nuevaVentana = new Stage();
        nuevaVentana.setScene(new Scene(root));
        nuevaVentana.setTitle("Menú Principal");
        nuevaVentana.show();

        // Cerrar la ventana actual (login)
        Stage ventanaActual = (Stage) ((Node) event.getSource()).getScene().getWindow();
        ventanaActual.close();
    }

    @FXML
    private void showPanelHabitacion(ActionEvent event) throws IOException {
        try {
            // Cargar el nuevo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/HabitacionView.fxml"));
            Parent nuevoPanel = loader.load();

            // Limpiar el contenedor y agregar el nuevo panel
            panelesContainer.getChildren().setAll(nuevoPanel);

            // Ajustar el nuevo panel al tamaño del contenedor
            AnchorPane.setTopAnchor(nuevoPanel, 0.0);
            AnchorPane.setBottomAnchor(nuevoPanel, 0.0);
            AnchorPane.setLeftAnchor(nuevoPanel, 0.0);
            AnchorPane.setRightAnchor(nuevoPanel, 0.0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showpanelhuesped(ActionEvent event) {
        try {
            // Cargar el nuevo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/HuespedView.fxml"));
            Parent nuevoPanel = loader.load();

            // Limpiar el contenedor y agregar el nuevo panel
            panelesContainer.getChildren().setAll(nuevoPanel);

            // Ajustar el nuevo panel al tamaño del contenedor
            AnchorPane.setTopAnchor(nuevoPanel, 0.0);
            AnchorPane.setBottomAnchor(nuevoPanel, 0.0);
            AnchorPane.setLeftAnchor(nuevoPanel, 0.0);
            AnchorPane.setRightAnchor(nuevoPanel, 0.0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showpanelhospedaje(ActionEvent event) {
        try {
            // Cargar el nuevo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/HospedajeView.fxml"));
            Parent nuevoPanel = loader.load();

            // Limpiar el contenedor y agregar el nuevo panel
            panelesContainer.getChildren().setAll(nuevoPanel);

            // Ajustar el nuevo panel al tamaño del contenedor
            AnchorPane.setTopAnchor(nuevoPanel, 0.0);
            AnchorPane.setBottomAnchor(nuevoPanel, 0.0);
            AnchorPane.setLeftAnchor(nuevoPanel, 0.0);
            AnchorPane.setRightAnchor(nuevoPanel, 0.0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    private void refreshDashboard(ActionEvent event) {
        try {
            // Recargar el dashboard actual
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Dashboard.fxml"));
            Parent nuevoPanel = loader.load();

            // Limpiar el contenedor y agregar el nuevo panel
            panelesContainer.getChildren().setAll(nuevoPanel);

            // Ajustar el nuevo panel al tamaño del contenedor
            AnchorPane.setTopAnchor(nuevoPanel, 0.0);
            AnchorPane.setBottomAnchor(nuevoPanel, 0.0);
            AnchorPane.setLeftAnchor(nuevoPanel, 0.0);
            AnchorPane.setRightAnchor(nuevoPanel, 0.0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
}
