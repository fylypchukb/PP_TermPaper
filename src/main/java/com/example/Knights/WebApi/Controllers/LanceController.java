package com.example.Knights.WebApi.Controllers;

import com.example.Knights.Data.Entities.Ammunition.Helm;
import com.example.Knights.Data.Entities.Ammunition.Lance;
import com.example.Knights.Domain.Response.BaseResponse;
import com.example.Knights.Domain.Services.LanceService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
@FxmlView("lance.fxml")
public class LanceController {

    @FXML
    private Spinner<Integer> damageField;

    @FXML
    private TextField nameField;
    @FXML
    private Spinner<Integer> priceField;

    @FXML
    private Spinner<Integer> weightField;
    @FXML
    private Spinner<Integer> lengthField;
    private final FxWeaver fxWeaver;
    private final LanceService lanceService;

    public LanceController(FxWeaver fxWeaver, LanceService lanceService) {
        this.fxWeaver = fxWeaver;
        this.lanceService = lanceService;
    }

    @FXML
    void addLance(ActionEvent event) {
        Lance lance=new Lance();
        lance.setWeight(weightField.getValue());
        lance.setLength(lengthField.getValue());
        lance.setName(nameField.getText());
        lance.setDamage(damageField.getValue());
        lance.setPrice(priceField.getValue());

        var response = lanceService.addAmmunition(lance);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Lance");

        alert.setHeaderText(null);

        if (response.getStatusCode() == HttpStatus.CREATED) {
            alert.setContentText("Lance was added!");

        } else {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Lance was not added!");
        }
        alert.showAndWait();
        String stylesheet = getClass().getResource("/css/styles.css").toExternalForm();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxWeaver.loadView(MainController.class));
        scene.getStylesheets().add(stylesheet);
        stage.setScene(scene);
        stage.show();
    }
    @PostMapping("/addLance")
    public ResponseEntity<BaseResponse> createLance(@RequestBody Lance lance) {
        return lanceService.addAmmunition(lance);
    }

    @PutMapping ("/updateLance")
    public ResponseEntity<BaseResponse> updateLance(@RequestBody Lance lance,Long id) {
        return lanceService.updateAmmunition(lance,id);
    }

    @DeleteMapping ("/deleteLance")
    public ResponseEntity<BaseResponse> deleteLance(Long id) {
        return lanceService.deleteAmmunition(id);
    }
}
