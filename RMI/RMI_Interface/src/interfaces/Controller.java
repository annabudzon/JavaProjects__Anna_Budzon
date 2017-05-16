package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Controller extends Remote {

    public void setTextArea(String logg) throws RemoteException;

    public void addRow(Object student) throws RemoteException;

    public void deleteRow(Object student)throws RemoteException;
}
