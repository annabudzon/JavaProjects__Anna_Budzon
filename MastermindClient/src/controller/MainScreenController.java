package controller;

import boxes.ConfirmBox;
import client.Client;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import static main.Window.screenGameOver;
import static main.Window.screenGameWin;
import static main.Window.screenMenu;
import message_communicator.MessageLogger;
import message_communicator.MessageModel;
import static message_communicator.MessageType.LOGOUT;
import static message_communicator.MessageType.MESSAGE;

public class MainScreenController implements Initializable, ControlledScreen {

    @FXML
    private VBox vBox;

    @FXML
    private MessageBoxController messageBoxControlController;

    @FXML
    private GameController gameControlController;

    @FXML
    private TextField messageField;

    @FXML
    private Button sendButton;

    private Stage window;

    private ScreensController myController;

    private Client client;

    private Thread clientThread;

    private MessageLogger msgLogger;

    private final String HOST = "127.0.0.1";

    private final int PORT = 9999;

    private String clientName;

    final BooleanProperty escPressed = new SimpleBooleanProperty(false);

    private boolean connected;

    @FXML
    public void handleClose() {
        closeProgram();
    }

    @FXML
    public void backMenuHandle() {

        ConfirmBox box = new ConfirmBox();
        boolean answer = box.display("Game interruption", "Sure you want to exit the game?");
        if (answer) {
            setLoggedOut();
        }
    }

    @FXML
    public void sendHandle() {

        if (client.getSocket() != null) {

            String message = messageField.getText();
            MessageModel msg = new MessageModel(this.clientName, MESSAGE, message);
            msgLogger.log(msg);
            client.sendMessage(msg, client.getSocket());
        } else {
            System.out.println("Cannot get socket channel of the client.\n");
        }
        messageField.clear();
    }

    @Override
    public void setParentScreen(ScreensController screenParent) {
        myController = screenParent;
    }

    @Override
    public void closeGame(Stage window) {
        this.window = window;
        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        connected = false;
    }

    public void setLogged() {
        messageField.setDisable(true);
        sendButton.setDisable(true);
        gameControlController.setDisable(true);

        if (!connected) {
            connectCommunicator();
            connectGame();
        }
        connected = true;
    }
    
    public void setLoggedOut() {
        myController.setScreen(screenMenu);
            MessageModel msg = new MessageModel(this.clientName, LOGOUT, " has been logged out. \n");
            client.sendMessage(msg, client.getSocket());
            messageBoxControlController.refresh();
            gameControlController.refresh();
            //client.disconnect(client.getSocket());
            /*try {
                clientThread.join();
            } catch (InterruptedException e) {
                System.out.println("InteruptedException.\n");
            }*/
    }

    public void setConnected() {
        messageField.setDisable(false);
        sendButton.setDisable(false);
    }

    public void setDisconnected() {
        messageField.setDisable(true);
        sendButton.setDisable(true);
    }

    private void connectCommunicator() {
        msgLogger = new MessageLogger(this);
        this.clientName = "Client player";
        try {
            client = new Client(HOST, PORT, this, this.clientName);
        } catch (IOException ex) {
            System.out.println("Cannot create new Client.");
        }
        clientThread = new Thread(client);
        clientThread.start();
    }

    private void connectGame() {
        gameControlController.connectGame(this);
    }

    public void setText(String message) {
        messageBoxControlController.setText(message);
    }

    public void setGameOver() {

        myController.setScreen(screenGameOver);
        messageBoxControlController.refresh();
        //client.disconnect(client.getSocket());
        //try {
        //serverThread.join();   
        //} catch (InterruptedException e) {
        //    System.out.println("InterruptedException.\n");
        //}
    }

    public void setGameWin() {

        myController.setScreen(screenGameWin);
        messageBoxControlController.refresh();
        //client.disconnect(client.getSocket());
        //try {
        //serverThread.join();
        //} catch (InterruptedException e) {
        // System.out.println("InterruptedException.\n");
        // }
    }

    public void closeProgram() {
        ConfirmBox box = new ConfirmBox();
        boolean answer = box.display("Closing window", "Sure you want to exit?");
        if (answer) {
            MessageModel msg = new MessageModel(this.clientName, LOGOUT, " has been logged out.");
            if (client.getSocket() != null && client.isServerConnected()) {
                client.sendMessage(msg, client.getSocket());
                client.disconnect(client.getSocket());
                try {
                    clientThread.join();
                } catch (InterruptedException ex) {
                    System.out.println("InterruptedException.\n");
                }
            }
            window.close();
        }
    }

    public void handleEscPressed() {
        escPressed.addListener((ObservableValue<? extends Boolean> observable, Boolean werePressed, Boolean arePressed) -> {
            closeProgram();
        });

        vBox.getScene().setOnKeyPressed((KeyEvent ke) -> {
            if (ke.getCode() == KeyCode.ESCAPE) {
                escPressed.set(true);
            }
        });
    }

    public void handleEscReleased() {
        vBox.getScene().setOnKeyReleased((KeyEvent ke) -> {
            if (ke.getCode() == KeyCode.ESCAPE) {
                escPressed.set(false);
            }
        });
    }

}
