package entities;
//Jake

import java.io.Serializable;

public class StartGameGranted implements Serializable
{
	private static final long serialVersionUID = 1095734165056227372L;	//auto-generated
	
	private String player1, player2;
	private int gameID;
	
	
	public StartGameGranted(int id, String p1, String p2)
	{
		gameID = id;
		player1 = p1;
		player2 = p2;
	}
	
	
	public String getPlayer1() {
		return player1;
	}

	public void setPlayer1(String player1) {
		this.player1 = player1;
	}

	public String getPlayer2() {
		return player2;
	}

	public void setPlayer2(String player2) {
		this.player2 = player2;
	}


	public int getGameID() {
		return gameID;
	}

	public void setGameID(int gameID) {
		this.gameID = gameID;
	}



}
