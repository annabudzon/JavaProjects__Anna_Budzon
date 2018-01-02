package boxes;

import controller.GameController;
import controller.PopUpController;
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
    
    private GameController gameControl;

    public MyPopUp(GameController gameControl) {
        this.gameControl = gameControl;
    }

    public void showPopup(String buttonID, Stage window) {
        paintedButtonID = buttonID;
        stage = window;
        popup = new Popup();
        popup.setAutoHide(true);

        myPane = new Pane();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PopUp.fxml"));

        try {
            myPane = (Pane) loader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("FXMLLoader exception.");
        }

        controller = (PopUpController) loader.getController();
        controller.set(this.gameControl, this);
        popup.getContent().addAll(myPane);
        popup.show(stage);
        popup.setX(stage.getX());
        popup.setY(stage.getY() + 480);
    }

    public Popup getPopup() {
        return popup;
    }

    public String getButtonID() {
        return paintedButtonID;
    }
}
