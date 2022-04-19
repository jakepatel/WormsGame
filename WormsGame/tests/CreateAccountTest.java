package tests;

import static org.junit.Assert.*;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.io.IOException;

import org.junit.Test;

import ClientComm.GameClient;
import backend.DeletePlayerModel;
import controller.*;
import frontend.*;

import javax.swing.*;

import org.junit.AfterClass;
import org.junit.Before;


public class CreateAccountTest extends JFrame{

	//Instantiate the Views and Controls the Tests will utilize
	private static InitialView iv;
	private static InitialControl ic;
	
	private static LoginView lv;
	private static LoginControl lc;
	
	private static CreateAccountView cav;
	private static CreateAccountControl cac;
	
	//Instantiate GameClient
	private static GameClient gc;
	
	private static JPanel container;
	
	private static JTextField user;
	private static JPasswordField pass;
	private static JPasswordField verify;
	private static JButton button;
	
	//Instantiate variable that's used to force the Tests to be in the same window
	private static boolean setUpDone = false;
	
	//Sets the amount of time that the system will pause when Thread.sleep() is called
	int sleep = 500;
	
	@Before
	public void setUp() throws InterruptedException
	{
		if(!setUpDone)
		{		
			//set up Client, container for createAccount
			gc = new GameClient();
			CardLayout cardLayout = new CardLayout();
			container = new JPanel(cardLayout);
			cac = new CreateAccountControl(container,gc);
			cav = new CreateAccountView(cac);
			cav.setName("CreateAccountView");
		
			//use of initialview and loginview is to get back to createaccountview in specific
			ic = new InitialControl(container);
			lc = new LoginControl(container, gc);
			
			iv = new InitialView(ic);
			iv.setName("InitialView");
			lv = new LoginView(lc);
			lv.setName("LoginView");
		
			container.add(iv, "InitialView");
	    	container.add(lv, "LoginView");
			container.add(cav, "CreateAccountView");
		
			gc.setLoginControl(lc);
			gc.setCreateAccountControl(cac);
			
			this.add(container, BorderLayout.CENTER);
			gc.setContainer(container);
			cardLayout.show(container, "InitialView");
			this.setSize(550,350);
			this.setVisible(true);
			
			JButton createAcc = iv.getCreateButton();
			createAcc.doClick(1000);
		}
		
		user = cav.getUsernameField();
		pass = cav.getPasswordField();
		verify = cav.getVerifyField();
		button = cav.getSubmitButton();
		
		//clear textfields
		user.setText("");
		pass.setText("");
		verify.setText("");
		
		setUpDone = true;
	}
	
	@Test
	public void NotSamePassword() throws InterruptedException
	{
		Thread.sleep(sleep);
		user.setText("TestAccount");
		Thread.sleep(sleep);
		pass.setText("testPass");
		Thread.sleep(sleep);
		verify.setText("testpass");
		Thread.sleep(sleep);
		button.doClick(sleep);
		
		assertTrue("Check for non-matching passwords", cav.getError().equals("The password entries do not match!"));
	}
	
	@Test
	public void ShortPassword() throws InterruptedException
	{
		Thread.sleep(sleep);
		user.setText("TestAccount");
		Thread.sleep(sleep);
		pass.setText("test");
		Thread.sleep(sleep);
		verify.setText("test");
		Thread.sleep(sleep);
		button.doClick(500);
		
		assertTrue("Check for too short of password", cav.getError().equals("The password must be atleast six characters"));
	}
	
	@Test
	public void EmptyField1() throws InterruptedException
	{
		pass.setText("testPass");
		Thread.sleep(sleep);
		verify.setText("testPass");
		Thread.sleep(sleep);
		button.doClick(sleep);
		
		assertTrue("Check for empty field", cav.getError().equals("You must enter in a username and both entries of password"));
	}
	
	@Test
	public void EmptyField2() throws InterruptedException
	{
		
		
		Thread.sleep(sleep);
		user.setText("TestAccount");
		Thread.sleep(sleep);
		pass.setText("testPass");
		Thread.sleep(sleep);
		button.doClick(sleep);
		
		assertTrue("Check for empty field", cav.getError().equals("You must enter in a username and both entries of password"));
	}
	
	@Test
	public void EmptyField3() throws InterruptedException
	{
		Thread.sleep(sleep);
		user.setText("TestAccount");
		Thread.sleep(sleep);
		verify.setText("testPass");
		Thread.sleep(sleep);
		button.doClick(sleep);
		
		assertTrue("Check for empty field", cav.getError().equals("You must enter in a username and both entries of password"));
	}

	//Will fail if account already exists
	@Test
	public void AccountCreation1() throws InterruptedException
	{
		Thread.sleep(sleep);
		user.setText("TestAccount");
		Thread.sleep(sleep);
		pass.setText("testPass");
		Thread.sleep(sleep);
		verify.setText("testPass");
		Thread.sleep(sleep);
		button.doClick(sleep);
		JPanel card = null;
		for(Component comp : container.getComponents())
		{
			//Have Main thread wait for task
			Thread.sleep(100);
			if (comp.isVisible() == true)
			{
				
				card = (JPanel) comp;
				break;
			}
		}
		
		if(card.getName()==("LoginView"))
		{
			JButton back = lv.getCancelButton();
			Thread.sleep(sleep);
			back.doClick(sleep);
			back = iv.getCreateButton();
			back.doClick(sleep);
			
			//Test passes
			return;
		}
		else
			fail();
	}
	
	@Test
	public void AccountCreation2() throws InterruptedException
	{
		Thread.sleep(sleep);
		user.setText("TestAccount");
		Thread.sleep(sleep);
		pass.setText("testPass");
		Thread.sleep(sleep);
		verify.setText("testPass");
		Thread.sleep(sleep);
		button.doClick(sleep);
		
		//Have Main thread wait for task
		Thread.sleep(100);
		if(cav.getError()=="Account already exists")
		{
			return;
		}
		else
			fail();
	}
	
	//Delete the TestAccount on the database
	@AfterClass
	public static void cleanUp() throws IOException, InterruptedException
	{
		DeletePlayerModel dpl = new DeletePlayerModel("TestAccount");
		gc.sendToServer(dpl);
		Thread.sleep(1000);
	}
}
