package fxml;

import model.Student;
import controller.MyController;
import logger.Logger;
import static fxml.Crawler.STATUS.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Crawler {

    private List<Student> previousStudents;
    private List<Student> resultStudents;
    private final String adress = "D:\\students.txt";
    private MyController control;

    public enum STATUS {
        STARTING,
        I_STARTED,
        I_COMPLETED,
        ADDED,
        REMOVED,
        UNCHANGED
    }

    public Crawler(MyController controller) {
        this.control = controller;
        //previousStudents = null;
        resultStudents = new ArrayList<>();
    }

    private List<Logger> iterationStartedListeners = new ArrayList<>();

    public void addIterationStartedListener(Logger listener) {
        iterationStartedListeners.add(listener);
    }

    public void removeIterationStartedListener(Logger listener) {
        iterationStartedListeners.remove(listener);
    }
    private List<Logger> iterationComplitedListeners = new ArrayList<>();

    public void addIterationComplitedListener(Logger listener) {
        iterationComplitedListeners.add(listener);
    }

    public void removeIterationComplitedListener(Logger listener) {
        iterationComplitedListeners.remove(listener);
    }

    private List<Logger> addNewStudentListeners = new ArrayList<>();

    public void addNewStudentListener(Logger listener) {
        addNewStudentListeners.add(listener);
    }

    public void removeNewStudentListener(Logger listener) {
        addNewStudentListeners.remove(listener);
    }

    private List<Logger> addRemoveStudentListeners = new ArrayList<>();

    public void addRemoveStudentListener(Logger listener) {
        addRemoveStudentListeners.add(listener);
    }

    public void removeRemoveStudentListener(Logger listener) {
        addRemoveStudentListeners.remove(listener);
    }

    private List<Logger> addUnchangedListeners = new ArrayList<>();

    public void addUnchangedListener(Logger listener) {
        addUnchangedListeners.add(listener);
    }

    public void removeUnchangedListener(Logger listener) {
        addUnchangedListeners.remove(listener);
    }

    public void run() {
        StudentsListener handler;
        File f = new File(adress);

        for (int i = 1;; ++i) {
            for (Logger l : iterationStartedListeners) {
                l.log(I_STARTED, i);
            }

            try {
                resultStudents = StudentsParser.parse(f);
            } catch (IOException ex) {
                System.out.println("Studentsparser exception");
            }

            if (previousStudents == null) { //jeśli i = 1
                if (!resultStudents.isEmpty()) {
                    for (Logger l : addNewStudentListeners) {
                        for (Student s : resultStudents) {
                            l.log(ADDED, s, this.control);
                        }
                    }
                }
            } else if (resultStudents.isEmpty()) { //jeśli i = 1
                if (!previousStudents.isEmpty()) {
                    for (Logger l : addRemoveStudentListeners) {
                        for (Student s : previousStudents) {
                            l.log(REMOVED, s, this.control);
                        }
                    }
                }
            } else if (previousStudents.size() > resultStudents.size()) {
                //usunięto
                handler = new StudentsListener();
                List<Student> st = handler.removed(previousStudents, resultStudents);
                for (Student s : st) {
                    for (Logger l : addRemoveStudentListeners) {
                        l.log(REMOVED, s, this.control);
                    }
                }
            } else if (previousStudents.size() < resultStudents.size()) {
                //dodano
                handler = new StudentsListener();
                List<Student> st = handler.added(previousStudents, resultStudents);

                for (Student s : st) {
                    for (Logger l : addNewStudentListeners) {
                        l.log(ADDED, s, this.control);
                    }
                }
            } else {
                // nie zmodyfikowano  
                for (Logger l : addUnchangedListeners) {
                    l.log(UNCHANGED, this.control);
                }
            }

            previousStudents = resultStudents;

            try {
                Thread.sleep(10000);
            } catch (Exception e) {
                System.out.println(e);
            }

            for (Logger l : iterationComplitedListeners) {
                l.log(I_COMPLETED, i);
            }

        }
    }
}//class

