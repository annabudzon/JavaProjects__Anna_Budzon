package main;

import boxes.ConfirmBox;
import controller.ScreensController;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class MyApplication {

    public static String screenLogIn = "logIn";
    public static String screenLogInFile = "/fxml/LogIn.fxml";
    public static String screenSignUp = "signUp";
    public static String screenSignUpFile = "/fxml/SignUp.fxml";
    public static String screenMain = "myController";
    public static String screenMainFile = "/fxml/MainScreen.fxml";
    public static Stage window;

    public static void startApplication(Stage stage) {
        final BooleanProperty controlPressed = new SimpleBooleanProperty(false);
        final BooleanProperty cPressed = new SimpleBooleanProperty(false);
        final BooleanBinding spaceAndRightPressed = controlPressed.and(cPressed);
        window = stage;
        window.setTitle("CRAWLER 3.0");
        window.setResizable(false);
        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });

        ScreensController mainContainer = new ScreensController();

        mainContainer.loadScreen(screenMain, screenMainFile);
        mainContainer.loadScreen(screenLogIn, screenLogInFile);
        mainContainer.loadScreen(screenSignUp, screenSignUpFile);

        mainContainer.setScreen(screenLogIn);

        Group root = new Group();
        root.getChildren().add(mainContainer);
        // Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

        Scene scene = new Scene(root);
        spaceAndRightPressed.addListener((ObservableValue<? extends Boolean> observable, Boolean werePressed, Boolean arePressed) -> {
            closeProgram();
        });

        scene.setOnKeyPressed((KeyEvent ke) -> {
            if (ke.getCode() == KeyCode.CONTROL) {
                controlPressed.set(true);
            } else if (ke.getCode() == KeyCode.C) {
                cPressed.set(true);
            }
        });

        scene.setOnKeyReleased((KeyEvent ke) -> {
            if (ke.getCode() == KeyCode.CONTROL) {
                controlPressed.set(false);
            } else if (ke.getCode() == KeyCode.C) {
                cPressed.set(false);
            }
        });
        window.setScene(scene);
        window.show();
    }

    public static void closeProgram() {
        ConfirmBox box = new ConfirmBox();
        boolean answer = box.display("Closing window", "Sure you want to exit?");
        if (answer) {
            window.close();
        }
    }

}
