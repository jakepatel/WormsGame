package backend;

public class MainMenuModel {
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
