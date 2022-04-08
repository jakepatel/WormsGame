package ClientComm;
//Jake

import ocsf.client.*;

public class GameClient extends AbstractClient{

	public GameClient(String host, int port) {
		super(host, port);
		// TODO Auto-generated constructor stub
	}
	
	public GameClient()
	{
		super("localhost", 8300);
	}

	@Override
	protected void handleMessageFromServer(Object arg0) {
		// TODO Auto-generated method stub
		System.out.println((String)arg0);
		
	}

}
