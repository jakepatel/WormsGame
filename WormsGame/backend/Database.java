package backend;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;


public class Database {
  private Connection conn;
	  
	  private String url;
	  private String user;
	  private String pass;
	  //Add any other data fields you like � at least a Connection object is mandatory
	  public Database() throws IOException
	  {
	    //Add your code here
		//Read properties file
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream("lab7out/db.properties");
		prop.load(fis);
		url = prop.getProperty("url");
		user = prop.getProperty("user");
		pass = prop.getProperty("password");  
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
			ResultSet rs = stmt.executeQuery("select username, aes_decrypt(passwd, 'key') as passwd from user where username='" + username + "' and role='admin';");
			if (rs.next() && rs.getString("passwd").equals((password))) {
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
			ResultSet rs = stmt.executeQuery("select username, aes_decrypt(passwd, 'key') as passwd from user where username='" + username + "';");
		
			if (rs.next() && rs.getString("passwd").equals((password))) {
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
				  insert.executeUpdate("insert into user values ('" + username + "', aes_encrypt('" + password + "', 'key'));");
				  return true;
			  }
		  } catch (SQLException e) {
			  e.printStackTrace();
		  }
	    
	    return false;
	  }
}

