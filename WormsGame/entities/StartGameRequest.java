package entities;
//Jake 


import java.io.Serializable;

public class StartGameRequest implements Serializable
{
	
	private static final long serialVersionUID = -2250388693926048236L;	//auto-generated
	
	
	private String sentBy;	//contains name of the user who sent a request
	
	
	public String getAccount() {
		return sentBy;
	}

	public void setAccount(String account) {
		this.sentBy = account;
	}

	public StartGameRequest(String player)
	{
		sentBy = player;
	}

}