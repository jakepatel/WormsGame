package frontend;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ClientComm.GameClient;
import controller.GameOverControl;
import entities.Player;
import entities.StartGameRequest;

public class TestFrame extends JFrame
{
	private JPanel container;
	private JPanel waitingView;
	private JPanel gameOverView;
	private GameClient client;
	private Player player1;
	private ArrayList<Player> names;
	
	
	public TestFrame()
	{
		CardLayout layout = new CardLayout();
		
		
		container = new JPanel(layout);
		client = new GameClient();
		client.setTestFrame(this);
		
		
		this.setSize(1000, 650);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    GameOverControl gameoverController = new GameOverControl(container, client);
	    
	    JPanel waitingView = new GameWaitingView();
	    gameOverView = new GameOverView(gameoverController);
	    
	    container.add(gameOverView, "GameOverView");
	    container.add(waitingView, "WaitingView");
	    
	    layout.show(container, "WaitingView");
	    
		client.setContainer(container);
	    
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
