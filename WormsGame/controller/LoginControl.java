package controller;
//Jake

import java.awt.*;
import javax.swing.*;
import ClientComm.GameClient;
import backend.Database;
import backend.LoginModel;
import frontend.CreateAccountView;
import frontend.GameGUI;
import frontend.LoginView;
import java.awt.event.*;
import java.io.IOException;

public class LoginControl implements ActionListener {
	// Private data fields for the container and chat client.
	private JPanel container;
	private GameClient client;


	// Constructor for the login controller.
	public LoginControl(JPanel container, GameClient client)
	{
		this.container = container;
		this.client = client;

	}

	// Handle button clicks.
	public void actionPerformed(ActionEvent ae)
	{
		// Get the name of the button clicked.
		String command = ae.getActionCommand();

		// The Cancel button takes the user back to the initial panel.
		if (command == "Cancel")
		{
			CardLayout cardLayout = (CardLayout)container.getLayout();
			cardLayout.show(container, "InitialView");
		}

		// The Submit button submits the login information to the server.
		else if (command == "Submit")
		{
			// Get the username and password the user entered.
			LoginView loginView = (LoginView)container.getComponent(1);
			LoginModel data = new LoginModel(loginView.getUsername(), loginView.getPassword());

			// Check the validity of the information locally first.
			if (data.getUsername().equals("") || data.getPassword().equals(""))
			{
				displayError("You must enter a username and password.");
				return;
			}

			// Submit the login information to the server.
			try {
				client.sendToServer(data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	// Method that displays a message in the error - could be invoked by ChatClient or by this class (see above)
	public void displayError(String error)
	{
		LoginView loginView = (LoginView)container.getComponent(1);
		loginView.setError(error);
		CardLayout cardLayout = (CardLayout)container.getLayout();
		cardLayout.show(container, "LoginView");

	}

	// After an account is created, set the User object and display the contacts screen.
	public void loginSuccess()
	{
		LoginView createAccountPanel = (LoginView)container.getComponent(2);
		GameGUI clientGUI = (GameGUI)SwingUtilities.getWindowAncestor(createAccountPanel);
		//clientGUI.setUser(new User(createAccountPanel.getUsername(), createAccountPanel.getPassword()));
		CardLayout cardLayout = (CardLayout)container.getLayout();
		cardLayout.show(container, "LoginView");
	}
}
