/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thePack;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class ServerEngine {
    
	public static void main(String[] args) throws RemoteException, MalformedURLException{
		
		System.setSecurityManager(new SecurityManager());
		Naming.rebind("RMIChatServer", new Server());
	}
    
}
