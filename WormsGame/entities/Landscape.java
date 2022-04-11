package entities;

public class Landscape {
	private Player player;
	private Maps map;
	private Vehicle vehicle;
	

	
	
	private StaticObjects statObj;
	private ReactiveObjects reactObj;
	
	
	
	public Landscape(Player player, Maps map, Vehicle vehicle, StaticObjects statObj, ReactiveObjects reactObj){

		this.player = player;
		this.map = map;
		this.vehicle = vehicle;
		//this.statObj = statObj;
	}
		
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	public void setMap(Maps map) {
		this.map = map;
	}
	
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	
	public void setStatObject(StaticObjects statObj) {
		this.statObj = statObj;
	}
	
	public void setReactiveObject(ReactiveObjects reactObj) {
		this.reactObj = reactObj;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Maps getMap() {
		return map;
	}
	
	public Vehicle getVehicle() {
		return vehicle;
	}
	
	public StaticObjects getStaticObjects() {
		return statObj;
	}
	
	public ReactiveObjects getReactiveObjects() {
		return reactObj;
	}
	
	public Vehicle updateVehiclePos(Vehicle vehicle) {
		
		return vehicle;
	}
	
	public void updateTerrain() {
		
	}
}
