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
import javax.swing.JViewport;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class ClientGui extends JFrame implements Runnable{
	
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
    private DefaultListModel<String> usersModel = new DefaultListModel<String>();
    private DefaultListModel<String> roomsModel = new DefaultListModel<String>();
    
    
// Variable for funtionnning
    private String pseudo;
    private int nbChatRooms = 0;
    private int idLastMessage = 0;
    private ArrayList<JTextArea> messArea;
    private ArrayList<Integer> roomsID;
    
// RMI
    ServerIF server;
    
//Variables for testing
    private JScrollPane scroll;
    
    
    
    public ClientGui(ServerIF serv){
    	server = serv;
    }
    
    class JTextFieldLimit extends PlainDocument {
		private static final long serialVersionUID = 1L;
		private int limit;
		  JTextFieldLimit(int limit) {
		    super();
		    this.limit = limit;
		  }
	
		  JTextFieldLimit(int limit, boolean upper) {
		    super();
		    this.limit = limit;
		  }
	
		  public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
		    if (str == null)
		      return;
	
		    if ((getLength() + str.length()) <= limit) {
		      super.insertString(offset, str, attr);
		    }
		  }
    }
    
    
    
	
	@Override
	public void run(){
		boolean login = false;;
		for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                try {
					UIManager.setLookAndFeel(info.getClassName());
	                
	                tabChatRooms = new JTabbedPane();
	                sendBut = new JButton();
	                logOutBut = new JButton();
	                messSend = new JTextField();
	                messSend.setDocument(new JTextFieldLimit(256));
	                jScrollPane1 = new JScrollPane();
	                listChatRooms = new JList<String>();
	                jScrollPane2 = new JScrollPane();
	                listUsrLog = new JList<String>();
	                startChat = new JButton();
	                messArea = new ArrayList<JTextArea>();
	                roomsID = new ArrayList<Integer>();
	                
	                //process de login
	                pseudo = null;
	                while(pseudo == null || login == false){
	                	pseudo = JOptionPane.showInputDialog("Entrez votre pseudo:");
	                	if(pseudo == null) System.exit(0);
	                	else
	                		if(pseudo.equals(null) || pseudo.equals("")){
	                			JOptionPane.showMessageDialog(null, "Isèrer un pseudo");
	                		}
	                		else{
	                			login = server.logon(pseudo);
	                			if(login == false)
	                				JOptionPane.showMessageDialog(null, "Pseudo déjà utiliser");
	                		}
	                	
	                }
	                usersModel.addElement(pseudo);
	                
	                
	                setTitle("Le chat a ReMI");
	                setBounds(new Rectangle(300, 150, 800, 500));
	                
	                this.addWindowListener(new WindowAdapter()
	        		{
	        			public void windowClosing(WindowEvent e)
	        			{
	        				try {
	        					server.logout(pseudo);
	        				} catch (RemoteException ex) {
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
					System.out.println(e.getMessage());
				}
	        }
		}
	}
	private void sendMessage(ActionEvent evt) {
      try{
          //here send message to server and get messages from server.
      	server.sendMessage(pseudo + " : " + messSend.getText(),roomsID.get(tabChatRooms.getSelectedIndex())/3); 
      	clientUpdate();
      }
      catch(RemoteException ex){
          System.out.println(ex.getMessage());
      }
      messSend.setText(null);
  }                            

  private void newChatRoom(ActionEvent evt) {
      String title=JOptionPane.showInputDialog(null,"Nom de la room : ");
      //here inform server of new chat room
      
	   try {
			roomsID.add(server.joinRoom(title, (short) 0));
			roomsID.add(0);
		} 
	      catch (RemoteException e) {
			// TODO Auto-generated catch block
		e.printStackTrace();
	  }
	  
      roomsModel.addElement(title);
      
      JTextArea newTextArea = new JTextArea();
      scroll = new JScrollPane(newTextArea);
      messArea.add(newTextArea);
      tabChatRooms.add(title, scroll);
  
      
      listChatRooms.setModel(roomsModel);
      listUsrLog.clearSelection();
  }
  
  private void newChatRoomPriv(int index) { 
      //here inform server of new private chat room
	try 
	{
		roomsID.add(server.joinRoom(pseudo+"privatechatwith"+usersModel.get(index), (short) 0));
		roomsID.add(0);
	} 
	catch (RemoteException e) 
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  String title = new String(usersModel.get(index) + " Private");
	  JTextArea newTextArea = new JTextArea();
      scroll = new JScrollPane(newTextArea);
      messArea.add(newTextArea);
      tabChatRooms.add(title, scroll);
  }

  private void logOut(ActionEvent evt) {                        
	  try{
		  server.logout(pseudo);
	  } 	  
	  catch (RemoteException e) {
			System.out.println(e.getMessage());
		}
    System.exit(0);
  }
  
  private void clickDouble(java.awt.event.MouseEvent evt) {                             
      JList<String> liste = (JList<String>)evt.getSource();
      if(evt.getClickCount() >= 2){
          newChatRoomPriv(liste.getSelectedIndex());
      }
  }
  
  private void clickDooubleRoom(MouseEvent evt){
	  
      JList<String> liste = (JList<String>)evt.getSource();
      if(evt.getClickCount() >= 2){
          int selec = listChatRooms.getSelectedIndex();
          String title = roomsModel.get(selec);
          
          try 
      	{
      		roomsID.add(server.joinRoom(title, (short) 0));
      		roomsID.add(0);
      	} 
      	catch (RemoteException e) 
      	{
      		// TODO Auto-generated catch block
      		e.printStackTrace();
      	}

          
          JTextArea newTextArea = new JTextArea();
          scroll = new JScrollPane(newTextArea);
          messArea.add(newTextArea);
          tabChatRooms.add(title, scroll);
          liste.clearSelection();
      }
  }
  
  public void clientUpdate()
  {
	  
  	try {
  		//getting the currently selected tab
  		if(tabChatRooms.getTabCount() > 0){
  			//TODO append the text to the arraylist of textarea
            ArrayList<String> toDisplay=new ArrayList<String>();
  			toDisplay=server.getLastMessageUpdate(roomsID);
  			for(int i=0;i<toDisplay.size();i++)
  			{
		  		JScrollPane tempSP = (JScrollPane)tabChatRooms.getComponentAt(i);
		  		JViewport tempVP = tempSP.getViewport();
		  		JTextArea tempTA = (JTextArea)tempVP.getView();
		  		tempTA.setCaretPosition(tempTA.getDocument().getLength());
		  		tempTA.append(toDisplay.get(i));
		  		roomsID=server.getCurrentMessageSize(roomsID);
  			}
  		}
			ArrayList<String> usersList=server.getChatUsers();
			usersModel.clear();
			for(int i=0;i<usersList.size();i++)
			{
				usersModel.addElement(usersList.get(i));
			}
			listUsrLog.setModel(usersModel);
			ArrayList<String> roomsList=server.publicRoomsName();
			roomsModel.clear();
			for(int i=0;i<roomsList.size();i++)
			{
				roomsModel.addElement(roomsList.get(i));
			}
			listChatRooms.setModel(roomsModel);
  		
	} 
  	catch (RemoteException e) {
		System.out.println(e.getMessage());
	}
  }
}
