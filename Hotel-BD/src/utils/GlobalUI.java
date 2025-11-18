/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import com.mysql.jdbc.Connection;
import hotel.bd.Conexion;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.time.LocalDate;

/**
 *
 * @author Lab3
 */
public class GlobalUI {
    
    public void AjustarTamano(javafx.scene.layout.AnchorPane rootPane){
        Platform.runLater(() -> {
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.setResizable(false);
            javafx.geometry.Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

            double width = screenBounds.getWidth() * 0.90;
            double height = screenBounds.getHeight() * 0.90;
            stage.setWidth(width);
            stage.setHeight(height);

            stage.setX((screenBounds.getWidth() - width) / 2);
            stage.setY((screenBounds.getHeight() - height) / 2);
            
            
        });
    }
   
    public void AjustarTamano2(javafx.scene.layout.AnchorPane rootPane){
        Platform.runLater(() -> {
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.setResizable(false);
            javafx.geometry.Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

            double width = screenBounds.getWidth() * 0.50;
            double height = screenBounds.getHeight() * 0.50;
            stage.setWidth(width);
            stage.setHeight(height);

            stage.setX((screenBounds.getWidth() - width) / 2);
            stage.setY((screenBounds.getHeight() - height) / 2);
            
            
        });
    }
    public void showNotification(javafx.scene.control.Label lblsucceful, javafx.scene.control.Label lblerror, boolean res){
        if (res) {

            lblsucceful.setVisible(true);
            lblerror.setVisible(false);
            lblsucceful.setTranslateY(30); // empieza desde más abajo
            lblsucceful.setOpacity(1);     // aseguramos que esté visible

            // 🔼 Animación de subida (más lenta)
            TranslateTransition subir = new TranslateTransition(Duration.millis(1200), lblsucceful);
            subir.setToY(0);

            // ⏳ Pausa de 3 segundos
            PauseTransition pausa = new PauseTransition(Duration.seconds(3));

            // 🔽 Animación de bajada
            TranslateTransition bajar = new TranslateTransition(Duration.millis(600), lblsucceful);
            bajar.setToY(30); // vuelve a bajar

            // 🕶️ Ocultar después de bajar
            bajar.setOnFinished(e -> lblsucceful.setVisible(false));

            // 🔁 Secuencia: subir → pausa → bajar
            SequentialTransition secuencia = new SequentialTransition(subir, pausa, bajar);
            secuencia.play();
        } else {
            lblsucceful.setVisible(false);
            lblerror.setVisible(true);
            lblerror.setTranslateY(30); // empieza desde más abajo
            lblerror.setOpacity(1);     // aseguramos que esté visible

            // 🔼 Animación de subida (más lenta)
            TranslateTransition subir = new TranslateTransition(Duration.millis(1200), lblerror);
            subir.setToY(0);

            // ⏳ Pausa de 3 segundos
            PauseTransition pausa = new PauseTransition(Duration.seconds(3));

            // 🔽 Animación de bajada
            TranslateTransition bajar = new TranslateTransition(Duration.millis(600), lblerror);
            bajar.setToY(30); // vuelve a bajar

            // 🕶️ Ocultar después de bajar
            bajar.setOnFinished(e -> lblerror.setVisible(false));

            // 🔁 Secuencia: subir → pausa → bajar
            SequentialTransition secuencia = new SequentialTransition(subir, pausa, bajar);
            secuencia.play();
            
            

        }
       
    }
    public boolean validarLogin(String usuario,String password,javafx.scene.control.TextField CampoUsuario,
            javafx.scene.control.PasswordField CampoContra,javafx.scene.control.Label message) throws SQLException{
           Connection con=Conexion.conectar();
           boolean ResultadoLogin=false;
           int exist=0;
           String sql="SELECT COUNT(*) AS existe FROM personal WHERE usuario=? AND password=?";
           PreparedStatement ps = con.prepareStatement(sql);
           
           ps.setString(1, usuario);
           ps.setString(2, password);
           ResultSet rs=ps.executeQuery();
           
           while (rs.next()) {
            exist=rs.getInt("existe");
           
        }
           if (exist==0) {
               System.out.println("no existen");
               message.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
               message.setVisible(true);
               message.setText("No se encontraron registros de los datos ingresados intente de nuevo");
               
               CampoUsuario.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
               CampoContra.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
               
        }else if (exist==1) {
               System.out.println("existen");
               
               message.setText(null);
               message.setVisible(false);
               
               CampoUsuario.setStyle("-fx-border-color: black; -fx-border-width: 2px;");
               CampoContra.setStyle("-fx-border-color: black; -fx-border-width: 2px;");
               ResultadoLogin = true;
        }
           return ResultadoLogin;
       }

    // Validación genérica para campos de huésped (nuevo/editar)
    public boolean validarCamposHuesped(String nombre,
                                        String paterno,
                                        String materno,
                                        String ci,
                                        LocalDate fechaNac,
                                        String telefono,
                                        String tipoHuesped,
                                        String estadoCivil) {
        StringBuilder errores = new StringBuilder();

        if (nombre == null || nombre.trim().isEmpty()) {
            errores.append("- El nombre es obligatorio.\n");
        }
        if (paterno == null || paterno.trim().isEmpty()) {
            errores.append("- El apellido paterno es obligatorio.\n");
        }
        if (materno == null || materno.trim().isEmpty()) {
            errores.append("- El apellido materno es obligatorio.\n");
        }
        if (ci == null || ci.trim().isEmpty()) {
            errores.append("- El CI es obligatorio.\n");
        }
        if (fechaNac == null) {
            errores.append("- La fecha de nacimiento es obligatoria.\n");
        }
        if (telefono == null || telefono.trim().isEmpty()) {
            errores.append("- El teléfono es obligatorio.\n");
        }
        if (tipoHuesped == null || tipoHuesped.trim().isEmpty()) {
            errores.append("- Debe seleccionar el tipo de huésped.\n");
        }
        if (estadoCivil == null || estadoCivil.trim().isEmpty()) {
            errores.append("- Debe seleccionar el estado civil.\n");
        }

        if (errores.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos incompletos");
            alert.setHeaderText("Revise la información del huésped");
            alert.setContentText(errores.toString());
            alert.showAndWait();
            return false;
        }

        return true;
    }

    // Aplica estilos visuales a los campos de huésped según si son válidos o no
    public void marcarErroresHuesped(String nombre,
                                     String paterno,
                                     String materno,
                                     String ci,
                                     LocalDate fechaNac,
                                     String telefono,
                                     String tipoHuesped,
                                     String estadoCivil,
                                     TextField txtNombre,
                                     TextField txtPaterno,
                                     TextField txtMaterno,
                                     TextField txtCi,
                                     DatePicker datePicker,
                                     TextField txtTelefono,
                                     ComboBox<String> cbTipo,
                                     ToggleGroup civil) {

        String estiloError = "-fx-border-color: red; -fx-border-width: 2px;";
        String estiloOk = "";

        txtNombre.setStyle(estiloOk);
        txtPaterno.setStyle(estiloOk);
        txtMaterno.setStyle(estiloOk);
        txtCi.setStyle(estiloOk);
        txtTelefono.setStyle(estiloOk);
        if (datePicker != null && datePicker.getEditor() != null) {
            datePicker.getEditor().setStyle(estiloOk);
        }
        if (cbTipo != null) {
            cbTipo.setStyle(estiloOk);
        }
        // Reset estado civil (bordes de radios)
        if (civil != null) {
            for (Toggle t : civil.getToggles()) {
                if (t instanceof RadioButton) {
                    ((RadioButton) t).setStyle(estiloOk);
                }
            }
        }

        if (nombre == null || nombre.trim().isEmpty()) {
            txtNombre.setStyle(estiloError);
        }
        if (paterno == null || paterno.trim().isEmpty()) {
            txtPaterno.setStyle(estiloError);
        }
        if (materno == null || materno.trim().isEmpty()) {
            txtMaterno.setStyle(estiloError);
        }
        if (ci == null || ci.trim().isEmpty()) {
            txtCi.setStyle(estiloError);
        }
        if (fechaNac == null && datePicker != null && datePicker.getEditor() != null) {
            datePicker.getEditor().setStyle(estiloError);
        }
        if (telefono == null || telefono.trim().isEmpty()) {
            txtTelefono.setStyle(estiloError);
        }
        if (tipoHuesped == null || tipoHuesped.trim().isEmpty()) {
            if (cbTipo != null) {
                cbTipo.setStyle(estiloError);
            }
        }
        if (estadoCivil == null || estadoCivil.trim().isEmpty()) {
            if (civil != null) {
                for (Toggle t : civil.getToggles()) {
                    if (t instanceof RadioButton) {
                        ((RadioButton) t).setStyle(estiloError);
                    }
                }
            }
        }
    }

    // Validación básica para nueva habitación
    public boolean validarCamposHabitacion(String numHab, String tipo) {
        StringBuilder errores = new StringBuilder();
        if (numHab == null || numHab.trim().isEmpty()) {
            errores.append("- El número de habitación es obligatorio.\n");
        } else {
            try {
                Integer.parseInt(numHab.trim());
            } catch (NumberFormatException e) {
                errores.append("- El número de habitación debe ser numérico.\n");
            }
        }
        if (tipo == null || tipo.trim().isEmpty()) {
            errores.append("- Debe seleccionar el tipo de habitación.\n");
        }
        if (errores.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos incompletos");
            alert.setHeaderText("Revise la información de la habitación");
            alert.setContentText(errores.toString());
            alert.showAndWait();
            return false;
        }
        return true;
    }

    // Aplica estilos visuales a los campos de nueva habitación (número y tipo)
    public void marcarErroresHabitacion(String numHab,
                                        String tipo,
                                        TextField txtNum,
                                        ComboBox<String> cbTipo) {
        String estiloError = "-fx-border-color: red; -fx-border-width: 2px;";
        String estiloOk = "";

        if (txtNum != null) txtNum.setStyle(estiloOk);
        if (cbTipo != null) cbTipo.setStyle(estiloOk);

        boolean numError = false;
        if (numHab == null || numHab.trim().isEmpty()) {
            numError = true;
        } else {
            try {
                Integer.parseInt(numHab.trim());
            } catch (NumberFormatException e) {
                numError = true;
            }
        }
        if (numError && txtNum != null) {
            txtNum.setStyle(estiloError);
        }

        if (tipo == null || tipo.trim().isEmpty()) {
            if (cbTipo != null) {
                cbTipo.setStyle(estiloError);
            }
        }
    }

    // Validación básica para nuevo personal
    public boolean validarCamposPersonal(String nombre,
                                         String paterno,
                                         String materno,
                                         String ci,
                                         String telefono,
                                         String direccion,
                                         String usuario,
                                         String password,
                                         String tipoPersonal,
                                         String estado,
                                         java.time.LocalDate fechaIngreso) {
        StringBuilder errores = new StringBuilder();
        if (nombre == null || nombre.trim().isEmpty()) errores.append("- El nombre es obligatorio.\n");
        if (paterno == null || paterno.trim().isEmpty()) errores.append("- El apellido paterno es obligatorio.\n");
        if (materno == null || materno.trim().isEmpty()) errores.append("- El apellido materno es obligatorio.\n");
        if (ci == null || ci.trim().isEmpty()) errores.append("- El CI es obligatorio.\n");
        if (telefono == null || telefono.trim().isEmpty()) errores.append("- El teléfono es obligatorio.\n");
        if (direccion == null || direccion.trim().isEmpty()) errores.append("- La dirección es obligatoria.\n");
        if (usuario == null || usuario.trim().isEmpty()) errores.append("- El usuario es obligatorio.\n");
        if (password == null || password.trim().isEmpty()) errores.append("- La contraseña es obligatoria.\n");
        if (tipoPersonal == null || tipoPersonal.trim().isEmpty()) errores.append("- Debe seleccionar el tipo de personal.\n");
        if (estado == null || estado.trim().isEmpty()) errores.append("- Debe seleccionar el estado.\n");
        if (fechaIngreso == null) errores.append("- La fecha de ingreso es obligatoria.\n");

        if (errores.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos incompletos");
            alert.setHeaderText("Revise la información del personal");
            alert.setContentText(errores.toString());
            alert.showAndWait();
            return false;
        }
        return true;
    }

    // Aplica estilos visuales para el formulario de personal
    public void marcarErroresPersonal(String nombre,
                                      String paterno,
                                      String materno,
                                      String ci,
                                      String telefono,
                                      String direccion,
                                      String usuario,
                                      String password,
                                      String tipoPersonal,
                                      String estado,
                                      java.time.LocalDate fechaIngreso,
                                      TextField txtNombre,
                                      TextField txtAP,
                                      TextField txtAM,
                                      TextField txtCI,
                                      TextField txtTel,
                                      TextField txtDireccion,
                                      TextField txtUsuario,
                                      TextField txtPassword,
                                      ComboBox<String> cbTipoPersonal,
                                      ComboBox<String> cbEstado,
                                      DatePicker dpFecha) {

        String estiloError = "-fx-border-color: red; -fx-border-width: 2px;";
        String estiloOk = "";

        txtNombre.setStyle(estiloOk);
        txtAP.setStyle(estiloOk);
        txtAM.setStyle(estiloOk);
        txtCI.setStyle(estiloOk);
        txtTel.setStyle(estiloOk);
        txtDireccion.setStyle(estiloOk);
        txtUsuario.setStyle(estiloOk);
        txtPassword.setStyle(estiloOk);
        if (cbTipoPersonal != null) cbTipoPersonal.setStyle(estiloOk);
        if (cbEstado != null) cbEstado.setStyle(estiloOk);
        if (dpFecha != null && dpFecha.getEditor() != null) dpFecha.getEditor().setStyle(estiloOk);

        if (nombre == null || nombre.trim().isEmpty()) txtNombre.setStyle(estiloError);
        if (paterno == null || paterno.trim().isEmpty()) txtAP.setStyle(estiloError);
        if (materno == null || materno.trim().isEmpty()) txtAM.setStyle(estiloError);
        if (ci == null || ci.trim().isEmpty()) txtCI.setStyle(estiloError);
        if (telefono == null || telefono.trim().isEmpty()) txtTel.setStyle(estiloError);
        if (direccion == null || direccion.trim().isEmpty()) txtDireccion.setStyle(estiloError);
        if (usuario == null || usuario.trim().isEmpty()) txtUsuario.setStyle(estiloError);
        if (password == null || password.trim().isEmpty()) txtPassword.setStyle(estiloError);
        if (tipoPersonal == null || tipoPersonal.trim().isEmpty()) {
            if (cbTipoPersonal != null) cbTipoPersonal.setStyle(estiloError);
        }
        if (estado == null || estado.trim().isEmpty()) {
            if (cbEstado != null) cbEstado.setStyle(estiloError);
        }
        if (fechaIngreso == null && dpFecha != null && dpFecha.getEditor() != null) {
            dpFecha.getEditor().setStyle(estiloError);
        }
    }

    // Validación básica para edición de hospedaje
    public boolean validarCamposHospedaje(java.time.LocalDate fechaInicio,
                                          java.time.LocalDate fechaFin,
                                          String habitacion,
                                          String nombreHuesped) {
        StringBuilder errores = new StringBuilder();

        if (fechaInicio == null) {
            errores.append("- La fecha de ingreso es obligatoria.\n");
        }
        if (fechaInicio != null && fechaFin != null && fechaFin.isBefore(fechaInicio)) {
            errores.append("- La fecha de salida no puede ser anterior a la de ingreso.\n");
        }
        if (habitacion == null || habitacion.trim().isEmpty()) {
            errores.append("- Debe seleccionar una habitación.\n");
        }
        if (nombreHuesped == null || nombreHuesped.trim().isEmpty()) {
            errores.append("- El nombre del huésped es obligatorio.\n");
        }

        if (errores.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos incompletos");
            alert.setHeaderText("Revise la información del hospedaje");
            alert.setContentText(errores.toString());
            alert.showAndWait();
            return false;
        }
        return true;
    }

    // Aplica estilos visuales para el modal de hospedaje
    public void marcarErroresHospedaje(java.time.LocalDate fechaInicio,
                                       java.time.LocalDate fechaFin,
                                       String habitacion,
                                       String nombreHuesped,
                                       DatePicker dpInicio,
                                       DatePicker dpFin,
                                       ComboBox<String> cbHabitacion,
                                       TextField txtNombreHuesped) {

        String estiloError = "-fx-border-color: red; -fx-border-width: 2px;";
        String estiloOk = "";

        if (dpInicio != null && dpInicio.getEditor() != null) dpInicio.getEditor().setStyle(estiloOk);
        if (dpFin != null && dpFin.getEditor() != null) dpFin.getEditor().setStyle(estiloOk);
        if (cbHabitacion != null) cbHabitacion.setStyle(estiloOk);
        if (txtNombreHuesped != null) txtNombreHuesped.setStyle(estiloOk);

        if (fechaInicio == null && dpInicio != null && dpInicio.getEditor() != null) {
            dpInicio.getEditor().setStyle(estiloError);
        }
        if (fechaInicio != null && fechaFin != null && fechaFin.isBefore(fechaInicio) && dpFin != null && dpFin.getEditor() != null) {
            dpFin.getEditor().setStyle(estiloError);
        }
        if (habitacion == null || habitacion.trim().isEmpty()) {
            if (cbHabitacion != null) cbHabitacion.setStyle(estiloError);
        }
        if (nombreHuesped == null || nombreHuesped.trim().isEmpty()) {
            if (txtNombreHuesped != null) txtNombreHuesped.setStyle(estiloError);
        }
    }

    // Validación para NUEVO hospedaje (incluye horas, personal, número y total)
    public boolean validarCamposNuevoHospedaje(java.time.LocalDate fechaIngreso,
                                               java.time.LocalDate fechaSalida,
                                               String horaIngreso,
                                               String horaSalida,
                                               String tipoHabitacion,
                                               String numHabitacion,
                                               String personal,
                                               String totalTexto) {
        StringBuilder errores = new StringBuilder();

        if (fechaIngreso == null) errores.append("- La fecha de ingreso es obligatoria.\n");
        if (fechaSalida == null) errores.append("- La fecha de salida es obligatoria.\n");
        if (fechaIngreso != null && fechaSalida != null && fechaSalida.isBefore(fechaIngreso)) {
            errores.append("- La fecha de salida no puede ser anterior a la de ingreso.\n");
        }
        if (horaIngreso == null || horaIngreso.trim().isEmpty()) errores.append("- La hora de ingreso es obligatoria.\n");
        if (horaSalida == null || horaSalida.trim().isEmpty()) errores.append("- La hora de salida es obligatoria.\n");
        if (tipoHabitacion == null || tipoHabitacion.trim().isEmpty()) errores.append("- Debe seleccionar el tipo de habitación.\n");
        if (numHabitacion == null || numHabitacion.trim().isEmpty()) errores.append("- Debe seleccionar una habitación.\n");
        if (personal == null || personal.trim().isEmpty()) errores.append("- Debe seleccionar el personal responsable.\n");

        if (totalTexto == null || totalTexto.trim().isEmpty()) {
            errores.append("- El total no puede estar vacío.\n");
        } else {
            try {
                double total = Double.parseDouble(totalTexto);
                if (total <= 0) errores.append("- El total debe ser mayor a 0.\n");
            } catch (NumberFormatException e) {
                errores.append("- El total debe ser un número válido.\n");
            }
        }

        if (errores.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos incompletos");
            alert.setHeaderText("Revise la información del nuevo hospedaje");
            alert.setContentText(errores.toString());
            alert.showAndWait();
            return false;
        }
        return true;
    }

    // Estilos visuales para NUEVO hospedaje
    public void marcarErroresNuevoHospedaje(java.time.LocalDate fechaIngreso,
                                            java.time.LocalDate fechaSalida,
                                            String horaIngreso,
                                            String horaSalida,
                                            String tipoHabitacion,
                                            String numHabitacion,
                                            String personal,
                                            String totalTexto,
                                            DatePicker dpIngreso,
                                            DatePicker dpSalida,
                                            TextField txtHoraIngreso,
                                            TextField txtHoraSalida,
                                            ComboBox<String> cbTipo,
                                            Label lblNumHab,
                                            ComboBox<String> cbPersonal,
                                            Label lblTotal) {

        String estiloError = "-fx-border-color: red; -fx-border-width: 2px;";
        String estiloOk = "";

        if (dpIngreso != null && dpIngreso.getEditor() != null) dpIngreso.getEditor().setStyle(estiloOk);
        if (dpSalida != null && dpSalida.getEditor() != null) dpSalida.getEditor().setStyle(estiloOk);
        if (txtHoraIngreso != null) txtHoraIngreso.setStyle(estiloOk);
        if (txtHoraSalida != null) txtHoraSalida.setStyle(estiloOk);
        if (cbTipo != null) cbTipo.setStyle(estiloOk);
        if (lblNumHab != null) lblNumHab.setStyle(estiloOk);
        if (cbPersonal != null) cbPersonal.setStyle(estiloOk);
        if (lblTotal != null) lblTotal.setStyle(estiloOk);

        if (fechaIngreso == null && dpIngreso != null && dpIngreso.getEditor() != null) {
            dpIngreso.getEditor().setStyle(estiloError);
        }
        if (fechaSalida == null && dpSalida != null && dpSalida.getEditor() != null) {
            dpSalida.getEditor().setStyle(estiloError);
        }
        if (fechaIngreso != null && fechaSalida != null && fechaSalida.isBefore(fechaIngreso)
                && dpSalida != null && dpSalida.getEditor() != null) {
            dpSalida.getEditor().setStyle(estiloError);
        }
        if (horaIngreso == null || horaIngreso.trim().isEmpty()) {
            if (txtHoraIngreso != null) txtHoraIngreso.setStyle(estiloError);
        }
        if (horaSalida == null || horaSalida.trim().isEmpty()) {
            if (txtHoraSalida != null) txtHoraSalida.setStyle(estiloError);
        }
        if (tipoHabitacion == null || tipoHabitacion.trim().isEmpty()) {
            if (cbTipo != null) cbTipo.setStyle(estiloError);
        }
        if (numHabitacion == null || numHabitacion.trim().isEmpty()) {
            if (lblNumHab != null) lblNumHab.setStyle(estiloError);
        }
        if (personal == null || personal.trim().isEmpty()) {
            if (cbPersonal != null) cbPersonal.setStyle(estiloError);
        }
        boolean totalInvalido = false;
        if (totalTexto == null || totalTexto.trim().isEmpty()) {
            totalInvalido = true;
        } else {
            try {
                double total = Double.parseDouble(totalTexto);
                if (total <= 0) totalInvalido = true;
            } catch (NumberFormatException e) {
                totalInvalido = true;
            }
        }
        if (totalInvalido && lblTotal != null) {
            lblTotal.setStyle(estiloError);
        }
    }
}
