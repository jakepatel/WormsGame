package controller;
//Name: Jake

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JPanel;

import ClientComm.GameClient;
import backend.AdminLoginModel;
import backend.LoginModel;
import frontend.AdminLoginView;
import frontend.LoginView;

public class AdminLoginControl implements ActionListener 
{
	
	 // Private data fields for the container and chat client.
	  private JPanel container;
	  private GameClient client;
	  
	  
	  // Constructor for the login controller.
	  public AdminLoginControl(JPanel container, GameClient client)
	  {
	    this.container = container;
	    this.client = client;
	   
	  }

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// Get the name of the button clicked.
	    String command = e.getActionCommand();

	    // The Cancel button takes the user back to the initial panel.
	    if (command == "Cancel")
	    {
	      CardLayout cardLayout = (CardLayout)container.getLayout();
	      cardLayout.show(container, "InitialView");
	    }
	    
	 // The Submit button submits the login information to the server.
	    else if (command == "Submit")
	    {
	      // Get the username and password the user entered.
	    	// Get the username and password the user entered.
		      AdminLoginView adminLoginView = (AdminLoginView)container.getComponent(3);	//fetch the getComponent number from ViewTest
		      AdminLoginModel data = new AdminLoginModel(adminLoginView.getUsername(), adminLoginView.getPassword());
		      
		      // Check the validity of the information locally first.
		      if (data.getUsername().equals("") || data.getPassword().equals(""))
		      {
		        displayError("You must enter a username and password.");
		        return;
		      }

		      // Submit the login information to the server.
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
	}

		

	// Method that displays a message in the error - could be invoked by ChatClient or by this class (see above)
	public void displayError(String error)
	{
	  AdminLoginView loginView = (AdminLoginView)container.getComponent(3);
	  loginView.setError(error);
	  CardLayout cardLayout = (CardLayout)container.getLayout();
	  cardLayout.show(container, "AdminLoginView");
	  
	}

}
