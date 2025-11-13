/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controler.FXML;

import Controler.Table.crtHuesped;
import Controler.Table.crtPais;
import Model.mdlHuesped;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class NuevoHuespedModController implements Initializable {

    @FXML
    private ComboBox<String> cbpais;
    @FXML
    private AnchorPane panelAñadir;
    @FXML
    private ToggleButton btnmas;
    @FXML
    private TextField txtpais;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtpaterno;
    @FXML
    private TextField txtMaterno;
    @FXML
    private TextField txtci;
    @FXML
    private DatePicker datef;
    @FXML
    private TextField txttel;
    @FXML
    private TextField txtdir;
    @FXML
    private ComboBox<String> cbtipo;
    @FXML
    private ComboBox<String> cbprofesion;
    @FXML
    private ToggleGroup civil;
    @FXML
    private RadioButton casado;
    @FXML
    private RadioButton soltero;
    @FXML
    private ComboBox<String> cbprocedencia;
    @FXML
    private ToggleButton btnmas2;
    @FXML
    private TextField txtpro;
    @FXML
    private AnchorPane panelAñadir2;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        crtPais ver=new crtPais();
        cbpais.setItems(ver.obtenerPais());
        
        cbtipo.getItems().clear();
        cbtipo.getItems().addAll("Nacional", "Extranjero");
        
        cbprofesion.setItems(ver.obtenerProfesion());
        
       
    }    

    @FXML
    private void verañadir(ActionEvent event) {
        if(btnmas.isSelected()){
            panelAñadir.setVisible(true);
        }else{
            panelAñadir.setVisible(false);
        }
    }

    @FXML
    private void btninsertar(ActionEvent event) {

        crtPais añadir=new crtPais();
        
        añadir.agregarPais(txtpais.getText());
        panelAñadir.setVisible(false);
        
    }

    @FXML
    private void insertar(ActionEvent event) {
        mdlHuesped  ver=new mdlHuesped();
        crtHuesped añadirnuevo=new crtHuesped();
        
        ver.setNombre(txtNombre.getText());
        ver.setPaterno(txtpaterno.getText());
        ver.setMaterno(txtMaterno.getText());
        ver.setCedula(txtci.getText());
        ver.setFechanac(Date.valueOf(datef.getValue()));
        ver.setDireccion(txtdir.getText());
        ver.settHuesped(cbtipo.getSelectionModel().getSelectedItem());
        if(civil.getSelectedToggle()==soltero){
            ver.setEstadocivil("Soltero");
        }else if (civil.getSelectedToggle()==casado) {
            ver.setEstadocivil("Casado");
        }
        
        ver.setIdprocedencia(cbprocedencia.getSelectionModel().getSelectedIndex()+1);
        ver.setIdprofesion(cbprofesion.getSelectionModel().getSelectedIndex()+1);
        
        
        añadirnuevo.insertarHuesped(ver);
    }

    @FXML
    private void verañadir2(ActionEvent event) {
        if(btnmas2.isSelected()){
            panelAñadir.setVisible(true);
        }else{
            panelAñadir.setVisible(false);
        }
    }

    @FXML
    private void btninserta2(ActionEvent event) {
         crtPais añadir=new crtPais();
        
        añadir.insertarProcedencia(txtpro.getText(), cbpais.getSelectionModel().getSelectedIndex()+1);
        panelAñadir.setVisible(false);
        
    }

    @FXML
    private void obtenerid(ActionEvent event) {
        crtPais  ver=new crtPais();
         cbprocedencia.setItems(ver.obtenerProcedenciasPorPais(cbpais.getSelectionModel().getSelectedIndex()+1));
    }
    
}
