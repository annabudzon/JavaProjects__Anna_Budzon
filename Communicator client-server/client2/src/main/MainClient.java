package main;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainClient extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        MainScreen.startApplication(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
