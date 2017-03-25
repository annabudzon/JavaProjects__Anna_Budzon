
package Crawler;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class CustomTabPane extends AnchorPane {
    private static TabPane tabPane;
    
    public static TabPane display(){
        tabPane = new TabPane();
        
            Tab studentsTab = new Tab();
            studentsTab.setText("Students");
            
            Tab logTab = new Tab();
            logTab.setText("Logs");
            
            Tab histogramTab = new Tab();
            histogramTab.setText("Histogram");
            
            
            /*HBox hbox = new HBox();
            hbox.getChildren().add(new Label("Tab" + i));
            hbox.setAlignment(Pos.CENTER);
            tab.setContent(hbox);*/
            
                     
        tabPane.getTabs().addAll(histogramTab,logTab,studentsTab);
        return tabPane;
    }
    
}
