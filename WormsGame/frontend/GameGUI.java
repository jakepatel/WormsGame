package frontend;


import java.io.IOException;

import javax.swing.*;
import java.awt.*;

import ClientComm.GameClient;
import controller.AdminLoginControl;
import controller.CreateAccountControl;
import controller.InitialControl;
import controller.LeaderboardControl;
import controller.LoginControl;
import controller.LogoutControl;
import controller.MainMenuControl;

//Jake


public class GameGUI extends JFrame 
{
	// Constructor that creates the client GUI.
	public GameGUI()
	{
		// Set up the chat client.
		GameClient client = new GameClient();

		// Set the title and default close operation.
		this.setTitle("Chat Client");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create the card layout container.
		CardLayout cardLayout = new CardLayout();
		JPanel container = new JPanel(cardLayout);

		//instantiate all the different controllers with container and client
	    InitialControl control_initial = new InitialControl(container);
	    LoginControl control_login = new LoginControl(container, client);
	    CreateAccountControl control_ca = new CreateAccountControl(container, client);
	    AdminLoginControl control_alc = new AdminLoginControl(container, client);
	    MainMenuControl control_mmc = new MainMenuControl(container, client);
	    LogoutControl control_logout = new LogoutControl(container, client, this);	//pass this to close frame 
	    LeaderboardControl control_leaderboard = new LeaderboardControl(container, client);
	    
	    
	    //instantiate all the different view with their controllers when ready
	    InitialView initial =  new InitialView(control_initial);
	    LoginView loginview = new LoginView(control_login);
	    CreateAccountView createaccview = new CreateAccountView(control_ca);
	    AdminLoginView adminloginview = new AdminLoginView(control_alc);
	    MainMenuView mainmenuview = new MainMenuView(control_mmc);
	    LogoutView logoutview = new LogoutView(control_logout);
	    LeaderboardView leaderboardView = new LeaderboardView(control_leaderboard);
	    GameWaitingView waitingView = new GameWaitingView();
	    
	    
	    //add the view to the container and the commented numbers correspond to the cardLayout.getComponent(number);
	    container.add(initial, "InitialView");	//0
	    container.add(loginview, "LoginView");	//1
	    container.add(createaccview, "CreateAccountView");	//2
	    container.add(adminloginview, "AdminLoginView");	//3
	    container.add(mainmenuview, "MainMenuView");	//4
	    container.add(logoutview, "LogoutView"); 	//5
	    container.add(leaderboardView, "LeaderboardView");	//6
	    container.add(waitingView, "GameWaitingView");
	    
	    
	    //test the view
		this.add(container, BorderLayout.CENTER);
//		cardLayout.show(container, "GameWaitingView");
		cardLayout.show(container, "InitialView");
		
		// Show the JFrame.
	    this.setSize(550, 350);
	    this.setVisible(true);
	}

	// Main function that creates the client GUI when the program is started.
	public static void main(String[] args)
	{
		new GameGUI();
	}
}



