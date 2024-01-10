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
import lk.ijse.GameCafe.bo.custom.CustomerBO;
import lk.ijse.GameCafe.db.DbConnection;
import lk.ijse.GameCafe.dto.CustomerDto;
import lk.ijse.GameCafe.view.tdm.tm.CustomerTm;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class CustomerFormController {

    @FXML
    private TableColumn<?, ?> colCusId;

    @FXML
    private TableColumn<?, ?> colCusNum;

    @FXML
    private TableColumn<?, ?> colCusEmail;

    @FXML
    private TableColumn<?, ?> colCusName;

    @FXML
    private TableColumn<?, ?> colCusAddress;

    @FXML
    private TableView<CustomerTm> tblCustomer;

    @FXML
    private TextField txtCusContactNum;

    @FXML
    private TextField txtCusEmail;

    @FXML
    private TextField txtCusId;

    @FXML
    private TextField txtCusName;

    @FXML
    private TextField txtCusAddress;

    @FXML
    private TextField txtSearchBar;

    CustomerBO customerBO = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);

    @FXML
    void ButtonDeleteOnAction(ActionEvent event) throws ClassNotFoundException {
        CustomerTm selectedCustomer = tblCustomer.getSelectionModel().getSelectedItem();

        if (selectedCustomer != null) {
            String id = txtCusId.getText();
            try {
                boolean isDeleted = customerBO.deleteCustomer(id);

                if (isDeleted) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Customer Deleted Successfully").show();
                    loadAllCustomers();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }else{
            new Alert(Alert.AlertType.INFORMATION, "Please select a customer to delete. ");
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) throws ClassNotFoundException {
        clearFields();
        generateCustomerId();
    }
    private void clearFields() {

        txtCusId.clear();
        txtCusContactNum.clear();
        txtCusEmail.clear();
        txtCusName.clear();
        txtCusAddress.clear();

    }
    private void fillField(CustomerDto dto) {
        txtCusId.setText(dto.getCusId());
        txtCusName.setText(dto.getCusName());
        txtCusEmail.setText(dto.getCusEmail());
        txtCusContactNum.setText(dto.getCusContactNum());
        txtCusAddress.setText(dto.getCusAddress());
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws ClassNotFoundException {

        boolean isCustomerValidated = ValidateCustomer();

        if (!isCustomerValidated) {
            return;
        }

        String cusId = txtCusId.getText();
        String cusContactNum = txtCusContactNum.getText();
        String cusEmail = txtCusEmail.getText();
        String cusName = txtCusName.getText();
        String cusAddress = txtCusAddress.getText();

        CustomerDto dto = new CustomerDto(cusId, cusContactNum, cusEmail, cusName, cusAddress);
        try{
            boolean isSaved = customerBO.saveCustomer(dto);

            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION, "Customer Saved Successfully").show();
                loadAllCustomers();
                clearFields();
                generateCustomerId();
            }
        }catch(SQLException e){
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    private void generateCustomerId() throws ClassNotFoundException {
        try {
            String newCustomerId = customerBO.generateCustomerId();

            txtCusId.setText(newCustomerId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    public void initialize() throws ClassNotFoundException {
        setCellValueFactory();
        loadAllCustomers();
        generateCustomerId();

        tblCustomer.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {

                CustomerTm selectedCustomer = tblCustomer.getSelectionModel().getSelectedItem();

                if (selectedCustomer != null) {
                    fillFields(selectedCustomer);
                }
            }
        });
    }

    private void fillFields(CustomerTm tm) {
        txtCusId.setText(tm.getCusId());
        txtCusName.setText(tm.getCusName());
        txtCusEmail.setText(tm.getCusEmail());
        txtCusContactNum.setText(tm.getCusContactNum());
        txtCusAddress.setText(tm.getCusAddress());
    }

    private void setCellValueFactory() {
        colCusId.setCellValueFactory(new PropertyValueFactory<>("cusId"));
        colCusNum.setCellValueFactory(new PropertyValueFactory<>("cusContactNum"));
        colCusEmail.setCellValueFactory(new PropertyValueFactory<>("cusEmail"));
        colCusName.setCellValueFactory(new PropertyValueFactory<>("cusName"));
        colCusAddress.setCellValueFactory(new PropertyValueFactory<>("cusAddress"));
    }

    private void loadAllCustomers() throws ClassNotFoundException {

        ObservableList<CustomerTm> obList = FXCollections.observableArrayList();
         try{
             List <CustomerDto> list = customerBO.getAllCustomers();

             for (CustomerDto dto : list){
                 CustomerTm customerTm = new CustomerTm(
                         dto.getCusId(),
                         dto.getCusContactNum(),
                         dto.getCusEmail(),
                         dto.getCusName(),
                         dto.getCusAddress());

                 obList.add(customerTm);
             }
             tblCustomer.setItems(obList);

         }catch (SQLException e){

             new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
         }
        System.out.println("loading");
    }

    private boolean ValidateCustomer() {
        
        String cusIdText = txtCusId.getText();
        boolean isCustomerIdValidated = Pattern.matches("[C][0-9]{4}", cusIdText);

        if (!isCustomerIdValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Customer Id!!").show();
            return false;
        }

        String CusContactNumText = txtCusContactNum.getText();
        boolean isCusContactNumValidated = Pattern.matches("[0-9]{10}", CusContactNumText);
        
        if (!isCusContactNumValidated){
            new Alert(Alert.AlertType.ERROR, "Invalid Customer Contact Number!!").show();
            return false;
        }

        String CusEmailText = txtCusEmail.getText();
        boolean isCustomerEmailValidated = Pattern.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}", CusEmailText);

        if (!isCustomerEmailValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Customer Email!!").show();
            return false;
        }

        String CusNameText = txtCusName.getText();
        boolean isCustomerNameValidated = Pattern.matches("[A-Za-z](.*)", CusNameText);
        
        if (!isCustomerNameValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Customer Name!!").show();
            return false;
        }

        String CusAddressText = txtCusAddress.getText();
        boolean isCustomerAddressValidated = Pattern.matches("^[a-zA-Z0-9\\s.,#-]+$", CusAddressText);

        if (!isCustomerAddressValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Customer Address!!").show();
            return false;
        }
        
        return true;
        
    }
        @FXML
        void btnSearchOnAction (ActionEvent event) throws ClassNotFoundException {
            String id = txtSearchBar.getText();
            try {
                CustomerDto dto = customerBO.searchCustomer(id);
                if (dto != null){
                    fillField(dto);
                }else {
                    new Alert(Alert.AlertType.INFORMATION,"Customer not found").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }
        }

        @FXML
        void btnUpdateOnAction (ActionEvent event) throws ClassNotFoundException {

            String cusId = txtCusId.getText();
            String cusContactNum = txtCusContactNum.getText();
            String cusEmail = txtCusEmail.getText();
            String cusName = txtCusName.getText();
            String cusAddress = txtCusAddress.getText();

            CustomerDto dto = new CustomerDto(cusId, cusContactNum, cusEmail, cusName, cusAddress);
            try {
                boolean isUpdated = customerBO.updateCustomer(dto);
                if (isUpdated){
                    new Alert(Alert.AlertType.CONFIRMATION, "Customer Updated Successfully").show();
                    loadAllCustomers();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    @FXML
    void btnReportOnAction(ActionEvent event) throws JRException, SQLException {
        InputStream resourceAsStream = getClass().getResourceAsStream("/reports/customerReport.jrxml");
        JasperDesign jasperDesign = JRXmlLoader.load(resourceAsStream);
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DbConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);
    }
}
