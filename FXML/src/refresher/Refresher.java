package refresher;

import controller.MainScreenController;
import java.util.List;
import model.StudentModel;

public interface Refresher {

    public void refresh(List<StudentModel> students, MainScreenController control);
}
