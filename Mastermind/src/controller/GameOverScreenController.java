package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

public class GameOverScreenController implements Initializable, ControlledScreen {
    ScreensController myController;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    @Override
    public void setParentScreen(ScreensController screenParent){
        myController = screenParent;
    }

}
