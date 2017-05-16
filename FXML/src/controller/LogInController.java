package controller;

import boxes.AlertBox;
import boxes.ConfirmBox;
import main.MyApplication;
import static main.MyApplication.window;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import model.UserModel;
import user.UserSearcher;

public class LogInController implements Initializable, ControlScreen {

    private final AlertBox box = new AlertBox();
    private ScreensController myController;
    final BooleanProperty escPressed = new SimpleBooleanProperty(false);

    public enum ACCESS {
        GRANTED,
        NOT_USER,
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
        UserModel newUser = new UserModel();
        newUser.setPassword(passwordInput.getText());
        newUser.setUserName(usernameInput.getText());
        if (passwordInput.getText().trim().isEmpty() || usernameInput.getText().trim().isEmpty()) {
            box.display("Complete your username and password!");
        }

        pass = UserSearcher.search(newUser);

        switch (pass) {
            case GRANTED:
                myController.setScreen(MyApplication.screenMain);
                break;
            case NOT_USER:
                box.display("Your not the user! Sign in!");

                break;
            case WRONG_PASSWORD:
                box.display("Your password is incorrect!");
                break;
            default:
                System.out.println("Pass exception - UserSearcher error.");
                break;
        }
        usernameInput.clear();
        passwordInput.clear();
    }

    @FXML
    protected void handleSignUpEvent() {
        myController.setScreen(MyApplication.screenSignUp);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @Override
    public void setParentScreen(ScreensController screenParent) {
        myController = screenParent;
    }

    public static void closeProgram() {
        ConfirmBox box = new ConfirmBox();
        boolean answer = box.display("Closing window", "Sure you want to exit?");
        if (answer) {
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

}
