package frontend;

import java.awt.BorderLayout;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ClientComm.GameClient;
import entities.Player;
import entities.StartGameRequest;

public class TestFrame extends JFrame
{
	
	private JPanel waitingView;
	private GameClient client;
	private Player player1;
	private ArrayList<Player> names;
	
	
	public TestFrame()
	{
		client = new GameClient();
		client.setTestFrame(this);
		
		this.setSize(1000, 650);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    JPanel waitingView = new GameWaitingView();
	    
	    this.add(waitingView, BorderLayout.CENTER);
	    
	    this.setVisible(true);
	    
	    //pass this to client
	    client.setTestFrame(this);
	    
	    //make a start game request
	    names = TestNames.getNames();
	    
	    setPlayer();
	    Player sentBy = player1;
	    StartGameRequest request = new StartGameRequest(sentBy);
	    
	    try {
			client.sendToServer(request);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    
	}
	
	public GameClient getClient()
	
	{
		return client;
	}
	
	public void setPlayer()
	{
		this.player1 = names.get(TestNames.getIndex());
	}
	
	public Player getPlayer()
	{
		return player1;
	}
	
	
	


}
