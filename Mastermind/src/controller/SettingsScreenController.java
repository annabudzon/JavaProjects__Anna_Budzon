/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import static main.Window.screenMenu;

public class SettingsScreenController implements Initializable, ControlledScreen {

    ScreensController myController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @Override
    public void setParentScreen(ScreensController screenParent) {
        myController = screenParent;
    }
    
    public void goBackHandle(){
        myController.setScreen(screenMenu);
    }

}
