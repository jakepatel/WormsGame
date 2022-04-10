package frontend;
//Jake

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.Timer;

import ClientComm.GameClient;
import controller.GameControl;
import entities.Maps;
import entities.SoundEffect;


public class GameFrame extends JFrame implements ActionListener 
{ //the class that has to be run to start the game
	
	
	GameView gameView;
	GameControl gameController;
	JPanel container;
	GameClient client;
	CardLayout card;
	
	GameGUI UI;
	
	
	
	
	StartGameFrame startGameFrame1;
    BetweenRoundsPanel betweenRoundsPanel1;
    GoHomePageFrame goHomePageFrame1;
    JDesktopPane desktop;
    Timer timer;
    
	private int currentMap;
	private String MapWinner[];
	private String player1Name,player2Name;
	
	public GameFrame(GameClient client, String title)
	{
		
		this.setTitle(title);

		this.setSize(1000, 650);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
		MapWinner=new String[3];
		desktop = new JDesktopPane();
		
		//instantiate the client
		this.client = client;
		
	
		
		
		//CardLayout
		card = new CardLayout();
		container = new JPanel(card);
		
		//instantiate game controller
		gameController = new GameControl(container, client);

		
		
		//sets the first view for a frame to enter player name and button to start game
	    startGameFrame1=new StartGameFrame();		//omit later
	    startGameFrame1.setPlayer1Name(client.getPlayer1());
	    startGameFrame1.setPlayer2Name(client.getPlayer2());
	    
	    startGameFrame1.btnNewButton.addActionListener(this);
	    this.add(startGameFrame1);
	    
	    
	    betweenRoundsPanel1 = new BetweenRoundsPanel();	//possibly omit later
	    
	    
	    goHomePageFrame1=new GoHomePageFrame();		//possibly omit later
	    goHomePageFrame1.btnNewButton.addActionListener(this);
	    goHomePageFrame1.btnNewButton_1.addActionListener(this);
	    goHomePageFrame1.setVisible(true);
	    
	    
	    betweenRoundsPanel1.button.addActionListener(this);
	    betweenRoundsPanel1.btnStartMap.addActionListener(this);
	    
	    //show the game waiting view
	    
	    
	    this.setVisible(true);
	    timer = new Timer(1000,this);
	}
	
	/*
	public static void main(String[] args)
	{
		GameFrame frame = new GameFrame("Game Window");
	}
	*/
	
	
	//-----Setters/Getters-------------------------------------------------------------------
	
	public void setPlayer1Name(String name)
	{
		this.player1Name = name;
	}
	
	public void setPlayer2Name(String name)
	{
		this.player2Name = name;
	}
	
	
	public String getPlayer1Name() {
		return this.player1Name;

	}
	public String getPlayer2Name() {
		return this.player2Name;
	}
	
	public GameView getGameView() {
		return gameView;
	}


	public void setGameView(GameView gameView) {
		this.gameView = gameView;
	}


	public GameControl getGameController() {
		return gameController;
	}


	public void setGameController(GameControl gameController) {
		this.gameController = gameController;
	}

	//-------END of Getters/Setters-----------------------------------
	

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// TODO Auto-generated method stub
		//if statements start the game
		/*
		// TODO Auto-generated method stub
				if(e.getActionCommand()==timer.getActionCommand())
				{
					if(gameView.team1.get(0).playerHealth==0 && gameView.team2.get(0).playerHealth==0)
					{
						this.setVisible(false);
						this.remove(gameView);
						MapWinner[currentMap]="The game was a draw";
						timer.stop();
						if(currentMap==0)
						{
							betweenRoundsPanel1.btnStartMap.setEnabled(true);
							betweenRoundsPanel1.winner1.setText(MapWinner[currentMap]);
							betweenRoundsPanel1.status1.setText("Finished");
						}
						if(currentMap==1)
						{
							betweenRoundsPanel1.button.setEnabled(true);
							betweenRoundsPanel1.btnStartMap.setEnabled(false);
							betweenRoundsPanel1.winner2.setText(MapWinner[currentMap]);
							betweenRoundsPanel1.status2.setText("Finished");
						}
						desktop.remove(goHomePageFrame1);
						desktop.remove(startGameFrame1);
						desktop.add(betweenRoundsPanel1);
						this.add(desktop);
						this.setVisible(true);
						this.currentMap++;
						
					}
					else if(gameView.team2.get(0).playerHealth==0 && gameView.team2.get(1).playerHealth==0 && gameView.team2.get(2).playerHealth==0 && gameView.team2.get(3).playerHealth==0)
					{
					
						this.setVisible(false);
						this.remove(gameView);
						MapWinner[currentMap]=this.getPlayer1Name();
						timer.stop();
						if(currentMap==0)
						{
							betweenRoundsPanel1.btnStartMap.setEnabled(true);
							betweenRoundsPanel1.winner1.setText(MapWinner[currentMap]);
							betweenRoundsPanel1.status1.setText("Finished");
						}
						if(currentMap==1)
						{
							betweenRoundsPanel1.button.setEnabled(true);
							betweenRoundsPanel1.btnStartMap.setEnabled(false);
							betweenRoundsPanel1.winner2.setText(MapWinner[currentMap]);
							betweenRoundsPanel1.status2.setText("Finished");
						}
						desktop.remove(goHomePageFrame1);
						desktop.remove(startGameFrame1);
						desktop.add(betweenRoundsPanel1);
						this.add(betweenRoundsPanel1);
						this.setVisible(true);
						this.currentMap++;
						
					}
					
					if(gameView.team1.get(0).playerHealth==0 && gameView.team1.get(1).playerHealth==0 && gameView.team1.get(2).playerHealth==0 && gameView.team1.get(3).playerHealth==0)
					{
						this.setVisible(false);
						this.remove(gameView);
						MapWinner[currentMap]=this.getPlayer2Name();
						timer.stop();
						if(currentMap==0)
						{
							betweenRoundsPanel1.btnStartMap.setEnabled(true);
							betweenRoundsPanel1.winner1.setText(MapWinner[currentMap]);
							betweenRoundsPanel1.status1.setText("Finished");
						}
						if(currentMap==1)
						{
							betweenRoundsPanel1.button.setEnabled(true);
							betweenRoundsPanel1.btnStartMap.setEnabled(false);
							betweenRoundsPanel1.winner2.setText(MapWinner[currentMap]);
							betweenRoundsPanel1.status2.setText("Finished");
						}
						desktop.remove(goHomePageFrame1);
						desktop.remove(startGameFrame1);
						desktop.add(betweenRoundsPanel1);
						this.add(betweenRoundsPanel1);
						this.setVisible(true);
						this.currentMap++;
					}
				}
		*/
		if(e.getActionCommand()==startGameFrame1.btnNewButton.getActionCommand())
		{	
			
				SoundEffect.STARTROUND.play();
				
			this.player1Name=startGameFrame1.player1Area.getText();
			this.player2Name=startGameFrame1.player2Area.getText();
			
			gameView = new GameView(gameController, this.getPlayer1Name(), this.getPlayer2Name(),new Maps(1));	//added gameView
			//add to container
			container.add(gameView, "GameView");	//0
			gameController.setGameView(container);
			
			this.setVisible(false);
			this.remove(desktop);
			this.remove(startGameFrame1);
			desktop.remove(startGameFrame1);
			this.add(gameView);
			currentMap=0;
			MapWinner=new String[3];
			//mntmHomePage.setVisible(true);
			this.setVisible(true);	
			timer.start();
			
			card.show(container, "GameView");
			
			
		}
		
		if(e.getActionCommand()==betweenRoundsPanel1.btnStartMap.getActionCommand())
		{   
			gameView.setEnabled(false);
			gameView.stopTimers();
			this.remove(gameView);
			
			gameView = new GameView(gameController, this.getPlayer1Name(), this.getPlayer2Name(),new Maps(2));
			//add to container
			container.add(gameView, "GameView");	//0
			gameController.setGameView(container);
			
			this.setVisible(false);
			this.remove(desktop);
			this.remove(betweenRoundsPanel1);
			this.add(gameView);
			this.setVisible(true);	
			timer.start();
			
			card.show(container, "GameView");
			
		}
     
       if(e.getActionCommand()==betweenRoundsPanel1.button.getActionCommand())
		{
    	    gameView.setEnabled(false);
    	    gameView.stopTimers();
    		this.remove(gameView);
    		
    		
		 	gameView = new GameView(gameController, this.getPlayer1Name(), this.getPlayer2Name(),new Maps(3));
		 	//add to container
			container.add(gameView, "GameView");	//0
			gameController.setGameView(container);
			
			this.setVisible(false);
			this.remove(desktop);
			this.remove(betweenRoundsPanel1);
			this.add(gameView);
			this.setVisible(true);
			timer.start();
			
			card.show(container, "GameView");
		}
       
       /*
       if(e.getActionCommand()==goHomePageFrame1.btnNewButton.getActionCommand())
		{
			this.setVisible(false);
			this.remove(desktop);
			desktop.remove(startGameFrame1);
			desktop.remove(goHomePageFrame1);
			desktop.add(startGameFrame1);
			this.add(desktop);
			this.setVisible(true);	
		}
		*/
		
	}
	
	
	
}