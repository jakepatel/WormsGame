package entities;
//Jake 


import java.io.Serializable;

public class StartGameRequest implements Serializable
{
	
	private static final long serialVersionUID = -2250388693926048236L;	//auto-generated
	
	
	private Player sentBy;	//contains name of the user who sent a request
	
	
	
	public Player getPlayerAccount() {
		return sentBy;
	}

	public void setAccount(Player account) {
		this.sentBy = account;
	}

	public StartGameRequest(Player player)
	{
		sentBy = player;
	}

}