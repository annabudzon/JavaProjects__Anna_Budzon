package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import message_game.MessageColors;

public class GamePanelController implements Initializable {

    @FXML
    private VBox guessPane;

    @FXML
    private VBox resultPane;

    private List<Circle> circles;

    private List<HBox> hboxes;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hboxes = new ArrayList<>();

    }

    public void setHint(MessageColors hint, double guesses) {
        circles = new ArrayList<>();

        circles.add(0, new Circle(10));
        if (hint.getColor1().equals("transparent")) {
            circles.get(0).setFill(Color.TRANSPARENT);
        } else {
            Color color1 = Color.web(hint.getColor1());
            circles.get(0).setFill(color1);
        }
        circles.get(0).setStroke(Color.BLACK);

        circles.add(1, new Circle(10));
        if (hint.getColor2().equals("transparent")) {
            circles.get(1).setFill(Color.TRANSPARENT);
        } else {
            Color color2 = Color.web(hint.getColor2());
            circles.get(1).setFill(color2);
        }
        circles.get(1).setStroke(Color.BLACK);

        circles.add(2, new Circle(10));
        if (hint.getColor3().equals("transparent")) {
            circles.get(2).setFill(Color.TRANSPARENT);
        } else {
            Color color3 = Color.web(hint.getColor3());
            circles.get(2).setFill(color3);
        }
        circles.get(2).setStroke(Color.BLACK);

        circles.add(3, new Circle(10));
        if (hint.getColor4().equals("transparent")) {
            circles.get(3).setFill(Color.TRANSPARENT);
        } else {
            Color color4 = Color.web(hint.getColor4());
            circles.get(3).setFill(color4);
        }
        circles.get(3).setStroke(Color.BLACK);

        resultPane.getChildren().add(getHBoxHint(circles));
    }

    public void setGuess(MessageColors receivedColors) {
        circles = new ArrayList<>();

        circles.add(0, new Circle(15));
        Color color1 = Color.web(receivedColors.getColor1());
        circles.get(0).setFill(color1);
        circles.get(0).setStroke(Color.BLACK);

        circles.add(1, new Circle(15));
        Color color2 = Color.web(receivedColors.getColor2());
        circles.get(1).setFill(color2);
        circles.get(1).setStroke(Color.BLACK);

        circles.add(2, new Circle(15));
        Color color3 = Color.web(receivedColors.getColor3());
        circles.get(2).setFill(color3);
        circles.get(2).setStroke(Color.BLACK);

        circles.add(3, new Circle(15));
        Color color4 = Color.web(receivedColors.getColor4());
        circles.get(3).setFill(color4);
        circles.get(3).setStroke(Color.BLACK);

        guessPane.getChildren().add(getHBox(circles));
    }

    public void setMySequence(MessageColors sequence) {
        circles = new ArrayList<>();

        circles.add(0, new Circle(15));
        Color color1 = Color.web(sequence.getColor1());
        circles.get(0).setFill(color1);
        circles.get(0).setStroke(Color.BLACK);

        circles.add(1, new Circle(15));
        Color color2 = Color.web(sequence.getColor2());
        circles.get(1).setFill(color2);
        circles.get(1).setStroke(Color.BLACK);

        circles.add(2, new Circle(15));
        Color color3 = Color.web(sequence.getColor3());
        circles.get(2).setFill(color3);
        circles.get(2).setStroke(Color.BLACK);

        circles.add(3, new Circle(15));
        Color color4 = Color.web(sequence.getColor4());
        circles.get(3).setFill(color4);
        circles.get(3).setStroke(Color.BLACK);

        guessPane.getChildren().addAll(getHBox(circles), new Separator());
        resultPane.getChildren().addAll(getHBoxHint(new ArrayList<>()), new Separator());

    }

    private HBox getHBox(List<Circle> circles) {
        HBox sequence = new HBox();
        sequence.setAlignment(Pos.CENTER);
        sequence.setSpacing(15);
        sequence.setPadding(new Insets(5));
        sequence.setPrefHeight(50);
        sequence.setStyle("-fx-background-color: rgba(200,200,200,000.3);"
                + "-fx-background-radius: 10;"
                + "-fx-border-style: solid;"
                + "-fx-border-width: 1px;"
                + "-fx-border-radius: 10;"
                + "-fx-border-color: black;");

        sequence.getChildren().addAll(circles);
        hboxes.add(sequence);
        return sequence;
    }

    private HBox getHBoxHint(List<Circle> circles) {
        HBox sequence = new HBox();
        sequence.setAlignment(Pos.CENTER);
        sequence.setSpacing(15);
        sequence.setPadding(new Insets(10));
        sequence.setPrefHeight(50);
        sequence.setStyle("-fx-background-color: rgba(200,200,200,000.3);"
                + "-fx-background-radius: 10;"
                + "-fx-border-style: solid;"
                + "-fx-border-width: 1px;"
                + "-fx-border-radius: 10;"
                + "-fx-border-color: black;");

        sequence.getChildren().addAll(circles);
        hboxes.add(sequence);
        return sequence;
    }

    public void refresh() {
        if (hboxes.isEmpty()) {
            return;
        }
        guessPane.getChildren().removeAll(hboxes);
        resultPane.getChildren().removeAll(hboxes);
    }
}
