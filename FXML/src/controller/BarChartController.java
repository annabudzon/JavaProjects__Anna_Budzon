package controller;

import model.StudentModel;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.util.StringConverter;

public class BarChartController implements Initializable {

    @FXML
    private BarChart barChart;
    @FXML
    CategoryAxis categoryAxis;
    @FXML
    NumberAxis numberAxis;

    private ObservableList<XYChart.Data<String, Integer>> marks = FXCollections.observableArrayList();
    private XYChart.Series<String, Integer> st = new XYChart.Series<>(marks);
    private List<StudentModel> students;
    private final int MARK_AMOUNT = 7;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        numberAxis.setTickLabelFormatter(new StringConverter<Number>() {
            @Override
            public String toString(Number object) {
                if (object.intValue() != object.doubleValue()) {
                    return "";
                }
                return "" + (object.intValue());
            }

            @Override
            public Number fromString(String string) {
                Number val = Double.parseDouble(string);
                return val.intValue();
            }
        });

        marks.addAll(
                new XYChart.Data<>(Double.toString(2.0), 0),
                new XYChart.Data<>(Double.toString(3.0), 0),
                new XYChart.Data<>(Double.toString(3.5), 0),
                new XYChart.Data<>(Double.toString(4.0), 0),
                new XYChart.Data<>(Double.toString(4.5), 0),
                new XYChart.Data<>(Double.toString(5.0), 0)
        );
        barChart.getData().addAll(st);
    }

    public synchronized void updateChartAdd(double d) {
        for (XYChart.Data<String, Integer> tmp : marks) {
            int pom = tmp.getYValue();
            {
                if (Double.toString(d).equals(tmp.getXValue())) {
                    tmp.setYValue(++pom);
                }
            }
        }
    }

    public synchronized void updateChartRemove(double d) {
        for (XYChart.Data<String, Integer> tmp : marks) {
            int pom = tmp.getYValue();
            {
                if (Double.toString(d).equals(tmp.getXValue())) {
                    tmp.setYValue(--pom);
                }
            }
        }
    }
}
