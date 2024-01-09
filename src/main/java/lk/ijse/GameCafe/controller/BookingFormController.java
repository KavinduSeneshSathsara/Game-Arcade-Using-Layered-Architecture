package lk.ijse.GameCafe.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.util.Duration;
import lk.ijse.GameCafe.dao.custom.BookingDAO;
import lk.ijse.GameCafe.dao.custom.BookingDetailDAO;
import lk.ijse.GameCafe.dao.custom.CustomerDAO;
import lk.ijse.GameCafe.dao.custom.PlayStationDAO;
import lk.ijse.GameCafe.dao.custom.impl.BookingDAOImpl;
import lk.ijse.GameCafe.dao.custom.impl.BookingDetailDAOImpl;
import lk.ijse.GameCafe.dao.custom.impl.CustomerDAOImpl;
import lk.ijse.GameCafe.dao.custom.impl.PlayStationDAOImpl;
import lk.ijse.GameCafe.db.DbConnection;
import lk.ijse.GameCafe.dto.*;
import lk.ijse.GameCafe.dto.tm.CartTm;

import java.net.URL;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class BookingFormController implements Initializable{
    @FXML
    private TableView<CartTm> tblCart;

    @FXML
    private TableColumn<?, ?> colStationId;

    @FXML
    private TableColumn<?, ?> colHours;

    @FXML
    private TableColumn<?, ?> colRate;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private ComboBox<String> cmbCusNumbers;

    @FXML
    private Text lblTime;

    @FXML
    private Text lblDate;

    @FXML
    private Label lblCustomerEmail;

    @FXML
    private Label lblCustomerName;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<String> cmbStartTime;

    @FXML
    private ComboBox<String> cmbEndTime;

    @FXML
    private ComboBox<String> cmbTimeZone;

    @FXML
    private ComboBox<String> cmbStation;

    @FXML
    private Text lblRate;

    @FXML
    private Text lblNetTotal;

    @FXML
    private Text lblOrderId;

    @FXML
    private JFXButton btnAddToList;

    private final ObservableList<CartTm> cart = FXCollections.observableArrayList();

    CustomerDAO customerDAO = new CustomerDAOImpl();
    BookingDAO bookingDAO = new BookingDAOImpl();
    PlayStationDAO playStationDAO = new PlayStationDAOImpl();
    BookingDetailDAO bookingDetailDAO = new BookingDetailDAOImpl();

    public void loadOrderId() throws ClassNotFoundException {
        try {
            lblOrderId.setText( bookingDAO.generateNextId() );
        } catch ( SQLException e ) {
            e.printStackTrace();
        }
    }
    private void time() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa");
        Timeline timeline = new Timeline(new KeyFrame(javafx.util.Duration.ZERO, e -> {

            lblTime.setText(timeFormat.format(new java.util.Date()));
            lblDate.setText(dateFormat.format(new java.util.Date()));
        }), new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);

        timeline.play();
    }

    private void setCellValueFactory() {
        colStationId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colHours.setCellValueFactory(new PropertyValueFactory<>("hours"));
        colRate.setCellValueFactory(new PropertyValueFactory<>("rate"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    private void loadTimeZone() {
        ObservableList<String> timeList = FXCollections.observableArrayList();
        timeList.add("AM");
        timeList.add("PM");
        cmbTimeZone.setItems(timeList);
    }

    @FXML
    void btnAddToListOnAction(ActionEvent event) throws ClassNotFoundException {
        String station = cmbStation.getValue();

        Button btn = new Button("remove");
        btn.setCursor(Cursor.HAND);

        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                CartTm selectedCartItem = tblCart.getSelectionModel().getSelectedItem();
                cart.remove(selectedCartItem);
                tblCart.refresh();
                calculateNetTotal();
            }
        });

        double total=0;

        try {
            total= getHours(cmbStartTime.getValue(), cmbEndTime.getValue()).doubleValue() * playStationDAO.getRate(station);
        }catch (SQLException e){
            e.printStackTrace();
        }

        try {
            cart.add(new CartTm(
                    station,
                    getHours(cmbStartTime.getValue(),cmbEndTime.getValue()),
                    playStationDAO.getRate(station),
                    total,
                    btn
            ));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        tblCart.setItems(cart);
        calculateNetTotal();

        cmbStartTime.setDisable(true);
        cmbEndTime.setDisable(true);
        datePicker.setDisable(true);
        cmbTimeZone.setDisable(true);
    }



    private Double getHours(String startTime , String endTime) {
        String time1 = startTime + " " + cmbTimeZone.getValue();
        String time2 = endTime + " " + cmbTimeZone.getValue();

        SimpleDateFormat format = new SimpleDateFormat("h.mm aa");

        try {
            java.util.Date date1 = format.parse(time1);
            java.util.Date date2 = format.parse(time2);
            long difference = date2.getTime() - date1.getTime();
            int diffMinutes = (int) (difference / (60 * 1000) % 60);
            int diffHours = (int) (difference / (60 * 60 * 1000) % 24);
            String time =null;
            if (diffMinutes==30)time= diffHours + ".50";
            else time=diffHours+".00";
            Double hours = Double.parseDouble(time);
            return hours;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    private void calculateNetTotal() {
        double total = 0;
        for (int i = 0; i < tblCart.getItems().size(); i++) {
            total += (double) colTotal.getCellData(i);
        }
        lblNetTotal.setText(String.valueOf(total));
    }

    private Time makeTime(String rawTime) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("h.mm aa");
            // Parse the string to get a Date object
            java.util.Date date = sdf.parse(rawTime + " " + cmbTimeZone.getValue());
            // Convert the Date object to a Time object
            System.out.println(new Time(date.getTime()));
            return new Time(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @FXML
    void btnBookOnAction(ActionEvent event) throws ParseException, SQLException, ClassNotFoundException {
        Date nowDate = Date.valueOf( datePicker.getValue() );
        Time nowTime = Time.valueOf(LocalTime.now());
        Time startTime = makeTime(cmbStartTime.getValue());
        Time endTime = makeTime(cmbEndTime.getValue());

        Connection connection = null;

        try {

            CustomerDto customerDto = customerDAO.getCustomer(String.valueOf(cmbCusNumbers.getValue()));

            connection = DbConnection.getInstance( ).getConnection( );
            connection.setAutoCommit( false );

            boolean isSaved = bookingDAO.saveBooking(new BookingDto(bookingDAO.generateNextId(), customerDto.getCusId(), nowDate, nowTime, startTime, endTime, "Not Paid", Double.parseDouble(lblNetTotal.getText())));
            if (isSaved) {
                boolean saved = bookingDetailDAO.saveDetails( tblCart.getItems().stream().map(tm -> new BookingDetailsDto(lblOrderId.getText(), tm.getId())).collect(Collectors.toList()));

                if ( saved ) {
                    connection.commit();
                    new Alert(Alert.AlertType.CONFIRMATION, "Saved Successfully").show();
                    loadOrderId();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            connection.rollback();
        } finally {
            connection.setAutoCommit( true );
        }
    }

    @FXML
    void cmbEndTimeOnAction(ActionEvent event) {
        cmbStation.setDisable(false);
    }

    private void loadAllStations() throws ClassNotFoundException {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<PlayStationDto> dtos = playStationDAO.getAllPlayStations( );
            for (PlayStationDto dto : dtos) {
                obList.add(dto.getPlayStationId());
            }
            cmbStation.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbStartTimeOnAction(ActionEvent event) {}

    @FXML
    void cmbTimeZoneOnAcion(ActionEvent event) {
        String value = cmbTimeZone.getValue();
        ObservableList<String> oblist = FXCollections.observableArrayList();
        if (value.equals("AM")) {
            for (int i = 8; i < 12; i++) {
                oblist.add(i + ".00");
                oblist.add(i + ".30");
            }
        } else {
            oblist.add("12.00");
            oblist.add("12.30");
            for (int i = 1; i < 6; i++) {
                oblist.add(i + ".00");
                oblist.add(i + ".30");
            }
        }
        cmbStartTime.setItems(oblist);
        cmbEndTime.setItems(oblist);
        cmbEndTime.setDisable(false);
        cmbStartTime.setDisable(false);
    }

    @FXML
    void cmbCusNumbersOnAction(Event event) throws ClassNotFoundException {
        try {
            CustomerDto customerDto = customerDAO.getCustomer(String.valueOf(cmbCusNumbers.getValue()));
            lblCustomerName.setText(customerDto.getCusName());
            lblCustomerEmail.setText(customerDto.getCusEmail());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void cmbStationOnAction(ActionEvent event) throws ClassNotFoundException {
        try {
            List<BookingDto> allBookings = bookingDAO.getAllBookings( cmbStation.getValue( ), Date.valueOf( datePicker.getValue( ) ) );

            boolean overlap = isOverlap( allBookings, makeTime( cmbStartTime.getValue() + " " + cmbTimeZone.getValue() ).toLocalTime(), makeTime( cmbEndTime.getValue() + " " + cmbTimeZone.getValue() ).toLocalTime() );

            if ( overlap ) {
                new Alert( Alert.AlertType.ERROR, "Play station is already booked for this time" ).show();
                btnAddToList.setDisable( true );
            } else {
                btnAddToList.setDisable( false );
            }
        } catch ( SQLException e ) {
            e.printStackTrace();
        }
    }

    public boolean isOverlap(List<BookingDto> existingBookings, LocalTime newBookingStartTime, LocalTime newBookingEndTime) {
        for (BookingDto existingBooking : existingBookings) {

            if (isOverlapTime( newBookingStartTime, newBookingEndTime, existingBooking.getStartTime().toLocalTime(), existingBooking.getEndTime().toLocalTime() )) {
                return true;  // Overlap found
            }
        }
        return false;  // No overlap found
    }

    public boolean isOverlapTime(LocalTime newStartTime, LocalTime newEndTime, LocalTime startTime, LocalTime endTime) {
        return (startTime.isBefore(newEndTime) && endTime.isAfter(newStartTime));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCellValueFactory();
        cmbStartTime.setDisable(true);
        cmbEndTime.setDisable(true);
        cmbStation.setDisable(true);
        loadTimeZone();
        time();
        lblNetTotal.setText("");
        lblRate.setText("");

        try {
            loadOrderId();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            loadAllStations();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            loadAllNumbers();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        tblCart.setItems(cart);
    }

    private void loadAllNumbers() throws ClassNotFoundException {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<CustomerDto> dtos = customerDAO.getAllCustomers();
            for (CustomerDto dto : dtos) {
                obList.add(dto.getCusContactNum());
            }
            cmbCusNumbers.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}