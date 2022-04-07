package frontend;
//Collins

import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;

import javax.swing.*;

import controller.LeaderboardControl;

public class LeaderboardView extends JPanel
{
	//private variables here
	//TODO Create database connection here
	//private Database;

	
	public LeaderboardView(LeaderboardControl lc)
	{
		
		// TODO Load databbase connection
		
			    
		//create title label for the LeadersBoardView Panel
		JLabel titleLabel = new JLabel("Leaders Board", JLabel.CENTER);
		
		//Creating a Scroll Pane for leadersBoard 
		JPanel scrollPanel = new JPanel();
		//TODO: data = Query database for list of usernames.
		JList dataList = new JList(/*TODO data*/);
		JScrollPane scrollPane = new JScrollPane(dataList);
		scrollPanel.add(scrollPane);
		
		
	    // Create a panel for the buttons.
	    JPanel buttonPanel = new JPanel();
	    JButton menuButton = new JButton("Back to Main Menu");
	    //menuButton.addActionListener(lc);    
	    buttonPanel.add(menuButton);
	    
	    // Arrange the three panels in a grid.
	    JPanel grid = new JPanel(new GridLayout(3, 1, 0, 10));
	    grid.add(titleLabel);
	    grid.add(scrollPanel);
	    grid.add(buttonPanel);
	    this.add(grid);
	}
	
	
}
