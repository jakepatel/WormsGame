package ClientComm;
//Jake

import java.io.IOException;

import javax.swing.JPanel;

import controller.CreateAccountControl;
import controller.LoginControl;
import ocsf.client.*;

public class GameClient extends AbstractClient{
	// Private data fields for storing the GUI controllers.
	private LoginControl loginControl;
	private CreateAccountControl createAccountControl;

	public GameClient(String host, int port) {
		super(host, port);
		// TODO Auto-generated constructor stub
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
				CreateAccountControl.createAccountSuccess();
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
	// Setters for the GUI controllers.
	  public void setLoginControl(LoginControl loginControl)
	  {
	    this.loginControl = loginControl;
	  }
	  public void setCreateAccountControl(CreateAccountControl createAccountControl)
	  {
	    this.createAccountControl = createAccountControl;
	  }
	//Dummy Main
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

}
