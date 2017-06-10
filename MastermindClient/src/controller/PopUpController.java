package controller;

import boxes.MyPopUp;
import static controller.GameController.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Popup;

public class PopUpController implements Initializable {

    private GameController controller;

    @FXML
    Circle yellowCircle;

    @FXML
    Circle orangeCircle;

    @FXML
    Circle redCircle;

    @FXML
    Circle violetCircle;

    @FXML
    Circle blueCircle;

    @FXML
    Circle greenCircle;

    private MyPopUp popUp;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void exitClicked(MouseEvent e) {
        Popup popup = popUp.getPopup();
        popup.hide();
    }

    public void colorClicked(MouseEvent e) {
        String color = "#000000";

        Popup popup = popUp.getPopup();
        popup.hide();

        if (e.getSource().equals(yellowCircle)) {
            color = "#f1ff1f";
        } else if (e.getSource().equals(orangeCircle)) {
            color = "#ffb221";
        } else if (e.getSource().equals(redCircle)) {
            color = "#ff2121";
        } else if (e.getSource().equals(violetCircle)) {
            color = "#a467b2";
        } else if (e.getSource().equals(blueCircle)) {
            color = "#59ccdd";
        } else if (e.getSource().equals(greenCircle)) {
            color = "#33bc3c";
        } else {
            color = "#000000";
        }

        switch (popUp.getButtonID()) {
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

    public void set(GameController gameControl, MyPopUp popUp) {
        this.controller = gameControl;
        this.popUp = popUp;
    }
}
