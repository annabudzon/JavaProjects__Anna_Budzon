package logger;

import controller.MainScreenController;
import interfaces.Controller;
import interfaces.Logger;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import rmi_common.Status.STATUS;
import student.Student;

public class GUILogger extends UnicastRemoteObject implements Logger {

    private Controller controller;

    public GUILogger(MainScreenController control) throws RemoteException{
        super();
        this.controller = control;
    }

    @Override
    public void log(STATUS status, Object student) throws RemoteException {
        try {
            switch (status) {
                case ADDED: {

                    controller.setTextArea("Status: -----ADDED: " + (Student)student + "\n");

                }
                controller.addRow((Student)student);

                break;
                case REMOVED:
                    controller.setTextArea("Status: -----REMOVED: " + (Student)student + "\n");
                    controller.deleteRow((Student)student);
                    break;
                case UNCHANGED:
                    controller.setTextArea("Status: -----UNCHANGED-----\n");
                    break;
            }
        } catch (RemoteException ex) {
            System.out.println("\nRemoteException was thrown.");
        }
    }

    @Override
    public void log(STATUS status, int iteracja) throws RemoteException {
    }

    @Override
    public void log(STATUS status) throws RemoteException {
        Student student = null;
        this.log(status, student);
    }
}
