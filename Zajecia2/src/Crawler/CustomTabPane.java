
package Crawler;

import java.io.IOException;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CustomTabPane {
    private static TabPane tabPane;
    private static AnchorPane root;
    private static VBox vBox;
    private static TableView table;
     private static BarChart barChart;   
    
    public static TabPane display() throws IOException{
        tabPane = new TabPane();
        vBox = new VBox();
        
                
            Tab studentsTab = new Tab();
            studentsTab.setText("Students"); 
            table = CustomTableView.display();
            root = CustomTableView.createEdit();
            vBox.getChildren().addAll(table,root);
            studentsTab.setContent(vBox);
            studentsTab.setClosable(false);
            tabPane.getTabs().add(studentsTab);
            
              
            Tab logTab = new Tab();
            logTab.setText("Logs");
            logTab.setClosable(false);
            tabPane.getTabs().add(logTab);
            
            Tab histogramTab = new Tab();
            histogramTab.setText("Histogram");
            barChart = CustomBarChart.display();
            histogramTab.setClosable(false);
            histogramTab.setContent(barChart);
            tabPane.getTabs().add(histogramTab);
            
            
            /*HBox hbox = new HBox();
            hbox.getChildren().add(new Label("Tab" + i));
            hbox.setAlignment(Pos.CENTER);
            tab.setContent(hbox);*/
            
        tabPane.getSelectionModel().selectNext();
        
        return tabPane;
    }
    
}
