package controller;

import javafx.stage.Stage;

public interface ControlledScreen {

    public void setParentScreen(ScreensController screenPage);

    public void closeGame(Stage stage);
}
