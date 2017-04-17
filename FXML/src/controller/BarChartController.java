package controller;

import model.Student;
import fxml.StudentsParser;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class BarChartController implements Initializable {

    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    private List<Student> tempStudents;
    private final int MARK_AMOUNT = 7;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    public void loadBarChart(String adress) {
//        barChart.getData().isEmpty() //        if(barChart )
//        barChart.getData().clear();
//        barChart.layout();
//        XYChart.Series<String, Number> series = new XYChart.Series<>();
//        series.setName("Mark");
//        int[] markCount;
//        markCount = markCounter(adress);
//        double j = 2.0;
//
//        for (int i = 0; i < MARK_AMOUNT; i++) {
//            series.getData().add(new XYChart.Data<>(Double.toString(j), markCount[i]));
//            j += 0.5;
//        }
//        barChart.getData().add(series);
    }

    private int[] markCounter(String adress) {
        int[] temp = new int[MARK_AMOUNT];

        File f = new File(adress);
        try {
            tempStudents = StudentsParser.parse(f);
        } catch (IOException ex) {
        }

        for (int i = 0; i < 7; i++) {
            temp[i] = 0;
        }
        for (Student s : tempStudents) {
            if (s.getMark() == 2.0) {
                temp[0]++;
            } else if (s.getMark() == 2.5) {
                temp[1]++;
            } else if (s.getMark() == 3.0) {
                temp[2]++;
            } else if (s.getMark() == 3.5) {
                temp[3]++;
            } else if (s.getMark() == 4.0) {
                temp[4]++;
            } else if (s.getMark() == 4.5) {
                temp[5]++;
            } else if (s.getMark() == 5.0) {
                temp[6]++;
            }
        }

        return temp;
    }

}
