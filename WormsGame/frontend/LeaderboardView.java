package frontend;
//Collins

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
		    listModel.addElement(dataList[i]);
		}
		this.dataList.setModel(listModel);
		
	}
	public JList<String> getDataList(){
		return dataList;
	}
	public LeaderboardView(LeaderboardControl lc)
	{
		//create title label for the LeadersBoardView Panel
		JLabel titleLabel = new JLabel("Leaders Board", JLabel.CENTER);

		//Creating a Scroll Pane for leadersBoard 
		JPanel scrollPanel = new JPanel();
		dataList = new JList<String>();
		JScrollPane scrollPane = new JScrollPane(dataList);
		scrollPanel.add(scrollPane);


		// Create a panel for the buttons.
		JPanel buttonPanel = new JPanel();
		JButton menuButton = new JButton("Back to Main Menu");
		menuButton.addActionListener(lc);
		buttonPanel.add(menuButton);

		// Arrange the three panels in a grid.
		JPanel grid = new JPanel(new GridLayout(3, 1, 0, 10));
		grid.add(titleLabel);
		grid.add(scrollPanel);
		grid.add(buttonPanel);
		this.add(grid);
	}


}
