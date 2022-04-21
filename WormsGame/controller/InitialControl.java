package controller;
//Name: Jake

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import ClientComm.GameClient;
import frontend.CreateAccountView;
import frontend.LoginView;

public class InitialControl implements ActionListener{
	
	// Private data field for storing the container.
	  private JPanel container;
	 
	  // Constructor for the initial controller.
	  public InitialControl(JPanel container)
	  {
	    this.container = container;
	   
	  }
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		
		 // Get the name of the button clicked.
	    String command = e.getActionCommand();
		
		// The Login button takes the user to the login panel.
	    if (command.equals("Player Login"))
	    {
	    	//handle page to login view 
	      LoginView loginview = (LoginView)container.getComponent(1);
	      loginview.setError("");
	      CardLayout cardLayout = (CardLayout)container.getLayout();
	      cardLayout.show(container, "LoginView");
	     
	    }
	    
	    // The Create button takes the user to the create account panel.
	    else if (command.equals("Create New Account"))
	    {
	      //Handle CreatAccount Here
	    	CreateAccountView createAccountView = (CreateAccountView)container.getComponent(2);
	    	CardLayout cardLayout = (CardLayout)container.getLayout();
	    	cardLayout.show(container, "CreateAccountView");
	    }
	    else if(command.equals("Change Server"))
	    {
	    	//handle page to Change Server
	    	CardLayout cardLayout = (CardLayout)container.getLayout();
	    	cardLayout.show(container, "ChangeServerView");
	    	
	    }
	    else
	    	System.out.println("Inital Control: Option Not Found");
		
	}

}
