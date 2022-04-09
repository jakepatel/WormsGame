package ClientComm;
//Jake

import java.io.IOException;

import ocsf.client.*;

public class GameClient extends AbstractClient{

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
		System.out.println((String)arg0);
		
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
