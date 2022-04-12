package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JPanel;

import ClientComm.GameClient;
import backend.GameModel;
import frontend.GameView;

public class ChangeTurnsControl implements ActionListener
{
	private GameClient client;
	private JPanel gameContainer;
	private GameView view;



	private GameControl controller;
	
	private String p1;
	private String p2;
	private int gameID;
	
	public ChangeTurnsControl(GameClient client, JPanel gameContainer)
	{
		this.client = client;
		this.gameContainer = gameContainer;
	}
	
	public ChangeTurnsControl(GameClient client, JPanel gameContainer, GameView gameview, GameControl controller)
	{
		this.client = client;
		this.gameContainer = gameContainer;
		this.controller = controller;
		this.view = gameview;
		
		
	}
	
	public GameView getView() {
		return view;
	}

	public void setView(GameView view) {
		this.view = view;
	}


	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// TODO Auto-generated method stub
		//make the GameModel
		
		
		
		GameModel data = new GameModel(p1, p2, gameID);
		data.setMethodCalled("ChangeTurnsTimer");
		data.setActionE(e);
		
		//send to server the GameModel
		try {
			client.sendToServer(data);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
	}

	public void setPlayer1Name(String player1Name) 
	{
		// TODO Auto-generated method stub
		p1 = player1Name;
	}

	public void setPlayer2Name(String player2Name) 
	{
		// TODO Auto-generated method stub
		p2 = player2Name;
	}

	public void setGameID(int id) {
		// TODO Auto-generated method stub
		gameID = id;
		
	}

}
