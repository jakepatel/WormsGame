package frontend;
//Name: Jake

import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.*;

import controller.GameOverControl;

public class GameOverView extends JPanel 
{
	JPanel container;
	JLabel msg;
	JButton backToMainMenu;
	JButton logOutButton;
	
	
	public GameOverView(GameOverControl controller)
	{
		msg = new JLabel("");
		backToMainMenu = new JButton("Back to Main Menu");
		logOutButton = new JButton("Log Out");
		
		JPanel msgPanel = new JPanel();
		msgPanel.add(msg);
		
		JPanel backToMainMenuPanel = new JPanel();
		backToMainMenuPanel.add(backToMainMenu);
		
		JPanel logOutPanel = new JPanel();
		logOutPanel.add(logOutButton);
		
		backToMainMenu.addActionListener(controller);
		logOutButton.addActionListener(controller);
		
		JPanel grid = new JPanel(new GridLayout(3,1,5,5));

		grid.add(msgPanel);
		grid.add(backToMainMenuPanel);
		grid.add(logOutPanel);
		this.add(grid);
		
	}
	
	public void setMsg(String message)
	{
		msg.setText(message);
	}
	
	public String getMsg()
	{
		return msg.getText();
	}
}
