package frontend;
//Barton

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.*;

import controller.ChangeServerControl;

public class ChangeServerView extends JPanel
{
	private JTextField serverIP;
	private JTextField serverPort;
	
	public ChangeServerView(ChangeServerControl csc)
	{
		//Create labels
		JLabel changelbl = new JLabel("Change Server");
		JLabel portlbl = new JLabel("Port #:");
		JLabel iplbl = new JLabel("Server IP:");
		
		//Create buttons
		JButton changeServerBtn = new JButton("Connect to Server");
		JButton cancelBtn = new JButton("Cancel");
		
		//Instantiate textFields
		serverIP = new JTextField(10);
		serverPort = new JTextField(10);
		
		//Buffer panel for changelbl
		JPanel buffer = new JPanel();
		changelbl.setHorizontalAlignment(JLabel.CENTER);
		buffer.add(changelbl);
		
		//Buttons panel
		JPanel buttons = new JPanel();
		buttons.add(changeServerBtn);
		buttons.add(cancelBtn);
		
		JPanel serverInfo = new JPanel(new FlowLayout());
		serverInfo.add(iplbl);
		serverInfo.add(serverIP);
		serverInfo.add(portlbl);
		serverInfo.add(serverPort);
		
		changeServerBtn.addActionListener(csc);
		cancelBtn.addActionListener(csc);
		
		JPanel grid = new JPanel(new GridLayout(3,2,5,5));
		grid.add(changelbl);
		grid.add(serverInfo);
		grid.add(buttons);
		this.add(grid);
	}
	
	public String getServerIP()
	{
		return serverIP.getText();
	}
	public int getServerPort()
	{
		return Integer.parseInt(serverPort.getText());
	}
}
