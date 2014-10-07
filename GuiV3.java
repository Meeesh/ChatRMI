/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thePack;

import java.awt.*;
import java.awt.event.*;
import java.net.MalformedURLException;
import java.rmi.*;
import java.util.ArrayList;

import javax.swing.*;

/**
 *
 * @author Michael
 */
public class GuiV3 extends JFrame{
    // Variables for GUI                     
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JList listChatRooms;
    private JList listUsrLog;
    private JButton logOutBut;
    private JTextField messSend;
    private JButton sendBut;
    private JButton startChat;
    private JTabbedPane tabChatRooms;
    // Variable for functionning
    private String pseudo;
    private int nbChatRooms = 0;
    private int idLastMessage = 0;
    
    //RMI
    private ServerIF server;
    
    //Variables for testing
    private String listUsrs = new String();
    private JTextArea testText;
    
    private DefaultListModel usersModel = new DefaultListModel();
    private DefaultListModel roomsModel = new DefaultListModel();
    
    public static void main(String[] args){
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    GuiV3 client = new GuiV3();
                    client.setVisible(true);
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GuiV3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GuiV3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GuiV3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GuiV3.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        catch(MalformedURLException ex){
            System.out.println(ex.getMessage());
        }
        catch(RemoteException ex){
            System.out.println(ex.getMessage());
        }
        catch(NotBoundException ex){
            System.out.println(ex.getMessage());
        }
    }
    
    public GuiV3() throws MalformedURLException, RemoteException, NotBoundException {
        
        tabChatRooms = new JTabbedPane();
        sendBut = new JButton();
        logOutBut = new JButton();
        messSend = new JTextField();
        jScrollPane1 = new JScrollPane();
        listChatRooms = new JList();
        jScrollPane2 = new JScrollPane();
        listUsrLog = new JList();
        startChat = new JButton();
        testText = new JTextArea();
        
        //RMI
        String chatServerURL="rmi://192.168.1.5/RMIChatServer";    	
    	System.setSecurityManager(new SecurityManager());    	
    	server = (ServerIF) Naming.lookup(chatServerURL);
        
        pseudo = JOptionPane.showInputDialog("Entrez votre pseudo:");
        server.logon(pseudo);
        listUsrs = pseudo;
        usersModel.addElement(listUsrs);
        
        //here call method on server to try to log in via given pseudo
        
        setTitle("Le chat a ReMI");
        setBounds(new Rectangle(300, 150, 800, 500));
          
        this.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				try {
					server.logout(pseudo);
				} catch (RemoteException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
		        System.exit(0);
			}
		});
        
        sendBut.setText("SEND");
        sendBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendMessage(evt);
            }
        });
        
        logOutBut.setText("LOG OUT");
        logOutBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logOut(evt);
            }
        });

        messSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendMessage(evt);
            }
        });
        
        listUsrLog.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                clickDouble(evt);
            }
        });
        
        listChatRooms.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent evt){
                clickDooubleRoom(evt);
            }
        });
        
        listChatRooms.setModel(roomsModel);
        jScrollPane1.setViewportView(listChatRooms);

        
        listUsrLog.setModel(usersModel);
        jScrollPane2.setViewportView(listUsrLog);

        startChat.setText("CREATE NEW ROOM");
        startChat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newChatRoom(evt);
            }
        });
        
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(messSend, GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
                    .addComponent(tabChatRooms))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, GroupLayout.Alignment.TRAILING)
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 202, GroupLayout.PREFERRED_SIZE)
                            .addComponent(sendBut, GroupLayout.PREFERRED_SIZE, 202, GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(logOutBut, GroupLayout.PREFERRED_SIZE, 202, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(startChat, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(tabChatRooms, GroupLayout.PREFERRED_SIZE, 350, GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(logOutBut, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(startChat, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(messSend)
                    .addComponent(sendBut, GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE))
                .addContainerGap())
        );
        
        pack();
    }
        
        
    private void sendMessage(ActionEvent evt) {                             
//        testText.append("Server : " + server.getMessage() + "\n");
        try{
            //here send message to server and get messages from server.
        	server.sendMessage(pseudo + " : " + messSend.getText()); 
        	clientUpdate();
        }
        catch(RemoteException ex){
            System.out.println(ex.getMessage());
        }
        messSend.setText(null);
    }                            

    private void newChatRoom(ActionEvent evt) {
        
        //here inform server of new chat room
        
        int[] selec = listUsrLog.getSelectedIndices();
        for(int i = 0; i < selec.length ; i++){
//            roomsModel.addElement(listUsrs.get(selec[i]) + " Room");
            String title = new String("Room " + nbChatRooms++);
            roomsModel.addElement(title);
            tabChatRooms.add(title, testText);
        }
        
        listChatRooms.setModel(roomsModel);
        listUsrLog.clearSelection();
    }
    
    private void newChatRoomPriv(int index) { 
        //here inform server of new private chat room
        String title = new String(listUsrs + " Private");
        tabChatRooms.add(title, testText);
        listUsrLog.clearSelection();
    }

    private void logOut(java.awt.event.ActionEvent evt) {                        
        // TODO add your handling code here:
    	try {
			server.logout(pseudo);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.exit(0);
    }
    
    private void clickDouble(java.awt.event.MouseEvent evt) {                             
        JList liste = (JList)evt.getSource();
        if(evt.getClickCount() >= 2){
            newChatRoomPriv(liste.getSelectedIndex());
        }
    }
    
    private void clickDooubleRoom(MouseEvent evt){
        JList liste = (JList)evt.getSource();
        if(evt.getClickCount() >= 2){
            int selec = listChatRooms.getSelectedIndex();
            String title = new String((String)roomsModel.get(selec));
            tabChatRooms.add(title, testText);
            liste.clearSelection();
        }
    }
    
    public void clientUpdate()
    {
    	try {
			
    		testText.append(server.getLastMessageUpdate(idLastMessage));
			idLastMessage=server.getCurrentMessageSize();
			ArrayList<String> usersList=server.getChatUsers();
			usersModel.clear();
			for(int i=0;i<usersList.size();i++)
			{
				usersModel.addElement(usersList.get(i));
			}
			listUsrLog.setModel(usersModel);
		} 
    	catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }
}
