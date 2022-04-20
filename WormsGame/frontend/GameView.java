package frontend;
//Name: Jake


import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.Timer;

import backend.GameModel;

import controller.GameControl;
import entities.Bullet;
import entities.Grenade;
import entities.GamePlayer;
import entities.Maps;
import entities.Missile;
import entities.ReactiveObjects;
import entities.SoundEffect;
import entities.StaticObjects;





public class GameView extends JPanel implements Serializable
{
	Timer timer, changeTurns; // this is the object which is going to call the
	// function actionPerformed

	public ArrayList<GamePlayer> team1 = new ArrayList<GamePlayer>();
	public ArrayList<GamePlayer> team2 = new ArrayList<GamePlayer>();
	public KeyListener listener1;
	public Image backgroundImage, boardImage, player1WeaponImage, player2WeaponImage;
	public ArrayList<Integer> pressedKeys = new ArrayList<Integer>();
	public ArrayList<StaticObjects> staticobjects;
	public ArrayList<ReactiveObjects> reactiveobjects;
	public ArrayList<Missile> missiles;
	public ArrayList<Grenade> grenades;
	public ArrayList<Bullet> bullets;
	public double clickVelocity; // stores the velocity for weapon
	public int playerTurn = 1;
	public int[] mouseXY = new int[2]; // stores coordinates of mouse
	public int player1Weapon = 1, player2Weapon = 1;
	public int timeLeftInTurn = 30, weaponsUsedInTurn = 0, MaxWeaponsPerTurn = 1;

	public String[] board= {" "," "," "," "," "," "};
	public boolean fired = false;
	public boolean actionSwitcher = false;
	public String player1Name;
	public String player2Name;

	public GamePlayer p; 
	public boolean move = true;
	public int moving = 0;

	public int boxPos= -200;
	public int randX = 0;
	public boolean drop = false;
	public StaticObjects box = new StaticObjects(randX, boxPos, 0);

	GameControl controller;
	public int gameID = 0;		//will be set by the server

	public String numberPlayer;

	//helper functions









	//end helper functions

	public GameView(GameControl controller, String player1Name, String player2Name, String numberPlayer, Maps map) 
	{		
		this.controller = controller;


		this.player1Name = player1Name;
		this.player2Name = player2Name;
		this.numberPlayer = numberPlayer;

		this.setFocusable(true);
		ImageIcon tempImageIcon;
		tempImageIcon = new ImageIcon("images/background.png");
		backgroundImage = tempImageIcon.getImage();
		tempImageIcon = new ImageIcon("images/board.png");
		boardImage = tempImageIcon.getImage();
		staticobjects = new ArrayList<StaticObjects>();
		reactiveobjects = new ArrayList<ReactiveObjects>();
		missiles = new ArrayList<Missile>();
		grenades = new ArrayList<Grenade>();
		bullets = new ArrayList<Bullet>();

		int posX = 0, posY = 0, type = 0;

		for (int i = 0; i < map.objectTypeAtIndex.size(); i++) {
			posX = map.objectPositionsXAtIndex.get(i);
			posY = map.objectPositionsYAtIndex.get(i);
			type = map.objectTypeAtIndex.get(i);
			switch (type) {
			case 0:
				staticobjects.add(new StaticObjects(posX, posY, 0));
				break;
			case 1:
				staticobjects.add(new StaticObjects(posX, posY, 1));
				break;
			case 2:
				staticobjects.add(new StaticObjects(posX, posY, 2));
				break;
			case 3:
				reactiveobjects.add(new ReactiveObjects(posX, posY, 1));
				break;
			case 4:
				reactiveobjects.add(new ReactiveObjects(posX, posY, 2));
				break;
			case 5:
				reactiveobjects.add(new ReactiveObjects(posX, posY, 0));
				break;
			case 6:
				reactiveobjects.add(new ReactiveObjects(posX, posY, 3));
				break;
			case 7:
				reactiveobjects.add(new ReactiveObjects(posX, posY, 4));
				break;
			case 8:
				reactiveobjects.add(new ReactiveObjects(posX, posY, 5));
				break;
			case 9:
				reactiveobjects.add(new ReactiveObjects(posX, posY, 6));
				break;
			}
		}
		this.player1WeaponImage = (new ImageIcon("images/weapons/missile0.png")
				.getImage());
		this.player2WeaponImage = (new ImageIcon("images/weapons/missile0.png")
				.getImage());

		team1.add(new GamePlayer(50, 50, staticobjects, reactiveobjects,
				team2));
		team2.add(new GamePlayer(650, 100, staticobjects, reactiveobjects,
				team1));

		team1.add(new GamePlayer(500, 100, staticobjects, reactiveobjects,
				team2));
		team2.add(new GamePlayer(400, 100, staticobjects, reactiveobjects,
				team1));
		team1.add(new GamePlayer(300, 100, staticobjects, reactiveobjects,
				team2));
		team2.add(new GamePlayer(900, 100, staticobjects, reactiveobjects,
				team1));
		team1.add(new GamePlayer(150, 100, staticobjects, reactiveobjects,
				team2));
		team2.add(new GamePlayer(800, 50, staticobjects, reactiveobjects,
				team1));

		this.p = team1.get(0);

		this.addKeyListener(controller);
		this.addMouseListener(controller);
		timer = new Timer(30, controller);
		timer.addActionListener(controller);

		changeTurns = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) 
			{


				if (timeLeftInTurn == 0 ) 
				{
					fired = false;
					if(playerTurn==8)
					{
						playerTurn=1;
					}
					else
					{
						playerTurn++;
					}
					MaxWeaponsPerTurn = 1;
					weaponsUsedInTurn = 0;
					timeLeftInTurn = 30;
				} 
				else
					timeLeftInTurn--;
				if(timeLeftInTurn>0 && timeLeftInTurn<=10)
				{
					SoundEffect.TIMERTICK.play();
				}		

				//System.out.println(weaponsUsedInTurn);
				if(weaponsUsedInTurn > 1)
				{
					timeLeftInTurn = 5;
					weaponsUsedInTurn = 0;
					MaxWeaponsPerTurn = 0;

				}

				board = createResultBoard();


			}

		});




		playerTurn = 1;
		timer.start();
		changeTurns.start();
		this.setVisible(true);

	}

	//Getter/Setters Functions//------------------------------------------------------------
	public int getGameID()
	{
		return gameID;
	}

	public void setGameID(int id)
	{
		gameID = id;
	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

	public Timer getChangeTurns() {
		return changeTurns;
	}

	public void setChangeTurns(Timer changeTurns) {
		this.changeTurns = changeTurns;
	}

	public ArrayList<GamePlayer> getTeam1() {
		return team1;
	}

	public void setTeam1(ArrayList<GamePlayer> team1) {
		this.team1 = team1;
	}

	public ArrayList<GamePlayer> getTeam2() {
		return team2;
	}

	public void setTeam2(ArrayList<GamePlayer> team2) {
		this.team2 = team2;
	}

	public KeyListener getListener1() {
		return listener1;
	}

	public void setListener1(KeyListener listener1) {
		this.listener1 = listener1;
	}

	public Image getBackgroundImage() {
		return backgroundImage;
	}

	public void setBackgroundImage(Image backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	public Image getBoardImage() {
		return boardImage;
	}

	public void setBoardImage(Image boardImage) {
		this.boardImage = boardImage;
	}

	public Image getPlayer1WeaponImage() {
		return player1WeaponImage;
	}

	public void setPlayer1WeaponImage(Image player1WeaponImage) {
		this.player1WeaponImage = player1WeaponImage;
	}

	public Image getPlayer2WeaponImage() {
		return player2WeaponImage;
	}

	public void setPlayer2WeaponImage(Image player2WeaponImage) {
		this.player2WeaponImage = player2WeaponImage;
	}

	public ArrayList<Integer> getPressedKeys() {
		return pressedKeys;
	}

	public void setPressedKeys(ArrayList<Integer> pressedKeys) {
		this.pressedKeys = pressedKeys;
	}

	public ArrayList<StaticObjects> getStaticobjects() {
		return staticobjects;
	}

	public void setStaticobjects(ArrayList<StaticObjects> staticobjects) {
		this.staticobjects = staticobjects;
	}

	public ArrayList<ReactiveObjects> getReactiveobjects() {
		return reactiveobjects;
	}

	public void setReactiveobjects(ArrayList<ReactiveObjects> reactiveobjects) {
		this.reactiveobjects = reactiveobjects;
	}

	public ArrayList<Missile> getMissiles() {
		return missiles;
	}

	public void setMissiles(ArrayList<Missile> missiles) {
		this.missiles = missiles;
	}

	public ArrayList<Grenade> getGrenades() {
		return grenades;
	}

	public void setGrenades(ArrayList<Grenade> grenades) {
		this.grenades = grenades;
	}

	public ArrayList<Bullet> getBullets() {
		return bullets;
	}

	public void setBullets(ArrayList<Bullet> bullets) {
		this.bullets = bullets;
	}

	public double getClickVelocity() {
		return clickVelocity;
	}

	public void setClickVelocity(double clickVelocity) {
		this.clickVelocity = clickVelocity;
	}

	public int getPlayerTurn() {
		return playerTurn;
	}

	public void setPlayerTurn(int playerTurn) {
		this.playerTurn = playerTurn;
	}

	public int[] getMouseXY() {
		return mouseXY;
	}

	public void setMouseXY(int[] mouseXY) {
		this.mouseXY = mouseXY;
	}

	public int getPlayer1Weapon() {
		return player1Weapon;
	}

	public void setPlayer1Weapon(int player1Weapon) {
		this.player1Weapon = player1Weapon;
	}

	public int getPlayer2Weapon() {
		return player2Weapon;
	}

	public void setPlayer2Weapon(int player2Weapon) {
		this.player2Weapon = player2Weapon;
	}

	public int getTimeLeftInTurn() {
		return timeLeftInTurn;
	}

	public void setTimeLeftInTurn(int timeLeftInTurn) {
		this.timeLeftInTurn = timeLeftInTurn;
	}

	public int getWeaponsUsedInTurn() {
		return weaponsUsedInTurn;
	}

	public void setWeaponsUsedInTurn(int weaponsUsedInTurn) {
		this.weaponsUsedInTurn = weaponsUsedInTurn;
	}

	public int getMaxWeaponsPerTurn() {
		return MaxWeaponsPerTurn;
	}

	public void setMaxWeaponsPerTurn(int maxWeaponsPerTurn) {
		MaxWeaponsPerTurn = maxWeaponsPerTurn;
	}

	public String[] getBoard() {
		return board;
	}

	public void setBoard(String[] board) {
		this.board = board;
	}

	public boolean isFired() {
		return fired;
	}

	public void setFired(boolean fired) {
		this.fired = fired;
	}

	public boolean isActionSwitcher() {
		return actionSwitcher;
	}

	public void setActionSwitcher(boolean actionSwitcher) {
		this.actionSwitcher = actionSwitcher;
	}

	public String getPlayer1Name() {
		return player1Name;
	}

	public void setPlayer1Name(String player1Name) {
		this.player1Name = player1Name;
	}

	public String getPlayer2Name() {
		return player2Name;
	}

	public void setPlayer2Name(String player2Name) {
		this.player2Name = player2Name;
	}

	public GamePlayer getP() {
		return p;
	}

	public void setP(GamePlayer p) {
		this.p = p;
	}

	public boolean getMove() {
		return move;
	}

	public void setMove(boolean move) {
		this.move = move;
	}

	public int getMoving() {
		return moving;
	}

	public void setMoving(int moving) {
		this.moving = moving;
	}

	public int getBoxPos() {
		return boxPos;
	}

	public void setBoxPos(int boxPos) {
		this.boxPos = boxPos;
	}

	public int getRandX() {
		return randX;
	}

	public void setRandX(int randX) {
		this.randX = randX;
	}

	public boolean isDrop() {
		return drop;
	}

	public void setDrop(boolean drop) {
		this.drop = drop;
	}

	public StaticObjects getBox() {
		return box;
	}

	public void setBox(StaticObjects box) {
		this.box = box;
	}

	//End of Getters/Setters-------------------------------------------------------------------

	public String[] createResultBoard() {
		String[] board = new String[this.board.length];


		int playerhealth1 = team1.get(0).playerHealth + team1.get(1).playerHealth + team1.get(2).playerHealth + team1.get(3).playerHealth;
		board[0] = String.valueOf(playerhealth1);

		if (player1Weapon == 0)
			board[1] = String.valueOf(team1.get(0).getGrenadesAvailable());
		if (player1Weapon == 1)
			board[1] = String.valueOf(team1.get(0).getMissilesAvailable());

		board[2] = "Time: " + String.valueOf(timeLeftInTurn);


		int playerhealth2 = team2.get(0).playerHealth + team2.get(1).playerHealth + team2.get(2).playerHealth + team2.get(3).playerHealth;
		board[3] = String.valueOf(playerhealth2);

		if (player2Weapon == 0)
			board[4] = String.valueOf(team2.get(0).getGrenadesAvailable());
		if (player2Weapon == 1)
			board[4] = String.valueOf(team2.get(0).getMissilesAvailable());

		board[5] = this.numberPlayer;

		return board;
	}


	public void stopTimers() {
		this.timer.stop();
		this.changeTurns.stop();
	}

	public void startTimers() {
		this.timer.start();
		this.changeTurns.start();
	}



	public void paintComponent(Graphics g) 
	{
		renderScreen(g);
	}

	public void renderScreen(Graphics g) 
	{
		super.paintComponent(g);
		g.drawImage(backgroundImage, 0, 0, null);
		// create the result board
		g.setFont(new Font("standart", Font.BOLD, 20));
		g.setColor(Color.orange);
		g.drawImage(boardImage, 0, 0, null);

		for(int i = 0; i<board.length; i++) {
			if(this.board[i] != null) {
				if(i==0)
					g.drawString(board[i], 150, 25);
				if(i==1)
					g.drawString(board[i], 300, 25);
				if(i==2)
					g.drawString(board[i], 350, 25);	
				if(i==3)
					g.drawString(board[i], 585, 25);		
				if(i==4)
					g.drawString(board[i], 740, 25);
				if(i==5)
					g.drawString(board[i], 770, 25);
			}
		}

		g.setFont(new Font("dialog", Font.ROMAN_BASELINE, 12));

		int weapon;
		if(playerTurn%2==1){weapon = player1Weapon;}			
		else{weapon = player2Weapon;}

		if(weapon == 1){
			g.drawString("The MISSILE has High damage ", 810, 15);
			g.drawString("and explodes on inpact ", 810, 33);
		}else if(weapon == 2){
			g.drawString("The DIGGER cuts easily ", 810, 15);
			g.drawString("through terrain ", 810, 33);
		}else if(weapon == 0){
			g.drawString("The GRENADE bounces with ", 810, 15);
			g.drawString("High damage ", 810, 33);
		}
		g.drawImage(player1WeaponImage, 270, 5, null);
		g.drawImage(player2WeaponImage, 700, 5, null);
		// create the result board
		g.drawImage(team1.get(0).playerImage, team1.get(0).x, team1.get(0).y, null);
		g.drawImage(team2.get(0).playerImage, team2.get(0).x, team2.get(0).y, null);

		g.drawImage(team1.get(1).playerImage, team1.get(1).x, team1.get(1).y, null);
		g.drawImage(team2.get(1).playerImage, team2.get(1).x, team2.get(1).y, null);
		g.drawImage(team1.get(2).playerImage, team1.get(2).x, team1.get(2).y, null);
		g.drawImage(team2.get(2).playerImage, team2.get(2).x, team2.get(2).y, null);
		g.drawImage(team1.get(3).playerImage, team1.get(3).x, team1.get(3).y, null);
		g.drawImage(team2.get(3).playerImage, team2.get(3).x, team2.get(3).y, null);

		if (reactiveobjects.isEmpty() == false)
			for (int i = 0; i < reactiveobjects.size(); i++)
				if (reactiveobjects.get(i).visible == true)
					g.drawImage(reactiveobjects.get(i).currentImage,
							reactiveobjects.get(i).x, reactiveobjects.get(i).y,
							null);
		if (staticobjects.isEmpty() == false)
			for (int i = 0; i < staticobjects.size(); i++)
				if (staticobjects.get(i).visible == true)
					g.drawImage(staticobjects.get(i).objectImage,
							staticobjects.get(i).x, staticobjects.get(i).y,
							null);
		if (missiles.isEmpty() == false)
			for (int i = 0; i < missiles.size(); i++)
				if (missiles.get(i).visible == true)
					g.drawImage(missiles.get(i).currentImage,
							missiles.get(i).x, missiles.get(i).y, null);
		if (grenades.isEmpty() == false)
			for (int i = 0; i < grenades.size(); i++)
				if (grenades.get(i).visible == true)
					g.drawImage(grenades.get(i).currentImage,
							grenades.get(i).x, grenades.get(i).y, null);
		if (bullets.isEmpty() == false)
			for (int i = 0; i < bullets.size(); i++)
				if (bullets.get(i).visible == true)
					g.drawImage(bullets.get(i).currentImage, bullets.get(i).x,
							bullets.get(i).y, null);
		String temp = "";
		g.setFont(new Font("standart", Font.BOLD, 12));
		g.setColor(Color.RED);
		for(int i=0; i<4; i++){
			temp = "";
			temp += team1.get(i).playerHealth;
			g.drawString(temp, team1.get(i).getX()+10, team1.get(i).getY());
		}
		g.setColor(Color.BLUE);
		for(int i=0; i<4; i++){
			temp = "";
			temp += team2.get(i).playerHealth;
			g.drawString(temp, team2.get(i).getX()+10, team2.get(i).getY());
		}

		g.setFont(new Font("standart", Font.ROMAN_BASELINE, 50));


		if(playerTurn%2==1){
			g.setColor(Color.RED);							
		}
		if(playerTurn%2==1 & timeLeftInTurn>27){			
			g.drawString("Player 1", 400, 300);
		}else if(playerTurn%2==0 & timeLeftInTurn>27){
			g.drawString("Player 2", 400, 300);
		}
		g.fillPolygon(arrowX(), arrowY(), 8);		


		g.drawImage(box.objectImage,box.x,box.y,null);
		validate();


	}





	//start here
	public int[] arrowX(){
		int[] x = {20,40,40,60,30,0,20,20};
		if(playerTurn%2==1){			
			for(int i = 0; i<x.length; i++){
				x[i] = x[i] + team1.get((playerTurn-1)/2).x-10;
			}
		}else{
			for(int i = 0; i<x.length; i++){
				x[i] = x[i] + team2.get((playerTurn-1)/2).x-10;
			}
		}
		return x;
	}

	public int[] arrowY(){
		int[] y = {0,0,30,30,50,30,30,0};
		if(playerTurn%2==1){
			for(int i = 0; i<y.length; i++){
				y[i] = y[i] + team1.get((playerTurn-1)/2).y -65;
			}
		}else{
			for(int i = 0; i<y.length; i++){
				y[i] = y[i] + team2.get((playerTurn-1)/2).y -65;
			}
		}
		return y;
	}



}

