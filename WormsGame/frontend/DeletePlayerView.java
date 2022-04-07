package frontend;
//Collins

import java.awt.Color;

import java.awt.GridLayout;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;
import javax.swing.*;

import controller.DeletePlayerControl;

public class DeletePlayerView extends JPanel
{
	//TODO Create database connection here
	//private Database;
	private Connection conn;
	  
	  private String url;
	  private String user;
	  private String pass;
	  HashMap<String, String> database;
	
	public DeletePlayerView(DeletePlayerControl dpc)
	{
		// TODO Load databbase connection
		//Read properties file
		Properties prop = new Properties();
		/*FileInputStream fis = new FileInputStream("WormsGame/db.properties");
		prop.load(fis);*/
		url = prop.getProperty("url");
		user = prop.getProperty("user");
		pass = prop.getProperty("password"); 
		
		//Perform the Connection
	    try {
			conn = DriverManager.getConnection(url,user,pass);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Panel label
		JLabel titleLabel = new JLabel("Delete Account", JLabel.CENTER);
		
		// Scroll Pane
		JPanel scrollPanel = new JPanel();
		//TODO: data = Query database for list of usernames.
		JList dataList = new JList(/*TODO data*/);
		JScrollPane scrollPane = new JScrollPane(dataList);
		scrollPanel.add(scrollPane);
		
		
	    // Create a panel for the buttons.
	    JPanel buttonPanel = new JPanel();
	    JButton deleteButton = new JButton("Delete Player");
	    //deleteButton.addActionListener(dpc);
	    JButton menuButton = new JButton("Main Menu");
	    //menuButton.addActionListener(dpc);    
	    buttonPanel.add(deleteButton);
	    buttonPanel.add(menuButton);

	    // Arrange the three panels in a grid.
	    JPanel grid = new JPanel(new GridLayout(3, 1, 0, 10));
	    grid.add(titleLabel);
	    grid.add(scrollPanel);
	    grid.add(buttonPanel);
	    this.add(grid);
	
	}
	
	
}
