package logger;

import crawler.Crawler.STATUS;
import controller.MainScreenController;
import model.StudentModel;

public class GUILogger implements Logger {

    private MainScreenController controller;

    public GUILogger() {
    }

    @Override
    public void log(STATUS status, StudentModel student, MainScreenController control) {
        controller = control;

        switch (status) {
            case ADDED:
                controller.setTextArea("Status: -----ADDED: " + student + "\n");
                controller.addRow(student);

                break;
            case REMOVED:
                controller.setTextArea("Status: -----REMOVED: " + student + "\n");
                controller.deleteRow(student);
                break;
            case UNCHANGED:
                controller.setTextArea("Status: -----UNCHANGED-----\n");
                break;
        }
    }

    @Override
    public void log(STATUS status, int iteracja) {
    }

    @Override
    public void log(STATUS status, MainScreenController control) {
        StudentModel student = null;
        this.log(status, student, control);
    }
}
