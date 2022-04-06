package controller;
//Jake

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ClientComm.GameClient;
import frontend.ViewTest;

public class LogoutControl implements ActionListener
{
	// Private data fields for the container and client.
		  private JPanel container;
		  private GameClient client;
		  private JFrame window;
		  
		  
		  // Constructor for the login controller.
		  public LogoutControl(JPanel container, GameClient client, JFrame window)
		  {
		    this.container = container;
		    this.client = client;
		    this.window = window;
		   
		  }

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		//Note: container component #5 is LogoutView
		
		// Get the name of the button clicked.
	    String command = e.getActionCommand();
	    
	 // The Back to Home button takes the user back to the initial panel.
	    if (command == "Back to Home")
	    {
	      CardLayout cardLayout = (CardLayout)container.getLayout();
	      cardLayout.show(container, "InitialView");
	    }
	    
	 // The Close Window Button closes the window
	    else if (command == "Close Window")
	    {
	    	window.dispose();
	    }
	    
	    else
	    	System.out.println("Logout Control: invalid option detected");	//print error string
		
		
		
	}

}
