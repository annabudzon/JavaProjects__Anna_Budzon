package main;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class StartWindow {

    public static final int WIDTH = 1333;
    public static final int HEIGHT = 589;
    public static HBox layout;

    public Scene createStartScene(Stage window) {
        layout = new HBox();
        final Canvas canvas = new Canvas(WIDTH, HEIGHT);
        layout.getChildren().add(canvas);

        Image image = new Image("/images/start.jpg");
        Scene sceneStart = new Scene(layout, WIDTH, HEIGHT);
        ImagePattern pattern = new ImagePattern(image);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.WHITE);
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(1);
        Font theFont = Font.font("Times New Roman", FontWeight.BOLD, 24);
        gc.setFont(theFont);
        gc.fillText("Click anywhere to continue...", WIDTH / 2 - 150, HEIGHT - 30);
        gc.strokeText("Click anywhere to continue...", WIDTH / 2 - 150, HEIGHT - 30);

        sceneStart.setFill(pattern);

        EventHandler<MouseEvent> eventHandler = (MouseEvent e) -> {
            window.close();
        };
        //Registering the event filter 
        sceneStart.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
        return sceneStart;
    }
}
