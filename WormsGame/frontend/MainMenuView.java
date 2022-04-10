package frontend;

import java.awt.GridLayout;

//name: Jake

import javax.swing.*;

import controller.MainMenuControl;

public class MainMenuView extends JPanel
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton joinAGameButton;
	private JButton leaderboardButton;
	private JButton logOutButton;
	private JButton deleteAccountButton;
	
	public MainMenuView(MainMenuControl mmc)
	{
		//instantiate the buttons
		joinAGameButton = new JButton("Join A Game");
		leaderboardButton = new JButton("Leaderboard");
		logOutButton = new JButton("Log Out");
		deleteAccountButton = new JButton("Delete Account");
		
		//create buffers for each button
		JPanel joinAGameBtnBuffer = new JPanel();
		joinAGameBtnBuffer.add(joinAGameButton);
		JPanel leaderboardBtnBuffer = new JPanel();
		leaderboardBtnBuffer.add(leaderboardButton);
		JPanel logOutBtnBuffer = new JPanel();
		logOutBtnBuffer.add(logOutButton);
		JPanel deleteAccountBtnBuffer = new JPanel();
		deleteAccountBtnBuffer.add(deleteAccountButton);
		
		//add actionlisteners to the buttons
		joinAGameButton.addActionListener(mmc);
		leaderboardButton.addActionListener(mmc);
		logOutButton.addActionListener(mmc);
		deleteAccountButton.addActionListener(mmc);
		
		//set up the view for the buttons
		JPanel grid = new JPanel(new GridLayout(4,1,5,5));
		grid.add(joinAGameBtnBuffer);
		grid.add(leaderboardBtnBuffer);
		grid.add(logOutBtnBuffer);
		grid.add(deleteAccountBtnBuffer);
		this.add(grid);
		
		
		
	}

}
