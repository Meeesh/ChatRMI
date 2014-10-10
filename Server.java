package thePack;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;


public class Server extends UnicastRemoteObject implements ServerIF {

	private static final long serialVersionUID = 1L;
	private String message;
	private ArrayList<String> chatUsers;
	private ArrayList<String> lastMessages;
	private List<List> rooms;
	private ArrayList<String> publicRoomsName;

	public Server() throws RemoteException {
		message="Bienvenue sur le chat de ReMI :)";
		chatUsers = new ArrayList<String>();
		lastMessages = new ArrayList<String>();
		rooms = new ArrayList<List>();
		publicRoomsName = new ArrayList<String>();
	}

	@Override
	public synchronized String getMessage() throws RemoteException {
		// TODO Auto-generated method stub
		return message;
	}

	@Override
	public synchronized boolean logon(String login) throws RemoteException {
		// TODO Auto-generated method stub
		int i=chatUsers.indexOf(login);
		if(i<0)
		{
			System.out.println(login+" vient de rejoindre le chat.");
			chatUsers.add(login);
			return true;
		}
		else
		{
			return false;
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
	
	public synchronized ArrayList<String> getChatUsers() throws RemoteException {
		return chatUsers;
	}
	
	@Override
	public int joinRoom(String roomName, short roomKind) throws RemoteException {
		// TODO Auto-generated method stub
		String check="";
		int roomID=-1;
		int i;
		for(i=0;i<rooms.size();i++)
		{
			check=(String) rooms.get(i).get(0);
			if(check.equals(roomName)==true)
			{
				roomID=i;
				i=rooms.size();
			}
		}
		if(roomID==-1)
		{
			rooms.add(new ArrayList<String>());
			rooms.get(i).add(roomName);
			roomID=i;
			if(roomKind==1)
			{
				publicRoomsName.add(roomName);
			}
			System.out.println("New room created : "+roomName+" with ID : "+roomID);
		}
		return roomID;
	}
	
	@Override
	public synchronized ArrayList<String> publicRoomsName() throws RemoteException {
		// TODO Auto-generated method stub
		return publicRoomsName;
	}


	@Override
	public synchronized void sendMessage(String message, int id) throws RemoteException {
		// TODO Auto-generated method stub
		lastMessages.add(message);
		rooms.get(id).add(message);
		System.out.println(message);
	}

	@Override
	public synchronized ArrayList<String> getLastMessageUpdate(ArrayList<Integer> ids) throws RemoteException {
		// TODO Auto-generated method stub
		boolean isRoomID=true;
		int roomID = 0;
		ArrayList<String> messages= new ArrayList<String>();
		for(int cpt=0;cpt<ids.size();cpt++)
		{
			if(isRoomID==true)
			{
				roomID=ids.get(cpt);
				isRoomID=false;
			}
			else
			{
				String messageBlock="";
				for(int i=ids.get(cpt);i<rooms.get(roomID).size();i++)
				{
					messageBlock=messageBlock+rooms.get(roomID).get(i)+"\n";
				}
				messages.add(messageBlock);
				isRoomID=true;
			}
		}	
		return messages;
	}
	
	@Override
	public synchronized ArrayList<Integer> getCurrentMessageSize(ArrayList<Integer> ids) throws RemoteException {
		// TODO Auto-generated method stub
		boolean isRoomID=true;
		int roomID = 0;
		for(int cpt=0;cpt<ids.size();cpt++)
		{
			if(isRoomID==true)
			{
				roomID=ids.get(cpt);
				isRoomID=false;
			}
			else
			{
				ids.remove(cpt);
				ids.add(cpt, rooms.get(roomID).size());
				isRoomID=true;
			}
		}	
		return ids;
	}


}
