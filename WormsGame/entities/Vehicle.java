package entities;

import java.util.ArrayList;

public class Vehicle {
	protected String playerId, username;
	protected ArrayList<String> stats;
	protected double[][] mapPos;

	public Vehicle(String playerId, String username,ArrayList<String> stats,double[][] mapPos) {
		this.playerId = playerId;
		this.username = username;
		this.stats = stats;
		this.mapPos = mapPos;

	}
	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setStats(ArrayList<String> stats) {
		this.stats = stats;
	}
	public void setMapPos(double[][] mapPos) {
		this.mapPos = mapPos;
	}
	public String getPlayerId() {
		return playerId;
	}
	public String getUsername() {
		return username;
	}
	public ArrayList<String> getStats() {
		return stats;
	}
	public double[][] getMapPos() {
		return mapPos;
	}


}
