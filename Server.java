package thePack;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;


public class Server extends UnicastRemoteObject implements ServerIF {

	private static final long serialVersionUID = 1L;
	private String message;
	private ArrayList<String> chatUsers;
	private ArrayList<String> lastMessages;

	public Server() throws RemoteException {
		message="Bienvenue sur le chat de ReMI :)";
		chatUsers = new ArrayList<String>();
		lastMessages = new ArrayList<String>();
	}

	@Override
	public synchronized String getMessage() throws RemoteException {
		// TODO Auto-generated method stub
		return message;
	}

	@Override
	public void logon(String login) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println(login+" vient de rejoindre le chat.");
		chatUsers.add(login);
	}

	@Override
	public void sendMessage(String message) throws RemoteException {
		// TODO Auto-generated method stub
		lastMessages.add(message);
		System.out.println(message);
	}

}
