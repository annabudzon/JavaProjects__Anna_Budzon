package Crawler;

import java.io.IOException;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class MyStage {
    private static Stage window;
    private static Scene scene1;
    private static AnchorPane root;
    //private static Button button;
    
    public static void goApp(Stage primaryStage) throws IOException {
        window = primaryStage;
        window.setTitle("CRAWLER 2.0");
        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });
        root = new AnchorPane();
        /*VBox topMenu = new VBox();
        VBox centerMenu = new VBox();
        BorderPane pane = new BorderPane();*/
        MenuBar menuBar = CustomMenuBar.display();
        TabPane tabPane = CustomTabPane.display();
        
        menuBar.prefWidthProperty().bind(window.widthProperty());
        tabPane.prefWidthProperty().bind(window.widthProperty());
        root.getChildren().addAll(menuBar, tabPane);
        //centerMenu.getChildren().addAll(tabPane);
        //pane.setTop(topMenu);
        //pane.setCenter(centerMenu);
        scene1 = new Scene(root, 750, 480);
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
    
    

