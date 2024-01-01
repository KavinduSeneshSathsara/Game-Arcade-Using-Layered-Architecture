package lk.ijse.GameCafe.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import lk.ijse.GameCafe.dto.CustomerDto;
import lk.ijse.GameCafe.dto.PlayStationDto;
import lk.ijse.GameCafe.dto.tm.CustomerTm;
import lk.ijse.GameCafe.dto.tm.PlayStationTm;
import lk.ijse.GameCafe.model.PlayStationModel;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class PlayStationFormController {

    @FXML
    private JFXButton btnSave;

    @FXML
    private Pane pane;

    @FXML
    private TableView<PlayStationTm> tblPlayStation;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colNumber;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableColumn<?, ?> colRate;

    @FXML
    private TextField txtPlayStationId;

    @FXML
    private TextField txtPlayStationNumber;

    @FXML
    private TextField txtSearchBar;

    @FXML
    private TextField txtRate;

    PlayStationModel playStationModel = new PlayStationModel();

    public void initialize(){
        setCellValueFactory();
        loadAllPlayStations();

        tblPlayStation.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                // Single-click detected, get the selected row
                PlayStationTm selectPlayStation = tblPlayStation.getSelectionModel().getSelectedItem();

                // If a row is selected, fill the fields with its data
                if (selectPlayStation != null) {
                    fillFields(selectPlayStation);
                }
            }
        });
    }

    private void fillFields(PlayStationTm tm) {
        txtPlayStationId.setText(tm.getPlayStationId());
        txtPlayStationNumber.setText(tm.getPlayStationNumber());
        txtRate.setText(String.valueOf(tm.getRate()));
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("playStationId"));
        colNumber.setCellValueFactory(new PropertyValueFactory<>("playStationNumber"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colRate.setCellValueFactory(new PropertyValueFactory<>("rate"));
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        PlayStationTm selectPlayStation = tblPlayStation.getSelectionModel().getSelectedItem();

    if (selectPlayStation != null) {
        String id = txtPlayStationId.getText();
        PlayStationModel playStationModel = new PlayStationModel();

        try {
            boolean isDeleted = playStationModel.deletePlayStation(id);

            if (isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "Play Station Deleted Successfully").show();
                loadAllPlayStations();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }else{
            new Alert(Alert.AlertType.INFORMATION, "Please select a Play Station to delete. ");
        }
    }

    private void loadAllPlayStations() {

        ObservableList<PlayStationTm> obList = FXCollections.observableArrayList();

        try {
            List<PlayStationDto> dtoList = playStationModel.getAll();

            for(PlayStationDto dto : dtoList) {
                obList.add(
                        new PlayStationTm(
                                dto.getPlayStationId(),
                                dto.getPlayStationNumber(),
                                dto.getStatus(),
                                dto.getRate()
                        )
                );
            }
            tblPlayStation.setItems(obList);
        } catch (SQLException e) {
           e.printStackTrace();
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtPlayStationId.clear();
        txtPlayStationNumber.clear();
        txtRate.clear();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {

        boolean isPlaystationValidated = ValidatePlaystation();

        if (!isPlaystationValidated){
            return;
        }

        String playStation = txtPlayStationId.getText();
        String playStationNum = txtPlayStationNumber.getText();
        String status = "Free";
        double rate= Double.parseDouble(txtRate.getText());

        try {
            boolean isSaved = playStationModel.savePlayStation(new PlayStationDto(playStation, playStationNum, status,rate));
            if (isSaved){
                new Alert(Alert.AlertType.CONFIRMATION,"Saved Successfully").show();
                loadAllPlayStations();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private boolean ValidatePlaystation() {
        String playstationId = txtPlayStationId.getText();
        boolean isPlayStationIdValidated = Pattern.matches("[P][0-9]{4}", playstationId);

        if (!isPlayStationIdValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Playstation Id!!").show();
            return false;
        }

        String playstationNumber = txtPlayStationNumber.getText();
        boolean isCusContactNumValidated = Pattern.matches("[1-9]", playstationNumber);

        if (!isCusContactNumValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid Playstation Number!!").show();
            return false;
        }

        String rate= txtRate.getText();
        boolean isRateValidated = Pattern.matches("[1-9][0-9]*.?[0-9]*", rate);

        if (!isRateValidated) {
            new Alert(Alert.AlertType.ERROR, "Invalid rate!!").show();
            return false;
        }

        return true;
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String id = txtSearchBar.getText();
        var model = new PlayStationModel();

        try {
            PlayStationDto dto = model.searchModel(id);
            if (dto != null){
                fillField(dto);
            }else {
                new Alert(Alert.AlertType.INFORMATION,"PlayStation not found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private void fillField(PlayStationDto dto) {
        txtPlayStationId.setText(dto.getPlayStationId());
        txtPlayStationNumber.setText(dto.getPlayStationNumber());
        txtRate.setText(String.valueOf(dto.getRate()));
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event)  {
        String playStationId = txtPlayStationId.getText();
        String playStationNum = txtPlayStationNumber.getText();
        String status = "Free";
        double rate= Double.parseDouble((txtRate.getText()));

        PlayStationDto dto = new PlayStationDto(playStationId, playStationNum, status, rate);
        PlayStationModel playStationModel = new PlayStationModel();

        try{
            boolean isUpdated = playStationModel.updatePlayStation(dto);
            if (isUpdated){
                new Alert(Alert.AlertType.CONFIRMATION, "PlayStation Updated Successfully");

                loadAllPlayStations();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
}
