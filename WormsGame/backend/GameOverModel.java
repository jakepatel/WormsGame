package backend;

import java.io.Serializable;

public class GameOverModel implements Serializable
{
	private static final long serialVersionUID = 5082809569687640101L;	//auto-generated
	
	private String player1, player2;
	private String sentBy; 
	
	private String gameWinner;	//either player1 or player2
	private String numberWinner;	//either P1 or P2
	private int gameID;		//the id of the game
	
	private boolean draw = false;	//incase game is a draw
	
	private String serverMsg;
	
	


	public GameOverModel(boolean drawGame, int gameID)
	{
		this.draw = drawGame;
		this.gameID = gameID;
		this.player1 = "";
		this.player2 = "";
		this.gameWinner = "draw";
		this.numberWinner = "draw";
		this.sentBy = "";
	}
	
	public GameOverModel(String winner, String numOfWinner, int id)
	{
		this.gameWinner = winner;
		this.numberWinner = numOfWinner;
		this.gameID = id;
		
	}
	
	public boolean isDraw() {
		return draw;
	}

	public void setDraw(boolean draw) {
		this.draw = draw;
	}

	public String getPlayer1() {
		return player1;
	}
	public void setPlayer1(String player1) {
		this.player1 = player1;
	}
	
	public String getSentBy() {
		return sentBy;
	}
	public void setSentBy(String sentBy) {
		this.sentBy = sentBy;
	}
	
	public String getPlayer2() {
		return player2;
	}
	public void setPlayer2(String player2) {
		this.player2 = player2;
	}
	public String getGameWinner() {
		return gameWinner;
	}
	public void setGameWinner(String gameWinner) {
		this.gameWinner = gameWinner;
	}
	public String getNumberWinner() {
		return numberWinner;
	}
	public void setNumberWinner(String numberWinner) {
		this.numberWinner = numberWinner;
	}
	public int getGameID() {
		return gameID;
	}
	public void setGameID(int gameID) {
		this.gameID = gameID;
	}
	
	public String getServerMsg() {
		return serverMsg;
	}

	public void setServerMsg(String serverMsg) {
		this.serverMsg = serverMsg;
	}

}
