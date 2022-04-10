package ClientComm;
//Jake

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JPanel;

import controller.CreateAccountControl;
import controller.LeaderboardControl;
import controller.LoginControl;
import controller.MainMenuControl;
import ocsf.client.*;

public class GameClient extends AbstractClient{
	// Private data fields for storing the GUI controllers.
	private LoginControl loginControl;
	private CreateAccountControl createAccountControl;
	private LeaderboardControl leaderboardControl;

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
		if(arg0 instanceof String) {
			System.out.println((String)arg0);
			String msg = (String)arg0;
			if(msg.equals("account created")) {
				createAccountControl.createAccountSuccess();
				//Handle Login Here
			}
			if(msg.equals("login successful")) {
				loginControl.loginSuccess();
				//Handle Login Here
			}
			if(msg.contains(",")) {
				
				String[] token = msg.split(",");
				leaderboardControl.showLeaderboard(token);
				//Handle Login Here
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

}
