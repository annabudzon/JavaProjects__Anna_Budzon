package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

public class GameScreenController implements Initializable, ControlledScreen {
    private ScreensController myController;
            
    @Override
    public void initialize(URL url, ResourceBundle rb) {}

    @Override
    public void setParentScreen(ScreensController screenParent) {
        myController = screenParent;
    }   
}
