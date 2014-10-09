package thePack;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

public class ClientGui extends JFrame implements Runnable{
	
// Variables for GUI                     
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JList<String> listChatRooms;
    private JList<String> listUsrLog;
    private JButton logOutBut;
    private JTextField messSend;
    private JButton sendBut;
    private JButton startChat;
    private JTabbedPane tabChatRooms;
    
// Variable for funtionnning
    private String pseudo;
    
// RMI
    ServerIF server;
    
//Variables for testing
    private String listUsrs = new String();
    private JTextArea testText;    
//    private DefaultListModel usersModel = new DefaultListModel();
//    private DefaultListModel roomsModel = new DefaultListModel();
    private int nbChatRooms = 0;
    private int idLastMessage = 0;
    
    public ClientGui(ServerIF serv){
    	server = serv;
    }
	
	@Override
	public void run(){
		for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                try {
					UIManager.setLookAndFeel(info.getClassName());
	                
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
	                
	                pseudo = JOptionPane.showInputDialog("Entrez votre pseudo:");
	                server.logon(pseudo);
	                listUsrs = pseudo;
	                usersModel.addElement(listUsrs);
	                
	                
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
	        					System.out.println(ex.getMessage());
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
	                
	                setVisible(true);

	    	        Background bgTask = new Background(this);

	    	        Thread taches = new Thread(bgTask);

	    	        taches.start();
	                break;
	            }
	                catch (ClassNotFoundException | InstantiationException | IllegalAccessException	| UnsupportedLookAndFeelException | RemoteException e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
				}
	        }
		}
	}
	private void sendMessage(ActionEvent evt) {                             
//      testText.append("Server : " + server.getMessage() + "\n");
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
//          roomsModel.addElement(listUsrs.get(selec[i]) + " Room");
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

  private void logOut(ActionEvent evt) {                        
	  try{
		  server.logout(pseudo);
	  } 	  
	  catch (RemoteException e) {
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
		System.out.println(e.getMessage());
	}
  	
  	
  }

}
