package backend;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JTextArea;

import entities.Player;
import entities.StartGameGranted;
import entities.StartGameRequest;
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
		super(12345);
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
					StartGameGranted granted = new StartGameGranted(grantGame(), connections.get(0).getName(), connections.get(1).getName());
					
					try {
						connections.get(0).sendToClient(granted);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					try {
						connections.get(1).sendToClient(granted);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					
				}
				
			}
			
			//continue if more than two are connected
			
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





}
