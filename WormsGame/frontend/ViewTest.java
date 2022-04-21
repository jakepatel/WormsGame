package frontend;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.io.IOException;

import javax.swing.*;

import ClientComm.GameClient;
import controller.CreateAccountControl;
import controller.DeletePlayerControl;
import controller.InitialControl;
import controller.LeaderboardControl;
import controller.LoginControl;
import controller.LogoutControl;
import controller.MainMenuControl;

public class ViewTest extends JFrame 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ViewTest()
	{
		// Set the title and default close operation.
	    this.setTitle("Test");
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    CardLayout cardLayout = new CardLayout();
	    
	    
	    JPanel container = new JPanel(cardLayout);
	    
	    GameClient testClient = new GameClient();

	    //instantiate all the different controllers with container and client
	    InitialControl control_initial = new InitialControl(container);
	    LoginControl control_login = new LoginControl(container, testClient);
	    CreateAccountControl control_ca = new CreateAccountControl(container, testClient);
	    MainMenuControl control_mmc = new MainMenuControl(container, testClient);
	    LogoutControl control_logout = new LogoutControl(container, testClient, this);	//pass this to close frame 
	    LeaderboardControl control_leaderboard = new LeaderboardControl(container, testClient);
	    DeletePlayerControl delete_control = new DeletePlayerControl(container, testClient);
	    
	    
	    //instantiate all the different view with their controllers when ready
	    InitialView initial =  new InitialView(control_initial);
	    LoginView loginview = new LoginView(control_login);
	    CreateAccountView createaccview = new CreateAccountView(control_ca);
	    MainMenuView mainmenuview = new MainMenuView(control_mmc);
	    LogoutView logoutview = new LogoutView(control_logout);
	    LeaderboardView leaderboardView = new LeaderboardView(control_leaderboard);
	    GameWaitingView waitingView = new GameWaitingView();
	    DeletePlayerView deleteView = new DeletePlayerView(delete_control);
	    
	    
	    //add the view to the container and the commented numbers correspond to the cardLayout.getComponent(number);
	    container.add(initial, "InitialView");	//0
	    container.add(loginview, "LoginView");	//1
	    container.add(createaccview, "CreateAccountView");	//2
	    container.add(mainmenuview, "MainMenuView");	//4
	    container.add(logoutview, "LogoutView"); 	//5
	    container.add(leaderboardView, "LeaderboardView");	//6
	    container.add(waitingView, "GameWaitingView");	//7
	    container.add(deleteView, "DeletePlayerView");	//8


	    
	    //test the view
		this.add(container, BorderLayout.CENTER);
//		cardLayout.show(container, "GameWaitingView");
		cardLayout.show(container, "InitialView");
		
		// Show the JFrame.
	    this.setSize(550, 350);
	    this.setVisible(true);
	}
	
	public static void main(String[] args)
	{
		new ViewTest();
	}
}
