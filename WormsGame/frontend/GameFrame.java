package frontend;
//Jake

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

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
import backend.GameOverModel;
import controller.GameControl;
import entities.Maps;
import entities.SoundEffect;


public class GameFrame extends JFrame implements ActionListener, WindowListener 
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
		this.setResizable(false);

		this.setSize(1000, 650);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
		MapWinner=new String[3];
		desktop = new JDesktopPane();
		
		//instantiate the client
		this.client = client;
		
		this.addWindowListener(this);
		
		
		//CardLayout
		card = new CardLayout();
		container = new JPanel(card);
		
		//instantiate game controller
		gameController = new GameControl(container, client);

		
		
		//sets the first view for a frame to enter player name and button to start game
	    
		
		startGameFrame1=new StartGameFrame();		//keep this for now
	    //startGameFrame1.setPlayer1Name(client.getPlayer1());
	    //startGameFrame1.setPlayer2Name(client.getPlayer2());
	    
	    //startGameFrame1.btnNewButton.addActionListener(this);
	   	this.add(startGameFrame1);
	    
	    
	    
	    betweenRoundsPanel1 = new BetweenRoundsPanel();	//possibly omit later
	    
	    
	    goHomePageFrame1=new GoHomePageFrame();		//possibly omit later
	    goHomePageFrame1.btnNewButton.addActionListener(this);
	    goHomePageFrame1.btnNewButton_1.addActionListener(this);
	    goHomePageFrame1.setVisible(true);
	    
	    
	    betweenRoundsPanel1.button.addActionListener(this);
	    betweenRoundsPanel1.btnStartMap.addActionListener(this);
	    
	    
	    
	    //show the game frame
	    this.setVisible(true);
	    timer = new Timer(1000,this);
	    
	    
	    

	    

	    
	    //build view
	    
		SoundEffect.STARTROUND.play();
		
		this.player1Name= client.getPlayer1();
		this.player2Name= client.getPlayer2();
		
		gameView = new GameView(gameController, this.getPlayer1Name(), this.getPlayer2Name(), client.getNumberPlayer(), new Maps(1));	//added gameView
		//add to container
		container.add(gameView, "GameView");	//0
		gameController.setGameView(container);
		gameView.setGameID(client.getGameID());
		
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
		
		// TODO Auto-generated method stub
				if(e.getActionCommand() == timer.getActionCommand())
				{
					boolean team1HealthZero = false;
					boolean team2HealthZero = false;
					
					if(gameView.team1.get(0).playerHealth==0 && 
							gameView.team1.get(1).playerHealth==0 && 
							gameView.team1.get(2).playerHealth==0 && 
							gameView.team1.get(3).playerHealth==0)
					{
						//checking player 1 health is zero
						
						team1HealthZero = true;
						
					}
					
					if(gameView.team2.get(0).playerHealth==0 && 
							gameView.team2.get(1).playerHealth==0 && 
							gameView.team2.get(2).playerHealth==0 && 
							gameView.team2.get(3).playerHealth==0)
					{
						//checking player 2 health is zero
						
						team2HealthZero = true;
						
					}
					
					//team1HealthZero && team2HealthZero
					
					if(team1HealthZero && team2HealthZero)
					{//both team health is zero, the game is a draw, end game
						
						//this.setVisible(false);
						this.remove(gameView);
						MapWinner[currentMap] ="The game was a draw";
						timer.stop();	//stop timer
						/*
						if(currentMap==0)
						{
							betweenRoundsPanel1.btnStartMap.setEnabled(true);
							betweenRoundsPanel1.winner1.setText(MapWinner[currentMap]);
							betweenRoundsPanel1.status1.setText("Finished");
						}

						desktop.remove(goHomePageFrame1);
						desktop.remove(startGameFrame1);
						desktop.add(betweenRoundsPanel1);
						this.setVisible(true);
						this.currentMap++;
						*/
						
						//report to the server about the game results
						GameOverModel gameOver = new GameOverModel(true, gameView.gameID);
						gameOver.setPlayer1(player1Name);
						gameOver.setPlayer2(player2Name);
						gameOver.setSentBy(client.getClientPlayer());
						
						try {
							client.sendToServer(gameOver);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}


						
					}
					else if(team2HealthZero)
					{//player 2 loses all health, player 1 wins, end game
					
						this.setVisible(false);
						this.remove(gameView);
						MapWinner[currentMap]= this.getPlayer1Name();
						timer.stop();
						
						/*
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
						*/
						
						//report to the server about the game results
						GameOverModel gameOver = new GameOverModel(MapWinner[0], "P1", gameView.gameID);
						gameOver.setPlayer1(player1Name);
						gameOver.setPlayer2(player2Name);
						gameOver.setSentBy(client.getClientPlayer());
						gameOver.setDraw(false);
						
						try {
							client.sendToServer(gameOver);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					}
					
					if(team1HealthZero)
					{//player 1 loses all health, player 2 wins, end game
					
						this.setVisible(false);
						this.remove(gameView);
						MapWinner[currentMap]=this.getPlayer2Name();
						timer.stop();
						
						/*
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
						*/
						
						//report to the server about the game results
						GameOverModel gameOver = new GameOverModel(MapWinner[0], "P2", gameView.gameID);
						gameOver.setPlayer1(player1Name);
						gameOver.setPlayer2(player2Name);
						gameOver.setSentBy(client.getClientPlayer());
						gameOver.setDraw(false);
						
						try {
							client.sendToServer(gameOver);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
		
		
		/*
		if(e.getActionCommand() == startGameFrame1.btnNewButton.getActionCommand())
		{
			SoundEffect.STARTROUND.play();
			
			this.player1Name= client.getPlayer1();
			this.player2Name= client.getPlayer2();
			
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
			
		}*/

		
		if(e.getActionCommand()==betweenRoundsPanel1.btnStartMap.getActionCommand())
		{   
			gameView.setEnabled(false);
			gameView.stopTimers();
			this.remove(gameView);
			
			gameView = new GameView(gameController, this.getPlayer1Name(), this.getPlayer2Name(), client.getNumberPlayer(), new Maps(1));	//added gameView
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
    		
    		
    		gameView = new GameView(gameController, this.getPlayer1Name(), this.getPlayer2Name(), client.getNumberPlayer(),  new Maps(1));	//added gameView
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




	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		//send a GameOverModel to server
				//report to the server about the game results
				GameOverModel gameOver;
				
				if(client.getNumberPlayer().equals("P1"))
					gameOver = new GameOverModel(client.getPlayer2(), "P2", client.getGameID());
				else
					gameOver = new GameOverModel(client.getPlayer1(), "P1", client.getGameID());
				
				gameOver.setPlayer1(player1Name);
				gameOver.setPlayer2(player2Name);
				gameOver.setSentBy(client.getClientPlayer());
				gameOver.setDraw(false);
				gameOver.setEarlyGameOver(true);
				
				try {
					client.sendToServer(gameOver);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		
	}




	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		

		
		
	}




	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
