package fxml;

import model.Student;
import java.util.ArrayList;
import java.util.List;

public class StudentsListener {

    public List<Student> added(List<Student> previousStudents, List<Student> resultStudents) { //powiadomienie o dodaniu studenta
        
        List<Student> actualSt = new ArrayList<>(resultStudents);
        List<Student> previousSt = new ArrayList<>(previousStudents);
        actualSt.removeAll(previousSt);
        return actualSt;
    }

    public List<Student> removed(List<Student> previousStudents, List<Student> resultStudents) { // powiadomienie o usunieciu studenta
        List<Student> previousSt = new ArrayList<>(previousStudents);
        List<Student> actualSt = new ArrayList<>(resultStudents);
        previousSt.removeAll(actualSt);
        return previousSt;
    }
}
