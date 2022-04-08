package backend;

import java.io.Serializable;

public class CreateAccountModel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;
	private String verifyPass;
	
	public CreateAccountModel(String username, String password, String verifyPass){
		this.username = username;
		this.password = password;
		this.verifyPass = verifyPass;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setVerifyPass(String verifyPass) {
		this.verifyPass = verifyPass;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public String getVerifyPass() {
		return verifyPass;
	}
}
