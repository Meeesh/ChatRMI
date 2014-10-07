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
	public synchronized void logon(String login) throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println(login+" vient de rejoindre le chat.");
		chatUsers.add(login);
	}

	@Override
	public synchronized void sendMessage(String message) throws RemoteException {
		// TODO Auto-generated method stub
		lastMessages.add(message);
		System.out.println(message);
	}

	@Override
	public synchronized String getLastMessageUpdate(int id) throws RemoteException {
		// TODO Auto-generated method stub
		String messages="";
		for(int now=lastMessages.size();id<now;id++)
		{
			messages=(messages+lastMessages.get(id)+"\n");
		}
		return messages;
	}

	@Override
	public synchronized int getCurrentMessageSize() throws RemoteException {
		// TODO Auto-generated method stub
		return lastMessages.size();
	}

}
