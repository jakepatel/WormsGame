package tests;

import static org.junit.Assert.*;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.junit.Before;
import org.junit.Test;

import ClientComm.GameClient;
import controller.*;
import frontend.*;

public class LoginViewTest extends JFrame{

	private static LoginView lv;
	private static LoginControl lc;
	private static GameClient gc;
	private static JPanel container;
	private static InitialView iv;
	private static InitialControl ic;
	
	// Private data fields for the important GUI components.
	  private static JTextField user;
	  private static JPasswordField pass;
	  private static JLabel er;
	  
	  //private field for buttons for their JButton getters
	  private static JButton submit;
	  private static JButton cancel;
	  
	  private static boolean setUpDone = false;
	  
	  int sleep = 500;
	  
	  @Before
	  public void setUp() {
		  if (!setUpDone) {
		  //set up Client, container for loginView
			gc = new GameClient();
			CardLayout cardLayout = new CardLayout();
			container = new JPanel(cardLayout);
			lc = new LoginControl(container, gc);
			lv = new LoginView(lc);
			
			//use of initialview and loginview is to get back to createaccountview in specific
			ic = new InitialControl(container);
			
			
			iv = new InitialView(ic);
			iv.setName("InitialView");
			
			lv.setName("LoginView");
			
			container.add(iv, "InitialView");
	    	container.add(lv, "LoginView");
	    	
	    	gc.setLoginControl(lc);
	    	
	    	this.add(container, BorderLayout.CENTER);
			gc.setContainer(container);
			cardLayout.show(container, "InitialView");
			this.setSize(550,350);
			this.setVisible(true);
			
			JButton login = iv.getLoginButton();
			login.doClick(1000);
		  }
	    	user = lv.getUsernameField();
	    	pass = lv.getPasswordField();
	    	submit = lv.getSubmitButton();
	    	cancel = lv.getCancelButton();
	    	
	    	//clear textfield
	    	user.setText("");
	    	//clear passwordFeild
	    	pass.setText("");
	    	
	    	setUpDone = true;
	  }
	  
	@Test
	public void fieldLogin() throws InterruptedException
	{
		user.setText("karaze255");
		Thread.sleep(sleep);
		pass.setText("library256");
		Thread.sleep(sleep);
		submit.doClick(500);
		Thread.sleep(sleep);
		
		assertTrue("Check for incorrect login", lv.getError().equals("Login Failed"));
	
	}
	
	@Test
	public void EmptyFieldUser() throws InterruptedException
	{
		
		Thread.sleep(sleep);
		pass.setText("library256");
		Thread.sleep(sleep);
		submit.doClick(500);
		Thread.sleep(sleep);
		
		assertTrue("Check for incorrect empty user", lv.getError().equals("You must enter a username and password."));
	
	}
	
	@Test
	public void EmptyFieldpass() throws InterruptedException
	{
		user.setText("ckaraze");
		Thread.sleep(sleep);
		submit.doClick(500);
		Thread.sleep(sleep);
		
		assertTrue("Check for incorrect empty password", lv.getError().equals("You must enter a username and password."));
	
	}
	
	@Test
	public void EmptyFields() throws InterruptedException
	{
		
		submit.doClick(500);
		Thread.sleep(sleep);
		
		assertTrue("Check for incorrect empty data entry", lv.getError().equals("You must enter a username and password."));
	
	}
	
	

}
