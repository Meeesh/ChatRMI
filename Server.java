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
		int i=chatUsers.indexOf(login);
		if(i<0)
		{
			System.out.println(login+" vient de rejoindre le chat.");
			chatUsers.add(login);
		}
	}
	
	@Override
	public void logout(String login) throws RemoteException {
		// TODO Auto-generated method stub
		int i=chatUsers.indexOf(login);
		if(i>=0)
		{
			System.out.println(login+" vient de quitter le chat.");
			chatUsers.remove(i);
		}
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

	public synchronized ArrayList<String> getChatUsers() throws RemoteException {
		return chatUsers;
	}

	@Override
	public synchronized int getCurrentMessageSize() throws RemoteException {
		// TODO Auto-generated method stub
		return lastMessages.size();
	}

	

}
