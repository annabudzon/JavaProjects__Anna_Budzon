package controller;

import boxes.ConfirmBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import static main.Window.*;

public class MenuScreenController implements Initializable, ControlledScreen {
    ScreensController myController;
    @FXML StackPane stackPane;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    @Override
    public void setParentScreen(ScreensController screenParent){
        myController = screenParent;
    }

    public void newGameHandle(){
        myController.setScreen(screenGame);
    }
    
    public void rulesHandle(){
        myController.setScreen(screenRules);
    }
    
    public void settingsHandle(){
        myController.setScreen(screenSettings);
    }
    
    public void exitHandle(){
        Stage stage  = (Stage) stackPane.getScene().getWindow();
        closeProgram(stage);
    }
    
    public void closeProgram(Stage window) {
        boolean answer = ConfirmBox.display("Closing window", "Sure you want to exit?");
        if (answer) {
            window.close();
        }
    }
}
