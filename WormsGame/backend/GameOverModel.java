package backend;

public class GameOverModel {
	private String finalScore;
	
	
	public GameOverModel(String finalScore){
		this.finalScore = finalScore;
	}
	
	public void setFinalScore(String finalScore) {
		this.finalScore = finalScore;
	}
	

	public String getFinalScore() {
		return finalScore;
	}
	

}
