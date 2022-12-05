package com.example.Knights.WebApi.Controllers;

import com.example.Knights.Data.Entities.Ammunition.Ammunition;
import com.example.Knights.Data.Entities.Knight.Knight;
import com.example.Knights.Domain.Response.BaseResponse;
import com.example.Knights.Domain.Response.RestException;
import com.example.Knights.Domain.Services.KnightService;
import com.example.Knights.Domain.Services.ShopService;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@FxmlView("shop.fxml")
public class ShopController implements Initializable {

    private final ShopService shopService;
    private final KnightService knightService;
    private final FxWeaver fxWeaver;
    private Long selectedKnightId;
    private Long selectedAmmunitionId;

    public ShopController(ShopService shopService, KnightService knightService, FxWeaver fxWeaver) {
        this.shopService = shopService;
        this.knightService = knightService;
        this.fxWeaver = fxWeaver;
    }
    @FXML
    private Button backButton;

    @FXML
    private Button buyButton;
    @FXML
    private ListView<Knight> knightList;
    @FXML
    private ListView<Ammunition> ammunitionList;

    @FXML
    void back(ActionEvent event) {
        String stylesheet = getClass().getResource("/css/styles.css").toExternalForm();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxWeaver.loadView(MainController.class));
        scene.getStylesheets().add(stylesheet);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void addArmor(ActionEvent event) {
        String stylesheet = getClass().getResource("/css/styles.css").toExternalForm();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxWeaver.loadView(ArmorController.class));
        scene.getStylesheets().add(stylesheet);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void addHelm(ActionEvent event) {
        String stylesheet = getClass().getResource("/css/styles.css").toExternalForm();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxWeaver.loadView(HelmController.class));
        scene.getStylesheets().add(stylesheet);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void addLance(ActionEvent event) {
        String stylesheet = getClass().getResource("/css/styles.css").toExternalForm();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxWeaver.loadView(LanceController.class));
        scene.getStylesheets().add(stylesheet);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void addShield(ActionEvent event) {
        String stylesheet = getClass().getResource("/css/styles.css").toExternalForm();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxWeaver.loadView(ShieldController.class));
        scene.getStylesheets().add(stylesheet);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void addSword(ActionEvent event) {
        String stylesheet = getClass().getResource("/css/styles.css").toExternalForm();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxWeaver.loadView(SwordController.class));
        scene.getStylesheets().add(stylesheet);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void buy(ActionEvent event) {
        shopService.buyAmmunition(selectedAmmunitionId,selectedKnightId);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ammunition");

        alert.setHeaderText(null);
        alert.setContentText("Purchase is successful!");

        alert.showAndWait();
    }

    @FXML
    public void handleMouseClickKnights(MouseEvent arg0) throws RestException {
        var selectedKnight=knightList.getSelectionModel().getSelectedItem();
        selectedKnightId=selectedKnight.getId();
    }

    @FXML
    public void handleMouseClickAmmunition(MouseEvent arg0) throws RestException {
        var selectedAmmunition=ammunitionList.getSelectionModel().getSelectedItem();
        selectedAmmunitionId=selectedAmmunition.getammunitionId();
    }
    @PostMapping("/buyAmmunition")
    public ResponseEntity<BaseResponse> buyAmmunition( Long ammunitionId, Long knightId) {
        return shopService.buyAmmunition(ammunitionId,knightId);
    }
    @GetMapping("/allAmmunition")
    public ResponseEntity<ObservableList<Ammunition>> allAmmunition()
    {
        return shopService.allAmmunition();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.knightList.setItems(this.knightService.getAllKnights().getBody());
        this.ammunitionList.setItems(this.shopService.allAmmunition().getBody());

        this.knightList.getSelectionModel().select(0);
        this.selectedKnightId=knightList.getSelectionModel().getSelectedItem().getId();

        this.ammunitionList.getSelectionModel().select(0);
        this.selectedAmmunitionId=this.ammunitionList.getSelectionModel().getSelectedItem().getammunitionId();
    }
}
