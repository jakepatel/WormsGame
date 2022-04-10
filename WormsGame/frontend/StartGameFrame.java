package frontend;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;


public class StartGameFrame extends JPanel {

	JButton btnNewButton;
	JTextArea player1Area;
	JTextArea player2Area;
	public StartGameFrame() 
	{
		
		setLayout(null);
		
		
		
		JLabel lblNewLabel = new JLabel("Player 1 Name");
		lblNewLabel.setBounds(180, 305, 108, 14);
		add(lblNewLabel);
		
		JLabel lblPlayerName = new JLabel("Player 2 Name");
		lblPlayerName.setBounds(438, 305, 108, 14);
		add(lblPlayerName);
		
	    player1Area = new JTextArea();
		player1Area.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		player1Area.setBounds(180, 343, 108, 28);
		add(player1Area);
		
	    player2Area = new JTextArea();
		player2Area.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		player2Area.setBounds(438, 343, 108, 28);
		add(player2Area);
		
	    btnNewButton = new JButton("Start Game");
		btnNewButton.setBounds(319, 208, 120, 23);
		add(btnNewButton);

	}
	
	public void setPlayer1Name(String name)
	{
		this.player1Area.setText(name);
	}
	
	public void setPlayer2Name(String name)
	{
		this.player2Area.setText(name);
	}
	
}
