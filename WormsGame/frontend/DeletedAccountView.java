package frontend;
//Name:Barton

import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.*;

import controller.DeletedAccountControl;

public class DeletedAccountView extends JPanel 
{
	
	private JLabel deletedMessage;
	private JButton backToHomeBtn;
	private JButton closeWindowBtn;

	public DeletedAccountView(DeletedAccountControl dac) 
	{
		deletedMessage = new JLabel("You have deleted your account");
		backToHomeBtn = new JButton("Back to Home");
		closeWindowBtn = new JButton("Close Window");
		
		//create buffers for the elements
		JPanel messageBuffer = new JPanel();
		messageBuffer.add(deletedMessage);
		JPanel backToHomeBtnBuffer = new JPanel();
		backToHomeBtnBuffer.add(backToHomeBtn);
		JPanel closeWindowBtnBuffer = new JPanel();
		closeWindowBtnBuffer.add(closeWindowBtn);
		
		//add actionlistener for the buttons
		backToHomeBtn.addActionListener(dac);
		closeWindowBtn.addActionListener(dac);
		
		//set up the view for the buttons
		JPanel grid = new JPanel(new GridLayout(3,1,5,5));
		grid.add(deletedMessage);
		grid.add(backToHomeBtn);
		grid.add(closeWindowBtn);
		this.add(grid);	
	}
}
