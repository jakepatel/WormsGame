package frontend;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameWaitingView extends JPanel
{
	
	private JLabel waitingMessage;
	
	public GameWaitingView()
	{
		waitingMessage = new JLabel("Searching for another player, Please wait...");
		JPanel messagebuffer = new JPanel();
		messagebuffer.add(waitingMessage, BorderLayout.CENTER);
		
		this.add(messagebuffer, BorderLayout.CENTER);
		
	}
	
	

}
