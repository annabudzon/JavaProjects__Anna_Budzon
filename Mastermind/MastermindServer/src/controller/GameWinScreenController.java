package controller;

import static main.Window.screenMenu;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

public class GameWinScreenController implements Initializable, ControlledScreen {

    ScreensController myController;

    @FXML
    public void goBackHandle() {
        myController.setScreen(screenMenu);
    }

    @Override
    public void setParentScreen(ScreensController screenParent) {
        myController = screenParent;
    }

    @Override
    public void closeGame(Stage stage) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}
