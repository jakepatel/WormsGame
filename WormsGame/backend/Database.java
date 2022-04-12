package backend;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;


public class Database {
  private Connection conn;
	  
	  private String url;
	  private String user;
	  private String pass;
	  //Add any other data fields you like � at least a Connection object is mandatory
	  public Database()
	  {
	    try {
	    	Properties prop = new Properties();
			FileInputStream fis = new FileInputStream("jake_db.properties");
			prop.load(fis);
			url = prop.getProperty("url");
			user = prop.getProperty("user");
			pass = prop.getProperty("password");
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
		//Perform the Connection
	    try { 
			conn = DriverManager.getConnection(url, user, pass);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	  
	  public boolean verifyAdminAccount(String username, String password)
	  {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select username, aes_decrypt(password, 'key') as password from user where username='" + username + "' and role='admin';");
			if (rs.next() && rs.getString("password").equals((password))) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	  }
	  
	  // Method for verifying a username and password.
	  public boolean verifyAccount(String username, String password)
	  {
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select username, aes_decrypt(password, 'key') as password from user where username='" + username + "';");
		
			if (rs.next() && rs.getString("password").equals((password))) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	  }
	  
	  // Method for creating a new account.
	  public boolean createNewAccount(String username, String password)
	  {
		  try {
			  Statement stmt = conn.createStatement();
			  ResultSet rs = stmt.executeQuery("select * from user where username='" + username + "';");
			  if (rs.next()) {
				  return false;
			  } else {
				  Statement insert = conn.createStatement();
				  insert.executeUpdate("insert into user values ('" + username + "', aes_encrypt('" + password + "', 'key'), 'user');");
				  return true;
			  }
		  } catch (SQLException e) {
			  e.printStackTrace();
		  }
	    
	    return false;
	  }
	  
	  public boolean createAdminAccount(String username, String password)
	  {
		  try {
			  Statement stmt = conn.createStatement();
			  ResultSet rs = stmt.executeQuery("select * from user where username='" + username + "';");
			  if (rs.next()) {
				  return false;
			  } else {
				  Statement insert = conn.createStatement();
				  insert.executeUpdate("insert into user values ('" + username + "', aes_encrypt('" + password + "', 'key'), 'admin');");
				  return true;
			  }
		  } catch (SQLException e) {
			  e.printStackTrace();
		  }
	    
	    return false;
	  }
	  
	  public boolean deletePlayer(String playerId) {
		  try {
			  Statement stmt = conn.createStatement();
			  ResultSet rs = stmt.executeQuery("select * from user where id='" + playerId + "';");
			  
			  if (rs.next()) {
				  return false;
			  } else {
				  Statement insert = conn.createStatement();
				  insert.executeUpdate("delete from user where id='" + playerId + "';");
				  return true;
			  }
		  } catch (SQLException e) {
			  e.printStackTrace();
		  }
		  return false;
	  }
	  
	  public ArrayList<String> getLeaderBoard() {
		  
		  ArrayList<String> leaderboard = new ArrayList<String>();
		  try {
			  Statement stmt = conn.createStatement();
			  ResultSet rs = stmt.executeQuery("select * from user;");
			  
			  while(rs.next()) {
				 leaderboard.add(rs.getString(1));
			  } 
				  	  
		  } catch (SQLException e) {
			  e.printStackTrace();
		  }
		  return leaderboard;
	  }
}

