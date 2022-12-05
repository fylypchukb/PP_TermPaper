package com.example.Knights.WebApi.Controllers;

import com.example.Knights.Data.Entities.Ammunition.Ammunition;
import com.example.Knights.Data.Entities.Knight.Knight;
import com.example.Knights.Domain.CommandPattern.*;
import com.example.Knights.Domain.Response.BaseResponse;
import com.example.Knights.Domain.Response.RestException;
import com.example.Knights.Domain.Services.KnightService;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
@FxmlView("knights.fxml")
public class KnightController implements Initializable {

    @FXML
    private Button backButton;

    @FXML
    private ListView<Knight> knightList;
    @FXML
    private Button addButton;

    @FXML
    private Spinner<Integer> ageInput;
    @FXML
    private Spinner<Integer> healthInput;

    @FXML
    private TextField nameInput;

    @FXML
    private TextField titleInput;
    private final KnightService knightService;
    private final FxWeaver fxWeaver;

    @Autowired
    public KnightController(KnightService knightService, FxWeaver fxWeaver) {
        this.knightService = knightService;
        this.fxWeaver = fxWeaver;

    }

    @FXML
    void addKnight(ActionEvent event) {
        Knight knightViewModel = new Knight();
        knightViewModel.setTitle(titleInput.getText());
        knightViewModel.setName(nameInput.getText());
        knightViewModel.setAge(ageInput.getValue());
        knightViewModel.setHitPoints(healthInput.getValue());

        var response = knightService.addKnight(knightViewModel);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Knights");

        alert.setHeaderText(null);

        if (response.getStatusCode() == HttpStatus.CREATED) {
            alert.setContentText("Knight was added!");
            this.knightList.setItems(knightService.getAllKnights().getBody());
        } else {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Knight was not added!");
        }
        alert.showAndWait();
    }

    @FXML
    void back(ActionEvent event) {
        String stylesheet = getClass().getResource("/css/styles.css").toExternalForm();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxWeaver.loadView(MainController.class));
        scene.getStylesheets().add(stylesheet);
        stage.setScene(scene);
        stage.show();
    }

    @PostMapping("/addKnight")
    public ResponseEntity<BaseResponse> createKnight(@RequestBody Knight knightViewModel) {
        return knightService.addKnight(knightViewModel);
    }

    @PutMapping("/updateKnight")
    public ResponseEntity<BaseResponse> updateKnight(@RequestBody Knight knightViewModel, Long id) {
        return knightService.updateKnight(knightViewModel, id);
    }

    @DeleteMapping("/deleteKnight")
    public ResponseEntity<BaseResponse> deleteKnight(Long id) {
        return knightService.deleteKnight(id);
    }

    @GetMapping("/knightAmmunition")
    public ResponseEntity<ObservableList<Ammunition>> knightAmmunition(Long id) throws RestException {
        Command<ResponseEntity<ObservableList<Ammunition>>> ammunition = new KnightAmmunitionCommand(knightService, id);
        return ammunition.execute();
    }

    @GetMapping("/knightAmmunitionByWeight")
    public ResponseEntity<ObservableList<Ammunition>> knightAmmunitionByWeight(Long id) throws RestException {
        Command<ResponseEntity<ObservableList<Ammunition>>> sortByWeight = new KnightAmmunitionByWeightCommand(knightService, id);
        return sortByWeight.execute();
    }

    @GetMapping("/knightAmmunitionByCost")
    public ResponseEntity<ObservableList<Ammunition>> knightAmmunitionByCost(Long id) throws RestException {
        Command<ResponseEntity<ObservableList<Ammunition>>> sortByCost = new KnightAmmunitionByCostCommand(knightService, id);
        return sortByCost.execute();
    }

    @GetMapping("/knightAmmunitionByCostRange")
    public ResponseEntity<ObservableList<Ammunition>> knightAmmunitionByCostRange(Long id, int lLim, int rLim) throws RestException {
        Command<ResponseEntity<ObservableList<Ammunition>>> ammunitionByCostRange = new KnightAmmunitionByCostRangeCommand(knightService, id, lLim, rLim);
        return ammunitionByCostRange.execute();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.knightList.setItems(knightService.getAllKnights().getBody());
    }
}
