package com.example.Electric.WebApi.Controllers;

import com.example.Electric.Data.Entities.Room;
import com.example.Electric.Domain.Interfaces.IRoomManager;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@FxmlView("room-table.fxml")
public class RoomTableController implements Initializable {
    private final FxWeaver fxWeaver;

    @FXML
    public TableView<Room> RoomTable;
    @FXML
    public TableColumn<Room, Integer> idColumn;
    @FXML
    public TableColumn<Room, String> nameColumn;
    @FXML
    public TableColumn<Room, Integer> numberOfDevicesColumn;
    @FXML
    public TableColumn<Room, Float> powerConsumptionColumn;
    @FXML
    public Button devicesButton;

    private IRoomManager roomManager;

    private ObservableList<Room> rooms;

    @Autowired
    public RoomTableController(FxWeaver _fxWeaver, IRoomManager roomManager) {
        this.fxWeaver = _fxWeaver;
        this.roomManager = roomManager;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        rooms = roomManager.allRooms();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("room_Id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Room, String>("roomName"));
        numberOfDevicesColumn.setCellValueFactory(new PropertyValueFactory<>("devicesCount"));
        powerConsumptionColumn.setCellValueFactory(new PropertyValueFactory<>("powerConsumption"));

        RoomTable.setItems(rooms);
    }

    @FXML
    public void onDevicesButtonClick(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxWeaver.loadView(MainController.class));
        stage.setScene(scene);
        stage.show();
    }
}
