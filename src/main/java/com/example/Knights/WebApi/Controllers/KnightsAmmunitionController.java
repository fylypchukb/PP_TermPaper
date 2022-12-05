package com.example.Knights.WebApi.Controllers;

import com.example.Knights.Data.Entities.Ammunition.Ammunition;
import com.example.Knights.Data.Entities.Knight.Knight;
import com.example.Knights.Domain.Response.RestException;
import com.example.Knights.Domain.Services.KnightService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
@FxmlView("knightsAmmunition.fxml")
public class KnightsAmmunitionController implements Initializable {
    @FXML
    private Button menuButton;

    @FXML
    private Button sortByPriceButton;

    @FXML
    private Spinner<Integer> minCost;

    @FXML
    private Spinner<Integer> maxCost;

    @FXML
    private ListView<Knight> knightList;
    @FXML
    private ListView<Ammunition> knightAmmunitionList;
    private final KnightService knightService;
    private final FxWeaver fxWeaver;
    private Long selectedKnightId;

    @Autowired
    public KnightsAmmunitionController(KnightService knightService, FxWeaver fxWeaver) {
        this.knightService = knightService;
        this.fxWeaver=fxWeaver;
    }

    @FXML
    public void handleMouseClick(MouseEvent arg0) throws RestException {
        var selectedKnight=knightList.getSelectionModel().getSelectedItem();
        selectedKnightId=selectedKnight.getId();
        this.knightAmmunitionList.setItems(this.knightService.knightAmmunition(selectedKnight.getId()).getBody());
    }

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
    void sortByPrice(ActionEvent event) throws RestException {
        this.knightAmmunitionList.setItems(knightService.knightAmmunitionByCost(selectedKnightId).getBody());
    }

    @FXML
    void sortByWeight(ActionEvent event) throws RestException {
        this.knightAmmunitionList.setItems(knightService.knightAmmunitionByWeight(selectedKnightId).getBody());
    }

    @FXML
    void ammunitionByCostRange(ActionEvent event) throws RestException {
        int min= minCost.getValue();
        int max=  maxCost.getValue();
        this.knightAmmunitionList.setItems(knightService.findAmmunitionByCostRange(selectedKnightId,min,max).getBody());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.knightList.setItems(knightService.getAllKnights().getBody());
        this.knightList.getSelectionModel().select(0);
        this.selectedKnightId=knightList.getSelectionModel().getSelectedItem().getId();
        try {
            this.knightAmmunitionList.setItems(this.knightService.knightAmmunition(selectedKnightId).getBody());
        } catch (RestException e) {
            throw new RuntimeException(e);
        }
    }
}
