package backend;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Properties;



public class Database {
  private Connection conn;
	  
	  private String url;
	  private String user;
	  private String pass;
	  HashMap<String, String> database;
	  //Add any other data fields you like – at least a Connection object is mandatory
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
			conn = DriverManager.getConnection(url,user,pass);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	  
	  // Method for verifying a username and password.
	  public boolean verifyAccount(String username, String password)
	  { 
	    
	    // Stop if this account doesn't exist.
	    if (database.get(username) == null)
	      return false;
	    
	    // Check the username and password.
	    if (database.get(username).equals(password))
	      return true;
	    else
	      return false;
	  }
	  
	  // Method for creating a new account.
	  public boolean createNewAccount(String username, String password) throws SQLException
	  {	    
	    
	    // Stop if this account already exists.
	    if (database.get(username) != null)
	      return false;
	    
	    // Add the new account.
	    database.put(username, password);
	    executeDML("insert into user values('"+username+"',aes_encrypt('"+password+"','key'))");
	    return true;
	  }
	  
	  public synchronized void query(String type)
	  {	
		// Create a new HashMap for the database.
		database = new HashMap<String, String>();	  
		//Using the statement object executeQuery using the input query (Return the ResultSet)
		try {
			
			Statement stmt = conn.createStatement();			
			ResultSet rs = stmt.executeQuery("select username,aes_decrypt(password, 'key')  from user");
			
			while(rs.next()) {
				database.put(rs.getString(1),rs.getString(2));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}	   
		
	  }
	  
	  public void executeDML(String dml) throws SQLException
	  {
	    //Add your code here
		//Execute a DML statement
		  Statement stmt = conn.createStatement();
	      stmt.execute(dml);
	  }

}
