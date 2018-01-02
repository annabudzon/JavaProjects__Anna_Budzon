package boxes;

import static boxes.ConfirmBox.answer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {

    final BooleanProperty enterPressed = new SimpleBooleanProperty(false);

    public void display(String line) {
        Stage window = new Stage();
        window.setTitle("ALERT");
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(400);

        Label label = new Label(line);
        Button backButton = new Button("Continue");
        backButton.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, backButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);

        enterPressed.addListener((ObservableValue<? extends Boolean> observable, Boolean werePressed, Boolean arePressed) -> {
            answer = true;
            window.close();
        });

        scene.setOnKeyPressed((KeyEvent ke) -> {
            if (ke.getCode() == KeyCode.ENTER) {
                enterPressed.set(true);
            }
        });
        scene.setOnKeyReleased((KeyEvent ke) -> {
            if (ke.getCode() == KeyCode.ENTER) {
                enterPressed.set(false);
            }
        });

        window.showAndWait();
    }

}
