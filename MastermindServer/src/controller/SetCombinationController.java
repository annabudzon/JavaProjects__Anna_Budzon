package controller;

import boxes.MyPopUp;
import boxes.AlertBox;
import boxes.SetCombinationBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import message_game.MessageColors;
import static message_game.MessageState.SEQUENCE;

public class SetCombinationController implements Initializable, PopUpInterface {

    @FXML
    private VBox vBox;
    
    @FXML
    private Button circleButton1;

    @FXML
    private Button circleButton2;

    @FXML
    private Button circleButton3;

    @FXML
    private Button circleButton4;
    
    public static final String BUTTON1 = "circleButton1";

    public static final String BUTTON2 = "circleButton2";

    public static final String BUTTON3 = "circleButton3";

    public static final String BUTTON4 = "circleButton4";
    
    private String color1, color2, color3, color4;
    
    private GameController controller;
    
    private SetCombinationBox box;

    @FXML
    private void buttonsClicked(ActionEvent e) {
        MyPopUp popup = new MyPopUp(this);
        Stage stage = (Stage) vBox.getScene().getWindow();

        if (e.getSource().equals(circleButton1)) {
            popup.showPopup(BUTTON1, stage);
        } else if (e.getSource().equals(circleButton2)) {
            popup.showPopup(BUTTON2, stage);
        } else if (e.getSource().equals(circleButton3)) {
            popup.showPopup(BUTTON3, stage);
        } else if (e.getSource().equals(circleButton4)) {
            popup.showPopup(BUTTON4, stage);
        }
    }
    
    @FXML 
    private void handleContinue(){
        
        if (color1 == null || color2 == null || color3 == null || color4 == null) {
            AlertBox alert = new AlertBox();
            alert.display("Nie wybrano kolorow!");
        } else {
            MessageColors messageColors = new MessageColors(SEQUENCE, color1, color2, color3, color4);
            controller.setMySequence(messageColors);
            box.close();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
    @Override
    public void setButton1Color(String color) {
        if (!color.equals("brak")) {
            circleButton1.setStyle("-fx-border-style: solid;"
                    + "-fx-border-color: black;"
                    + "-fx-border-width: 1px;"
                    + "-fx-border-radius: 30px;"
                    + "-fx-background-color:" + color);
            color1 = color;
        }
    }

    @Override
    public void setButton2Color(String color) {
        if (!color.equals("brak")) {
            circleButton2.setStyle("-fx-border-style: solid;"
                    + "-fx-border-color: black;"
                    + "-fx-border-width: 1px;"
                    + "-fx-border-radius: 30px;"
                    + "-fx-background-color:" + color);

            color2 = color;
        }
    }

    @Override
    public void setButton3Color(String color) {
        if (!color.equals("brak")) {
            circleButton3.setStyle("-fx-border-style: solid;"
                    + "-fx-border-color: black;"
                    + "-fx-border-width: 1px;"
                    + "-fx-border-radius: 30px;"
                    + "-fx-background-color:" + color);
            color3 = color;
        }
    }

    @Override
    public void setButton4Color(String color) {
        if (!color.equals("brak")) {
            circleButton4.setStyle("-fx-border-style: solid;"
                    + "-fx-border-color: black;"
                    + "-fx-border-width: 1px;"
                    + "-fx-border-radius: 30px;"
                    + "-fx-background-color:" + color);
            color4 = color;
        }
    }

    public void set(GameController controller, SetCombinationBox box){
        this.controller = controller;
        this.box = box;
    }
}
