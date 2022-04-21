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
	private JButton createAccountButton;
	private JButton changeServerButton;
	
	public InitialView(InitialControl lc)
	{
		//instantiate the buttons
		playerLoginButton = new JButton("Player Login");
		createAccountButton = new JButton("Create New Account");
		changeServerButton = new JButton("Change Server");
		
		//create buffers for each button
		JPanel plyLogBtnBuffer = new JPanel();
		plyLogBtnBuffer.add(playerLoginButton);
		JPanel adminLogBtnBuffer = new JPanel();
		JPanel createAccBtnBuffer = new JPanel();
		createAccBtnBuffer.add(createAccountButton);
		JPanel changeServBtnBuffer = new JPanel();
		changeServBtnBuffer.add(changeServerButton);
		
		//add actionlisteners to the buttons
		playerLoginButton.addActionListener(lc);
		createAccountButton.addActionListener(lc);	
		changeServerButton.addActionListener(lc);
		
		//set up the view for the buttons
		JPanel grid = new JPanel(new GridLayout(3,1,5,5));
		grid.add(plyLogBtnBuffer);
		grid.add(createAccBtnBuffer);
		grid.add(changeServBtnBuffer);
		this.add(grid);
		
		
		
	}
	
	//getters for JButtons for JUnit testing
	public JButton getLoginButton()
	{
		return playerLoginButton;
	}
	public JButton getCreateButton()
	{
		return createAccountButton;
	}

}
