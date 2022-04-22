package frontend;
//Collins

import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import javax.swing.*;

import controller.LeaderboardControl;

public class LeaderboardView extends JPanel
{
	//private variables here
	private JList<String> dataList;

	// Getter for the text in the username field.
	public void setDataList(String[] dataList){
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		for (int i = 0; i < dataList.length; i++)
		{
			String[] entry = dataList[i].split(" ");
			String toEnter = "Name: " + entry[0] + "      Wins: " + entry[1] + "      Loss: " + entry[2]
					+ "      Win/Loss: " + entry[3];
			
			listModel.addElement(toEnter);
		}
		this.dataList.setModel(listModel);

		
		
	}
	public JList<String> getDataList(){
		return dataList;
	}
	public LeaderboardView(LeaderboardControl lc)
	{
		//create title label for the LeadersBoardView Panel
		JLabel titleLabel = new JLabel("Leaderboard", JLabel.CENTER);

		//Creating a Scroll Pane for leadersBoard 

		JPanel scrollPanel = new JPanel();
		dataList = new JList<String>();
		dataList.setPreferredSize(new Dimension(400, 200));
		JScrollPane scrollPane = new JScrollPane(dataList);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPanel.add(scrollPane);

		// Create a panel for the buttons.
		JPanel buttonPanel = new JPanel();
		JButton menuButton = new JButton("Back to Main Menu");
		menuButton.addActionListener(lc);
		buttonPanel.add(menuButton);

		// Arrange the three panels in a grid.
		JPanel grid = new JPanel(new GridLayout(3, 1, 0, 0));
		grid.add(titleLabel);
		grid.add(scrollPanel);
		grid.add(buttonPanel);
		this.add(grid);

	}


}
