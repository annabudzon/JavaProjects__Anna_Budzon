package main;

import boxes.ConfirmBox;
import controller.ScreensController;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainScreen {

    private static Stage window;
    public static String screenLogIn = "logIn";
    public static String screenLogInFile = "/fxml/LogIn.fxml";
    public static String screenSignUp = "signUp";
    public static String screenSignUpFile = "/fxml/SignUp.fxml";
    public static String screenMain = "clientScreen";
    public static String screenMainFile = "/fxml/ClientScreen.fxml";

    public static void startApplication(Stage stage) {
        window = stage;
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

        Scene scene = new Scene(root);
        window.setTitle("Chat");
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
