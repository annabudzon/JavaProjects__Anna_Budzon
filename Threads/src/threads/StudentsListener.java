package threads;

import java.util.ArrayList;
import java.util.List;

public class StudentsListener {
    public List<Student> added(List<Student> previousStudents, List<Student> resultStudents) { //powiadomienie o dodaniu studenta
        List<Student> tmp = new ArrayList<>(resultStudents);
        List<Student> tmp2 = new ArrayList<>(previousStudents);
        tmp.removeAll(tmp2);      

       // notifyIteration();
       // System.out.println("Zakres wieku: <" + Crawler.extractAge(resultStudents, MIN) + "," + Crawler.extractAge(resultStudents, MAX) + ">\n");
        //System.out.println("Zakres ocen: <" + Crawler.extractMark(resultStudents, MIN) + "," + Crawler.extractMark(resultStudents, MAX) + ">");
        //Crawler.extractStudents(resultStudents, MARK);
        return tmp;
    }

    public List<Student> removed(List<Student> previousStudents, List<Student> resultStudents) { // powiadomienie o usunieciu studenta
        List<Student> tmp = new ArrayList<>(previousStudents);
        List<Student> tmp2 = new ArrayList<>(resultStudents);
        tmp.removeAll(tmp2);
            
       // notifyIteration();
       // System.out.println("Zakres wieku: <" + Crawler.extractAge(resultStudents, MIN) + "," + Crawler.extractAge(resultStudents, MAX) + ">\n");
       // System.out.println("Zakres ocen: <" + Crawler.extractMark(resultStudents, MIN) + "," + Crawler.extractMark(resultStudents, MAX) + ">");
       // Crawler.extractStudents(resultStudents, MARK);
       return tmp;
    }
}
