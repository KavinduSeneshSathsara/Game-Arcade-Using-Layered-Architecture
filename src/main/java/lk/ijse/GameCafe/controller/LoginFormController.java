package lk.ijse.GameCafe.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import lk.ijse.GameCafe.bo.BOFactory;
import lk.ijse.GameCafe.bo.custom.UserBO;

import java.io.IOException;

public class LoginFormController {

    @FXML
    private AnchorPane root;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUsername;

    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);

    @FXML
    void btnContinueOnAction(ActionEvent event) throws IOException {
       if (userBO.verifyCredential(txtUsername.getText(),txtPassword.getText())){

           try {
               AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/main_form.fxml"));
                    Scene scene = new Scene(anchorPane);
                    Stage stage = (Stage) root.getScene().getWindow();
                    stage.setScene(scene);
                    stage.setTitle("Main Form");
                    stage.centerOnScreen();
           }catch (IOException e){
               throw new RuntimeException(e);
           }
       }
    }

    @FXML
    void btnRegisterOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/signup_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Signup Form");
    }

    @FXML
    public void lblForgotPasswordOnAction(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(this.getClass().getResource("/view/forgotPassword_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Signup Form");
    }
}
