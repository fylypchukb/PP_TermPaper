package com.example.Knights.WebApi.Controllers;

import com.example.Knights.Data.Entities.Ammunition.Armor;
import com.example.Knights.Data.Entities.Knight.Knight;
import com.example.Knights.Domain.Response.BaseResponse;
import com.example.Knights.Domain.Services.ArmorService;
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
@FxmlView("armor.fxml")
public class ArmorController implements Initializable {
    @FXML
    private CheckBox ceremonialField;

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
    private final ArmorService armorService;
    private final FxWeaver fxWeaver;

    public ArmorController(ArmorService armorService, FxWeaver weaver) {
        this.armorService = armorService;
        this.fxWeaver = weaver;
    }

    @FXML
    void addArmor(ActionEvent event) {
        Armor armor=new Armor();
        armor.setWeight(weightField.getValue());
        armor.setSize(sizeField.getValue());
        armor.setName(nameField.getText());
        armor.setCeremonial(ceremonialField.isSelected());
        armor.setCanTakeDamage(damageField.getValue());
        armor.setPrice(priceField.getValue());

        var response = armorService.addAmmunition(armor);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Armor");

        alert.setHeaderText(null);

        if (response.getStatusCode() == HttpStatus.CREATED) {
            alert.setContentText("Armor was added!");

        } else {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Armor was not added!");
        }
        alert.showAndWait();
        String stylesheet = getClass().getResource("/css/styles.css").toExternalForm();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxWeaver.loadView(MainController.class));
        scene.getStylesheets().add(stylesheet);
        stage.setScene(scene);
        stage.show();
    }


    @PostMapping("/addArmor")
    public ResponseEntity<BaseResponse> createArmor(@RequestBody Armor armor) {
        return armorService.addAmmunition(armor);
    }

    @PutMapping ("/updateArmor")
    public ResponseEntity<BaseResponse> updateArmor(@RequestBody Armor armor,Long id) {
        return armorService.updateAmmunition(armor,id);
    }

    @DeleteMapping ("/deleteArmor")
    public ResponseEntity<BaseResponse> deleteArmor(Long id) {
        return armorService.deleteAmmunition(id);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> list = FXCollections.observableArrayList("Small","Medium","Large");
        this.sizeField.setItems(list);
    }
}
