package controller;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import ClientComm.GameClient;
import backend.LeaderboardModel;
import backend.LoginModel;
import frontend.GameGUI;
import frontend.LeaderboardView;
import frontend.LoginView;
import frontend.MainMenuView;

//Name: Collins or Barton


public class LeaderboardControl implements ActionListener
{
	private JPanel container;
	private GameClient client;
	
	public LeaderboardControl(JPanel container, GameClient client)
	{
		this.container = container;
		this.client = client;
	}
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
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
	}
	// After an account is created, set the User object and display the contacts screen.
	public void showLeaderboard(String[] token)
	{
	    LeaderboardView leaderView = (LeaderboardView)container.getComponent(6);
	    LeaderboardModel leaderModel = new LeaderboardModel(token);
	    leaderView.setDataList(leaderModel.getleaderboard());
	    CardLayout cardLayout = (CardLayout)container.getLayout();
	    cardLayout.show(container, "LeaderboardView");	

		//GameGUI.setUser(new User(createAccountPanel.getUsername(), createAccountPanel.getPassword()));
	}
}
