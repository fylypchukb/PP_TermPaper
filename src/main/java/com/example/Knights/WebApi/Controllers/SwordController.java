package com.example.Knights.WebApi.Controllers;

import com.example.Knights.Data.Entities.Ammunition.Helm;
import com.example.Knights.Data.Entities.Ammunition.Sword;
import com.example.Knights.Domain.Response.BaseResponse;
import com.example.Knights.Domain.Services.SwordService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
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
@FxmlView("sword.fxml")
public class SwordController {
    @FXML
    private CheckBox twoHandedField;

    @FXML
    private Spinner<Integer> damageField;

    @FXML
    private TextField nameField;
    @FXML
    private Spinner<Integer> priceField;

    @FXML
    private Spinner<Integer> weightField;

    private final FxWeaver fxWeaver;
    private final SwordService swordService;

    public SwordController(FxWeaver fxWeaver, SwordService swordService) {
        this.fxWeaver = fxWeaver;
        this.swordService = swordService;
    }

    @FXML
    void addSword(ActionEvent event) {
        Sword sword=new Sword();
        sword.setWeight(weightField.getValue());
        sword.setName(nameField.getText());
        sword.setTwoHanded(twoHandedField.isSelected());
        sword.setDamage(damageField.getValue());
        sword.setPrice(priceField.getValue());

        var response = swordService.addAmmunition(sword);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sword");

        alert.setHeaderText(null);

        if (response.getStatusCode() == HttpStatus.CREATED) {
            alert.setContentText("Sword was added!");

        } else {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Sword was not added!");
        }
        alert.showAndWait();
        String stylesheet = getClass().getResource("/css/styles.css").toExternalForm();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxWeaver.loadView(MainController.class));
        scene.getStylesheets().add(stylesheet);
        stage.setScene(scene);
        stage.show();
    }
    @PostMapping("/addSword")
    public ResponseEntity<BaseResponse> createShield(@RequestBody Sword sword) {
        return swordService.addAmmunition(sword);
    }

    @PutMapping ("/updateSword")
    public ResponseEntity<BaseResponse> updateShield(@RequestBody Sword sword,Long id) {
        return swordService.updateAmmunition(sword,id);
    }

    @DeleteMapping ("/deleteSword")
    public ResponseEntity<BaseResponse> deleteShield(Long id) {
        return swordService.deleteAmmunition(id);
    }
}
