package crawler;

import model.StudentModel;
import controller.MainScreenController;
import student.StudentsListener;
import student.StudentsParser;
import logger.Logger;
import static crawler.Crawler.STATUS.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import logger.Refresher;

public class Crawler {
    
    private final List<Logger> iterationStartedListeners = new ArrayList<>();
    private final List<Logger> iterationComplitedListeners = new ArrayList<>();
    private final List<Logger> addRemoveStudentListeners = new ArrayList<>();
    private final List<Logger> addUnchangedListeners = new ArrayList<>();
    private final List<Logger> addNewStudentListeners = new ArrayList<>();
    private final List<Refresher> barChartChangedListeners = new ArrayList<>();
    private List<StudentModel> previousStudents;
    private List<StudentModel> currentStudents;
    private final String adress = "students.txt";
    private final MainScreenController control;

    public enum STATUS {
        STARTING,
        I_STARTED,
        I_COMPLETED,
        ADDED,
        REMOVED,
        UNCHANGED
    }

    public Crawler(MainScreenController controller) {
        this.control = controller;
        //previousStudents = null;
        currentStudents = new ArrayList<>();
    }  

    public void addIterationStartedListener(Logger listener) {
        iterationStartedListeners.add(listener);
    }

    public void removeIterationStartedListener(Logger listener) {
        iterationStartedListeners.remove(listener);
    }
    
    public void addIterationComplitedListener(Logger listener) {
        iterationComplitedListeners.add(listener);
    }

    public void removeIterationComplitedListener(Logger listener) {
        iterationComplitedListeners.remove(listener);
    }

    public void addNewStudentListener(Logger listener) {
        addNewStudentListeners.add(listener);
    }

    public void removeNewStudentListener(Logger listener) {
        addNewStudentListeners.remove(listener);
    }

    public void addRemoveStudentListener(Logger listener) {
        addRemoveStudentListeners.add(listener);
    }

    public void removeRemoveStudentListener(Logger listener) {
        addRemoveStudentListeners.remove(listener);
    }

    public void addUnchangedListener(Logger listener) {
        addUnchangedListeners.add(listener);
    }

    public void removeUnchangedListener(Logger listener) {
        addUnchangedListeners.remove(listener);
    }
    
    public void addbarChartChangedListener(Refresher listener) {
        barChartChangedListeners.add(listener);
    }

    public void removebarChartChangedListener(Refresher listener) {
        barChartChangedListeners.remove(listener);
    }
    

    public void run() {
        StudentsListener handler;
        File f = new File(adress);

        for (int i = 1;; ++i) {
            for (Logger l : iterationStartedListeners) {
                l.log(I_STARTED, i);
            }

            try {
                currentStudents = StudentsParser.parse(f);
            } catch (IOException ex) {
                System.out.println("Studentsparser exception");
            }

            if (previousStudents == null) { //jeśli i = 1
                if (!currentStudents.isEmpty()) {
                    for (Logger l : addNewStudentListeners) {
                        for (StudentModel s : currentStudents) {
                            l.log(ADDED, s, this.control);
                        }
                    }
                    for(Refresher r : barChartChangedListeners){
                        r.refresh(currentStudents, this.control);
                    }
                }
            } else if (currentStudents.isEmpty()) { //jeśli i = 1
                if (!previousStudents.isEmpty()) {
                    for (Logger l : addRemoveStudentListeners) {
                        for (StudentModel s : previousStudents) {
                            l.log(REMOVED, s, this.control);
                        }
                    }
                    for(Refresher r : barChartChangedListeners){
                        r.refresh(currentStudents, this.control);
                    }
                }
            } else if (previousStudents.size() > currentStudents.size()) {
                //usunięto
                handler = new StudentsListener();
                List<StudentModel> st = handler.removed(previousStudents, currentStudents);
                for (StudentModel s : st) {
                    for (Logger l : addRemoveStudentListeners) {
                        l.log(REMOVED, s, this.control);
                    }
                }
                for(Refresher r : barChartChangedListeners){
                        r.refresh(currentStudents, this.control);
                    }
            } else if (previousStudents.size() < currentStudents.size()) {
                //dodano
                handler = new StudentsListener();
                List<StudentModel> st = handler.added(previousStudents, currentStudents);

                for (StudentModel s : st) {
                    for (Logger l : addNewStudentListeners) {
                        l.log(ADDED, s, this.control);
                    }
                }
                for(Refresher r : barChartChangedListeners){
                        r.refresh(currentStudents, this.control);
                    }
            } else {
                // nie zmodyfikowano  
                for (Logger l : addUnchangedListeners) {
                    l.log(UNCHANGED, this.control);
                }
            }

            previousStudents = currentStudents;

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

