package boxes;

import controller.GameController;
import controller.PopUpCombController;
import controller.PopUpController;
import controller.PopUpInterface;
import controller.SetCombinationController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class MyPopUp {

    private Stage stage;
    private Pane myPane;
    private static Popup popup;
    private String paintedButtonID = "#000000";
    private PopUpController controller;
    private PopUpCombController controllerComb;
    private PopUpInterface buttonsControl;

    public MyPopUp(PopUpInterface buttonsControl) {

        this.buttonsControl = buttonsControl;
    }

    public void showPopup(String buttonID, Stage window) {
        paintedButtonID = buttonID;
        stage = window;
        popup = new Popup();
        popup.setAutoHide(true);

        myPane = new Pane();

        FXMLLoader loader = null;

        if (this.buttonsControl instanceof GameController) {
            loader = new FXMLLoader(getClass().getResource("/fxml/PopUp.fxml"));
        } else if (this.buttonsControl instanceof SetCombinationController) {
            loader = new FXMLLoader(getClass().getResource("/fxml/PopUpCombination.fxml"));
        }

        try {
            myPane = (Pane) loader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("FXMLLoader exception.");
        }

        if (this.buttonsControl instanceof GameController) {
            controller = (PopUpController) loader.getController();
            controller.set(this.buttonsControl, this);
        } else if (this.buttonsControl instanceof SetCombinationController) {
            controllerComb = (PopUpCombController) loader.getController();
            controllerComb.set(this.buttonsControl, this);
        }

        popup.getContent().addAll(myPane);
        popup.show(stage);
        popup.setX(stage.getX());

        if (this.buttonsControl instanceof GameController) {
            popup.setY(stage.getY() + 480);
        } else if (this.buttonsControl instanceof SetCombinationController) {
            popup.setY(stage.getY() + 200);
        }
    }

    public Popup getPopup() {
        return popup;
    }

    public String getButtonID() {
        return paintedButtonID;
    }
}
