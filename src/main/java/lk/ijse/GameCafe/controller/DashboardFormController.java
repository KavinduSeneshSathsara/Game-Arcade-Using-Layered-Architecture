package lk.ijse.GameCafe.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;

import lk.ijse.GameCafe.bo.BOFactory;
import lk.ijse.GameCafe.bo.custom.BookingBO;
import lk.ijse.GameCafe.bo.custom.CustomerBO;
import lk.ijse.GameCafe.bo.custom.DashboardBO;
import lk.ijse.GameCafe.bo.custom.EmployeeBO;
import lk.ijse.GameCafe.dto.DashboardTableDto;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

public class DashboardFormController {

    @FXML
    private TableColumn<?, ?> colBookingId;

    @FXML
    private TableColumn<?, ?> colCustomerId;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableView<DashboardTableDto> tblDashboard;

    @FXML
    private Label txtBookingCount;

    @FXML
    private Label txtCustomerCount;

    @FXML
    private Label txtEmployeeCount;

    @FXML
    private Label lblTime;

    @FXML
    private Label lblDate;

    private static DashboardFormController controller;

    BookingBO bookingBO = (BookingBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.BOOKING);
    CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);
    DashboardBO dashboardBO = (DashboardBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.DASHBOARD);
    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.EMPLOYEE);

    public DashboardFormController() {

        controller = this;
    }

    public static DashboardFormController getInstance() {

        return controller;
    }

    public void initialize() throws ClassNotFoundException {
        start();
        time();
    }

    public void start() throws ClassNotFoundException {

        try {
            txtEmployeeCount.setText(employeeBO.totalEmployeeCount());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            txtCustomerCount.setText(customerBO.totalCustomerCount());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            txtBookingCount.setText(bookingBO.totalBookingCount());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        setCellValueFactory();
        loadAllData();
    }

    private void loadAllData() throws ClassNotFoundException {
        ObservableList<DashboardTableDto> obList = FXCollections.observableArrayList();
        try {
            List<DashboardTableDto> list = dashboardBO.dashboardTableData();

            for (DashboardTableDto dto : list){
                DashboardTableDto  dashboardTableDto = new DashboardTableDto(dto.getBookingId(),
                        dto.getCusId(),
                        dto.getTotal());

                obList.add(dashboardTableDto);
            }
            tblDashboard.setItems(obList);

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
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
        colBookingId.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("cusId"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
    }
}
