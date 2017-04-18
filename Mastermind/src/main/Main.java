package main;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	public static void main(String[] args)
    {
        // Use the event dispatch thread to build the UI for thread-safety.
        launch(args);
    }

	@Override
	public void start(Stage primaryStage) throws Exception {
		new Window(primaryStage);
	}
	

}