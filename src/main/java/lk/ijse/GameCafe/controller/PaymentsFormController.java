package lk.ijse.GameCafe.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import lk.ijse.GameCafe.db.DbConnection;
import lk.ijse.GameCafe.dto.BookingDto;
import lk.ijse.GameCafe.dto.CustomerDto;
import lk.ijse.GameCafe.dto.PaymentDto;
import lk.ijse.GameCafe.dto.tm.CustomerTm;
import lk.ijse.GameCafe.dto.tm.EmployeeTm;
import lk.ijse.GameCafe.dto.tm.PaymentTm;
import lk.ijse.GameCafe.model.BookingModel;
import lk.ijse.GameCafe.model.CustomerModel;
import lk.ijse.GameCafe.model.PaymentModel;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;
import javax.mail.*;

public class PaymentsFormController implements Initializable {

    @FXML
    private Pane pane;

//    @FXML
//    private TextField txtBookingId;

    @FXML
    private TextField txtSearchBar;

    @FXML
    private Text lblDate;

    @FXML
    private Text lblTime;

    @FXML
    private TableView<PaymentTm> tblPayment;

    @FXML
    private TableColumn<?, ?> colPaymentId;

    @FXML
    private TableColumn<?, ?> colBookingId;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colTime;

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private ComboBox<String> cmbBookingId;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtCustomer;

    @FXML
    private Label lblAmount;

    @FXML
    private Label lblCustomerName;

    @FXML
    private Text lblPaymentID;

    @FXML
    private JFXButton btnPay;

    PaymentModel paymentModel = new PaymentModel();
    BookingModel bookingModel = new BookingModel();
    CustomerModel customerModel = new CustomerModel();
    CustomerDto customerDto = new CustomerDto();



    @FXML
    void btnClearOnAction(ActionEvent event) {
        cmbBookingId.getItems().clear();
        lblCustomerName.setText("");
        lblAmount.setText("");
    }

    private void setCellValueFactory() {
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colBookingId.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
    }

    @FXML
    void btnPayOnAction(ActionEvent event) throws SQLException, MessagingException {
        Connection connection = null;
        try {
            connection = DbConnection.getInstance( ).getConnection( );
            connection.setAutoCommit( false );

            boolean savePayment = paymentModel.savePayment( new PaymentDto(
                    lblPaymentID.getText( ),
                    cmbBookingId.getValue(),
                    Date.valueOf( LocalDate.now( ) ),
                    Time.valueOf( LocalTime.now( ) ),
                    Double.parseDouble(lblAmount.getText( ) )
            ) );

            if ( savePayment ) {
                boolean isUpdated = bookingModel.updateStatus(String.valueOf(cmbBookingId.getValue( )));
                loadAllPayments();

                if ( isUpdated ) {
                    connection.commit();
                    new Alert( Alert.AlertType.CONFIRMATION, "Payment Saved" ).show();
                    setPaymentId();
                    loadAllPayments();
//                    EmailController.sendEmail(customerDto.getCusEmail(), "Payment Confirmation", "Thank you for your payment!");
                } else {
                    new Alert( Alert.AlertType.ERROR , "Something Went Wrong" ).show();
                }
            } else {
                new Alert( Alert.AlertType.ERROR , "Something Went Wrong" ).show();
            }
        } catch ( SQLException e ) {
            e.printStackTrace();
            connection.rollback();
        } finally {
            connection.setAutoCommit( true );
        }
    }

    private void loadAllPayments() {
        System.out.println("load");
        PaymentModel paymentModel = new PaymentModel();

        ObservableList<PaymentTm> obList = FXCollections.observableArrayList();

        try{
            List<PaymentDto> list = paymentModel.getAllPayments();

            for (PaymentDto dto: list){
                PaymentTm paymentTm = new PaymentTm(
                        dto.getPaymentId(),
                        dto.getBookingId(),
                        dto.getDate(),
                        dto.getTime(),
                        dto.getAmount()
                );

                obList.add(paymentTm);
            }
            tblPayment.setItems(obList);
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
        System.out.println("loading");
    }

    @FXML
    void cmbBookingIdOnAction(ActionEvent event) {
        try {
            BookingDto bookingData = bookingModel.getBookingData((String) cmbBookingId.getValue());

            if ( bookingData != null && bookingData.getStatus().equals( "Not Paid" ) ) {

                CustomerDto dto = customerModel.SearchModel( bookingData.getCus_id() );
                lblCustomerName.setText( dto.getCusName() );
                lblAmount.setText( String.valueOf( bookingData.getTotal() ) );
                btnPay.setDisable( false );
            } else {
                new Alert( Alert.AlertType.ERROR, "Already Paid !" ).show();
                btnPay.setDisable( true );
            }
        } catch ( SQLException e ) {
            e.printStackTrace();
        }
    }

//    @FXML
//    void btnSearchOnAction(ActionEvent event) {
//        String id = txtSearchBar.getText();
//        var model = new PaymentModel();
//        try {
//
//            var dto = model.SearchModel(id);
//            if (dto != null){
//                fillField(dto);
//            }else {
//                new Alert(Alert.AlertType.INFORMATION,"Customer not found").show();
//            }
//        } catch (SQLException e) {
//            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
//        }
//    }

//    private void fillField(PaymentDto dto) {
//
//        txtBookingId.setText(dto.getBookingId());
//        txtCustomer.setText(dto1.getCusName());
//        txtAmount.setText(String.valueOf(dto.getAmount()));
//    }

    @FXML
    void btnReportOnAction(ActionEvent event) throws JRException, SQLException {
        InputStream resourceAsStream = getClass().getResourceAsStream("/reports/paymentReport.jrxml");
        JasperDesign jasperDesign = JRXmlLoader.load(resourceAsStream);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);
    }

    private void time() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa");
        Timeline timeline = new Timeline(new KeyFrame(javafx.util.Duration.ZERO, e -> {

            lblTime.setText(timeFormat.format(new java.util.Date()));
            lblDate.setText(dateFormat.format(new java.util.Date()));
        }), new KeyFrame( Duration.seconds(1)));
        timeline.setCycleCount( Animation.INDEFINITE);

        timeline.play();
    }

    public void setPaymentId() {
        try {
            lblPaymentID.setText( paymentModel.generateNextId() );
        } catch ( SQLException e ) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setPaymentId();
        time();
        setCellValueFactory();
        loadAllPayments();
        time();
        setPaymentId();
        loadAllBookingId();

    }


    private void loadAllBookingId() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<BookingDto> dtos = bookingModel.getAllBooking();
            for (BookingDto dto : dtos) {
                obList.add(dto.getBookingId());
            }
            cmbBookingId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
    }

    @FXML
    void btnDeleteOnAction(ActionEvent actionEvent) {
        PaymentTm selectedPayment = tblPayment.getSelectionModel().getSelectedItem();

        if (selectedPayment != null) {
            String id = selectedPayment.getPaymentId();

            try {
                boolean isDeleted = paymentModel.deletePayment(id);

                if (isDeleted) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Payment Deleted Successfully").show();
                    loadAllPayments();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete payment").show();
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Add this line to print the stack trace
                new Alert(Alert.AlertType.ERROR, "Error deleting payment: " + e.getMessage()).show();
            }
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Please select a Payment to delete.").show();
        }
    }
}
