package backend;

import java.util.ArrayList;

public class LeaderboardModel {
	private String playerHandle;
	private String playerID;
	private String playerScore;
	private String[] leaderboard;
	public LeaderboardModel(String[] leaderboard){
		this.leaderboard = leaderboard;
	}
	
	public void setLeaderboard(String[] leaderboard) {
		this.leaderboard = leaderboard;
	}

	public String[] getleaderboard() {
		return leaderboard;
	}
	

}
