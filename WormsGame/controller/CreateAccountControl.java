package controller;
//Collins

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

import ClientComm.GameClient;
import backend.CreateAccountModel;
import backend.Database;
import frontend.CreateAccountView;


public class CreateAccountControl implements ActionListener {
	private JPanel container;
	private GameClient client;
	
	public CreateAccountControl(JPanel container, GameClient client)
	{
		this.container = container;
		this.client = client;
	}
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String command = e.getActionCommand();
		
		CreateAccountView view = (CreateAccountView)container.getComponent(2);
		
		if(command == "Submit")
		{
			//if the user submits the credentials for creating new User
			//check that all fields match the parameters(password is at least 6 characters)
			if(view.getUsername().equals("") || view.getPassword().equals("") || view.getVerifyPassword().equals(""))
			{
				view.setError("You must enter in a username and both entries of password");
				return;
			}
			else if(view.getPassword().length() < 6 || view.getVerifyPassword().length() < 6)
			{
				view.setError("The password must be atleast six characters");
				return;
			}
			else if(!(view.getPassword().equals(view.getVerifyPassword())))
			{
				view.setError("The password entries do not match!");
				return;
			}
			else
			{
				CreateAccountModel account = new CreateAccountModel(view.getUsername(), view.getPassword(), view.getVerifyPassword());
				
				try {
					client.sendToServer(account);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			
		}
		else if(command.equals("Cancel"))
		{
			//go back to initalPanel
			CardLayout cardLayout = (CardLayout)container.getLayout();
			cardLayout.show(container, "InitialView");
			
			
		}
	}
}