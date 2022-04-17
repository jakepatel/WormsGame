package backend;

public class GameOverModel {
	private int finalScore;
	
	
	public GameOverModel(int finalScore){
		this.finalScore = finalScore;
	}
	
	public void setFinalScore(int finalScore) {
		this.finalScore = finalScore;
	}
	

	public int getFinalScore() {
		return finalScore;
	}
	

}
