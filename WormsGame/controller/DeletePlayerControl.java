package controller;
//Name:Barton

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import ClientComm.GameClient;
import frontend.DeletePlayerView;
import backend.DeletePlayerModel;

public class DeletePlayerControl implements ActionListener
{
	private JPanel container;
	private GameClient client;
	
	public DeletePlayerControl(JPanel container, GameClient client)
	{
		this.container = container;
		this.client = client;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Get the name of the button clicked.
		String command = e.getActionCommand();

		// The Main menu button takes the user back to the menu panel.
		if (command == "Back to Main Menu")
		{
			CardLayout cardLayout = (CardLayout)container.getLayout();
			cardLayout.show(container, "MainMenuView");
		}
		else if (command == "Delete Account")
		{
			
		}
	}

}
