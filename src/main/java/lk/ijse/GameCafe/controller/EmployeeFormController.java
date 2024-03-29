package lk.ijse.GameCafe.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.GameCafe.bo.BOFactory;
import lk.ijse.GameCafe.bo.custom.EmployeeBO;
import lk.ijse.GameCafe.dto.EmployeeDto;
import lk.ijse.GameCafe.view.tdm.tm.EmployeeTm;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class EmployeeFormController {

        @FXML
        private TableColumn<?, ?> colEmpAddress;

        @FXML
        private TableColumn<?, ?> colEmpContactNum;

        @FXML
        private TableColumn<?, ?> colEmpName;

        @FXML
        private TableColumn<?, ?> colEmpId;

        @FXML
        private TableColumn<?, ?> colEmpSalary;

        @FXML
        private TableView<EmployeeTm> tblEmployee;

        @FXML
        private TextField txtEmpContactNum;

        @FXML
        private TextField txtEmpName;

        @FXML
        private TextField txtEmpId;

        @FXML
        private TextField txtEmpSalary;

        @FXML
        private TextField txtEmpAddress;

        @FXML
        private TextField txtSearchBar;

    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.EMPLOYEE);

    @FXML
    void btnClearOnAction(ActionEvent event) throws ClassNotFoundException {
        clearFields();
        generateEmployeeId();
    }

    private void generateEmployeeId() throws ClassNotFoundException {
        try {
            String newCustomerId = employeeBO.generateEmployeeId();

            txtEmpId.setText(newCustomerId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    private void clearFields() {

        txtEmpId.clear();
        txtEmpName.clear();
        txtEmpContactNum.clear();
        txtEmpSalary.clear();
        txtEmpAddress.clear();

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws ClassNotFoundException {

        boolean isEmployeeValidated = ValidateEmployee();

        if (!isEmployeeValidated){
            return;
        }

        String id = txtEmpId.getText();
        String name = txtEmpName.getText();
        String contactNum = txtEmpContactNum.getText();
        String address = txtEmpAddress.getText();
        String salary = txtEmpSalary.getText();

        EmployeeDto dto = new EmployeeDto(id, name, contactNum, salary, address);
        try {
            boolean isSaved = employeeBO.saveEmployee(dto);

            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION, "Employee Saved Successfully");
                loadAllEmployees();
                clearFields();
                generateEmployeeId();
            }
        }catch (SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public void initialize() throws ClassNotFoundException {
        setCellValueFactory();
        loadAllEmployees();
        generateEmployeeId();

        tblEmployee.setOnMouseClicked(event -> {
            if(event.getClickCount() == 1){
                EmployeeTm selectedEmployee = tblEmployee.getSelectionModel().getSelectedItem();

                if(selectedEmployee != null){
                    fillFields(selectedEmployee);
                }
            }
        });
    }
    private void fillFields(EmployeeTm tm) {
        txtEmpId.setText(tm.getEmpId());
        txtEmpName.setText(tm.getEmpName());
        txtEmpContactNum.setText(tm.getEmpContactNum());
        txtEmpSalary.setText(tm.getEmpSalary());
        txtEmpAddress.setText(tm.getEmpAddress());
    }
    private void setCellValueFactory() {
        colEmpId.setCellValueFactory(new PropertyValueFactory<>("empId"));
        colEmpName.setCellValueFactory(new PropertyValueFactory<>("empName"));
        colEmpContactNum.setCellValueFactory(new PropertyValueFactory<>("empContactNum"));
        colEmpSalary.setCellValueFactory(new PropertyValueFactory<>("empSalary"));
        colEmpAddress.setCellValueFactory(new PropertyValueFactory<>("empAddress"));
    }

    private void loadAllEmployees() throws ClassNotFoundException {
        ObservableList<EmployeeTm> obList = FXCollections.observableArrayList();

        try {
            List<EmployeeDto> list = employeeBO.getAllEmployees();

            for (EmployeeDto dto : list){
                EmployeeTm  employeeTm = new EmployeeTm(dto.getEmpId(),
                        dto.getEmpName(),
                        dto.getEmpContactNum(),
                        dto.getEmpSalary(),
                        dto.getEmpAddress());

                obList.add(employeeTm);
            }
            tblEmployee.setItems(obList);

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private boolean ValidateEmployee() {
        String empIdText = txtEmpId.getText();
        boolean isEmployeeIdValidated = Pattern.matches("[E][0-9]{4}", empIdText);

        if (!isEmployeeIdValidated) {
            new Alert(Alert.AlertType.ERROR, "invalid Employee Id!").show();
            return false;
        }

        String empNameText = txtEmpName.getText();
        boolean isEmployeeNameValidated = Pattern.matches("[A-Za-z]+", empNameText);

        if(!isEmployeeNameValidated){
            new Alert(Alert.AlertType.ERROR, "invalid Employee Name!").show();
            return false;
        }

        String EmpContactNumText = txtEmpContactNum.getText();
        boolean isEmpContactNumberValidated = Pattern.matches("[0-9]{10}", EmpContactNumText  );

        if(!isEmpContactNumberValidated){
            new Alert(Alert.AlertType.ERROR, "invalid Employee Contact Number!").show();
            return false;

        }

        String empSalaryText = txtEmpSalary.getText();
        boolean isEmpSalaryValidated = Pattern.matches("[0-9](.*)", empSalaryText);

        if(!isEmpSalaryValidated){
            new Alert(Alert.AlertType.ERROR, "invalid Employee Salary!").show();
            return false;

        }

        String empAddressText = txtEmpAddress.getText();
        boolean isEmpAddressValidated = Pattern.matches("[A-Za-z]+(.*)", empAddressText);

        if(!isEmpAddressValidated){
            new Alert(Alert.AlertType.ERROR, "invalid Employee Address!").show();
            return false;

        }

        return true;
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws ClassNotFoundException {
        String id = txtEmpId.getText();
        try {
            boolean isDeleted = employeeBO.deleteEmployee(id);
            if(isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION,"Patient Deleted Successfully").show();
                loadAllEmployees();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws ClassNotFoundException {
        String id = txtEmpId.getText();
        String name = txtEmpName.getText();
        String email = txtEmpContactNum.getText();
        String contactNo = txtEmpSalary.getText();
        String address = txtEmpAddress.getText();

        EmployeeDto dto = new EmployeeDto(id,name,email,contactNo,address);
        try {
            boolean isUpdated = employeeBO.updateEmployee(dto);
            if(isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION,"Employee Updated Successfully").show();
                loadAllEmployees();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) throws ClassNotFoundException {
        String id = txtSearchBar.getText();
        try {

            EmployeeDto dto = employeeBO.searchEmployee(id);
            if (dto != null){
                fillField(dto);
            }else {
                new Alert(Alert.AlertType.INFORMATION,"Employee not found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void fillField(EmployeeDto dto) {
        txtEmpId.setText(dto.getEmpId());
        txtEmpName.setText(dto.getEmpName());
        txtEmpContactNum.setText(dto.getEmpContactNum());
        txtEmpSalary.setText(dto.getEmpSalary());
        txtEmpAddress.setText(dto.getEmpAddress());
    }

}
