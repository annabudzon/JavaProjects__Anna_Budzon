package logger;

import fxml.Crawler.STATUS;
import controller.MyController;
import model.Student;

public class ConsoleLogger implements Logger {

    @Override
    public void log(STATUS status, Student student, MyController control) {
        switch (status) {
            case ADDED:
                System.out.println("-----ADDED: " + student);
                break;
            case REMOVED:
                System.out.println("-----REMOVED: " + student);
                break;
        }

    }

    @Override
    public void log(STATUS status, int iteration) {
        switch (status) {
            case I_STARTED:
                System.out.println("---ITERATION STARTED: " + iteration);
                break;
            case I_COMPLETED:
                System.out.println("---ITERATION COMPLETED: " + iteration);
                break;
        }
    }

    @Override
    public void log(STATUS status, MyController control) {
        System.out.println("-----UNCHANGED-----");
    }

}
