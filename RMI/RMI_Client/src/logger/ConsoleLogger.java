package logger;

import interfaces.Controller;
import interfaces.Logger;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import rmi_common.Status.STATUS;
import student.Student;

public class ConsoleLogger extends UnicastRemoteObject implements Logger {

    public ConsoleLogger() throws RemoteException {
        super();
    }

    @Override
    public void log(STATUS status, Object student) throws RemoteException {
        switch (status) {
            case ADDED:
                System.out.println("-----ADDED: " + (Student) student);
                break;
            case REMOVED:
                System.out.println("-----REMOVED: " + (Student) student);
                break;
        }
    }

    @Override
    public void log(STATUS status, int iteration) throws RemoteException {
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
    public void log(STATUS status) throws RemoteException {
        System.out.println("-----UNCHANGED-----");
    }

}
