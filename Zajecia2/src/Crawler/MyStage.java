package Crawler;

import java.io.IOException;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class MyStage {
    public static Stage window;
    private static Scene scene1;
    private static AnchorPane root;
    private static TabPane tabPane;
    private static MenuBar menuBar;
    
    public static void goApp(Stage primaryStage) throws IOException {
        final BooleanProperty spacePressed = new SimpleBooleanProperty(false);
        final BooleanProperty rightPressed = new SimpleBooleanProperty(false);
        final BooleanBinding spaceAndRightPressed = spacePressed.and(rightPressed);
        window = primaryStage;
        window.setTitle("CRAWLER 2.0");
        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });
        
        root = new AnchorPane();
        menuBar = CustomMenuBar.display();
        tabPane = CustomTabPane.display();
        
        AnchorPane.setTopAnchor(menuBar, 5.0);
        AnchorPane.setTopAnchor(tabPane, 30.0);
        AnchorPane.setBottomAnchor(tabPane, 20.0);    
        menuBar.prefWidthProperty().bind(window.widthProperty());
        tabPane.prefWidthProperty().bind(window.widthProperty());
            
        root.getChildren().addAll(menuBar,tabPane);
        scene1 = new Scene(root, 600, 480);
        scene1.setFill(Color.OLDLACE);
       
        spaceAndRightPressed.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean werePressed, Boolean arePressed) {
                closeProgram();
            }    
        });

        scene1.setOnKeyPressed(new EventHandler<KeyEvent>() {
             @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.CONTROL) {
                    spacePressed.set(true);
                } else if (ke.getCode() == KeyCode.C) {
                    rightPressed.set(true);
                }
            }
        });

        scene1.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.CONTROL) {
                    spacePressed.set(false);
                } else if (ke.getCode() == KeyCode.C) {
                    rightPressed.set(false);
                }
            }
        });
        
        window.setScene(scene1);
        window.show();
        }

    public static void closeProgram(){
        boolean answer = ConfirmBox.display("Closing window", "Sure you want to exit?");
        if(answer)
            window.close();
    }
}