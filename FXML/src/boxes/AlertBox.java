package boxes;

import static boxes.ConfirmBox.answer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
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
       
    public void display(String line){
        Stage window = new Stage();
        window.setTitle("ALERT");
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(400);
        
        Label label = new Label(line);
        Button backButton = new Button("Continue");
        backButton.setOnAction(e -> window.close());
       
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label,backButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        
        enterPressed.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean werePressed, Boolean arePressed) {
                answer = true;
                window.close();
            }
        });

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.ENTER) {
                    enterPressed.set(true);
                }
            }
        });
        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.ENTER) {
                    enterPressed.set(false);
                }
            }
        });
        
        window.showAndWait();        
    }
    
}

