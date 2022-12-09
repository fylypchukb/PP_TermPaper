package com.example.Electric.WebApi.Controllers;

import com.example.Electric.Data.Entities.Device;
import com.example.Electric.Data.Entities.Room;
import com.example.Electric.Domain.Interfaces.IDeviceManager;
import com.example.Electric.Domain.Interfaces.IDeviceSearch;
import com.example.Electric.Domain.Interfaces.IElectricPower;
import com.example.Electric.Domain.Interfaces.IRoomManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
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
    @FXML
    public TextField nameTextField;
    @FXML
    public TextField powerTextField;
    @FXML
    public ComboBox<String> roomComboBox;
    @FXML
    public Button addButton;
    @FXML
    public Label errorLabel;

    private final IDeviceManager deviceManager;
    private final IElectricPower electricPower;
    private final IDeviceSearch deviceSearch;
    private final IRoomManager roomManager;
    @FXML
    public Button deleteButton;

    private ObservableList<Device> devices;

    @Autowired
    public MainController(FxWeaver _fxWeaver, IDeviceManager deviceManager,
                          IElectricPower electricPower, IDeviceSearch deviceSearch, IRoomManager roomManager) {
        this.fxWeaver = _fxWeaver;
        this.deviceManager = deviceManager;
        this.electricPower = electricPower;
        this.deviceSearch = deviceSearch;
        this.roomManager = roomManager;
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DeviceTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        DeviceTable.setEditable(true);

        devices = deviceManager.allDevices();

        DeviceID.setCellValueFactory(new PropertyValueFactory<>("device_id"));

        DeviceName.setCellValueFactory(new PropertyValueFactory<>("deviceName"));
        DeviceName.setCellFactory(TextFieldTableCell.forTableColumn());
        DeviceName.setOnEditCommit(
                event -> deviceManager.updateDeviceName(((Device) event.getTableView().getItems().get(
                        event.getTablePosition().getRow())), event.getNewValue())
        );

        DeviceStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        RoomName.setCellValueFactory(new PropertyValueFactory<>("roomName"));

        DevicePower.setCellValueFactory(new PropertyValueFactory<>("electricPowerDefault"));

        DevicePowerUsage.setCellValueFactory(new PropertyValueFactory<>("electricPower"));

        DeviceTable.setItems(devices);

        TotalConsumptionLabel.setText(electricPower.generalConsumption(devices).toString());

        toggleGroup = new ToggleGroup();
        activeRadioButton.setToggleGroup(toggleGroup);
        disabledRadioButton.setToggleGroup(toggleGroup);
        allRadioButton.setToggleGroup(toggleGroup);

        ObservableList<Room> rooms = roomManager.allRooms();
        ObservableList<String> roomNames = FXCollections.observableArrayList();
        for (var item : rooms) {
            roomNames.add(item.getRoomName());
        }

        roomComboBox.setItems(roomNames);
        roomComboBox.setValue(roomNames.get(0));
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
    public void onApplyButtonClick() {
        devices = deviceManager.allDevices();
        checkToggles();
        checkFilterBoxes();
        updateTable();
    }

    @FXML
    public void onAddButtonClick() {
        boolean isFloat = true;
        Float power = null;
        String fromComboBox = roomComboBox.getValue();

        try {
            power = Float.parseFloat(powerTextField.getText());
        } catch (Exception e) {
            isFloat = false;
        }

        if (nameTextField.getText().compareTo("") != 0 && nameTextField.getText().matches(".*[A-z].*") && isFloat
                && fromComboBox != null) {
            Device device = new Device(
                    nameTextField.getText(),
                    power,
                    roomManager.getByName(fromComboBox).getRoom_Id()
            );

            deviceManager.addDevice(device);
            errorLabel.setText("");
        } else {
            errorLabel.setText("Invalid name, power value or room isn't selected");
        }

        nameTextField.clear();
        powerTextField.clear();

        devices = deviceManager.allDevices();
        updateTable();
    }

    @FXML
    public void onDeleteButtonClick(){
        var selectedList = DeviceTable.getSelectionModel().getSelectedItems();
        deviceManager.deleteDevices(selectedList);

        devices = deviceManager.allDevices();
        checkToggles();
        checkFilterBoxes();
        updateTable();
    }

    private void updateTable() {
        DeviceTable.setItems(devices);
        TotalConsumptionLabel.setText(electricPower.generalConsumption(devices).toString());
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
