package lk.ijse.GameCafe.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import lk.ijse.GameCafe.bo.BOFactory;
import lk.ijse.GameCafe.bo.custom.UserBO;
import lk.ijse.GameCafe.util.Navigation;


import java.io.IOException;
import java.sql.SQLException;

public class ResetPasswordController {

    @FXML
    private TextField txtConfirmPassword;

    @FXML
    private TextField txtPassword;

    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);

    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException {
        Navigation.switchNavigation("login_form.fxml",event);
    }

    @FXML
    void btnResetPasswordOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        if(txtPassword.getText().equals(txtConfirmPassword.getText())) {

            boolean isUpdated = userBO.updatePassword(ForgotPasswordFormController.username, txtPassword.getText());

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
