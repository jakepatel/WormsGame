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
    	super(8000);
    }

	@Override
	protected void handleMessageFromClient(Object arg0, ConnectionToClient arg1) {
		// TODO Auto-generated method stub
		
		if (arg0 instanceof AdminSettingModel) {
			this.adminSettingData = (AdminSettingModel)arg0;
		} else if (arg0 instanceof LoginModel) {
			LoginModel m = (LoginModel)arg0;
			// process login
			if (db.verifyAccount(m.getUsername(), m.getPassword())) {
				// successful login, send to client
				try {
					arg1.sendToClient("Successful Login");
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
			LoginModel m = (LoginModel)arg0;
			// process login
			if (db.verifyAdminAccount(m.getUsername(), m.getPassword())) {
				// successful login, send to client
				try {
					arg1.sendToClient("Successful Login");
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
				
			}
		} else if (arg0 instanceof CreateAccountModel) {
			CreateAccountModel m = (CreateAccountModel)arg0;
			
			if (db.createNewAccount(m.getUsername(), m.getPassword())) {
				
			}
		}
		
	}
	
	public void serverStarted() {}
	
	public void serverStopped() {}
	
	public void serverClosed() {}
	
	public void clientConnected(ConnectionToClient client) {}
	
	public void listeningException(Throwable exception) {
		exception.printStackTrace();
	}
	
	
	public static void main(String[] args) {
		GameServer gs = new GameServer();
		
		try {
			gs.listen();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
