package Crawler;

import static Crawler.Crawler.adress;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

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
       
        Timeline tl = new Timeline();
        tl.getKeyFrames().add(new KeyFrame(Duration.millis(500), 
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                int i = 0;
                for (XYChart.Series<String, Number> series : barChart.getData()) {
                    for (XYChart.Data<String, Number> data : series.getData()) {
                        int[] markCount = markCounter();                       
                        data.setYValue(markCount[i]);
                        i++;                            
                        };
                    }
                }
            
        }));
        tl.setCycleCount(Animation.INDEFINITE);
        tl.play();
        
        barChart.getData().add(series);
        return barChart;
    }
    
    public static int[] markCounter(){
        int[] temp = new int[MARK_AMOUNT];
        
        File f = new File(adress);
        try {
            tempStudents = StudentsParser.parse(f);
        } catch (IOException ex) {}
        
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
    
    public static BarChart getBarChart(){
        return barChart;
    }
    public static void setBarChart(BarChart bar){
        barChart = bar;
    }
}
