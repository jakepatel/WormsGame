package frontend;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.*;

import ClientComm.GameClient;
import controller.CreateAccountControl;
import controller.InitialControl;
import controller.LoginControl;

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
	    InitialControl control_initial = new InitialControl(container, testClient);
	    LoginControl control_login = new LoginControl(container, testClient);
	    CreateAccountControl control_ca = new CreateAccountControl(container, testClient);
	    
	    
	    //instantiate all the different view with their controllers when ready
	    InitialView initial =  new InitialView(control_initial);
	    LoginView loginview = new LoginView(control_login);
	    CreateAccountView createaccview = new CreateAccountView(control_ca);
	    
	    
	    //add the view to the container
	    container.add(initial, "InitialView");
	    container.add(loginview, "LoginView");
	    container.add(createaccview, "CreateAccountView");


	    
	    //test the view
		this.add(container, BorderLayout.CENTER);
		cardLayout.show(container, "CreateAccountView");
		
		// Show the JFrame.
	    this.setSize(550, 350);
	    this.setVisible(true);
	}
	
	public static void main(String[] args)
	{
		new ViewTest();
	}
}
