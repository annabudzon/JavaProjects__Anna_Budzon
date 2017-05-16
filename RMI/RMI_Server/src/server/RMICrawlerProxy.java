package server;

import crawler.Crawler;
import interfaces.Controller;
import interfaces.Logger;
import interfaces.RMICrawlerProxyInterface;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMICrawlerProxy extends UnicastRemoteObject implements RMICrawlerProxyInterface, Serializable {

    private Crawler crawler;

    public RMICrawlerProxy() throws RemoteException {
            crawler = new Crawler();
    }

    @Override
    public void setController(Controller controller) throws RemoteException {
        crawler.setController(controller);
    }

    @Override
    public void addIterationStartedListener(Logger listener) throws RemoteException {
        crawler.iterationStartedListeners.add(listener);
    }

    @Override
    public void removeIterationStartedListener(Logger listener) throws RemoteException {
        crawler.iterationStartedListeners.remove(listener);
    }

    @Override
    public void addIterationComplitedListener(Logger listener) throws RemoteException {
        crawler.iterationComplitedListeners.add(listener);
    }

    @Override
    public void removeIterationComplitedListener(Logger listener) throws RemoteException {
        crawler.iterationComplitedListeners.remove(listener);
    }

    @Override
    public void addNewStudentListener(Logger listener) throws RemoteException {
        crawler.addNewStudentListeners.add(listener);
    }

    @Override
    public void removeNewStudentListener(Logger listener) throws RemoteException {
        crawler.addNewStudentListeners.remove(listener);
    }

    @Override
    public void addRemoveStudentListener(Logger listener) throws RemoteException {
        crawler.addRemoveStudentListeners.add(listener);
    }

    @Override
    public void removeRemoveStudentListener(Logger listener) throws RemoteException {
        crawler.addRemoveStudentListeners.remove(listener);
    }

    @Override
    public void addUnchangedListener(Logger listener) throws RemoteException {
        crawler.addUnchangedListeners.add(listener);
    }

    @Override
    public void removeUnchangedListener(Logger listener) throws RemoteException {
        crawler.addUnchangedListeners.remove(listener);
    }

    @Override
    public void run() throws RemoteException {
            crawler.run();
    }

}//class

