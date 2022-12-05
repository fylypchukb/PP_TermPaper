package com.example.Electric.WebApi.Controllers;

import com.example.Electric.Data.Entities.Device;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@FxmlView("menu.fxml")
public class MainController implements Initializable {
    private final FxWeaver fxWeaver;
    @FXML
    public TableView<Device> DeviceTable;
    @FXML
    public TableColumn<Device, Integer> DeviceID;
    @FXML
    public TableColumn<Device, String> DeviceName;
    @FXML
    public TableColumn<Device, String> RoomName;
    @FXML
    public TableColumn<Device, String> DeviceStatus;
    @FXML
    public TableColumn<Device, Float> DevicePower;
    @FXML
    public TableColumn<Device, Float> DevicePowerUsage;
    @FXML
    public Label TotalConsumptionLabel;

    private ObservableList<Device> devices;

    @Autowired
    public MainController(FxWeaver _fxWeaver) {
        this.fxWeaver=_fxWeaver;
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // todo: database
        // TotalConsumptionLabel.setText(ElectricPower.calculateGeneralInHome(DataBaseStorage.getHouse(0)).toString());

        DeviceID.setCellValueFactory(new PropertyValueFactory<Device, Integer>("id"));
        DeviceName.setCellValueFactory(new PropertyValueFactory<Device, String>("name"));
        RoomName.setCellValueFactory(new PropertyValueFactory<Device, String>("roomName"));
        DeviceStatus.setCellValueFactory(new PropertyValueFactory<Device, String>("status"));
        DevicePower.setCellValueFactory(new PropertyValueFactory<Device, Float>("electricPowerDefault"));
        DevicePowerUsage.setCellValueFactory(new PropertyValueFactory<Device, Float>("electricPower"));

        DeviceTable.setItems(devices);
    }
}
