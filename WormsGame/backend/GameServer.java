package backend;

import java.awt.CardLayout;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;


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
	private ArrayList<ConnectionToClient> gameOverQueue;

	private static int getStaticNumber = 0;
	
	private String supposedWinner = "";	//either P1 or P2
	private String actualWinner = "";	//either P1 or P2
	private boolean draw;	//game draw
	
	

	//end of game related data

	public GameServer(JTextArea log, JLabel status) {
		super(8300);
		setTimeout(500);
		this.setLog(log);
		this.setStatus(status);
		db = new Database();
		connections = new ArrayList<ConnectionToClient>();
		gameOverQueue = new ArrayList<ConnectionToClient>();
		supposedWinner = "";
		actualWinner = "";

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
			} else {
				log.append("Client " + arg1.getId() + " failed to create account " + m.getUsername() + "\n");
				try {
					arg1.sendToClient("account creation failed");
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
				System.out.println("ChangeTurns called");
			else if(gameData.getMethodCalled().equals("mousePressed"))
				mousePressed_OnGame(gameData, gameData.getMouseE(), arg1);	//mousePressed on the client side called (within GameControl class)
			else if(gameData.getMethodCalled().equals("mouseReleased"))
				mouseReleased_OnGame(gameData, gameData.getMouseE(), arg1);	//mouseReleased on the client side called (within GameControl class)
			else if(gameData.getMethodCalled().equals("keyPressed")) 
				keyPressed_OnGame(gameData, gameData.getKeyCode(), arg1);			
			else if(gameData.getMethodCalled().equals("keyReleased")) 
				keyReleased_OnGame(gameData, gameData.getKeyCode(), arg1);   //keyReleased on the client side called (within GameControl)

			



		} 
		else if (arg0 instanceof GameOverModel) 
		{

			
			GameOverModel data = (GameOverModel)arg0;
			
			//check to see if both client players report game over
			if(connections.size() <= 2 && !connections.isEmpty())		//forcing only two connections at the moment
			{
				//remove the connection to the array
				if(connections.contains(arg1))
				{
					connections.remove(arg1);
					gameOverQueue.add(arg1);
				}
				
				//check for two players
				if(player1 == null || data.getSentBy().equals(player1.getUsername()))	//no players set
				{
					//first player
					player1 = null;
				}
				if(player2 == null || data.getSentBy().equals(player2.getUsername()))	//1 player set and second player not set
				{
					//second player
					player2 = null;
				}
				
				if((player1 == null && player2 != null) || (player1 != null && player2 == null ))
					supposedWinner = data.getNumberWinner();
			}

			
			
			if(data.isDraw())
				draw = true;
			
			
			
			if(gameOverQueue.size() == 2)
			{//both clients reported game over
				
				if(supposedWinner.equals("P1") && data.getNumberWinner().equals("P1"))	//both clients agree on winner P1
					actualWinner = "P1";
				else if(supposedWinner.equals("P2") && data.getNumberWinner().equals("P2"))	//both clients agree on winner P2
					actualWinner = "P2";
				else if(supposedWinner.equals("draw") && data.getNumberWinner().equals("draw"))
					actualWinner = "draw";
				else
					System.out.println("Inconsisten winner reported");
				
				
				String winner = data.getGameWinner();
				
				if(actualWinner.equals("P1"))	//player 1 won
				{
					System.out.println("player 1: " + winner + " won");
					
					
					actualWinner = "";
					supposedWinner = "";
				}
				else if(actualWinner.equals("P2")) //player 2 won
				{
					System.out.println("player 2: " + winner + " won");
					
					actualWinner = "";
					supposedWinner = "";
				}
				else if(draw && actualWinner.equals("draw"))	//game was a draw
				{
					System.out.println("The game was a draw:  " + data.getPlayer1() + " & " + data.getPlayer2());
					
					actualWinner = "";
					supposedWinner = "";
				}
				else
				{
					System.out.println("Invalid GameOver msg");
					
					actualWinner = "";
					supposedWinner = "";
				}
				
				
				
				//db.updateScore(gameData.player1, data.getFinalScore());
				//db.updateScore(gameData.player2, data.getFinalScore());
				
				//report back to client
				try {
					gameOverQueue.get(0).sendToClient(data);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				try {
					gameOverQueue.get(1).sendToClient(data);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				gameOverQueue = new ArrayList<ConnectionToClient>();
				
			
				
			}
			
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

	private void mousePressed_OnGame(GameModel data, MouseEvent e, ConnectionToClient arg1)		//method found in GameControl
	{
		//this method is the API for "mousePressed" method in GameControl

		//for now, send back valid message
		data.setServerMsg("mousePressed_Valid");


		this.sendToAllClients(data);

	}

	//-------------

	private void mouseReleased_OnGame(GameModel data, MouseEvent e, ConnectionToClient arg1)
	{
		//this method is an API for "mouseReleased" method in GameControl

		//for now, send back valid message
		data.setServerMsg("mouseReleased_Valid");


		this.sendToAllClients(data);


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
