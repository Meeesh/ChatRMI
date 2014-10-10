package thePack;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Properties;

public class ClientEngine {
	private static String ip = "10.113.31.52";   //plus facile a trouver pour changer si on change de reseaux
	
	public static void main(String args[]){
		try{
			String url, policyfile;
			
			url = new String ("file:D:/Users/Michael/Documents/EclipseProjects/ChatGui/bin/thePack/ServerIF.class");
			policyfile = new String("server.policy");
			
			
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
