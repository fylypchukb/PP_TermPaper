package com.example.Electric.WebApi.Controllers;

import com.example.Electric.Data.Entities.Device;
import com.example.Electric.Domain.Interfaces.IDeviceManager;
import com.example.Electric.Domain.Interfaces.IDeviceSearch;
import com.example.Electric.Domain.Interfaces.IElectricPower;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    @FXML
    public Button switchButton;
    @FXML
    public Button roomsButton;

    @FXML
    public ToggleGroup toggleGroup;
    @FXML
    public RadioButton activeRadioButton;
    @FXML
    public RadioButton disabledRadioButton;
    @FXML
    public RadioButton allRadioButton;
    @FXML
    public Button applyButton;
    @FXML
    public CheckBox minCheckBox;
    @FXML
    public CheckBox averCheckBox;
    @FXML
    public CheckBox maxCheckBox;

    private IDeviceManager deviceManager;
    private IElectricPower electricPower;
    private IDeviceSearch deviceSearch;

    private ObservableList<Device> devices;

    @Autowired
    public MainController(FxWeaver _fxWeaver, IDeviceManager deviceManager, IElectricPower electricPower, IDeviceSearch deviceSearch) {
        this.fxWeaver = _fxWeaver;
        this.deviceManager = deviceManager;
        this.electricPower = electricPower;
        this.deviceSearch = deviceSearch;
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DeviceTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        devices = deviceManager.allDevices();

        DeviceID.setCellValueFactory(new PropertyValueFactory<Device, Integer>("device_id"));
        DeviceName.setCellValueFactory(new PropertyValueFactory<Device, String>("deviceName"));
        DeviceStatus.setCellValueFactory(new PropertyValueFactory<Device, String>("status"));
        RoomName.setCellValueFactory(new PropertyValueFactory<Device, String>("roomName"));
        DevicePower.setCellValueFactory(new PropertyValueFactory<Device, Float>("electricPowerDefault"));
        DevicePowerUsage.setCellValueFactory(new PropertyValueFactory<Device, Float>("electricPower"));

        DeviceTable.setItems(devices);

        TotalConsumptionLabel.setText(electricPower.GeneralConsumption(devices).toString());

        toggleGroup = new ToggleGroup();
        activeRadioButton.setToggleGroup(toggleGroup);
        disabledRadioButton.setToggleGroup(toggleGroup);
        allRadioButton.setToggleGroup(toggleGroup);
    }

    @FXML
    public void onSwitchButtonClick() {
        var selectedList = DeviceTable.getSelectionModel().getSelectedItems();
        for (var item : selectedList) {
            deviceManager.switchDevice(item);
        }

        devices = deviceManager.allDevices();

        checkToggles();
        checkFilterBoxes();
        updateTable();
    }

    @FXML
    public void onRoomsButtonClick(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxWeaver.loadView(RoomTableController.class));
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void onApplyButtonClick(ActionEvent event) {
        devices = deviceManager.allDevices();
        checkToggles();
        checkFilterBoxes();
        updateTable();
    }

    private void updateTable() {
        DeviceTable.setItems(devices);
        TotalConsumptionLabel.setText(electricPower.GeneralConsumption(devices).toString());
    }

    private void checkToggles() {
        if (activeRadioButton.isSelected()) {
            devices = deviceManager.filteredStatusDevices(devices, true);
        } else if (disabledRadioButton.isSelected()) {
            devices = deviceManager.filteredStatusDevices(devices, false);
        }
    }

    private void checkFilterBoxes() {
        float from = Float.MAX_VALUE, to = 0f;

        if (minCheckBox.isSelected()) {
            from = 0f;
            to = 500f;
        }
        if (averCheckBox.isSelected()) {
            from = Math.min(500f, from);
            to = 1000f;
        }
        if (maxCheckBox.isSelected()) {
            from = Math.min(1000f, from);
            to = Float.MAX_VALUE;
        }

        if (from == Float.MAX_VALUE) {
            from = 0f;
            to = Float.MAX_VALUE;
        }

        devices = deviceSearch.rangeDevicesSearch(devices, from, to);
    }

}
