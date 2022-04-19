package controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import ClientComm.GameClient;

public class GameOverControl implements ActionListener
{	
	private JPanel container;
	private GameClient client;

	public GameOverControl(JPanel container, GameClient client) 
	{
		// TODO Auto-generated constructor stub
		this.container = container;
		this.client = client;

	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		// Get the name of the button clicked.
		String command = e.getActionCommand();
		System.out.println(command);
		// The Login button takes the user to the login panel.
		if (command.equals("Back to Main Menu"))
		{
			//handle page to main menu 
			CardLayout cardLayout = (CardLayout)container.getLayout();
			cardLayout.show(container, "MainMenuView"); 
		}
		else if(command.equals("Log Out")) {
			//handle page to main menu 
			CardLayout cardLayout = (CardLayout)container.getLayout();
			cardLayout.show(container, "InitialView"); 
		}

	}

}
