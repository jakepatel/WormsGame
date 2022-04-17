package ClientComm;
//Jake

import java.awt.CardLayout;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JPanel;

import backend.GameModel;
import controller.CreateAccountControl;
import controller.GameControl;
import controller.LeaderboardControl;
import controller.LoginControl;
import controller.MainMenuControl;
import entities.StartGameGranted;
import frontend.GameFrame;
import frontend.GameGUI;
import frontend.GameView;
import frontend.TestFrame;
import ocsf.client.*;

public class GameClient extends AbstractClient implements Serializable{
	// Private data fields for storing the GUI controllers.
	private LoginControl loginControl;
	private CreateAccountControl createAccountControl;
	private LeaderboardControl leaderboardControl;
	
	
	
	//Game related
	private JPanel container;
	private GameControl controller;
	private GameView game;
	private GameFrame gameFrame;
	private GameGUI guiFrame;
	private String player1, player2;
	private String clientPlayer;	//the player that belongs to this client (either player 1 or player 2)
	private String numberPlayer; //either P1 or P2
	
	//test game
	private TestFrame testFrame;


	// Setters for the GUI controllers.
	public void setLoginControl(LoginControl loginControl)
	{
		this.loginControl = loginControl;
	}
	public void setCreateAccountControl(CreateAccountControl createAccountControl)
	{
		this.createAccountControl = createAccountControl;
	}
	public void setLeaderboardControl(LeaderboardControl leaderboardControl)
	{
		this.leaderboardControl = leaderboardControl;
	}
	
	
	
	//game related method
	public void setContainer(JPanel container)
	{
		this.container = container;
	}
	
	private  void showView(String viewName) 
	{
		if(container != null)
		{
		  CardLayout layout = (CardLayout)container.getLayout();
		  layout.show(container, viewName);
		}
		else
			System.out.print("Container is not set");
	}
	
	public GameControl getGameController() {
		return controller;
	}

	public void setGameController(GameControl gameController) {
		this.controller = gameController;
	}

	public GameView getGameView() {
		return game;
	}

	public void setGameView(GameView gameView) {
		this.game = gameView;
	}
	
	public String getPlayer1() {
		return player1;
	}

	public void setPlayer1(String player1) {
		this.player1 = player1;
	}

	public String getPlayer2() {
		return player2;
	}

	public void setPlayer2(String player2) {
		this.player2 = player2;
	}

	public String getClientPlayer() {
		return clientPlayer;
	}
	public void setClientPlayer(String clientPlayer) {
		this.clientPlayer = clientPlayer;
	}

	public String getNumberPlayer() {
		return numberPlayer;
	}
	public void setNumberPlayer(String numberPlayer) {
		this.numberPlayer = numberPlayer;
	}

	
	public GameGUI getGuiFrame() {
		return guiFrame;
	}
	public void setGuiFrame(GameGUI guiFrame) {
		this.guiFrame = guiFrame;
	}

	
	public GameFrame getGameFrame(GameFrame frame)
	{
		return gameFrame;
	}
	
	//end of game related method
	
	//start of test game methods
	
	public TestFrame getTestFrame() {
		return testFrame;
	}
	public void setTestFrame(TestFrame testFrame) {
		this.testFrame = testFrame;
	}
	
	
	//end of test game methods
	
	
	
	public GameClient()
	{
		super("localhost", 8300);
		try {
			this.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void handleMessageFromServer(Object arg0) {
		// TODO Auto-generated method stub
		if(arg0 instanceof String) 
		{
			System.out.println((String)arg0);
			String msg = (String)arg0;
			if(msg.equals("account created")) {
				createAccountControl.createAccountSuccess();
				//Handle Login Here
			} else if(msg.equals("login successful")) {
				loginControl.loginSuccess();
				//Handle Login Here
			} else if (msg.equals("Failed Login")) {
				loginControl.displayError("Login Failed");
			} else if(msg.contains(",")) {
				
				String[] leaderboard = msg.split(",");
				leaderboardControl.showLeaderboard(leaderboard);
				//Handle Login Here
			} else if (msg.equals("account creation failed")) {
				createAccountControl.createAccountFailure();
			}
		}
		else if(arg0 instanceof StartGameGranted)
		{
			//hide the user interface frame
			if(guiFrame != null)
				guiFrame.setVisible(false);
			
			//test frame
			if(testFrame != null)
				testFrame.setVisible(false);
			
			//TypeCast
			StartGameGranted info = (StartGameGranted)arg0;
			player1 = info.getPlayer1();
			player2 = info.getPlayer2();
			clientPlayer = info.getYourPlayer();
			numberPlayer = info.getNumberPlayer();
			
			
	
			//game starts because game request is processed by server
			gameFrame = new GameFrame(this, "Game Window");
			gameFrame.setPlayer1Name(info.getPlayer1());
			gameFrame.setPlayer2Name(info.getPlayer2());
			
			


			
			
			
			/*
			this.setGameView(info.getView());	//set the view for this client
			this.container.add(this.gameView);	//add the view to the container
			this.setGameController(info.getControl());		//set the controller for this client
			this.gameController.setGameView(container);     //pass the container to the controller
			*/
			
			
		}
		
		else if (arg0 instanceof GameModel)
		{//game model passed from server, refresh view
			
			//Typecast
			GameModel data = (GameModel)arg0;

			if(data.getServerMsg().equals("mousePressed_Valid"))
			{
				//mousePressed was validated by server, execute method
				controller.mousePressedGranted(data.getMouseE());

			}
			else if(data.getServerMsg().equals("mouseReleased_Valid"))
			{
				//mouseReleased was validated by server, execute method
				controller.mouseReleasedGranted(data.getMouseE());

			}
			else if(data.getServerMsg().equals("keyPressed_Valid" ))
			{
				//keyPressed_Valid was validated by server, execute method
				controller.keyPressedGranted(data);

				
			}
			else if(data.getServerMsg().equals("keyReleased_Valid"))
			{
				//keyReleased_Valid was validated by server, execute method
				controller.keyReleasedGranted(data);

				
			}

		}

		

	}
	public void connectionException (Throwable exception) 
	{
		//Add your code here
	}
	public void connectionEstablished()
	{
		//Add your code here
		System.out.println("Connection Established");
	}

	//Dummy Main
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}
	
	//game related methods --------------------------------------------------------------------
	/*
	private void mousePressed(MouseEvent e)
	{
		//the actual implementation of the method
		game.fired = true;
		game.mouseXY[0] = e.getX() - game.team1.get((game.playerTurn-1)/2).getX();// gets x of mouse
				// and takes away
				// player x
		game.mouseXY[1] = game.team1.get((game.playerTurn-1)/2).getY() - e.getY();// gets -y of mouse
				// and adds
				// player y
				
		
		//mouseReleased implementation
		int mousecode = e.getButton();
		if (game.weaponsUsedInTurn < game.MaxWeaponsPerTurn)
			if (game.playerTurn%2 ==1)
				if (game.team1.get(0).getGrenadesAvailable() > 0) 
				{
					game.mouseXY[0] = e.getX() - game.team1.get((game.playerTurn-1)/2).getX();// gets x of mouse
															// and takes away
															// player x
					game.mouseXY[1] = game.team1.get((game.playerTurn-1)/2).getY() - e.getY();// gets -y of mouse
															// and adds
					// player y
		
					if (mousecode == MouseEvent.BUTTON1) 
					{
						controller.fire(game.clickVelocity); // fires weapon
						game.fired = false;						 
						game.clickVelocity = 0; // resets click velocity
						game.weaponsUsedInTurn++;
					}
				}
		if (game.weaponsUsedInTurn < game.MaxWeaponsPerTurn)
			if (game.playerTurn%2 == 0)
				if (game.team1.get(0).getGrenadesAvailable() > 0) 
				{
					game.mouseXY[0] = e.getX() - game.team2.get((game.playerTurn-1)/2).getX();// gets x of mouse
															// and takes away
															// player x
					game.mouseXY[1] = game.team2.get((game.playerTurn-1)/2).getY() - e.getY();// gets -y of mouse
															// and adds
					// player y
		
					if (mousecode == MouseEvent.BUTTON1) 
					{
						controller.fire(game.clickVelocity); // fires weapon
						game.fired = false; // ends the log
						game.clickVelocity = 0; // resets click velocity
						game.weaponsUsedInTurn++;
					}
				}
		
		
	}
	
	
	//--------------
	
	private void mouseReleased(MouseEvent e) 
	{

		int mousecode = e.getButton();
		if (game.weaponsUsedInTurn < game.MaxWeaponsPerTurn)
			if (game.playerTurn%2 ==1)
				if (game.team1.get(0).getGrenadesAvailable() > 0) 
				{
					game.mouseXY[0] = e.getX() - game.team1.get((game.playerTurn-1)/2).getX();// gets x of mouse
															// and takes away
															// player x
					game.mouseXY[1] = game.team1.get((game.playerTurn-1)/2).getY() - e.getY();// gets -y of mouse
															// and adds
					// player y
		
					if (mousecode == MouseEvent.BUTTON1) 
					{
						controller.fire(game.clickVelocity); // fires weapon
						game.fired = false;						 
						game.clickVelocity = 0; // resets click velocity
						game.weaponsUsedInTurn++;
					}
				}
		if (game.weaponsUsedInTurn < game.MaxWeaponsPerTurn)
			if (game.playerTurn%2 == 0)
				if (game.team1.get(0).getGrenadesAvailable() > 0) 
				{
					game.mouseXY[0] = e.getX() - game.team2.get((game.playerTurn-1)/2).getX();// gets x of mouse
															// and takes away
															// player x
					game.mouseXY[1] = game.team2.get((game.playerTurn-1)/2).getY() - e.getY();// gets -y of mouse
															// and adds
					// player y
		
					if (mousecode == MouseEvent.BUTTON1) 
					{
						controller.fire(game.clickVelocity); // fires weapon
						game.fired = false; // ends the log
						game.clickVelocity = 0; // resets click velocity
						game.weaponsUsedInTurn++;
					}
				}
		
		
	}
	
	
	//-----------------------
	
	public void keyPressed(GameModel data) 
	{ // fires automatically when a key is

		
		if(game.move == true )
		{
			int keycode = data.getKeyCode();
			if (game.pressedKeys.contains(keycode) == false) 
			{
				game.pressedKeys.add(keycode);
			}
		
			if (game.playerTurn == 1 | game.playerTurn == 3 | game.playerTurn == 5 | game.playerTurn == 7) 
			{
				game.p = game.team1.get((game.playerTurn-1)/2);
				
				if (game.pressedKeys.contains(KeyEvent.VK_DOWN)) {
					controller.changeWeapon(0);
				}
				if (game.pressedKeys.contains(KeyEvent.VK_UP)) {
					controller.playerJump();
				}
				if (game.pressedKeys.contains(KeyEvent.VK_RIGHT))
					game.p.moveRight(3);
				if (game.pressedKeys.contains(KeyEvent.VK_LEFT))
					game.p.moveLeft(3);
				if (game.pressedKeys.contains(KeyEvent.VK_SPACE)) 
				{
					//weaponLaunch();
				}
		
			} 
			
			else 
			{
				game.p = game.team2.get((game.playerTurn-1)/2);
				
				if (game.pressedKeys.contains(KeyEvent.VK_DOWN)) {
					controller.changeWeapon(1);
				}
				if (game.pressedKeys.contains(KeyEvent.VK_UP)) {
					controller.playerJump();
				}
				if (game.pressedKeys.contains(KeyEvent.VK_RIGHT))
					game.p.moveRight(3);
				if (game.pressedKeys.contains(KeyEvent.VK_LEFT))
					game.p.moveLeft(3);
				if (game.pressedKeys.contains(KeyEvent.VK_SPACE)) 
				{
					//weaponLaunch();
				}
			}
			game.move = false;
		}
		
		//key released implementation
		//set the view again
		

		
		int index;
		index = game.pressedKeys.indexOf(data.getKeyCode());
		if (index != -1)
		game.pressedKeys.remove(index);
		
		


	}
	
	//--------------
	
	public void keyReleased(GameModel data) 
	{
		// TODO Auto-generated method stub
		// fires automatically when a key is
		// released
		

		
		int index;
		index = game.pressedKeys.indexOf(data.getKeyCode());
		if (index != -1)
		game.pressedKeys.remove(index);
		

		
	}
*/
}//end of class
