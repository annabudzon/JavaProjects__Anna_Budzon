package controller;

import boxes.ConfirmBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import static main.Window.*;

public class MenuScreenController implements Initializable, ControlledScreen {
    ScreensController myController;
    @FXML StackPane stackPane;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    @Override
    public void closeGame(Stage stage){}
    
    @Override
    public void setParentScreen(ScreensController screenParent){
        myController = screenParent;
    }

    public void newGameHandle(){
        myController.setScreen(screenGame);
        FXMLLoader loader = myController.setScreen(screenGame);
        MainScreenController control = loader.getController();
        control.setLogged();
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
        ConfirmBox box = new ConfirmBox();
        boolean answer = box.display("Closing window", "Sure you want to exit?");
        if (answer) {
            window.close();
        }
    }
}
