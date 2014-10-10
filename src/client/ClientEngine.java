package thePack;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Properties;

import javax.swing.JOptionPane;

public class ClientEngine {
	
	public static void main(String args[]){
		try{
			String ip=JOptionPane.showInputDialog(null,"Entrez l'adresse IP du serveur : ","10.113.31.");
			if(ip==null)
			{
				System.exit(0);
			}
			
			String url, policyfile;
			
			url = new String ("file:D:/Users/Michael/Documents/EclipseProjects/ChatGui/bin/thePack/ServerIF.class");
			policyfile = new String("chat.policy");
			
			
			Properties props = System.getProperties(); 
			props.setProperty("java.rmi.server.codebase", url);
			props.setProperty("java.security.policy", policyfile);
	
	        String chatServerURL="rmi://"+ip+"/RMIChatServer";    	
	        System.setSecurityManager(new SecurityManager());    	
	        ServerIF server = (ServerIF) Naming.lookup(chatServerURL);
	        
	        ClientGui client = new ClientGui(server);
	        
	        Thread gui = new Thread(client);
	        
	        gui.start();
	        
	        
		}
		catch(RemoteException | MalformedURLException | NotBoundException e){
			System.out.println(e.getMessage());
		}
		
	}

}
