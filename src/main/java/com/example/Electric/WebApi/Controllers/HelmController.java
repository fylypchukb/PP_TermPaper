package com.example.Electric.WebApi.Controllers;

import com.example.Electric.Data.Entities.Ammunition.Armor;
import com.example.Electric.Data.Entities.Ammunition.Helm;
import com.example.Electric.Domain.Response.BaseResponse;
import com.example.Electric.Domain.Services.HelmService;
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
@FxmlView("helm.fxml")
public class HelmController implements Initializable {
    @FXML
    private CheckBox closeField;

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
    private final HelmService helmService;
    private final FxWeaver fxWeaver;

    public HelmController(HelmService helmService, FxWeaver fxWeaver) {
        this.helmService = helmService;
        this.fxWeaver = fxWeaver;
    }
    @FXML
    void addHelm(ActionEvent event) {
        Helm helm=new Helm();
        helm.setWeight(weightField.getValue());
        helm.setSize(sizeField.getValue());
        helm.setName(nameField.getText());
        helm.setCloseHelm(closeField.isSelected());
        helm.setCanTakeDamage(damageField.getValue());
        helm.setPrice(priceField.getValue());

        var response = helmService.addAmmunition(helm);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Helm");

        alert.setHeaderText(null);

        if (response.getStatusCode() == HttpStatus.CREATED) {
            alert.setContentText("Helm was added!");

        } else {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setContentText("Helm was not added!");
        }
        alert.showAndWait();
        String stylesheet = getClass().getResource("/css/styles.css").toExternalForm();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxWeaver.loadView(MainController.class));
        scene.getStylesheets().add(stylesheet);
        stage.setScene(scene);
        stage.show();
    }

    @PostMapping("/addHelm")
    public ResponseEntity<BaseResponse> createHelm(@RequestBody Helm helm) {
        return helmService.addAmmunition(helm);
    }

    @PutMapping ("/updateHelm")
    public ResponseEntity<BaseResponse> updateHelm(@RequestBody Helm helm,Long id) {
        return helmService.updateAmmunition(helm,id);
    }

    @DeleteMapping ("/deleteHelm")
    public ResponseEntity<BaseResponse> deleteHelm(Long id) {
        return helmService.deleteAmmunition(id);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> list = FXCollections.observableArrayList("Small","Medium","Large");
        this.sizeField.setItems(list);
    }
}
