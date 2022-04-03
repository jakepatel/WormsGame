package frontend;

import java.awt.BorderLayout;


import javax.swing.*;

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
	    
	    //test the view
	    JPanel test = new LoginView(new LoginControl());
		this.add(test, BorderLayout.CENTER);
		
		// Show the JFrame.
	    this.setSize(550, 350);
	    this.setVisible(true);
	}
	
	public static void main(String[] args)
	{
		new ViewTest();
	}
}
