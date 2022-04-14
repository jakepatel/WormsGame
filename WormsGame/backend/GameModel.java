package backend;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.Serializable;

import controller.GameControl;
import entities.Maps;
import entities.Player;
import entities.Vehicle;
import entities.Weapon;
import frontend.GameView;

public class GameModel implements Serializable{
	private static final long serialVersionUID = -6365350008916444434L;	//auto-generated


	//private Vehicle vehicle;
	
	//private variables for game
		String player1, player2;
		private int gameID;
		private String sentBy;
		
		private int score;
		private GameView viewofGame;
		private GameControl controller;
		
		//for methods
		private String methodCalled;	//includes the name of the method that was called by the control, ex: mousePressed
		private MouseEvent mouseE;
		private int keyCode;
		private int playerTurn;

		private ActionEvent actionE;
		
		//fields for client to use
		private String serverMessage;
	
	
	
	public GameModel(String player1, String player2, int gameID)
	{
		
		this.player1 = player1;
		this.player2 = player2;
		this.gameID = gameID;
		
	}
	
	public String getSentBy() 
	{
		return sentBy;
		
		//must be set to value of Player 1 name, or player 2 name, or Server
	}
	
	public void setSentBy(String string) 
	{
		this.sentBy = string;
		
		//must be set to value of Player 1 name, or player 2 name, or Server
	}
	
	public int getKeyCode() {
		return keyCode;
	}

	public void setKeyCode(int keyCode) {
		this.keyCode = keyCode;
	}

	

	
	
	public void setViewOfGame(GameView view)
	{
		this.viewofGame = view;
	}
	public GameView getViewOfGame()
	{
		
		return viewofGame;
	}
	
	public void setGameController(GameControl controller)
	{
		this.controller = controller;
	}
		
	public GameControl getController()
	{
		return controller;
	}
	
	public int getGameID()
	{
		return gameID;
		
		//must be assigned by server
	}
	
	public void setGameID(int id)
	{
		this.gameID = id;
		
		//must be assigned by server
	}
	
	public void setGameScore(int score)
	{
		this.score = score;
		
		//must be updated by server
	}
	
	public int getGameScore() 
	{
		return this.score;
	}
	
	public String getMethodCalled()
	{
		return methodCalled;
		
		//used by server to update the game view
	}
	
	public void setMethodCalled(String method)
	{
		this.methodCalled = method;
		
		//must be set by the game controller
	}
	
	public MouseEvent getMouseE() {
		return mouseE;
	}

	public void setMouseE(MouseEvent mouseE) {
		this.mouseE = mouseE;
	}



	public ActionEvent getActionE() {
		return actionE;
	}

	public void setActionE(ActionEvent actionE) {
		this.actionE = actionE;
	}

	public void setServerMsg(String string) {
		// TODO Auto-generated method stub
		serverMessage = string;
	}
	
	public String getServerMsg()
	{
		return serverMessage;
	}
	public void setPlayerTurn(int playerTurn) {
		this.playerTurn = playerTurn;
	}
	public int getPlayerTurn() {
		return playerTurn;
	}
}
