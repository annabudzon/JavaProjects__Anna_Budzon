package fxml;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class MyApplication {
    public static String screenLogIn = "logIn";
    public static String screenLogInFile = "LogIn.fxml";
    public static String screenSignUp = "signUp";
    public static String screenSignUpFile = "SignUp.fxml";
    public static String screenMain = "myController";
    public static String screenMainFile = "FXMLDocument.fxml";
    public static Stage window;
        
    public static void startApplication(Stage stage) {
        final BooleanProperty spacePressed = new SimpleBooleanProperty(false);
        final BooleanProperty rightPressed = new SimpleBooleanProperty(false);
        final BooleanBinding spaceAndRightPressed = spacePressed.and(rightPressed);
        window = stage;
        window.setTitle("CRAWLER 3.0");
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
        spaceAndRightPressed.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean werePressed, Boolean arePressed) {
                closeProgram();
            }    
        });

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
             @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.CONTROL) {
                    spacePressed.set(true);
                } else if (ke.getCode() == KeyCode.C) {
                    rightPressed.set(true);
                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.CONTROL) {
                    spacePressed.set(false);
                } else if (ke.getCode() == KeyCode.C) {
                    rightPressed.set(false);
                }
            }
        });
        window.setScene(scene);
        window.show();
    }

    public static void closeProgram(){
        boolean answer = ConfirmBox.display("Closing window", "Sure you want to exit?");
        if(answer)
            window.close();
    }
    
}
