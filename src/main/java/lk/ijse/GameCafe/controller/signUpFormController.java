package lk.ijse.GameCafe.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.GameCafe.dto.UserDto;
import lk.ijse.GameCafe.model.UserModel;

import javax.mail.MessagingException;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;

public class signUpFormController {

    @FXML
    private AnchorPane root;
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtConfirmPassword;
    @FXML
    private JFXCheckBox txtTerms;
    @FXML
    private JFXButton btnSignUp;

    @FXML
    public void initialize() {
        // Bind the disable property of the signup button to the not selected property of the checkbox
        btnSignUp.disableProperty().bind(txtTerms.selectedProperty().not());
    }

    @FXML
    public void btnSignupOnAction(ActionEvent actionEvent) throws MessagingException {
        boolean isUserValidated = ValidateUser();

        if (!isUserValidated){
            return;
        }

        String username = txtUsername.getText();
        String password = txtPassword.getText();
        String confirmPassword = txtConfirmPassword.getText();
        String email = txtEmail.getText();

        if (!password.equals(confirmPassword)) {
            new Alert(Alert.AlertType.ERROR, "Passwords do not match.").show();
            return; // Stop execution if passwords don't match
        }

        UserDto dto = new UserDto(username, password, email);
        UserModel userModel = new UserModel();

        try {
            boolean isSaved = userModel.saveUser(dto);
            if (isSaved) {
                if (txtTerms.isSelected()) {
//                     sendConfirmationEmail(email);
                    new Alert(Alert.AlertType.INFORMATION, "A confirmation email has been sent to your address.").show();
                }

                AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/login_form.fxml"));
                Scene scene = new Scene(anchorPane);
                Stage stage = (Stage) root.getScene().getWindow();
                stage.setScene(scene);
                stage.setTitle("Login Form");
                stage.centerOnScreen();
            } else {
                new Alert(Alert.AlertType.ERROR, "Error").show();
            }
        } catch (SQLException | IOException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private boolean ValidateUser() {
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        String email = txtEmail.getText();

        // Validate username
        boolean isUsernameValid = username.matches("^[a-zA-Z0-9_]{3,20}$");
        if (!isUsernameValid) {
            new Alert(Alert.AlertType.ERROR, "Invalid username. It should be 3-20 characters and can contain letters, numbers, and underscores.").show();
            return false;
        }

        // Validate password
        boolean isPasswordValid = password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$");
        if (!isPasswordValid) {
            new Alert(Alert.AlertType.ERROR, "Invalid password. It should be at least 8 characters long and include at least one uppercase letter, one lowercase letter, and one digit.").show();
            return false;
        }

        // Validate email
        boolean isEmailValid = email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
        if (!isEmailValid) {
            new Alert(Alert.AlertType.ERROR, "Invalid email address.").show();
            return false;
        }

        return true;
    }

    @FXML
    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/login_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Login Form");
        stage.centerOnScreen();
    }

//    @FXML
//    public void checkBoxONMouseClicked(javafx.scene.input.MouseEvent mouseEvent) {
//    }
}
