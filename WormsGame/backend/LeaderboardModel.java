package backend;

public class LeaderboardModel {
	private String playerHandle;
	private String playerID;
	private String playerScore;
	
	public LeaderboardModel(String playerHandle, String playerID, String playerScore){
		this.playerHandle = playerHandle;
		this.playerID = playerID;
		this.playerScore = playerScore;
	}
	
	public void setPlayerHandle(String playerHandle) {
		this.playerHandle = playerHandle;
	}
	
	public void setPlayerID(String playerID) {
		this.playerID = playerID;
	}
	
	public void setPlayerScore(String playerScore) {
		this.playerScore = playerScore;
	}
	
	public String gePlayerHandle() {
		return playerHandle;
	}
	
	public String getPlayerID() {
		return playerID;
	}
	
	public String getPlayerScore() {
		return playerScore;
	}
}
