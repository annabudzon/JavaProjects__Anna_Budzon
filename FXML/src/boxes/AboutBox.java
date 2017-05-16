package boxes;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AboutBox {

    public static void display() {
        Stage window = new Stage();
        window.setTitle("Aboout me");
        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(400);

        Label label = new Label("Autor: Anna Budzon");
        Button backButton = new Button("Go back");
        backButton.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, backButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    }

}
