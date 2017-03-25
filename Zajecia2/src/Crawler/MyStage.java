package Crawler;

import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class MyStage {
    private static Stage window;
    private static Scene scene1;
    //private static Button button;
    
    public static void goApp(Stage primaryStage) {
        window = primaryStage;
        window.setTitle("CRAWLER 2.0");
        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });
        VBox topMenu = new VBox();
        VBox centerMenu = new VBox();
        BorderPane pane = new BorderPane();
        MenuBar menuBar = CustomMenuBar.display();
        TabPane tabPane = CustomTabPane.display();
        
        topMenu.getChildren().addAll(menuBar);
        centerMenu.getChildren().addAll(tabPane);
        pane.setTop(topMenu);
        pane.setCenter(centerMenu);
        scene1 = new Scene(pane, 400, 300);
        scene1.setFill(Color.OLDLACE);
               
        window.setScene(scene1);
        window.show();
    }

    
    public static void closeProgram(){
        boolean answer = ConfirmBox.display("Closing window", "Sure you want to exit?");
        if(answer)
            window.close();
    }
}
    
    

