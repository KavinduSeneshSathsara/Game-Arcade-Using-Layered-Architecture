package lk.ijse.GameCafe.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import lk.ijse.GameCafe.model.UserModel;
import lk.ijse.GameCafe.util.Navigation;


import java.io.IOException;
import java.sql.SQLException;

public class ResetPasswordController {

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnReset;

    @FXML
    private TextField txtConfirmPassword;

    @FXML
    private TextField txtPassword;

    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException {
        Navigation.switchNavigation("login_form.fxml",event);
    }

    @FXML
    void btnResetPasswordOnAction(ActionEvent event) throws SQLException {

        UserModel userModel = new UserModel();
        if(txtPassword.getText().equals(txtConfirmPassword.getText())) {
            boolean isUpdated = userModel.updatePassword(ForgotPasswordFormController.username, txtPassword.getText());
            if (isUpdated) {
                System.out.println("OK");
            } else {
                System.out.println("WRONG");
            }
        }else {
            System.out.println("CONFIRMATION NOT MATCHED!");
        }
    }
    public void initialize(){
        System.out.println(ForgotPasswordFormController.username);

    }
}
