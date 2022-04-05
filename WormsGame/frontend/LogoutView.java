package frontend;

import java.awt.Component;
import java.awt.GridLayout;

//Name: Jake

import javax.swing.*;

import controller.LogoutControl;

public class LogoutView extends JPanel 
{
	
	private JLabel logoutMessage;
	private JButton backToHomeBtn;
	private JButton closeWindowBtn;

	public LogoutView(LogoutControl logout) 
	{
		logoutMessage = new JLabel("You have logged out of your account");
		backToHomeBtn = new JButton("Back to Home");
		closeWindowBtn = new JButton("Close Window");
		
		//create buffers for the elements
		JPanel messageBuffer = new JPanel();
		messageBuffer.add(logoutMessage);
		JPanel backToHomeBtnBuffer = new JPanel();
		backToHomeBtnBuffer.add(backToHomeBtn);
		JPanel closeWindowBtnBuffer = new JPanel();
		closeWindowBtnBuffer.add(closeWindowBtn);
		
		//add actionlistener for the buttons
		backToHomeBtn.addActionListener(logout);
		closeWindowBtn.addActionListener(logout);
		
		//set up the view for the buttons
		JPanel grid = new JPanel(new GridLayout(3,1,5,5));
		grid.add(logoutMessage);
		grid.add(backToHomeBtn);
		grid.add(backToHomeBtn);
		this.add(grid);
				
		
	}
	

}
