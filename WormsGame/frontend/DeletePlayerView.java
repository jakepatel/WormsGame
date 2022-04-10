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
	
	public DeletePlayerView(DeletePlayerControl dpc)
	{
		// Panel label
		JLabel titleLabel = new JLabel(("Delete Account"), JLabel.CENTER);
		
	    // Create a panel for the buttons.
	    JPanel buttonPanel = new JPanel();
	    JButton deleteButton = new JButton("Delete Account");
	    deleteButton.addActionListener(dpc);
	    JButton menuButton = new JButton("Back to Main Menu");
	    menuButton.addActionListener(dpc);    
	    buttonPanel.add(deleteButton);
	    buttonPanel.add(menuButton);

	    // Arrange the three panels in a grid.
	    JPanel grid = new JPanel(new GridLayout(2, 1, 0, 10));
	    grid.add(titleLabel);
	    grid.add(buttonPanel);
	    this.add(grid);
	
	}
	
	
}
