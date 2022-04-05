package frontend;

import java.awt.GridLayout;

//name: Jake

import javax.swing.*;

import controller.InitialControl;
import controller.MainMenuControl;

public class MainMenuView extends JPanel
{
	
	private JButton joinAGameButton;
	private JButton leaderboardButton;
	private JButton logOutButton;
	
	public MainMenuView(MainMenuControl mmc)
	{
		//instantiate the buttons
		joinAGameButton = new JButton("Join A Game");
		leaderboardButton = new JButton("Leaderboard");
		logOutButton = new JButton("Log Out");
		
		//create buffers for each button
		JPanel joinAGameBtnBuffer = new JPanel();
		joinAGameBtnBuffer.add(joinAGameButton);
		JPanel leaderboardBtnBuffer = new JPanel();
		leaderboardBtnBuffer.add(leaderboardButton);
		JPanel logOutBtnBuffer = new JPanel();
		logOutBtnBuffer.add(logOutButton);
		
		//add actionlisteners to the buttons
		joinAGameButton.addActionListener(mmc);
		leaderboardButton.addActionListener(mmc);
		logOutButton.addActionListener(mmc);
	
		
		//set up the view for the buttons
		JPanel grid = new JPanel(new GridLayout(3,1,5,5));
		grid.add(joinAGameBtnBuffer);
		grid.add(leaderboardBtnBuffer);
		grid.add(logOutBtnBuffer);
		this.add(grid);
		
		
		
	}

}
