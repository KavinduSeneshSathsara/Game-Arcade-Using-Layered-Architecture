package lk.ijse.GameCafe.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import lk.ijse.GameCafe.dao.custom.DashBoardDAO;
import lk.ijse.GameCafe.dao.custom.impl.DashBoardDAOImpl;
import lk.ijse.GameCafe.dto.DashboardTableDto;
import lk.ijse.GameCafe.model.BookingModel;
import lk.ijse.GameCafe.model.CustomerModel;
import lk.ijse.GameCafe.model.DashBoardModel;
import lk.ijse.GameCafe.model.EmployeeModel;

import java.sql.SQLException;
import java.util.List;

public class DashboardFormController {

    @FXML
    private TableColumn<?, ?> colBookingId;

    @FXML
    private TableColumn<?, ?> colCustomerId;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private Pane pane;

    @FXML
    private TableView<?> tblUpcomingAppoinment;

    @FXML
    private TableView<DashboardTableDto> tblDashboard;

    @FXML
    private Label txtBookingCount;

    @FXML
    private Label txtCustomerCount;

    @FXML
    private Label txtEmployeeCount;

    private static DashboardFormController controller;

    DashBoardDAO dashBoardDAO = new DashBoardDAOImpl();

    public DashboardFormController() {

        controller = this;
    }

    public static DashboardFormController getInstance() {

        return controller;
    }

    public void initialize() throws ClassNotFoundException {
        start();
    }

    public void start() throws ClassNotFoundException {

        EmployeeModel employeeModel = new EmployeeModel();
        CustomerModel customerModel = new CustomerModel();
        BookingModel bookingModel = new BookingModel();

        try {
            txtEmployeeCount.setText(employeeModel.totalEmployeeCount());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            txtCustomerCount.setText(customerModel.totalCustomerCount());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            txtBookingCount.setText(bookingModel.totalBookingCount());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        setCellValueFactory();
        loadAllData();
    }

    private void loadAllData() throws ClassNotFoundException {
//         DashBoardModel dashBoardModel = new DashBoardModel();
        ObservableList<DashboardTableDto> obList = FXCollections.observableArrayList();
        try {
//            List<DashboardTableDto> list = dashBoardModel.dashbaordTableData();
            List<DashboardTableDto> list = dashBoardDAO.dashbaordTableData();

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

    private void setCellValueFactory() {
        colBookingId.setCellValueFactory(new PropertyValueFactory<>("bookingId"));
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("cusId"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
    }
}
