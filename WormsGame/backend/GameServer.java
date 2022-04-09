package backend;

import java.awt.Color;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JTextArea;

import ocsf.server.*;

public class GameServer extends AbstractServer {
    public GameModel gameData;
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

    public GameServer(JTextArea log, JLabel status) {
    	super(12345);
    	setTimeout(500);
    	this.setLog(log);
    	this.setStatus(status);
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
		log.append("Server Started\n");
		status.setText("Listening");
		status.setForeground(Color.green);
	}
	
	public void serverStopped() {
		System.out.println("Server Stopped");
	}
	
	public void serverClosed() {
		System.out.println("Server Closed");
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
	
	
	

}
