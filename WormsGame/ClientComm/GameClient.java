package ClientComm;
//Jake

import java.awt.CardLayout;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JPanel;

import controller.CreateAccountControl;
import controller.GameControl;
import controller.LeaderboardControl;
import controller.LoginControl;
import controller.MainMenuControl;
import entities.StartGameGranted;
import frontend.GameFrame;
import frontend.GameGUI;
import frontend.GameView;
import frontend.TestFrame;
import ocsf.client.*;

public class GameClient extends AbstractClient{
	// Private data fields for storing the GUI controllers.
	private LoginControl loginControl;
	private CreateAccountControl createAccountControl;
	private LeaderboardControl leaderboardControl;
	
	
	
	//Game related
	private JPanel container;
	private GameControl gameController;
	private GameView gameView;
	private GameFrame gameFrame;
	private GameGUI UIFrame;
	private String player1, player2;
	
	//test game
	private TestFrame testFrame;


	// Setters for the GUI controllers.
	public void setLoginControl(LoginControl loginControl)
	{
		this.loginControl = loginControl;
	}
	public void setCreateAccountControl(CreateAccountControl createAccountControl)
	{
		this.createAccountControl = createAccountControl;
	}
	public void setLeaderboardControl(LeaderboardControl leaderboardControl)
	{
		this.leaderboardControl = leaderboardControl;
	}
	
	
	
	//game related method
	public void setContainer(JPanel container)
	{
		this.container = container;
	}
	
	private  void showView(String viewName) 
	{
		if(container != null)
		{
		  CardLayout layout = (CardLayout)container.getLayout();
		  layout.show(container, viewName);
		}
		else
			System.out.print("Container is not set");
	}
	
	public GameControl getGameController() {
		return gameController;
	}

	public void setGameController(GameControl gameController) {
		this.gameController = gameController;
	}

	public GameView getGameView() {
		return gameView;
	}

	public void setGameView(GameView gameView) {
		this.gameView = gameView;
	}
	
	public String getPlayer1() {
		return player1;
	}

	public void setPlayer1(String player1) {
		this.player1 = player1;
	}

	public String getPlayer2() {
		return player2;
	}

	public void setPlayer2(String player2) {
		this.player2 = player2;
	}
	
	public void setUIFrame(GameGUI frame)
	{
		this.UIFrame = frame;
	}
	
	public GameFrame getGameFrame(GameFrame frame)
	{
		return gameFrame;
	}
	
	//end of game related method
	
	//start of test game methods
	
	public TestFrame getTestFrame() {
		return testFrame;
	}
	public void setTestFrame(TestFrame testFrame) {
		this.testFrame = testFrame;
	}
	
	
	//end of test game methods
	
	
	
	public GameClient()
	{
		super("localhost", 8300);
		try {
			this.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void handleMessageFromServer(Object arg0) {
		// TODO Auto-generated method stub
		if(arg0 instanceof String) 
		{
			System.out.println((String)arg0);
			String msg = (String)arg0;
			if(msg.equals("account created")) {
				createAccountControl.createAccountSuccess();
				//Handle Login Here
			}
			if(msg.equals("login successful")) {
				loginControl.loginSuccess();
				//Handle Login Here
			}
			if(msg.contains(",")) {
				
				String[] token = msg.split(",");
				leaderboardControl.showLeaderboard(token);
				//Handle Login Here
			}
		}
		else if(arg0 instanceof StartGameGranted)
		{
			//hide the user interface frame
			UIFrame.setVisible(false);
			
			//TypeCast
			StartGameGranted info = (StartGameGranted)arg0;
			player1 = info.getPlayer1();
			player2 = info.getPlayer2();
			
	
			
			//game starts because game request is processed by server
			gameFrame = new GameFrame(this, "Game Window");
			gameFrame.setPlayer1Name(info.getPlayer1());
			gameFrame.setPlayer2Name(info.getPlayer2());
			
			


			
			
			
			/*
			this.setGameView(info.getView());	//set the view for this client
			this.container.add(this.gameView);	//add the view to the container
			this.setGameController(info.getControl());		//set the controller for this client
			this.gameController.setGameView(container);     //pass the container to the controller
			*/
			
			
		}
		

	}
	public void connectionException (Throwable exception) 
	{
		//Add your code here
	}
	public void connectionEstablished()
	{
		//Add your code here
		System.out.println("Connection Established");
	}

	//Dummy Main
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

}
