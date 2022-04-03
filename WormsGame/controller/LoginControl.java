package controller;
//Collins

import java.awt.*;
import javax.swing.*;

import ClientComm.GameClient;
import backend.Database;
import backend.LoginModel;

import java.awt.event.*;
import java.io.IOException;

public class LoginControl implements ActionListener {
	// Private data fields for the container and chat client.
	private JPanel container;
	private GameClient client;

	// Constructor for the login controller.
	public LoginControl(JPanel container, GameClient client) {
		this.container = container;
		this.client = client;
	}

	// Handle button clicks.
	public void actionPerformed(ActionEvent ae) {
		// Get the name of the button clicked.
		String command = ae.getActionCommand();

		// The Cancel button takes the user back to the initial panel.
		if (command == "Cancel") {
			CardLayout cardLayout = (CardLayout) container.getLayout();
			cardLayout.show(container, "1");
		}
		// The Submit button submits the login information to the server.
		else if (command == "Submit") {
			// Get the username and password the user entered.
			LoginModel loginPanel = (LoginModel) container.getComponent(1);
			LoginData data = new LoginData(loginPanel.getUsername(), loginPanel.getPassword());
			Database db = new Database();

			// Check the validity of the information locally first.
			if (data.getUsername().equals("") || data.getPassword().equals("")) {
				displayError("You must enter a username and password.");
				return;
			} else if (!db.validateAccount(loginPanel.getUsername(), loginPanel.getPassword())) {
				displayError("The username and password are incorrect.");
				return;
			} else {
				try {
					client.setLogin(this);
					client.sendToServer(data);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			// Submit the login information to the server.

		}
	}

	// After the login is successful, set the User object and display the contacts
	// screen. - this method would be invoked by
	// the ChatClient
	public void loginSuccess() {
		LoginModel lp = (LoginModel) container.getComponent(1);
		lp.setUsername("");
		lp.setPassword("");
		CardLayout cardLayout = (CardLayout) container.getLayout();
		cardLayout.show(container, "3");
	}

	// Method that displays a message in the error - could be invoked by ChatClient
	// or by this class (see above)
	public void displayError(String error) {
		LoginModel loginPanel = (LoginModel) container.getComponent(1);
		loginPanel.setError(error);

	}
}
