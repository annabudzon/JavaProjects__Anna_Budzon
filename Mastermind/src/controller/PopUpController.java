package controller;

import static controller.ButtonsController.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;

public class PopUpController implements Initializable {

    private MyPopUp mpop;
    private ButtonsController controller;
    private String buttonID;
    @FXML Button yellowCircle;
    @FXML Button orangeCircle;
    @FXML Button redCircle;
    @FXML Button violetCircle;
    @FXML Button blueCircle;
    @FXML Button greenCircle;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void colorClicked(ActionEvent e) {
        String color = "#000000";
        controller = new ButtonsController();
        Popup popup = MyPopUp.getPopup();
        popup.hide();

        
        if (e.getSource().equals(yellowCircle)){
            color = "#f1ff1f";
         }
         else if(e.getSource().equals(orangeCircle)){
            color = "#ffb221";
         }
         else if(e.getSource().equals(redCircle)){
            color = "#ff2121";
         }
         else if(e.getSource().equals(violetCircle)){
            color = "#a467b2";
         } 
        else if(e.getSource().equals(blueCircle)){
            color = "#59ccdd";
         } 
        else if(e.getSource().equals(greenCircle)){
            color = "#33bc3c";
         }
        
        switch (buttonID) {
            case BUTTON1:
                this.controller.setButton1Color(color);
                break;
            case BUTTON2:
                this.controller.setButton2Color(color);
                break;
            case BUTTON3:
                this.controller.setButton3Color(color);
                break;
            case BUTTON4:
                this.controller.setButton4Color(color);
                break;
        }
    }
    
    public void setButtonID(String buttonID){
        this.buttonID = buttonID;
    }
}
