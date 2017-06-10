package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import static main.Window.screenMenu;

public class GameOverScreenController implements Initializable, ControlledScreen {

    ScreensController myController;

    @FXML
    public void goBackHandle() {
        myController.setScreen(screenMenu);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @Override
    public void closeGame(Stage stage) {
    }

    @Override
    public void setParentScreen(ScreensController screenParent) {
        myController = screenParent;
    }

}
