package controller;
//Collins

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

import ClientComm.GameClient;

import backend.Database;
import frontend.CreateAccountView;


public class CreateAccountControl implements ActionListener {

	//Private variable declarations
	private CardLayout cl;
	private JPanel container;
	private GameClient client;

	//Constructor
	public CreateAccountControl(CardLayout cl, JPanel container, GameClient client) {
		this.cl = cl;
		this.container = container;
		this.client = client;
	}

	//Event Handler for actions performed in the Create Account Panel
	@Override
	public void actionPerformed(ActionEvent ae) {

		CreateAccountView createAccount = (CreateAccountView) container.getComponent(2);

		//Events
		if (ae.getSource() == createAccount.createAccount()) {
			String username = String.valueOf(createAccount.getUserName());
			String password = String.valueOf(createAccount.getPassword());
			String re_password = String.valueOf(createAccount.getRePassword());

			//Error messages if
			if (Database.usernameInUse(username))//Username taken
				createAccount.errorMessage("Username already chosen!");
			if (username.isEmpty())//Username field is empty
				createAccount.errorMessage("Username cannot be blank!");
			if (password.isEmpty())//Password field is empty
				createAccount.errorMessage("Password cannot be blank!");
			if (password.length() < 6 && password.length() != 0)//length of the password is less than 6
				createAccount.errorMessage("Password must be more than 6 characters");
			if (!password.equals(re_password))//password is not equal to confirm password
				createAccount.errorMessage("Passwords must match");

			//Finally if requirements are met
			if (password.equals(re_password) && password.length() >= 6 && !Database.usernameInUse(username)) {

				createAccount.errorMessage("");//Remove error message
				Database.writeToDatabase(username, password);//Write username and password to database
				LoginData accountData = new LoginData(username, password);//set login data

				try {
					client.sendToServer(accountData);//Send login data to server
					cl.show(container, "2");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else if (ae.getSource() == createAccount.cancelButton()) {
			createAccount.errorMessage(""); //Clear error message
			createAccount.clearUsernameField();//Clear previous username
			createAccount.clearPasswordField();//Clear previous password
			createAccount.clearRePasswordField();//Clear previous password
			cl.show(container, "1");//Go back
		}
	}
}