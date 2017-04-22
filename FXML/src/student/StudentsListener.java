package student;

import model.StudentModel;
import java.util.ArrayList;
import java.util.List;
import model.StudentModel;

public class StudentsListener {

    public List<StudentModel> added(List<StudentModel> previousStudents, List<StudentModel> resultStudents) { //powiadomienie o dodaniu studenta
        
        List<StudentModel> actualSt = new ArrayList<>(resultStudents);
        List<StudentModel> previousSt = new ArrayList<>(previousStudents);
        actualSt.removeAll(previousSt);
        return actualSt;
    }

    public List<StudentModel> removed(List<StudentModel> previousStudents, List<StudentModel> resultStudents) { // powiadomienie o usunieciu studenta
        List<StudentModel> previousSt = new ArrayList<>(previousStudents);
        List<StudentModel> actualSt = new ArrayList<>(resultStudents);
        previousSt.removeAll(actualSt);
        return previousSt;
    }
}
