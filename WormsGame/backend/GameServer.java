package backend;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JTextArea;

import controller.GameControl;
import entities.Player;
import entities.SoundEffect;
import entities.StartGameGranted;
import entities.StartGameRequest;
import frontend.GameView;
import ocsf.server.*;

public class GameServer extends AbstractServer {

	public LoginModel logInData;
	public LeaderboardModel leaderBrdData;
	public MainMenuModel mainMenuData;
	// public LogOutModel logOutData; ??
	public GameOverModel gameOverData;
	public AdminSettingModel adminSettingData;
	private boolean stopActionFlag;
	private Database db;

	private JTextArea log;
	private JLabel status;
	
	
	//game related data
	public GameModel gameData;
    
    private Player player1;
    private Player player2;
    
    private static int gamesGranted = 0;
    
    private ArrayList<ConnectionToClient> connections;
    
	private static int getStaticNumber = 0;

	//end of game related data
	
	public GameServer(JTextArea log, JLabel status) {
		super(8300);
		setTimeout(500);
		this.setLog(log);
		this.setStatus(status);
		db = new Database();
		connections = new ArrayList<ConnectionToClient>();


	}


	

	@Override
	protected void handleMessageFromClient(Object arg0, ConnectionToClient arg1) {
		// TODO Auto-generated method stub

		if (arg0 instanceof AdminSettingModel) {
			this.adminSettingData = (AdminSettingModel)arg0;
		} 
		else if (arg0 instanceof LoginModel) {
			System.out.println("login model recieved!");
			LoginModel m = (LoginModel)arg0;
			// process login
			if (db.verifyAccount(m.getUsername(), m.getPassword())) {
				// successful login, send to client
				try {
					arg1.sendToClient("login successful");
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				// send login failure to client
				try {
					arg1.sendToClient("Failed Login");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		} 
		else if (arg0 instanceof AdminLoginModel) {
			System.out.println("admin login model recieved!");
			LoginModel m = (LoginModel)arg0;
			// process login
			if (db.verifyAdminAccount(m.getUsername(), m.getPassword())) {
				// successful login, send to client
				try {
					arg1.sendToClient("admin login successful");
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				// send login failure to client
				try {
					arg1.sendToClient("Failed Login");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else if (arg0 instanceof DeletePlayerModel) {
			DeletePlayerModel m = (DeletePlayerModel)arg0;

			if (db.deletePlayer(m.getId())) {
				try{
					arg1.sendToClient("deleted player");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else if (arg0 instanceof CreateAccountModel) {
			System.out.println("CreateAccountModel recieved!");
			CreateAccountModel m = (CreateAccountModel)arg0;

			if (db.createNewAccount(m.getUsername(), m.getPassword())) {
				log.append("Client " + arg1.getId() + " created a new account called " + m.getUsername() + "\n");
				try {
					arg1.sendToClient("account created");

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else if (arg0 instanceof MainMenuModel) {
			System.out.println("MainMenuModel recieved!");
			MainMenuModel m = (MainMenuModel)arg0;
			

			if(m.getGameSelection().equals("Leaderboard")) {
				ArrayList<String> leaderboard = new ArrayList<String>();
				leaderboard = db.getLeaderBoard();
				String concatLeaderboard = "";
				for(String name : leaderboard) {
					concatLeaderboard +=name+",";
				}
				log.append(leaderboard.toString());
				try {
					arg1.sendToClient(concatLeaderboard);

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
		else if (arg0 instanceof StartGameRequest)
		{
			//TypeCast
			StartGameRequest request = (StartGameRequest)arg0;
			
			if(connections.size() <= 1)		//forcing only two connections at the moment
			{
				//store the connection to the array
				connections.add(arg1);
				arg1.setName(request.getPlayerAccount().getUsername());
				
				
				//check for two playes
				if(player1 == null && player2 == null)	//no players set
				{
						//first player
					player1 = request.getPlayerAccount();
				}
				else if(!(player1 == null) && player2 == null)	//1 player set and second player not set
				{
					//second player
					player2 = request.getPlayerAccount();
				}
				
				
				if(!(player1 == null || player2 == null))	//both player set
				{
					//two players connected, send a StartGameGranted to both
					StartGameGranted grantedP1 = new StartGameGranted(grantGame(), 
							connections.get(0).getName(), connections.get(1).getName(), connections.get(0).getName(), "P1");
					
					StartGameGranted grantedP2 = new StartGameGranted(grantGame(), 
							connections.get(0).getName(), connections.get(1).getName(), connections.get(1).getName(), "P2");
					
					try {
						connections.get(0).sendToClient(grantedP1);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					try {
						connections.get(1).sendToClient(grantedP2);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					
				}
				
			}
			
			//continue if more than two are connected
			
		}
		
		else if(arg0 instanceof GameModel)		//game model is passed to this server
		{
			gameData = (GameModel)arg0;
			
			//detect the method that is called
			if(gameData.getMethodCalled().equals("changeTurns"))	//changeTurns on the client side called (within GameView class)
				changeTurnsTimer(gameData, arg1);
			else if(gameData.getMethodCalled().equals("mousePressed"))
				mousePressed_OnGame(gameData, gameData.getMouseE(), arg1);	//mousePressed on the client side called (within GameControl class)
			else if(gameData.getMethodCalled().equals("mouseReleased"))
				mouseReleased_OnGame(gameData, gameData.getMouseE(), arg1);	//mouseReleased on the client side called (within GameControl class)
			else if(gameData.getMethodCalled().equals("keyPressed"))
				keyPressed_OnGame(gameData, gameData.getKeyCode(), arg1);	//keyPressed on the client side called (within GameControl)
			else if(gameData.getMethodCalled().equals("keyReleased"))
				keyReleased_OnGame(gameData, gameData.getKeyCode(), arg1);   //keyReleased on the client side called (within GameControl)
					
				
			
			
		}
	}

	public void serverStarted() {
		System.out.println("Server Started");
		log.append("Server Started\n");
		status.setText("Listening");
		status.setForeground(Color.green);
	}
	//When server is stopped it will send message to console and server log
	public void serverStopped() {

		//log.append("Server Stopped Accepting New Clients - Press Listen to Start Accepting New Clients\n");
		/*If the close() method was called first, 
		 * the stopActionFlag will be set to false.*/
		if(stopActionFlag==true) {
			System.out.println("Server Stopped");
			log.append("Server Stopped Accepting New Clients - "
					+ "Press Listen to reconnect");
			log.append("\n");
			status.setText("Stopped");
			status.setForeground(Color.red);
		}
	}

	public void serverClosed() {
		System.out.println("Server and all current clients are closed - Press Listen to Restart");
		//log.append("Server Stopped Accepting New Clients - Press Listen to Start Accepting New Clients\n");
		/*If the close() method was called first, 
		 * the stopActionFlag will be set to false.*/
		if(stopActionFlag==false) {
			log.append("Server Closed - "
					+ "Press Listen to reconnect");
			log.append("\n");
			status.setText("Closed");
			status.setForeground(Color.red);
		}
	}

	public void clientConnected(ConnectionToClient client) {
		System.out.println("Client Connected");
		//Display number of Clients:
		System.out.println("Number of Client:" + this.getNumberOfClients());	
		log.append("Client Connected\n");
	}

	public void listeningException(Throwable exception) {
		exception.printStackTrace();
	}
	public void setLog(JTextArea log)
	{
		this.log = log;
	}

	public JTextArea getLog()
	{
		return log;
	}

	public void setStatus(JLabel status)
	{
		this.status = status;
	}

	public JLabel getStatus()
	{
		return status;
	}
	/*Setting the parameter stopActionFlag from StopHandler class, to 
	 * this ChatServer's stopActionFlag */
	public void setStopFlag(boolean stopActionFlag) {
		this.stopActionFlag = stopActionFlag;
	}
	
	public static int getStaticNumber() 
    {
		return ++getStaticNumber;
	}
	
	private static int grantGame()
	{
		return ++gamesGranted;
	}

	
	//game related action and change view methods ---------------------------------------------------
	

	private void changeTurnsTimer(GameModel arg0, ConnectionToClient arg1)
	{//need ActionEvent e
		
		//this method is now depracted (ie. it is not used, just ignore)
		
		GameView game = gameData.getViewOfGame();
		ActionEvent e = gameData.getActionE();
		
		if (game.timeLeftInTurn == 0 ) 
		{
			game.fired = false;
			
			if(game.playerTurn==8)
			{
				game.playerTurn=1;
			}
			else
			{
				game.playerTurn++;
			}
			
			game.MaxWeaponsPerTurn = 1;
			game.weaponsUsedInTurn = 0;
			game.timeLeftInTurn = 30;
		} 
		else
			game.timeLeftInTurn--;
		if (game.timeLeftInTurn>0 && game.timeLeftInTurn<=10)
		{
			SoundEffect.TIMERTICK.play();
		}		
		System.out.println(game.weaponsUsedInTurn);
		if(game.weaponsUsedInTurn > 1)
		{
			game.timeLeftInTurn = 5;
			game.weaponsUsedInTurn = 0;
			game.MaxWeaponsPerTurn = 0;
			
		}
		
		game.board = game.createResultBoard();

		//send updated GameModel to client
		try {
			arg1.sendToClient(gameData);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
	}
	//-------------
	
	private void mousePressed_OnGame(GameModel data, MouseEvent e, ConnectionToClient arg1)		//method found in GameControl
	{
		//this method is the API for "mousePressed" method in GameControl
		
		//for now, send back valid message
		data.setServerMsg("mousePressed_Valid");
		
		
		this.sendToAllClients(data);
		
		/*
		try {
			connections.get(1).sendToClient(data);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		*/

		
		
	}
	
	//-------------
	
	private void mouseReleased_OnGame(GameModel data, MouseEvent e, ConnectionToClient arg1)
	{
		//this method is an API for "mouseReleased" method in GameControl
		
		//for now, send back valid message
		data.setServerMsg("mouseReleased_Valid");
		
		
		try{
			connections.get(0).sendToClient(data);;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			connections.get(1).sendToClient(data);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		

		
	}
	
	//----------

	private void keyPressed_OnGame(GameModel data, int keyCode, ConnectionToClient arg1)
	{
		//this method is an API for "mouseReleased" method in GameControl
		
		//for now, send back valid message
		data.setServerMsg("keyPressed_Valid");
		
		this.sendToAllClients(data);
		
		
	}
	
	//------------
	
	private void keyReleased_OnGame(GameModel data, int keyCode, ConnectionToClient arg1)
	{
		//this method is an API for "mouseReleased" method in GameControl
		
		//for now, send back valid message
		data.setServerMsg("keyReleased_Valid");
		
		this.sendToAllClients(data);
		
	}



}
