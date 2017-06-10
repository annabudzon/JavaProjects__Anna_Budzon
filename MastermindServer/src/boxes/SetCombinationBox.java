package boxes;

import controller.GameController;
import controller.SetCombinationController;
import java.io.IOException;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SetCombinationBox {

    final BooleanProperty enterPressed = new SimpleBooleanProperty(false);

    private Stage window;
    private Pane myPane;
    private GameController controller;
    private SetCombinationController combController;

    public SetCombinationBox(GameController controller) {
        this.controller = controller;
    }

    public void display() {
        window = new Stage();
        window.setTitle("Set your combination!");
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(400);
        window.setMinHeight(300);
        window.setOnCloseRequest(e -> {
            e.consume();
        });
        window.resizableProperty().setValue(Boolean.FALSE);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SetCombination.fxml"));

        try {
            myPane = (Pane) loader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("FXMLLoader exception.");
        }

        combController = (SetCombinationController) loader.getController();
        combController.set(this.controller, this);

        Scene scene = new Scene(myPane);
        window.setScene(scene);
        window.showAndWait();
    }

    public void close() {
        window.close();
    }
}
