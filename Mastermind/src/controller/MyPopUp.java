package controller;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class MyPopUp {

    private Stage stage;
    private Pane myPane;
    private static Popup popup;
    private String paintedButtonID = "#000000";
    private PopUpController controller;

    public void showPopup(String buttonID, Stage window) {
        paintedButtonID = buttonID;
        stage = window;
        popup = new Popup();
        popup.setAutoHide(false);
        popup.setX(200);
        popup.setY(530);
        controller = new PopUpController();
        myPane = new Pane();
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PopUp.fxml"));
            myPane = (Pane)loader.load();
            controller = (PopUpController) loader.getController();
        } catch (IOException ex) {
            System.out.println("FXMLLoader exception.");
        }
        
        controller.setButtonID(paintedButtonID);
        popup.getContent().addAll(myPane);
        popup.show(stage);
    }
    
    public static Popup getPopup(){
        return popup;
    }
}
