package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import ClientComm.GameClient;

//Name: Collins or Barton


public class LeaderboardControl implements ActionListener
{
	private JPanel container;
	private GameClient client;
	
	public LeaderboardControl(JPanel container, GameClient client)
	{
		this.container = container;
		this.client = client;
	}
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
