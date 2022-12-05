package com.example.Knights.WebApi.Controllers;

import com.example.Knights.Data.Entities.Ammunition.Helm;
import com.example.Knights.Data.Entities.Ammunition.Shield;
import com.example.Knights.Domain.Response.BaseResponse;
import com.example.Knights.Domain.Services.ShieldService;
import javafx.collections.FXCollections;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.util.ResourceBundle;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
@FxmlView("shield.fxml")
public class ShieldController implements Initializable {
    @FXML
    private Spinner<Integer> damageField;

    @FXML
    private TextField nameField;
    @FXML
    private Spinner<Integer> priceField;

    @FXML
    private Spinner<Integer> weightField;
    @FXML
    private ChoiceBox<String> sizeField;
    private final FxWeaver fxWeaver;

    private final ShieldService shieldService;

    public ShieldController(FxWeaver fxWeaver, ShieldService shieldService) {
        this.fxWeaver = fxWeaver;
        this.shieldService = shieldService;
    }
    @FXML
    void addShield(ActionEvent event) {
        Shield shield=new Shield();
        shield.setWeight(weightField.getValue());
        shield.setSize(sizeField.getValue());
        shield.setName(nameField.getText());
        shield.setCanTakeDamage(damageField.getValue());
        shield.setPrice(priceField.getValue());

        var response = shieldService.addAmmunition(shield);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Shield");

        alert.setHeaderText(null);

        if (response.getStatusCode() == HttpStatus.CREATED) {
            alert.setContentText("Shield was added!");

        } else {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Shield was not added!");
        }
        alert.showAndWait();
        String stylesheet = getClass().getResource("/css/styles.css").toExternalForm();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxWeaver.loadView(MainController.class));
        scene.getStylesheets().add(stylesheet);
        stage.setScene(scene);
        stage.show();
    }
    @PostMapping("/addShield")
    public ResponseEntity<BaseResponse> createShield(@RequestBody Shield shield) {
        return shieldService.addAmmunition(shield);
    }

    @PutMapping ("/updateShield")
    public ResponseEntity<BaseResponse> updateShield(@RequestBody Shield shield,Long id) {
        return shieldService.updateAmmunition(shield,id);
    }

    @DeleteMapping ("/deleteShield")
    public ResponseEntity<BaseResponse> deleteShield(Long id) {
        return shieldService.deleteAmmunition(id);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> list = FXCollections.observableArrayList("Small","Medium","Large");
        this.sizeField.setItems(list);
    }
}
