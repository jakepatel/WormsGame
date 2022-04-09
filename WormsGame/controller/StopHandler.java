//Name: Joseph Ticer
//Class: Software Engineering 
//Assignment: Lab3Out
//Instructor: Dr. Smith 
//Session: TR 2:40 - 4:15
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import ClientComm.GameClient;
import backend.GameServer;


public class StopHandler implements ActionListener{
	private JTextArea serverLog;
	private JTextField portNum, timeOutNum;	
	private GameServer server;
	private GameClient client;
	private boolean listenFlag;
	private boolean stopActionFlag;
	//Constructor used by ClientGUI
	public StopHandler(GameClient client) {
		this.client = client;
	}
	//Constructor used by ServerGUI, passing GUI components and ChatServer Object as parameters
	public StopHandler(JTextField portNum, JTextField timeOutNum, JTextArea serverLog, GameServer server) {
		this.serverLog = serverLog;
		this.portNum = portNum;
		this.timeOutNum = timeOutNum;
		this.server = server;
	}
	//Method used as stop and close button actionListeners
	public void actionPerformed(ActionEvent e) {    
		/*Condition that checks the port and timeout text fields for entry*/
		if(e.getActionCommand().equals("Listen") && (!portNum.getText().equals("") && !timeOutNum.getText().equals(""))) {
			listenFlag = true;
		}
		/*Checks to see if listenFlag has been changed to 
		 * true and which button is selected*/
		if(e.getActionCommand().equals("Stop") && listenFlag == true ){
			stopActionFlag = true;
			server.setStopFlag(stopActionFlag);
			server.stopListening();	
			
		}
		
		else if(e.getActionCommand().equals("Stop")) {
			try {
				client.sendToServer(" Disconnected From Server...");
				client.closeConnection();
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		else if(e.getActionCommand().equals("Close") && listenFlag == true ){
			try {
				stopActionFlag = false;
				server.setStopFlag(stopActionFlag);
				server.close();
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}		
		}
		//If there is no input in either text fields and Stop or Close buttons are pressed, print error message
		else if((e.getActionCommand().equals("Stop") && listenFlag == false) ||
				(e.getActionCommand().equals("Close") && listenFlag == false)) {		
			serverLog.append("Server not currently started");
			serverLog.append("\n");
		}

	}
	
}
