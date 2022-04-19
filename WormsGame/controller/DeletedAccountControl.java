package controller;
//Barton

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ClientComm.GameClient;

public class DeletedAccountControl implements ActionListener
{
		// Private data fields for the container
		private JPanel container;
		private JFrame window;
		  
		// Constructor for the login controller.
		public DeletedAccountControl(JPanel container,  JFrame window)
		{
			this.container = container;
			this.window = window;   
		}

		public void actionPerformed(ActionEvent e) {		
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
				System.out.println("DeletedAccount Control: invalid option detected");	//print error string
		
	}

}
