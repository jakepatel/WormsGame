package frontend;

import java.util.ArrayList;


import entities.Player;

public class TestNames 
{
	
	public static ArrayList<Player> playerNames;
	public static int counter = 0;
	
	public TestNames()
	{
		playerNames = new ArrayList<Player>();
		playerNames.add(new Player("Alpha"));
		playerNames.add(new Player("Beta"));
		playerNames.add(new Player("Charlie"));
		playerNames.add(new Player("Echo"));
	}
	
	public static ArrayList<Player> getNames()
	{
		return playerNames;
	}
	
	public static int getIndex()
	{
		return counter++;
	}
	
	public static void main(String[] args)
	{
		new TestNames();
		new TestFrame();
		new TestFrame();
	}

}
