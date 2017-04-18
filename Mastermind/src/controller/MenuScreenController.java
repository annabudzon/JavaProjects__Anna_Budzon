package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import main.Window;

public class MenuScreenController implements Initializable, ControlledScreen {
    ScreensController myController;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    @Override
    public void setParentScreen(ScreensController screenParent){
        myController = screenParent;
    }

    public void newGameHandle(){}
    public void rulesHandle(){}
    public void settingsHandle(){}
    public void exitHandle(){
        Window.closeProgram();
    }
}
