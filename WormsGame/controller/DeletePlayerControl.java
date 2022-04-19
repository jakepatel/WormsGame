package controller;
//Name:Barton

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JPanel;

import ClientComm.GameClient;
import frontend.DeletePlayerView;
import frontend.LoginView;
import backend.DeletePlayerModel;
import entities.Player;

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
			LoginView loginView = (LoginView)container.getComponent(1);			
			Player p = new Player(loginView.getUsername());
			DeletePlayerModel m = new DeletePlayerModel(p.getUsername());
			try {
				client.sendToServer(m);
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
