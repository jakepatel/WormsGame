package backend;

import java.io.Serializable;

public class MainMenuModel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String gameSelection;
	
	
	public MainMenuModel(String gameSelection){
		this.gameSelection = gameSelection;
	}
	
	public void setGameSelection(String gameSelection) {
		this.gameSelection = gameSelection;
	}
	

	public String getGameSelection() {
		return gameSelection;
	}
	
}
