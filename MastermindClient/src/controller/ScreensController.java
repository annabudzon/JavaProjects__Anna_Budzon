package controller;

import java.io.IOException;
import java.util.HashMap;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

public class ScreensController extends StackPane {

    private final HashMap<String, Node> screens = new HashMap<>();
    
    private final HashMap<String, FXMLLoader> loaders = new HashMap<>();

    public ScreensController() {
        super();
    }

    public void addScreen(String name, Node screen) {
        screens.put(name, screen);
    }

    public void addLoader(String name, FXMLLoader loader) {
        loaders.put(name, loader);
    }

    public Node getScreen(String name) {
        return screens.get(name);
    }

    public ControlledScreen loadScreen(String name, String resource) {
        try {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource(resource));
            Parent loadScreen = (Parent) myLoader.load();
            ControlledScreen myController = (ControlledScreen) myLoader.getController();
            myController.setParentScreen(this);
            addScreen(name, loadScreen);
            addLoader(name, myLoader);
            return myController;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public FXMLLoader setScreen(final String name) {
        if (screens.get(name) != null) {
            final DoubleProperty opacity = opacityProperty();
            if (!getChildren().isEmpty()) {
                getChildren().remove(0);
                getChildren().add(0, screens.get(name));

            } else {
                getChildren().add(screens.get(name));
            }
            return loaders.get(name);
        } else {
            System.out.println("No screen has been loaded! ");
            return null;
        }
    }

    public boolean unloadScreen(String name) {
        if (screens.remove(name) == null) {
            System.out.println("Screen doesn't exist! ");
            return false;
        } else {
            return true;
        }
    }

}
