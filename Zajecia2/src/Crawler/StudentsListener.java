package Crawler;

import static Crawler.Crawler.ExtremumMode.MAX;
import static Crawler.Crawler.ExtremumMode.MIN;
import static Crawler.Crawler.OrderMode.MARK;
import static Crawler.Crawler.i;
import static Crawler.Main.loggers;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class StudentsListener implements Handler {
    @Override
    public void notifyAdded(List<Student> previousStudents, List<Student> resultStudents) { //powiadomienie o dodaniu studenta
        Student student;
        if (previousStudents == null) {
            for (Logger l : loggers) {
                for (Student s : resultStudents) {
                    l.log("----- ADDED: ", s);
                }
            }
        } else {
            List<Student> tmp = new ArrayList<>(resultStudents);
            List<Student> tmp2 = new ArrayList<>(previousStudents);
            tmp.removeAll(tmp2);
            ListIterator<Student> it = tmp.listIterator();
            while (it.hasNext()) {
                student = it.next();
                for (Logger l : loggers) {
                    l.log("----- ADDED: ", student);
                }
            }
        }

        notifyIteration();
        System.out.println("Zakres wieku: <" + Crawler.extractAge(resultStudents, MIN) + "," + Crawler.extractAge(resultStudents, MAX) + ">\n");
        System.out.println("Zakres ocen: <" + Crawler.extractMark(resultStudents, MIN) + "," + Crawler.extractMark(resultStudents, MAX) + ">");
        Crawler.extractStudents(resultStudents, MARK);
    }

    @Override
    public void notifyRemoved(List<Student> previousStudents, List<Student> resultStudents) { // powiadomienie o usunieciu studenta
        Student student;
        if (resultStudents == null) {
            for (Logger l : loggers) {
                for (Student s : previousStudents) {
                    l.log("----- REMOVED: ", s);
                }
            }
        } else {
            List<Student> tmp = new ArrayList<>(previousStudents);
            List<Student> tmp2 = new ArrayList<>(resultStudents);

            tmp.removeAll(tmp2);
            ListIterator<Student> it = tmp.listIterator();

            while (it.hasNext()) {
                student = it.next();
                for (Logger l : loggers) {
                    l.log("----- REMOVED: ", student);
                }
            }
        }

        notifyIteration();
        System.out.println("Zakres wieku: <" + Crawler.extractAge(resultStudents, MIN) + "," + Crawler.extractAge(resultStudents, MAX) + ">\n");
        System.out.println("Zakres ocen: <" + Crawler.extractMark(resultStudents, MIN) + "," + Crawler.extractMark(resultStudents, MAX) + ">");
        Crawler.extractStudents(resultStudents, MARK);
    }

    @Override
    public void notifyUnchanged() {    // powiadomienie o braku zmian
        for (Logger l : loggers) {
            l.log(" ----- UNCHANGED ----- ");
        }
    }

    @Override
    public void notifyIteration() { //powiadomienie o iteracji
        for (Logger l : loggers) {
            l.log("------ ITERATION: ", i);
        }
    }
}
