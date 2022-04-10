package backend;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import controller.GameControl;
import entities.Maps;
import entities.Player;
import entities.Vehicle;
import entities.Weapon;
import frontend.GameView;

public class GameModel {
	private Weapon weapon;
	private Player player;
	private Maps map;
	//private Vehicle vehicle;
	
	//private variables for game
		String player1, player2;
		private int gameID;
		private int sentBy;
		
		private int score;
		private GameView viewofGame;
		private GameControl controller;
		
		//for methods
		private String methodCalled;	//includes the name of the method that was called by the control, ex: mousePressed
		private MouseEvent mouseE;
		private KeyEvent keyE;
		private ActionEvent actionE;
	
	
	
	public GameModel(String player1, String player2, int gameID)
	{
		
		this.player1 = player1;
		this.player2 = player2;
		this.gameID = gameID;
		
	}
	
	public int getSentBy() 
	{
		return sentBy;
		
		//must be set to value of Player 1 name, or player 2 name, or Server
	}

	public void setSentBy(int sentBy) 
	{
		this.sentBy = sentBy;
		
		//must be set to value of Player 1 name, or player 2 name, or Server
	}

	
	
	public void setViewOfGame(GameView view)
	{
		this.viewofGame = view;
	}
	
	public void setGameController(GameControl controller)
	{
		this.controller = controller;
	}
	
	public GameView getViewOfGame()
	{
		
		return viewofGame;
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

	public KeyEvent getKeyE() {
		return keyE;
	}

	public void setKeyE(KeyEvent keyE) {
		this.keyE = keyE;
	}

	public ActionEvent getActionE() {
		return actionE;
	}

	public void setActionE(ActionEvent actionE) {
		this.actionE = actionE;
	}
	
	
	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public void setMap(Maps map) {
		this.map = map;
	}
	

	
	public Weapon getWeapon() {
		return weapon;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Maps getMap() {
		return map;
	}
	
	/*
	public Vehicle getVehicle() {
		return vehicle;
	}
	
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	*/
}
