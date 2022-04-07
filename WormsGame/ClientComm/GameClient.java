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
		super("localhost", 12345);
	}

	@Override
	protected void handleMessageFromServer(Object arg0) {
		// TODO Auto-generated method stub
		
		
	}

}
