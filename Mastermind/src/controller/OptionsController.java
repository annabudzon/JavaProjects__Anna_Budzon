package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class OptionsController implements Initializable, ControlledScreen {
    private ScreensController myController;
    private MessageBoxController control = new MessageBoxController();
    @FXML TextField ipField;
    @FXML TextField portField;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @Override
    public void setParentScreen(ScreensController screenParent) {
        myController = screenParent;
    }
    
}
