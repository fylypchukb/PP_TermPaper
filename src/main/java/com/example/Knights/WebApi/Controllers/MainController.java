package com.example.Knights.WebApi.Controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@FxmlView("menu.fxml")
public class MainController {
    @FXML
    private Button ammunitionButton;

    @FXML
    private Button knightsButton;

    @FXML
    private Button shopButton;

    private final FxWeaver fxWeaver;

    @Autowired
    public MainController(FxWeaver _fxWeaver) {
        this.fxWeaver=_fxWeaver;
    }

    @FXML
    void ammunition(ActionEvent event) {
        String stylesheet = getClass().getResource("/css/styles.css").toExternalForm();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxWeaver.loadView(KnightsAmmunitionController.class));
        scene.getStylesheets().add(stylesheet);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void knights(ActionEvent event) throws IOException {
        String stylesheet = getClass().getResource("/css/styles.css").toExternalForm();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxWeaver.loadView(KnightController.class));
        scene.getStylesheets().add(stylesheet);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void shop(ActionEvent event) {
        String stylesheet = getClass().getResource("/css/styles.css").toExternalForm();
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxWeaver.loadView(ShopController.class));
        scene.getStylesheets().add(stylesheet);
        stage.setScene(scene);
        stage.show();
    }
}
