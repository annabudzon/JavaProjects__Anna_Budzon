package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ButtonsController implements Initializable {
    @FXML Button circleButton1;
    @FXML Button circleButton2;
    @FXML Button circleButton3;
    @FXML Button circleButton4;
    @FXML AnchorPane anchor;
    public static final String BUTTON1 = "circleButton1";
    public static final String  BUTTON2 = "circleButton2";
    public static final String BUTTON3 = "circleButton3";
    public static final String BUTTON4 = "circleButton4";
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
    public void circleClicked(ActionEvent e){
      MyPopUp popup = new MyPopUp();
       Stage stage = (Stage) anchor.getScene().getWindow();
        
         if (e.getSource().equals(circleButton1)){
             popup.showPopup(BUTTON1,stage);
         }
         else if(e.getSource().equals(circleButton2)){
            popup.showPopup(BUTTON2,stage);
         }
         else if(e.getSource().equals(circleButton3)){
            popup.showPopup(BUTTON3,stage);
         }
         else if(e.getSource().equals(circleButton4)){
            popup.showPopup(BUTTON4,stage);
         }    
    }
    
    public void guessHandle(){}
    
    public void setButton1Color(String color){
        circleButton1.setStyle("-fx-background-color:"+color);
    }
    public void setButton2Color(String color){
        circleButton1.setStyle("-fx-background-color:"+color);
    }
    public void setButton3Color(String color){
        circleButton1.setStyle("-fx-background-color:"+color);
    }
    public void setButton4Color(String color){
        circleButton1.setStyle("-fx-background-color:"+color);
    }
    
    
}
