package controller;

import boxes.MyPopUp;
import boxes.AlertBox;
import client_player.ClientPlayer;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import static message_communicator.MessageType.LOGOUT;
import message_game.MessageColors;
import static message_game.MessageState.GUESS;

public class GameController implements Initializable, ControlledScreen {

    @FXML
    private Button guessButton;

    @FXML
    private Button circleButton1;

    @FXML
    private Button circleButton2;

    @FXML
    private Button circleButton3;

    @FXML
    private Button circleButton4;

    @FXML
    private AnchorPane anchor;

    @FXML
    private GamePanelController gamePanelControlController;

    private ScreensController myController;

    private MainScreenController mainController;

    private ClientPlayer client;

    private Thread clientThread;

    private final String HOST = "127.0.0.1";

    private final int PORT = 9998;

    public static final String BUTTON1 = "circleButton1";

    public static final String BUTTON2 = "circleButton2";

    public static final String BUTTON3 = "circleButton3";

    public static final String BUTTON4 = "circleButton4";

    private String color1, color2, color3, color4;

    private static double guesses;

    private final double AMOUNT_OF_GUESSES = 10;

    @FXML
    public void circleClicked(ActionEvent e) {
        MyPopUp popup = new MyPopUp(this);
        Stage stage = (Stage) anchor.getScene().getWindow();

        if (e.getSource().equals(circleButton1)) {
            popup.showPopup(BUTTON1, stage);
        } else if (e.getSource().equals(circleButton2)) {
            popup.showPopup(BUTTON2, stage);
        } else if (e.getSource().equals(circleButton3)) {
            popup.showPopup(BUTTON3, stage);
        } else if (e.getSource().equals(circleButton4)) {
            popup.showPopup(BUTTON4, stage);
        }
    }

    @FXML
    public void guessHandle() {
        if (color1 == null || color2 == null || color3 == null || color4 == null) {
            AlertBox alert = new AlertBox();
            alert.display("Nie wybrano kolorow!");
        } else if (guesses <= AMOUNT_OF_GUESSES) {
            MessageColors messageColors = new MessageColors(GUESS, color1, color2, color3, color4, guesses);
            gamePanelControlController.setGuess(messageColors);
            client.sendMessage(messageColors, client.getSocket());
            setDisable(true);
            guesses++;
        } else {

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        guesses = 1.0;
    }

    @Override
    public void setParentScreen(ScreensController screenParent) {
        myController = screenParent;
    }

    @Override
    public void closeGame(Stage stage) {
    }

    public void connectGame(MainScreenController mainController) {

        this.mainController = mainController;
        try {
            client = new ClientPlayer(HOST, PORT, this);
        } catch (IOException ex) {
            System.out.println("Cannot create new Client. IOException. \n");
        }
        clientThread = new Thread(client);
        clientThread.start();
    }

    public void disconnectGame() {
        MessageColors msg = new MessageColors(LOGOUT);
        client.sendMessage(msg, client.getSocket());
        client.disconnect(client.getSocket());
        try {
            clientThread.join();
        } catch (InterruptedException ex) {
            System.out.println("InterruptedException.\n");
        }
    }

    public void setConnected() {
        this.setDisable(false);
    }

    public void setDisconnected() {
        this.setDisable(true);
        refresh();
        //this.disconnectGame();
    }

    public void setHint(MessageColors sequence) {
        gamePanelControlController.setHint(sequence);
    }

    public void setWinGame() {
        setDisconnected();
        mainController.setGameWin();
    }

    public void setGameOver() {
        setDisconnected();
        mainController.setGameOver();
    }

    public void setDisable(boolean ifDisable) {
        guessButton.setDisable(ifDisable);
        circleButton1.setDisable(ifDisable);
        circleButton2.setDisable(ifDisable);
        circleButton3.setDisable(ifDisable);
        circleButton4.setDisable(ifDisable);
    }

    public void refresh() {
        gamePanelControlController.refresh();
    }

    public void setButton1Color(String color) {
        if (!color.equals("brak")) {
            circleButton1.setStyle("-fx-border-style: solid;"
                    + "-fx-border-color: black;"
                    + "-fx-border-width: 1px;"
                    + "-fx-border-radius: 30px;"
                    + "-fx-background-color:" + color);
            color1 = color;
        }
    }

    public void setButton2Color(String color) {
        if (!color.equals("brak")) {
            circleButton2.setStyle("-fx-border-style: solid;"
                    + "-fx-border-color: black;"
                    + "-fx-border-width: 1px;"
                    + "-fx-border-radius: 30px;"
                    + "-fx-background-color:" + color);

            color2 = color;
        }
    }

    public void setButton3Color(String color) {
        if (!color.equals("brak")) {
            circleButton3.setStyle("-fx-border-style: solid;"
                    + "-fx-border-color: black;"
                    + "-fx-border-width: 1px;"
                    + "-fx-border-radius: 30px;"
                    + "-fx-background-color:" + color);
            color3 = color;
        }
    }

    public void setButton4Color(String color) {
        if (!color.equals("brak")) {
            circleButton4.setStyle("-fx-border-style: solid;"
                    + "-fx-border-color: black;"
                    + "-fx-border-width: 1px;"
                    + "-fx-border-radius: 30px;"
                    + "-fx-background-color:" + color);
            color4 = color;
        }
    }
}
