package backend;

import java.io.IOException;

import ocsf.server.*;

public class GameServer extends AbstractServer {
    public GameModel gameData;
    public LoginModel logInData;
    public LeaderboardModel leaderBrdData;
    public MainMenuModel mainMenuData;
    // public LogOutModel logOutData; ??
    public GameOverModel gameOverData;
    public AdminSettingModel adminSettingData;
    
    private Database db;

    public GameServer() {
    	super(12345);
    	
		db = new Database();
    	
    }

	@Override
	protected void handleMessageFromClient(Object arg0, ConnectionToClient arg1) {
		// TODO Auto-generated method stub
		
		if (arg0 instanceof AdminSettingModel) {
			this.adminSettingData = (AdminSettingModel)arg0;
		} else if (arg0 instanceof LoginModel) {
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
		} else if (arg0 instanceof AdminLoginModel) {
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
				try {
					arg1.sendToClient("account created");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public void serverStarted() {
		System.out.println("Server Started");
	}
	
	public void serverStopped() {
		System.out.println("Server Stopped");
	}
	
	public void serverClosed() {
		System.out.println("Server Closed");
	}
	
	public void clientConnected(ConnectionToClient client) {
		System.out.println("Client " + client.getId() + " Connected!");
	}
	
	public void listeningException(Throwable exception) {
		exception.printStackTrace();
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		GameServer gs = new GameServer();
		
		try {
			gs.setPort(8300);
			gs.setTimeout(500);
			gs.listen();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				System.out.println("Shutdown hook ran");
				try {
					gs.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
