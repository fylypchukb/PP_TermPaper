package com.example.Electric.WebApi.Controllers;

import com.example.Electric.Data.Entities.Device;
import com.example.Electric.Data.Entities.Room;
import com.example.Electric.Domain.Interfaces.IDeviceManager;
import com.example.Electric.Domain.Interfaces.IDeviceSearch;
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
@FxmlView("search-screen.fxml")
public class SearchScreenController implements Initializable {
    private final FxWeaver fxWeaver;
    public TableView<Device> DeviceTable;
    @FXML
    public TableColumn<Device, Float> DevicePowerUsage;
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
    public TextField searchTextField;
    @FXML
    public Spinner<Integer> fromFilterSpinner;
    @FXML
    public Spinner<Integer> toFilterSpinner;
    @FXML
    public ComboBox<String> filterRoomComboBox;
    @FXML
    public ToggleGroup toggleGroup;
    @FXML
    public RadioButton activeRadioButton;
    @FXML
    public RadioButton disabledRadioButton;
    @FXML
    public Label errorLabel;

    Logger logger = LoggerFactory.getLogger(SearchScreenController.class);

    private final IDeviceManager deviceManager;
    private final IDeviceSearch deviceSearch;
    private final IRoomManager roomManager;
    private ObservableList<Device> devices;

    @Autowired
    public SearchScreenController(FxWeaver fxWeaver, IDeviceManager deviceManager, IDeviceSearch deviceSearch,
                                  IRoomManager roomManager) {
        this.fxWeaver = fxWeaver;
        this.deviceManager = deviceManager;
        this.deviceSearch = deviceSearch;
        this.roomManager = roomManager;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DeviceTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        DeviceTable.setEditable(true);

        DeviceID.setCellValueFactory(new PropertyValueFactory<>("device_id"));

        DeviceName.setCellValueFactory(new PropertyValueFactory<>("deviceName"));
        DeviceName.setCellFactory(TextFieldTableCell.forTableColumn());
        DeviceName.setOnEditCommit(
                event -> deviceManager.updateDeviceName(event.getTableView().getItems().get(
                        event.getTablePosition().getRow()), event.getNewValue())
        );

        DeviceStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        RoomName.setCellValueFactory(new PropertyValueFactory<>("roomName"));

        DevicePower.setCellValueFactory(new PropertyValueFactory<>("electricPowerDefault"));

        DevicePowerUsage.setCellValueFactory(new PropertyValueFactory<>("electricPower"));

        toggleGroup = new ToggleGroup();
        activeRadioButton.setToggleGroup(toggleGroup);
        disabledRadioButton.setToggleGroup(toggleGroup);

        ObservableList<Room> rooms = roomManager.allRooms();
        ObservableList<String> roomNames = FXCollections.observableArrayList();
        for (var item : rooms) {
            roomNames.add(item.getRoomName());
        }
        filterRoomComboBox.setItems(roomNames);

        SpinnerValueFactory<Integer> valueFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 9999);
        valueFactory1.setValue(0);
        fromFilterSpinner.setValueFactory(valueFactory1);

        SpinnerValueFactory<Integer> valueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 9999);
        valueFactory2.setValue(9999);
        toFilterSpinner.setValueFactory(valueFactory2);

        logger.info("SearchScreen initialized");
    }

    @FXML
    public void onSearchButtonAction() {
        devices = deviceSearch.searchByName(deviceManager.allDevices(), searchTextField.getText());
        checkToggles();
        checkFilterSpinners();
        checkFilterRoomComboBox();

        DeviceTable.setItems(devices);

        logger.info("Searched with input \"" + searchTextField.getText() + "\"");
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

        DeviceTable.getItems().clear();

        logger.info("Reset filter");
    }

    @FXML
    public void onSwitchButtonClick() {
        if (DeviceTable.getItems().size() != 0) {
            var selectedList = DeviceTable.getSelectionModel().getSelectedItems();
            for (var item : selectedList) {
                deviceManager.switchDevice(item);
            }

            devices = deviceSearch.searchByName(deviceManager.allDevices(), searchTextField.getText());

            checkToggles();
            checkFilterSpinners();
            checkFilterRoomComboBox();

            onSearchButtonAction();
        }
    }

    @FXML
    public void onDevicesButtonClick(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxWeaver.loadView(MainController.class));
        stage.setScene(scene);
        stage.show();
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

        if (to < from) {
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
