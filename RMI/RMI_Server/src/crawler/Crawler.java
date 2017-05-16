package crawler;

import interfaces.Controller;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.*;
import interfaces.Logger;
import static rmi_common.Status.STATUS.*;
import student.Student;
import student.StudentsListener;
import student.StudentsParser;
import java.io.Serializable;

public class Crawler implements Serializable {

    public final List<Logger> iterationStartedListeners = new ArrayList<>();
    public final List<Logger> iterationComplitedListeners = new ArrayList<>();
    public final List<Logger> addRemoveStudentListeners = new ArrayList<>();
    public final List<Logger> addUnchangedListeners = new ArrayList<>();
    public final List<Logger> addNewStudentListeners = new ArrayList<>();
    private final String adress = "students.txt";
    private List<Student> currentStudents;
    private List<Student> previousStudents;
    private Controller control;

    public Crawler() throws RemoteException {
        super();
        currentStudents = new ArrayList<>();
    }

    public void setController(Controller controller) {
        this.control = controller;
    }

    public void addIterationStartedListener(Logger listener) throws RemoteException {
        iterationStartedListeners.add(listener);
    }

    public void removeIterationStartedListener(Logger listener) throws RemoteException {
        iterationStartedListeners.remove(listener);
    }

    public void addIterationComplitedListener(Logger listener) throws RemoteException {
        iterationComplitedListeners.add(listener);
    }

    public void removeIterationComplitedListener(Logger listener) throws RemoteException {
        iterationComplitedListeners.remove(listener);
    }

    public void addNewStudentListener(Logger listener) throws RemoteException {
        addNewStudentListeners.add(listener);
    }

    public void removeNewStudentListener(Logger listener) throws RemoteException {
        addNewStudentListeners.remove(listener);
    }

    public void addRemoveStudentListener(Logger listener) throws RemoteException {
        addRemoveStudentListeners.add(listener);
    }

    public void removeRemoveStudentListener(Logger listener) throws RemoteException {
        addRemoveStudentListeners.remove(listener);
    }

    public void addUnchangedListener(Logger listener) throws RemoteException {
        addUnchangedListeners.add(listener);
    }

    public void removeUnchangedListener(Logger listener) throws RemoteException {
        addUnchangedListeners.remove(listener);
    }

    public void run() throws RemoteException {
        int i = 1;
        StudentsListener handler;
        File f = new File(adress);
        while (true) {
            for (Logger l : iterationStartedListeners) {
                l.log(I_STARTED, i);
            }

            currentStudents = new ArrayList<>();
            try {
                currentStudents = StudentsParser.parse(f);
            } catch (IOException ex) {
            }
            
            System.out.println("1");
            if (previousStudents == null) { //jeśli i = 1
                if (!currentStudents.isEmpty()) {
                    for (Logger l : addNewStudentListeners) {
                        for (Student s : currentStudents) {
                            System.out.println("2");
                            l.log(ADDED, s);
                        }
                    }
                }
            } else if (currentStudents.isEmpty()) { //jeśli i = 1
                if (!previousStudents.isEmpty()) {
                    for (Logger l : addRemoveStudentListeners) {
                        for (Student s : previousStudents) {
                            l.log(REMOVED, s);
                        }
                    }
                }
            } else if (previousStudents.size() > currentStudents.size()) {
                //usunięto
                handler = new StudentsListener();
                List<Student> st = handler.removed(previousStudents, currentStudents);
                for (Student s : st) {
                    for (Logger l : addRemoveStudentListeners) {
                        l.log(REMOVED, s);
                    }
                }
            } else if (previousStudents.size() < currentStudents.size()) {
                //dodano
                handler = new StudentsListener();
                List<Student> st = handler.added(previousStudents, currentStudents);
                for (Student s : st) {
                    for (Logger l : addNewStudentListeners) {
                        l.log(ADDED, s);
                    }
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

            i++; //iteracja pętli
        }
    }

}//class

