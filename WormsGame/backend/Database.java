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
	//Add any other data fields you like at least a Connection object is mandatory
	public Database()
	{
		try {
			Properties prop = new Properties();
			FileInputStream fis = new FileInputStream("db.properties");
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
				System.out.println("duplicate account");
				return false;
			} else {
				Statement insert = conn.createStatement();
				insert.executeUpdate("insert into user (username, password) values ('" + username + "', aes_encrypt('" + password + "', 'key'));");
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean deletePlayer(String playerId) {
		try {
			Statement insert = conn.createStatement();
			insert.executeUpdate("delete from user where username='" + playerId + "';");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public ArrayList<String> getLeaderBoard() {

		ArrayList<String> leaderboard = new ArrayList<String>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select username, games_won + games_lost as games_played, games_won, games_lost, cast(games_won as decimal) / games_lost as win_loss from user order by games_won desc limit 10;");

			while(rs.next()) {
				System.out.println(rs.getFloat("win_loss"));
				System.out.println(rs.getInt("games_played"));
				leaderboard.add(rs.getString("username") + " " + rs.getInt("games_won") + " " + rs.getInt("games_lost") + " " + rs.getFloat("win_loss"));
			} 

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return leaderboard;
	}

	public void updateScore(String username, boolean win) {
		try {
			Statement stmt = conn.createStatement();
			// only update the score if the new score is greater than the previous one
			if (win) {
				stmt.executeUpdate("update user set games_won=games_won + 1 where username='" + username + "';");
			} else {
				stmt.executeUpdate("update user set games_lost=games_lost + 1 where username='" + username + "';");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
