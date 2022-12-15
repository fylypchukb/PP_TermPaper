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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@SuppressWarnings("DuplicatedCode")
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
    public Spinner<Integer> fromFilterSpinner;
    @FXML
    public Spinner<Integer> toFilterSpinner;
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
    @FXML
    public TextField searchTextField;
    @FXML
    public Button searchButton;
    @FXML
    public Button deleteButton;
    @FXML
    public Button resetButton;
    @FXML
    public ComboBox<String> filterRoomComboBox;

    private final IDeviceManager deviceManager;
    private final IElectricPower electricPower;
    private final IDeviceSearch deviceSearch;
    private final IRoomManager roomManager;

    Logger logger = LoggerFactory.getLogger(MainController.class);

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
                event -> deviceManager.updateDeviceName(( event.getTableView().getItems().get(
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
        filterRoomComboBox.setItems(roomNames);

        SpinnerValueFactory<Integer> valueFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 9999);
        valueFactory1.setValue(0);
        fromFilterSpinner.setValueFactory(valueFactory1);

        SpinnerValueFactory<Integer> valueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 9999);
        valueFactory2.setValue(9999);
        toFilterSpinner.setValueFactory(valueFactory2);

        logger.info("Menu was initialized");
    }

    @FXML
    public void onSwitchButtonClick() {
        var selectedList = DeviceTable.getSelectionModel().getSelectedItems();
        for (var item : selectedList) {
            deviceManager.switchDevice(item);
        }

        devices = deviceManager.allDevices();

        checkToggles();
        checkFilterSpinners();
        checkFilterRoomComboBox();
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
        checkFilterSpinners();
        checkFilterRoomComboBox();
        updateTable();

        logger.info("Applied filters");
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
        filterRoomComboBox.setValue(null);

        devices = deviceManager.allDevices();
        updateTable();
    }

    @FXML
    public void onDeleteButtonClick() {
        var selectedList = DeviceTable.getSelectionModel().getSelectedItems();
        deviceManager.deleteDevices(selectedList);

        devices = deviceManager.allDevices();
        checkToggles();
        checkFilterSpinners();
        checkFilterRoomComboBox();
        updateTable();
    }

    @FXML
    public void onSearchButtonAction() {
        devices = deviceSearch.searchByName(deviceManager.allDevices(), searchTextField.getText());

        Toggle toClear = toggleGroup.getSelectedToggle();
        if (toClear != null)
            toClear.setSelected(false);

        filterRoomComboBox.setValue(null);
        filterRoomComboBox.setPromptText("Select room");

        fromFilterSpinner.getValueFactory().setValue(0);
        toFilterSpinner.getValueFactory().setValue(9999);

        updateTable();
        logger.info("Searched for device with input - \"" + searchTextField.getText() + "\"");
    }

    @FXML
    public void onResetButtonClick() {
        Toggle toClear = toggleGroup.getSelectedToggle();
        if (toClear != null)
            toClear.setSelected(false);

        searchTextField.clear();
        filterRoomComboBox.setValue(null);
        filterRoomComboBox.setPromptText("Select room");

        fromFilterSpinner.getValueFactory().setValue(0);
        toFilterSpinner.getValueFactory().setValue(9999);

        devices = deviceManager.allDevices();
        updateTable();

        logger.info("Reset filter");
    }

    @FXML
    public void onExtendedSearchButtonClick(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxWeaver.loadView(SearchScreenController.class));
        stage.setScene(scene);
        stage.show();
    }

    private void updateTable() {
        DeviceTable.setItems(devices);
        TotalConsumptionLabel.setText(electricPower.generalConsumption(devices).toString());
    }

    private void checkToggles() {
        if (activeRadioButton.isSelected()) {
            devices = deviceManager.filterStatusDevices(devices, true);
        } else if (disabledRadioButton.isSelected()) {
            devices = deviceManager.filterStatusDevices(devices, false);
        }
    }

    private void checkFilterSpinners() {
        Float from = fromFilterSpinner.getValue().floatValue();
        Float to = toFilterSpinner.getValue().floatValue();

        if (to < from){
            errorLabel.setText("To-value cannot be bigger than from-value");
            fromFilterSpinner.getValueFactory().setValue(0);
            toFilterSpinner.getValueFactory().setValue(9999);
        } else
            devices = deviceSearch.rangeDevicesSearch(devices, from, to);
    }

    private void checkFilterRoomComboBox() {
        String fromComboBox = filterRoomComboBox.getValue();

        if (fromComboBox != null) {
            devices = deviceManager.filterRoomDevices(devices, roomManager.getByName(fromComboBox));
        }
    }
}
