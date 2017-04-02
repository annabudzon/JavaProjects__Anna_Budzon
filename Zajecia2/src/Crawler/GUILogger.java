package Crawler;

import Crawler.Crawler.STATUS;
import static Crawler.CustomBarChart.getBarChart;
import static Crawler.CustomBarChart.setBarChart;
import static Crawler.CustomTableView.getTableView;
import static Crawler.CustomTableView.setTableView;
import static Crawler.CustomTextArea.getTextArea;
import static Crawler.CustomTextArea.setTextArea;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

public class GUILogger  implements Logger {
   
    public GUILogger() {}
        private static TableView table;
        private static TextArea tArea;
        private static BarChart bar;
        
    @Override
    public void log(STATUS status, Student student){
        tArea = getTextArea();
        table = getTableView();
        bar = getBarChart();
               
            switch(status){
            case ADDED:
                 tArea.appendText("Status: -----ADDED: "+student+"\n");
                 if(!table.getItems().contains(student)){
                 table.getItems().add(student);
                 }
                break;
            case REMOVED:
                tArea.appendText("Status: -----REMOVED: "+student+"\n");
                if(table.getItems().contains(student)){
                ObservableList<Student>  allStudents = table.getItems();
                ObservableList<Student> remove = FXCollections.observableArrayList();
                remove.add(student);
                remove.forEach(allStudents::remove);
                }
                break;
            case UNCHANGED:
                tArea.appendText("Status: -----UNCHANGED-----\n");
                break;
        } 
            
        setTextArea(tArea);
        setTableView(table);
        setBarChart(bar);    
            //CustomTextArea.printLog(status, student);  
    }
    
    @Override
    public void log(STATUS status, int iteracja){
    }
    @Override
    public void log(STATUS status){
        Student student = null;
        this.log(status,student);  
    }  
}
