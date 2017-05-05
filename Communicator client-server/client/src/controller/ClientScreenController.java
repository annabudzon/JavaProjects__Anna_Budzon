package controller;

import boxes.ConfirmBox;
import client.Client;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.MainScreen;
import message.MessageLogger;
import static message.MessageType.*;
import model.UserModel;

public class ClientScreenController implements Initializable, ControlScreen {

    private Client client;

    private MessageLogger msgLogger;

    private final String HOST = "127.0.0.1";

    private final int PORT = 9999;

    private String clientName;

    final BooleanProperty escPressed = new SimpleBooleanProperty(false);

    private ScreensController myController;

    private UserModel user;

    @FXML
    private VBox vBox;
    @FXML
    private TextAreaController textAreaControlController;
    @FXML
    private TableViewController tableViewControlController;
    @FXML
    private TextField messageField;

    @FXML
    public void handleClose(ActionEvent event) {
        closeProgram();
    }

    @FXML
    public void handleSend(ActionEvent event) {
        if (client.getSocket() != null) {
            String message = this.clientName + ": " + messageField.getText() + "\n";
            msgLogger.log(MESSAGE, message);
            client.sendMessage(MESSAGE + ":" + message, client.getSocket());
        } else {
            System.out.println("Cannot get socket channel of the client.\n");
        }
        messageField.clear();
    }

    @FXML
    public void handleConnect(ActionEvent event) {
        new Thread(client).start();
        msgLogger.log(CONNECTED, this.clientName);
        addUser(user);
    }

    @FXML
    public void handleDisconnect(ActionEvent event) {
        client.disconnect(client.getSocket());
        msgLogger.log(LOGOUT, "You have been logged out.\n");
        removeUser(this.clientName);
    }

    @FXML
    public void handleLogout(ActionEvent event) {
        myController.setScreen(MainScreen.screenLogIn);
        client.sendMessage(LOGOUT + ":" + this.clientName, client.getSocket());
        removeUser(this.clientName);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Random randomizer = new Random();
        this.clientName = "user" + ((long) randomizer.nextInt() - (long) Integer.MIN_VALUE);
        try {
            client = new Client(HOST, PORT, this, this.clientName);
        } catch (IOException ex) {
            System.out.println("Cannot create new Client.");
        }
        new Thread(client).start();
    }

    @Override
    public void setParentScreen(ScreensController screenParent) {
        myController = screenParent;
    }

    public void setText(String message) {
        textAreaControlController.setText(message);
    }

    public void addUser(UserModel user) {
        tableViewControlController.addUser(user);
    }

    public void removeUser(String userName) {
        tableViewControlController.removeUser(userName);
    }

    public void closeProgram() {
        ConfirmBox box = new ConfirmBox();
        boolean answer = box.display("Closing window", "Sure you want to exit?");
        if (answer) {
            Stage window = (Stage) vBox.getScene().getWindow();
            client.disconnect(client.getSocket());
            client.sendMessage(LOGOUT+":"+this.clientName, client.getSocket());
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

    public void setLogged(UserModel user) {
        this.clientName = user.getUserName();
        System.out.println(this.clientName);
        client.setUserName(this.clientName);
        this.user = user;
        this.addUser(user);
        msgLogger = new MessageLogger(this);
        msgLogger.log(LOGIN, this.clientName);
        client.sendMessage(LOGIN + ":" + this.clientName, client.getSocket());       
    }

    public void setRegistered(List<String> registered) {
        for (String u : registered) {
            msgLogger.log(REGISTER, u);
            client.sendMessage(REGISTER + ":" + u, client.getSocket());
        }

    }
}
