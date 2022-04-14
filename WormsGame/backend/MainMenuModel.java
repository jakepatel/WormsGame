package backend;

import java.io.Serializable;
import entities.Player;

public class MainMenuModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String gameSelection;
	private Player player;
	
	public MainMenuModel(String gameSelection){
		this.gameSelection = gameSelection;
	}
	
	public void setGameSelection(String gameSelection) {
		this.gameSelection = gameSelection;
	}
	

	public String getGameSelection() {
		return gameSelection;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	

	public Player getPlayer() {
		return player;
	}
	
}
