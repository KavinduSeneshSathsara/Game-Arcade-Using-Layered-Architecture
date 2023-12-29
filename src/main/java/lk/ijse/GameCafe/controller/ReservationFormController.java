package lk.ijse.GameCafe.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import lk.ijse.GameCafe.dto.BookingDto;
import lk.ijse.GameCafe.dto.CustomerDto;
import lk.ijse.GameCafe.dto.PlayStationDto;
import lk.ijse.GameCafe.dto.tm.CartTm;
import lk.ijse.GameCafe.model.BookingModel;
import lk.ijse.GameCafe.model.CustomerModel;
import lk.ijse.GameCafe.model.PlayStationModel;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class ReservationFormController {
    @FXML
    private Pane pane;

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
    private TextField txtContact;

    @FXML
    private TextField txtCustMail;

    @FXML
    private TextField txtCustName;

    @FXML
    private Text lblTime;

    @FXML
    private Text lblDate;

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

    private PlayStationModel stationModel = new PlayStationModel();
    private final ObservableList<CartTm> cart = FXCollections.observableArrayList();
    private BookingModel bookingModel = new BookingModel();
    private CustomerModel customerModel = new CustomerModel();

    public void initialize() {
        setCellValueFactory();
        cmbStartTime.setDisable(true);
        cmbEndTime.setDisable(true);
        cmbStation.setDisable(true);
        loadTimeZone();
        time();
        lblNetTotal.setText("");
        lblRate.setText("");
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
    void btnAddToListOnAction(ActionEvent event) {
        String station = cmbStation.getValue();

        Button btn = new Button("remove");
        btn.setCursor(Cursor.HAND);

        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                int index = tblCart.getSelectionModel().getSelectedIndex();
                cart.remove(index);
                tblCart.refresh();
            }
        });
        double total=0;
        try {
             total= getHours(cmbStartTime.getValue(), cmbEndTime.getValue()).doubleValue() * stationModel.getRate(station);
        }catch (SQLException e){
            e.printStackTrace();
        }

        try {
            cart.add(new CartTm(
                    station,
                    getHours(cmbStartTime.getValue(),cmbEndTime.getValue()),
                    stationModel.getRate(station),
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
    void btnBookOnAction(ActionEvent event) throws ParseException {
        Date nowDate = Date.valueOf(LocalDate.now());
        Time nowTime = Time.valueOf(LocalTime.now());
        Time startTime = makeTime(cmbStartTime.getValue());
        Time endTime = makeTime(cmbEndTime.getValue());
        try {
            CustomerDto customerDto = customerModel.getCustomer(txtContact.getText());
            boolean isSaved = bookingModel.saveBooking(new BookingDto(bookingModel.generateNextId(), customerDto.getCusId(), nowDate, nowTime, startTime, endTime, "Pending", Double.parseDouble(lblNetTotal.getText())));
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Saved Successfully").show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void cmbEndTimeOnAction(ActionEvent event) {
        loadAllStations();
        cmbStation.setDisable(false);
    }

    private void loadAllStations() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<PlayStationDto> dtos = stationModel.getAllStationNotInThisTime(makeTime(cmbStartTime.getValue()),makeTime(cmbEndTime.getValue()));
            for (PlayStationDto dto : dtos) {
                obList.add(dto.getPlayStationId());
            }
            cmbStation.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbStartTimeOnAction(ActionEvent event) {

    }

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
    void txtContactOnAction(ActionEvent event) {
        try {
            CustomerDto customerDto = customerModel.getCustomer(txtContact.getText());
            txtCustName.setText(customerDto.getCusName());
            txtCustMail.setText(customerDto.getCusEmail());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}