package controller;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import ClientComm.GameClient;
import backend.MainMenuModel;
import frontend.CreateAccountView;
import frontend.DeletePlayerView;
import frontend.GameGUI;
import frontend.LeaderboardView;

import frontend.MainMenuView;

//Name: Jake

public class MainMenuControl implements ActionListener
{

	// Private data field for storing the container.
	private JPanel container;
	private GameClient client;

	// Constructor for the initial controller.
	public MainMenuControl(JPanel container, GameClient client)
	{
		this.container = container;
		this.client = client;

	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// Get the name of the button clicked.
		String command = e.getActionCommand();

		// The Login button takes the user to the login panel.
		if (command.equals("Join A Game"))
		{
			//handle page to Game View
			MainMenuView mmv = (MainMenuView)container.getComponent(4);	//component number of mainmenuview
			MainMenuModel data = new MainMenuModel(command.toString());
			//send data to server
			try 
			{
				client.sendToServer(data);
			} 
			catch (IOException e1) 
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			//handle control to Game waiting view

		}

		// The Create button takes the user to the create account panel.
		else if (command.equals("Leaderboard"))
		{
			//handle page to Leaderboard View
			MainMenuModel data = new MainMenuModel(command.toString());
			//handle page to login view 

			//send data to server
			try 
			{
				client.sendToServer(data);
			} 
			catch (IOException e1) 
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
		else if(command.equals("Log Out"))
		{
			CardLayout cardLayout = (CardLayout)container.getLayout();
			cardLayout.show(container, "LogoutView");	
		}
		else if(command.equals("Delete Account"))
		{
			//handle page to DeletePlayerView	    	
	    	DeletePlayerView deletePlayerView = (DeletePlayerView)container.getComponent(8);
	    	CardLayout cardLayout = (CardLayout)container.getLayout();
	    	cardLayout.show(container, "DeletePlayerView");
		}
		else
			//print an error on console
			System.out.println("Main Menu Control: Option Not Found");


	}


}
