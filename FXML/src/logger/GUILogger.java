package logger;

import fxml.Crawler.STATUS;
import controller.MyController;
import model.Student;

public class GUILogger  implements Logger {
  
        private MyController controller;
    public GUILogger() {}
    
    @Override
    public void log(STATUS status, Student student, MyController control){
        controller = control;
               
            switch(status){
            case ADDED:
                controller.setTextArea("Status: -----ADDED: "+student+"\n");
                controller.addRow(student);
                controller.loadBarChart();
                break;
            case REMOVED:
                controller.setTextArea("Status: -----REMOVED: "+student+"\n");
                controller.deleteRow(student);
                controller.loadBarChart();                
                break;
            case UNCHANGED:
                controller.setTextArea("Status: -----UNCHANGED-----\n");
                break;
        }     
    }
    
    @Override
    public void log(STATUS status, int iteracja){
    }
    @Override
    public void log(STATUS status, MyController control){
        Student student = null;
        this.log(status,student,control);  
    }  
}
