package entities;

import java.awt.Rectangle;
import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable
{
	private static final long serialVersionUID = -732641109462475955L;	//auto-generated
	
	public static final Rectangle CollisionRectangle = null;
	//private String id, 
	private String username;
	ArrayList<String> stats;
	
	public Player(/*String id,*/ String username, ArrayList<String> stats) {
		//this.id = id;
		this.username = username;
		this.stats = stats;
	}
	
	public Player(/*String id,*/ String username)
	{
		//this.id = id;
		this.username = username;
	}
	/*
	public void setId(String id) {
		this.id = id;
	}
	*/
	public void setUsername(String username) {
		this.username = username;
	}
	public void setStats(ArrayList<String> stats) {
		this.stats = stats;
	}
	/*
	public String getId() {
		return id;
	}
	*/
	public String getUsername() {
		return username;
	}
	public ArrayList<String> getStats() {
		return stats;
	}

	public void getsHit(int i) {
		// TODO Auto-generated method stub
		
	}
}
