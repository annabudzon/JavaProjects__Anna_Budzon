package controller;

import boxes.AlertBox;
import boxes.ConfirmBox;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import main.MainScreen;
import model.UserModel;
import user.UserSearcher;

public class LogInController implements Initializable, ControlScreen {

    private final AlertBox albox = new AlertBox();

    private final BooleanProperty escPressed = new SimpleBooleanProperty(false);

    private ScreensController myController;

    private List<String> registered = new ArrayList<>();

    private String loggedIn;

    public enum ACCESS {
        GRANTED,
        WRONG_PASSWORD,
        EXCEPTION
    }
    @FXML
    TextField usernameInput;
    @FXML
    PasswordField passwordInput;
    @FXML
    BorderPane borderPane;

    @FXML
    protected void handleSignInEvent() {
        ACCESS pass;
        if (passwordInput.getText().trim().isEmpty() || usernameInput.getText().trim().isEmpty()) {
            albox.display("Complete your username and password!");
        } else {
            UserModel newUser = new UserModel();
            newUser.setPassword(passwordInput.getText());
            newUser.setUserName(usernameInput.getText());

            pass = UserSearcher.search(newUser);

            switch (pass) {
                case GRANTED:
                    FXMLLoader loader = myController.setScreen(MainScreen.screenMain);
                    ClientScreenController clientControl = loader.getController();
                    clientControl.setLogged(newUser);
                    clientControl.setRegistered(registered);
                    break;
                case WRONG_PASSWORD:
                    albox.display("Your password is incorrect!");
                    break;
                default:
                    albox.display("Your not the user! Sign in!");
                    break;
            }
        }
        usernameInput.clear();
        passwordInput.clear();
    }

    @FXML
    protected void handleSignUpEvent() {
        myController.setScreen(MainScreen.screenSignUp);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @Override
    public void setParentScreen(ScreensController screenParent) {
        myController = screenParent;
    }

    public void closeProgram() {
        ConfirmBox box = new ConfirmBox();
        boolean answer = box.display("Closing window", "Sure you want to exit?");
        if (answer) {
            Stage window = (Stage) borderPane.getScene().getWindow();
            window.close();
        }
    }

    public void handleEscPressed() {
        escPressed.addListener((ObservableValue<? extends Boolean> observable, Boolean werePressed, Boolean arePressed) -> {
            closeProgram();
        });

        borderPane.getScene().setOnKeyPressed((KeyEvent ke) -> {
            if (ke.getCode() == KeyCode.ESCAPE) {
                escPressed.set(true);
            }
        });
    }

    public void handleEscReleased() {
        borderPane.getScene().setOnKeyReleased((KeyEvent ke) -> {
            if (ke.getCode() == KeyCode.ESCAPE) {
                escPressed.set(false);
            }
        });
    }

    public void setRegistered(String name) {
        this.registered.add(name);
    }

    public void setLoggedIn(String name) {
        this.loggedIn = name;
    }

}
