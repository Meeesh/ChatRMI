package thePack;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ServerIF extends Remote {
	
	String getMessage() throws RemoteException;
	
	boolean logon(String login) throws RemoteException;
	
	void logout(String login) throws RemoteException;
	
	void sendMessage(String message, int id) throws RemoteException;
	
	ArrayList<String> getLastMessageUpdate(ArrayList<Integer> ids) throws RemoteException;
	
	ArrayList<Integer> getCurrentMessageSize(ArrayList<Integer> ids) throws RemoteException;
	
	ArrayList<String> getChatUsers() throws RemoteException;
	
	int joinRoom(String roomName, short roomKind) throws RemoteException;
	
	ArrayList<String> publicRoomsName() throws RemoteException;

	
}
