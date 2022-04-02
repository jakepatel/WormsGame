package backend;

public class AdminSettingModel {
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
