//Joseph Ticer
//Lab5out 
//Dr. Smith
//Software Engineering TR 2:40
package frontend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import backend.GameServer;
import controller.ConnectHandler;
import controller.StopHandler;


public class ServerGUI extends JFrame
{
	/**
	 * Data fields to declare primary buttons, labels, text fields and text areas at compilation
	 */
	private static final long serialVersionUID = 1L;
	//Data Fields go here
	private JLabel status; //Initialized to “Not Connected”
	private JLabel westStatus;
	private String[] labels = {"Port #", "Timeout"};
	private JTextField[] textFields = new JTextField[labels.length];
	private JTextArea log;
	private JButton listen;
	private JButton close;
	private JButton stop;
	private JButton quit;
	//Instatiation ChatServer object
	private GameServer server;


	//ClientGUI constructor that builds the client GUI
	public ServerGUI(String title) {
		//Variable used to count iterations in for loops
		int i = 0;
		//Sets the title of the client side GUI window using parameter args[0]
		this.setTitle(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//ADD YOUR CODE HERE TO CREATE THE STATUS JLABEL AND THE JBUTTONS
		//Creating and adding buffer panel north to JFrame
		JPanel holder = new JPanel();
		JPanel north = new JPanel(new FlowLayout());
		this.add(north, BorderLayout.NORTH);
		westStatus = new JLabel("Status:");
		status = new JLabel("Not Connected");
		status.setForeground(Color.red);
		north.add(westStatus);
		north.add(status);

		//Creating and adding buffer panel south to JFrame
		JPanel south = new JPanel(new FlowLayout());
		this.add(south, BorderLayout.SOUTH);
		listen = new JButton("Listen");
		close = new JButton("Close");
		stop = new JButton("Stop");
		quit = new JButton("Quit");

		//Adding each button to the southern panel
		south.add(listen);
		south.add(close);
		south.add(stop);
		south.add(quit);
		
		//Creating center JPanel that holds all text fields, text areas and their respective labels
		JPanel center = new JPanel();
		BoxLayout layout = new BoxLayout(center, BoxLayout.Y_AXIS);
		center.setLayout(layout);
		//Creating a buffer panel for text fields and their labels
		JPanel panelOne = new JPanel(new FlowLayout());
		for(i = 0; i< labels.length; i++) {
			JLabel label = new JLabel(labels[i]);	
			//label.setHorizontalAlignment(JLabel.CENTER);
			center.add(panelOne);
			if(labels[i].equals("Port #")) {
				textFields[i]=new JTextField(10);
			}
			else {
				textFields[i]=new JTextField(10);
			}
			//Adding each label and its textfield to buffered panel
			panelOne.add(label);
			panelOne.add(textFields[i]);
		}
        /*Creating second buffered panel containing server label 
         * and server log text area*/
		JPanel panelTwo = new JPanel(new GridLayout(2,1,0,-60));
		JLabel serverLabel = new JLabel("Server Log Below");
		serverLabel.setHorizontalAlignment(JLabel.CENTER);
		center.add(panelTwo);

		log = new JTextArea(10,30);
		JScrollPane scrollPane = new JScrollPane(log);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panelTwo.add(serverLabel, BorderLayout.SOUTH);
		panelTwo.add(scrollPane);

		//Creating Respective Handler and ChatServer objects
		server = new GameServer();
		ConnectHandler listener1 = new ConnectHandler(textFields[0], textFields[1], log, server);
		StopHandler listener2 = new StopHandler(textFields[0], textFields[1], log, server);
		
		//Attaching the listeners to each button
		listen.addActionListener(listener1);
		listen.addActionListener(listener2);
		stop.addActionListener(listener2);
		close.addActionListener(listener2);
		quit.addActionListener(e -> this.dispose());
		quit.addActionListener(e -> {
			try {
				server.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		//adding buffer panel to main holder panel
		holder.add(center);
		//adding holder panel to Jframe
		this.add(holder);
		//Set Panel Size and Make Visible
		setSize(425,450);
		//this.pack();
		setVisible(true);
	}

	//Main take command line argument as parameter
	public static void main(String[] args)
	{
		new ServerGUI("Server Window"); //args[0] represents the title of the GUI

	}

}


