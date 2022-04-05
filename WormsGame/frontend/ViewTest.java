package frontend;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.*;

import ClientComm.GameClient;
import controller.AdminLoginControl;
import controller.CreateAccountControl;
import controller.InitialControl;
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
	    AdminLoginControl control_alc = new AdminLoginControl(container, testClient);
	    MainMenuControl control_mmc = new MainMenuControl(container, testClient);
	    LogoutControl control_logout = new LogoutControl(container, testClient);
	    
	    
	    //instantiate all the different view with their controllers when ready
	    InitialView initial =  new InitialView(control_initial);
	    LoginView loginview = new LoginView(control_login);
	    CreateAccountView createaccview = new CreateAccountView(control_ca);
	    AdminLoginView adminloginview = new AdminLoginView(control_alc);
	    MainMenuView mainmenuview = new MainMenuView(control_mmc);
	    LogoutView logoutview = new LogoutView(control_logout);
	    
	    
	    //add the view to the container and the commented numbers correspond to the cardLayout.getComponent(number);
	    container.add(initial, "InitialView");	//0
	    container.add(loginview, "LoginView");	//1
	    container.add(createaccview, "CreateAccountView");	//2
	    container.add(adminloginview, "AdminLoginView");	//3
	    container.add(mainmenuview, "MainMenuView");	//4
	    container.add(logoutview, "LogOutView"); 	//5
	    


	    
	    //test the view
		this.add(container, BorderLayout.CENTER);
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
