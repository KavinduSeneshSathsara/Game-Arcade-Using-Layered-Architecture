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
import lk.ijse.GameCafe.dao.custom.BookingDAO;
import lk.ijse.GameCafe.dao.custom.CustomerDAO;
import lk.ijse.GameCafe.dao.custom.PaymentDAO;
import lk.ijse.GameCafe.dao.custom.impl.BookingDAOImpl;
import lk.ijse.GameCafe.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.GameCafe.dao.custom.impl.PaymentDAOImpl;
import lk.ijse.GameCafe.db.DbConnection;
import lk.ijse.GameCafe.dto.BookingDto;
import lk.ijse.GameCafe.dto.CustomerDto;
import lk.ijse.GameCafe.dto.PaymentDto;
import lk.ijse.GameCafe.dto.tm.PaymentTm;
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
    private Label lblAmount;

    @FXML
    private Label lblCustomerName;

    @FXML
    private Text lblPaymentID;

    @FXML
    private JFXButton btnPay;

    PaymentDAO paymentDAO = new PaymentDAOImpl();
    BookingDAO bookingDAO = new BookingDAOImpl();
    CustomerDAO customerDAO = new CustomerDAOImpl();
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
    void btnPayOnAction(ActionEvent event) throws SQLException, MessagingException, ClassNotFoundException {
        Connection connection = null;
        try {
            connection = DbConnection.getInstance( ).getConnection( );
            connection.setAutoCommit( false );

            boolean savePayment = paymentDAO.savePayment( new PaymentDto(
                    lblPaymentID.getText( ),
                    cmbBookingId.getValue(),
                    Date.valueOf( LocalDate.now( ) ),
                    Time.valueOf( LocalTime.now( ) ),
                    Double.parseDouble(lblAmount.getText( ) )
            ) );

            if ( savePayment ) {
                boolean isUpdated = bookingDAO.updateStatus(String.valueOf(cmbBookingId.getValue( )));
                loadAllPayments();

                if ( isUpdated ) {
                    connection.commit();
                    new Alert( Alert.AlertType.CONFIRMATION, "Payment Saved" ).show();
                    setPaymentId();
                    loadAllPayments();
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

    private void loadAllPayments() throws ClassNotFoundException {
        System.out.println("load");
        ObservableList<PaymentTm> obList = FXCollections.observableArrayList();

        try{
            List<PaymentDto> list = paymentDAO.getAllPayments();

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
    void cmbBookingIdOnAction(ActionEvent event) throws ClassNotFoundException {
        try {
            BookingDto bookingData = bookingDAO.getBookingData((String) cmbBookingId.getValue());

            if ( bookingData != null && bookingData.getStatus().equals( "Not Paid" ) ) {

                CustomerDto dto = customerDAO.SearchModel( bookingData.getCus_id() );
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

    public void setPaymentId() throws ClassNotFoundException {
        try {
            lblPaymentID.setText( paymentDAO.generateNextId() );
        } catch ( SQLException e ) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            setPaymentId();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        time();
        setCellValueFactory();

        try {
            loadAllPayments();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        time();

        try {
            setPaymentId();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            loadAllBookingId();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    private void loadAllBookingId() throws ClassNotFoundException {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<BookingDto> dtos = bookingDAO.getAllBooking();
            for (BookingDto dto : dtos) {
                obList.add(dto.getBookingId());
            }
            cmbBookingId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {}

    @FXML
    void btnDeleteOnAction(ActionEvent actionEvent) throws ClassNotFoundException {
        PaymentTm selectedPayment = tblPayment.getSelectionModel().getSelectedItem();

        if (selectedPayment != null) {
            String id = selectedPayment.getPaymentId();

            try {
                boolean isDeleted = paymentDAO.deletePayment(id);

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
