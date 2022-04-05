package controller;
//Name: Collins or Barton

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import ClientComm.GameClient;

public class DeletePlayerControl implements ActionListener
{
	private JPanel container;
	private GameClient client;
	
	public DeletePlayerControl(JPanel container, GameClient client)
	{
		this.container = container;
		this.client = client;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
