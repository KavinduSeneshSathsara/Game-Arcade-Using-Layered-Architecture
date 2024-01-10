package lk.ijse.GameCafe.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.mail.MessagingException;

import lk.ijse.GameCafe.bo.BOFactory;
import lk.ijse.GameCafe.bo.custom.UserBO;
import lk.ijse.GameCafe.dto.UserDto;
import lk.ijse.GameCafe.util.Navigation;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Random;

public class ForgotPasswordFormController {

    @FXML
    private AnchorPane root;

    @FXML
    private Button btnReset;

    @FXML
    private TextField txtUsername;
    static String username;
    static int otp;

    UserBO userBO = (UserBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.USER);

    @FXML
    void btnBackOnAction(ActionEvent event) throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/login_form.fxml"));
        Scene scene = new Scene(anchorPane);
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Login Form");
        stage.centerOnScreen();
    }

    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException, MessagingException, IOException, ClassNotFoundException {
        username = txtUsername.getText();
        Random random = new Random();

        otp = random.nextInt(9000);
        otp += 1000;

        UserDto userDto = userBO.getEmail(username);
        System.out.println(userDto.getEmail());

        EmailController.sendEmail(userDto.getEmail(), "Verification Code for Password Reset", otp + "");

        btnReset.getScene().getWindow().hide();
        Navigation.switchNavigation("otp_form.fxml",event);
    }
}
