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

    @FXML
    Circle transparentCircle;

    @FXML
    Circle blackCircle;

    @FXML
    Circle whiteCircle;

    private GameController controller;

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

        if (e.getSource().equals(blackCircle)) {
            color = "#000000";
        } else if (e.getSource().equals(whiteCircle)) {
            color = "#ffffff";
        } else if (e.getSource().equals(transparentCircle)) {
            color = "transparent";
        } else {
            color = "brak";
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

    public void set(PopUpInterface buttonsControl, MyPopUp popUp) {
        this.controller = (GameController) buttonsControl;
        this.popUp = popUp;
    }
}
