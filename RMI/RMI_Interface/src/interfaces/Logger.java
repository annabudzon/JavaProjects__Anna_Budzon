package interfaces;

import interfaces.Controller;
import java.rmi.Remote;
import java.rmi.RemoteException;
import rmi_common.Status.STATUS;

public interface Logger extends Remote {
   public void log(STATUS status, Object student) throws RemoteException;
   public void log(STATUS status, int iteracja)throws RemoteException;
   public void log(STATUS status) throws RemoteException;
}
