package thePack;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ServerIF extends Remote {
	
	String getMessage() throws RemoteException;
	
	void logon(String login) throws RemoteException;
	
	void logout(String login) throws RemoteException;
	
	void sendMessage(String message) throws RemoteException;
	
	String getLastMessageUpdate(int id) throws RemoteException;
	
	int getCurrentMessageSize() throws RemoteException;
	
	ArrayList<String> getChatUsers() throws RemoteException;

}
