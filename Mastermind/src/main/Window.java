package main;

import controller.ScreensController;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import boxes.ConfirmBox;

public class Window {

    public static String screenMenu = "menu";
    public static String screenMenuFile = "/fxml/MenuScreen.fxml";
    public static String screenGame = "game";
    public static String screenGameFile = "/fxml/GameScreen.fxml";
    public static String screenGameOver = "gameOver";
    public static String screenGameOverFile = "/fxml/GameOverScreen.fxml";
    public static String screenSettings = "settings";
    public static String screenSettingsFile = "/fxml/SettingsScreen.fxml";
    public static String screenRules = "rules";
    public static String screenRulesFile = "/fxml/RulesScreen.fxml";
    public static String screenSetIP = "setIP";
    public static String screenSetIPFile = "/fxml/SetIP.fxml";
    private Stage window;
    private Stage startStage;
    private Group root;
    private StartWindow startWindow;

    public Window() {}
    
    public void startApplication(Stage primaryStage){
        window = primaryStage;
        window.setTitle("MASTERMIND by Annek&Gosiak");
        window.setWidth(965);
        window.setHeight(568);
        window.setResizable(false);
        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });

        startWindow = new StartWindow();
        startStage = new Stage();
        startStage.setTitle("MASTERMIND by Annek&Gosiak");
        startStage.setScene(startWindow.createStartScene(startStage));
        startStage.showAndWait();

        ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen(screenMenu, screenMenuFile);
        mainContainer.loadScreen(screenGame, screenGameFile);
        mainContainer.loadScreen(screenGameOver, screenGameOverFile);
        mainContainer.loadScreen(screenSettings, screenSettingsFile);
        mainContainer.loadScreen(screenRules, screenRulesFile);
//        mainContainer.loadScreen(screenSetIP, screenSetIPFile);

        root = new Group();
        root.getChildren().add(mainContainer);
  
        Scene scene = new Scene(root);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.ESCAPE) {
                    closeProgram();
                }
            }
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        mainContainer.setScreen(screenMenu);
        window.setScene(scene);
        window.show();
    }

    public void closeProgram() {
        boolean answer = ConfirmBox.display("Closing window", "Sure you want to exit?");
        if (answer) {
            getStage().close();
        }
    }
    
    public Stage getStage(){
        return this.window;
    }
}
