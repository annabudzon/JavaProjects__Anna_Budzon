package refresher;

import controller.MainScreenController;
import java.util.ArrayList;
import java.util.List;
import model.StudentModel;

public class BarChartRefresher implements Refresher {

    private List<StudentModel> students;
    private MainScreenController controller;

    @Override
    public void refresh(List<StudentModel> currentStudents, MainScreenController control) {
        students = new ArrayList(currentStudents);
        controller = control;
        //controller.loadBarChart(students);
    }

}
