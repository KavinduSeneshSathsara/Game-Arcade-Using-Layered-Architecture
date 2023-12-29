package lk.ijse.GameCafe.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.GameCafe.dto.UserDto;
import lk.ijse.GameCafe.model.UserModel;
import lk.ijse.GameCafe.util.Navigation;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

public class ForgotPasswordFormController {

    @FXML
    private AnchorPane ForgotPane;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnReset;

    @FXML
    private TextField txtUsername;

    static String username;
    static int otp;

    @FXML
    void btnBackOnAction(ActionEvent event) {

    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException, MessagingException, IOException {
        username = txtUsername.getText();

        UserModel userModel = new UserModel();
        Random random = new Random();

        otp = random.nextInt(9000);
        otp += 1000;

        UserDto userDto = userModel.getEmail(username);
        System.out.println(userDto.getEmail());

        EmailController.sendEmail(userDto.getEmail(), "Verification Code for Password Reset", otp + "");

        btnReset.getScene().getWindow().hide();
        Navigation.switchNavigation("otp_form.fxml",event);
    }
}
