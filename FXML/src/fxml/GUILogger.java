package fxml;

import fxml.Crawler.STATUS;

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
        //tArea = MyController.getTextArea();
       // table = MyController.getTableView();
       // bar = MyController.getBarChart();
               
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
            
        //MyController.setTextArea(tArea);
        //MyController.setTableView(table);
        //MyController.setBarChart(bar);    
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
