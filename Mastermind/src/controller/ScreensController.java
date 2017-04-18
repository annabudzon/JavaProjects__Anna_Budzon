package controller;

import controller.ControlledScreen;
import java.io.IOException;
import java.util.HashMap;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;

public class ScreensController extends StackPane {

	private HashMap<String, Node> screens = new HashMap<>();

	public ScreensController() {
		super();
	}

	public void addScreen(String name, Node screen) {
		screens.put(name, screen);
	}

	public Node getScreen(String name) {
		return screens.get(name);
	}

	public boolean loadScreen(String name, String resource) {
		try {
			FXMLLoader myLoader = new FXMLLoader(getClass().getResource(resource));
			Parent loadScreen = (Parent) myLoader.load();
			ControlledScreen myController = (ControlledScreen) myLoader.getController();
			myController.setParentScreen(this);
			addScreen(name, loadScreen);
			return true;
		} catch (IOException ex) {
			return false;
		}
	}

	public boolean setScreen(final String name) {
		if (screens.get(name) != null) {
			final DoubleProperty opacity = opacityProperty();
			if (!getChildren().isEmpty()) {
				getChildren().remove(0);
				getChildren().add(0, screens.get(name));

			} else {
				getChildren().add(screens.get(name));
			}
			return true;
		} else {
			System.out.println("No screen has been loaded! ");
			return false;
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
