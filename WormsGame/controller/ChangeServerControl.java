package controller;
//Barton

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ClientComm.GameClient;
import frontend.ChangeServerView;
import frontend.GameGUI;

public class ChangeServerControl implements ActionListener
{
	//Private data fields
	private JPanel container;
	private GameClient client;
	
	//Constructor
	public ChangeServerControl(JPanel container, GameClient client)
	{
		this.container = container;
		this.client = client;
	}
	
	public void actionPerformed(ActionEvent e)
	{
		//Get command name of button clicked
		String command = e.getActionCommand();
		
		ChangeServerView view = (ChangeServerView)container.getComponent(11);
		
		if(command == "Connect to Server")
		{
			//Close current connection, set IP address and Port of new server, connect to new server
			try {
				client.closeConnection();
				client.setHost(view.getServerIP());
				client.setPort(view.getServerPort());
				
				//If program runs too fast the connection won't work, Thread.sleep fixes this
				try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
				client.openConnection();
				
				//Go back a page
				gotoInitialView();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if(command == "Cancel")
		{
			//Go back a page
			gotoInitialView();
		}
	}
	
	public void gotoInitialView()
	{
		CardLayout cardLayout = (CardLayout)container.getLayout();
		cardLayout.show(container, "InitialView");
	}
}
