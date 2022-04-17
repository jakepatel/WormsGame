
//Name: Joseph Ticer
//Class: Software Engineering 
//Assignment: Lab5Out
//Instructor: Dr. Smith 
//Session: TR 2:40 - 4:15package lab1out;
package controller;
//Class that is used as connect and listen buttons actionListeners
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import ClientComm.GameClient;
import backend.GameServer;


public class ConnectHandler implements ActionListener{
	//Data fields that declares textAreas, textFields, ChatServer and ChatClient objects
	private JTextField portNum, timeOutNum, host, clientPort;	
	private JTextArea serverLog;
	private GameServer server;
	private GameClient	client;
	//Constructor used by ClientGUi
	public ConnectHandler(GameClient client, JTextField host,JTextField clientPort) {
		this.client = client;
		this.host = host;
		this.clientPort = clientPort;
	}	
	//Constructor used by ServerGUI, passing GUI components and ChatServer Object as parameters
	public ConnectHandler(JTextField portNum, JTextField timeOutNum, JTextArea serverLog, GameServer server) {
		this.portNum = portNum;
		this.timeOutNum = timeOutNum;
		this.serverLog = serverLog;
		this.server = server;

	}
	//Method used as Listen and Connect button actionListeners
	public void actionPerformed(ActionEvent e) {    
		//If client presses Connect
		/*
		if( (e.getActionCommand()).equals( "Connect"))  {
			System.out.println("Connect Button Pressed");
			int port = Integer.parseInt(clientPort.getText());
			try {
				client.setHost(host.getText());
				client.setPort(port);
				client.openConnection();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} 
		//If server user presses Listen 
		else */
		if( (e.getActionCommand()).equals("Listen"))  {
			//If the port and timeout text fields are empty print message to server text area
			if(portNum.getText().equals("") || timeOutNum.getText().equals("")) {
				serverLog.append("Port Number/timeout not entered before pressing Listen");
				serverLog.append("\n");			
			}
			/*If they are filled, set the new port and timeout 
			* parameters to the server and start listening*/
			else {
				try {
					server.setPort(Integer.parseInt(portNum.getText()));
					server.setTimeout(Integer.parseInt(timeOutNum.getText()));
					server.listen();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		} 	
	}
	
}
