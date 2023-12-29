package lk.ijse.GameCafe.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.GameCafe.util.Navigation;

import java.io.IOException;

public class OtpFormController {

    @FXML
    private AnchorPane OtpForm;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnVerify;

    @FXML
    private TextField txtOtp;

    @FXML
    void btnOnActionBack(ActionEvent event) throws IOException {
        btnBack.getScene().getWindow().hide();
        Navigation.switchNavigation("forgotPassword_form.fxml",event);
    }
    private int otp;
    @FXML
    void btnOnActionVerify(ActionEvent event) throws IOException {
        System.out.println(ForgotPasswordFormController.otp);
        if(String.valueOf(otp).equals(txtOtp.getText())){
            btnVerify.getScene().getWindow().hide();
            Navigation.switchNavigation("ResetPassword_form.fxml",event);
        }else{
            new Alert(Alert.AlertType.ERROR,"OTP WRONG");
        }
    }
    public void initialize(){
        System.out.println(ForgotPasswordFormController.otp);
        this.otp = ForgotPasswordFormController.otp;
    }
}
