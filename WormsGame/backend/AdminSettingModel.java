package backend;

import java.io.Serializable;

public class AdminSettingModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String gameTitle;
	
	
	public AdminSettingModel(String gameTitle){
		this.gameTitle = gameTitle;
	}
	
	public void setGameTitle(String gameTitle) {
		this.gameTitle = gameTitle;
	}
	

	public String getGameTitle() {
		return gameTitle;
	}
	

}
