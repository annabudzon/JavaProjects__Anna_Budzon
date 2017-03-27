package Crawler;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class CustomMenuBar extends MenuBar {
    private static MenuBar menuBar;
    public static MenuBar display(){
        menuBar = new MenuBar();
        // --- Menu File
        Menu menuFile = new Menu("File");
        MenuItem closeMenuItem = new MenuItem("Close  Ctrl+C");
        closeMenuItem.setOnAction(e -> MyStage.closeProgram());
        menuFile.getItems().addAll(closeMenuItem);
 
        // --- Menu About
        Menu menuAbout = new Menu("About");
        MenuItem aboutMenuItem = new MenuItem("About me");
        aboutMenuItem.setOnAction(e -> AboutBox.display());
        menuAbout.getItems().addAll(aboutMenuItem);
        menuBar.getMenus().addAll(menuFile, menuAbout);
        
                
        return menuBar;
    }
}
