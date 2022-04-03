package backend;

public class LoginModel {
	private String username;
	private String password;
	
	public LoginModel(String username, String password){
		this.username = username;
		this.password = password;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}

	public void setError(String error) {
		// TODO Auto-generated method stub
		
	}
}
