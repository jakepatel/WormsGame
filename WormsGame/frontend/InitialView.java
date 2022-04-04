package frontend;

import java.awt.*;
import javax.swing.*;

import controller.InitialControl;

//Jake



public class InitialView extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton playerLoginButton;
	private JButton adminLoginButton;
	private JButton createAccountButton;
	
	public InitialView(InitialControl lc)
	{
		//instantiate the buttons
		playerLoginButton = new JButton("Player Login");
		adminLoginButton = new JButton("Admin Login");
		createAccountButton = new JButton("Create New Account");
		
		//create buffers for each button
		JPanel plyLogBtnBuffer = new JPanel();
		plyLogBtnBuffer.add(playerLoginButton);
		JPanel adminLogBtnBuffer = new JPanel();
		adminLogBtnBuffer.add(adminLoginButton);
		JPanel createAccBtnBuffer = new JPanel();
		createAccBtnBuffer.add(createAccountButton);
		
		//add actionlisteners to the buttons
		playerLoginButton.addActionListener(lc);
		adminLoginButton.addActionListener(lc);
		createAccountButton.addActionListener(lc);		
		
		//set up the view for the buttons
		JPanel grid = new JPanel(new GridLayout(3,1,5,5));
		grid.add(plyLogBtnBuffer);
		grid.add(adminLogBtnBuffer);
		grid.add(createAccBtnBuffer);
		this.add(grid);
		
		
		
	}

}
