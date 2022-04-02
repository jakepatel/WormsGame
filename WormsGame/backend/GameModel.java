package backend;

import entities.Map;
import entities.Player;
import entities.Vehicle;
import entities.Weapon;

public class GameModel {
	private Weapon weapon;
	private Player player;
	private Map map;
	private Vehicle vehicle;
	
	
	public GameModel(Weapon weapon, Player player ,Map map, Vehicle vehicle){
		this.weapon = weapon;
		this.player = player;
		this.map = map;
		this.vehicle = vehicle;
	}
	
	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}
	
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public void setMap(Map map) {
		this.map = map;
	}
	
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	
	public Weapon getWeapon() {
		return weapon;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Map getMap() {
		return map;
	}
	
	public Vehicle getVehicle() {
		return vehicle;
	}
}
