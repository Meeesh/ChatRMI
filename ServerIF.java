package thePack;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerIF extends Remote {
	
	String getMessage() throws RemoteException;
	
	void logon(String login) throws RemoteException;
	
	void sendMessage(String message) throws RemoteException;

}
