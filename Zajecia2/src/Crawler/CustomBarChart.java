package Crawler;

import static Crawler.Crawler.adress;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;

public class CustomBarChart extends AnchorPane {
    private static BarChart<String, Number> barChart;
    private static CategoryAxis xAxis = new CategoryAxis();
    private static  NumberAxis yAxis = new NumberAxis();
    private static List<Student> tempStudents;
    private static final int MARK_AMOUNT = 7;
        
    public static BarChart display(){
        barChart = new BarChart<>(xAxis,yAxis);
        barChart.setTitle("Distribution of Marks");
        xAxis.setLabel("Mark");       
        yAxis.setLabel("Count");
        
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Mark");    
        int[] markCount;
        markCount = markCounter();
        double j = 2.0;
        
        for (int i = 0; i < MARK_AMOUNT; i++) {
            series.getData().add(new XYChart.Data<>(Double.toString(j), markCount[i]));
            j+=0.5;
        }
       
        barChart.getData().add(series);
        return barChart;
    }
    
    public static int[] markCounter(){
        int[] temp = new int[MARK_AMOUNT];
        
        File f = new File(adress);
        try {
            tempStudents = StudentsParser.parse(f);
        } catch (IOException ex) {
            Logger.getLogger(CustomBarChart.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for(int i = 0; i < 7; i++){
            temp[i] = 0;
        }
        for(Student s : tempStudents){
            if(s.getMark() == 2.0)
                temp[0]++;
            else if(s.getMark() == 2.5)
                temp[1]++;
            else if(s.getMark() == 3.0)
                temp[2]++;
            else if(s.getMark() == 3.5)
                temp[3]++;
            else if(s.getMark() == 4.0)
                temp[4]++;
            else if(s.getMark() == 4.5)
                temp[5]++;
            else if(s.getMark() == 5.0)
                temp[6]++;       
        }
    
        return temp;
    }
}
