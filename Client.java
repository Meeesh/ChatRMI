package thePack;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class Client {
    //composants fonctionnel
	
    
    public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
    	
    	String chatServerURL="rmi://192.168.1.5/RMIChatServer";
    	
    	System.setSecurityManager(new SecurityManager());
    	
    	ServerIF server= (ServerIF) Naming.lookup(chatServerURL) ;
    	
    	System.out.println(server.getMessage());
    	System.out.println("Entrez votre login :");
    	Scanner input = new Scanner(System.in);
    	String login;
    	login=input.nextLine(); 	
    	server.logon(login);
    	
    	String message;
    	int idLastMessage=0;
    	
    	while(true)
    	{
    		System.out.println("Entrez votre message :");
    		message=input.nextLine();
    		server.sendMessage(login+" dit : "+message);
    		System.out.println(server.getLastMessageUpdate(idLastMessage));
    		idLastMessage=server.getCurrentMessageSize();
    	}
    	
    }
    
}