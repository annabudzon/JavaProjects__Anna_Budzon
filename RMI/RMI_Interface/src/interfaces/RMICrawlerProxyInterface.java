package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMICrawlerProxyInterface extends Remote  {
    
    public void addIterationStartedListener(Logger listener) throws RemoteException;

    public void removeIterationStartedListener(Logger listener) throws RemoteException;

    public void addIterationComplitedListener(Logger listener) throws RemoteException;

    public void removeIterationComplitedListener(Logger listener) throws RemoteException;

    public void addNewStudentListener(Logger listener) throws RemoteException;

    public void removeNewStudentListener(Logger listener) throws RemoteException;

    public void addRemoveStudentListener(Logger listener) throws RemoteException;

    public void removeRemoveStudentListener(Logger listener) throws RemoteException;

    public void addUnchangedListener(Logger listener) throws RemoteException;

    public void removeUnchangedListener(Logger listener) throws RemoteException;

    public void run() throws RemoteException;
    
    public void setController(Controller controller) throws RemoteException;
	
}
