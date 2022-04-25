package controller;
//Jake


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
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import ClientComm.GameClient;
import backend.GameModel;
import entities.Bullet;
import entities.GamePlayer;
import entities.Grenade;
import entities.Missile;
import entities.ReactiveObjects;
import entities.SoundEffect;
import entities.StaticObjects;
import frontend.GameView;

public class GameControl implements ActionListener, KeyListener, Serializable, MouseListener
{

	JPanel container;
	GameClient client;
	GameView game;
	int gameID;


	public GameControl(JPanel container, GameClient client)
	{
		this.container = container;
		this.client = client;

	}

	public void setGameView(JPanel container)
	{
		this.container = container;
		this.game = (GameView)container.getComponent(0);
		setGameID(game.getGameID());

		client.setGameView(game);
		client.setGameController(this);
	}

	public int getGameID()
	{
		return gameID;
	}

	public void setGameID(int id)

	{
		gameID = game.getGameID();
	}

	public GameView getGameView() throws NullPointerException
	{
		if(game != null)
			return game;
		else
			return null;

	}



	@Override
	public void mouseReleased(MouseEvent e) 
	{
		
		if(client.getNumberPlayer().equals("P1"))
		{//client is labeled as player 1
		
			if(game.playerTurn == 1 || game.playerTurn == 3 || game.playerTurn == 5 || game.playerTurn == 7)
			{
				//team1

				game.p = game.team1.get((game.playerTurn-1)/2);
				
				if(game.p.playerHealth != 0)
				{
				
					//send to server with message mouseReleased
					GameModel toSendGameData = new GameModel(game.getPlayer1Name(), game.getPlayer2Name(), game.gameID);
					toSendGameData.setSentBy(client.getClientPlayer());
					toSendGameData.setMethodCalled("mouseReleased");		//method called, name used by server
					toSendGameData.setMouseE(e);	//parameter used by server
			
			
			
					try {
						client.sendToServer(toSendGameData);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else
				{
					//set the view again
					CardLayout card = (CardLayout)container.getLayout();
					card.show(container, "GameView");
				}
					
			}
			

		}
		else if(client.getNumberPlayer().equals("P2"))
		{//client is labeled as player 2

			if(game.playerTurn == 2 || game.playerTurn == 4 || game.playerTurn == 6 || game.playerTurn == 8)
			{
				//team2

				game.p = game.team2.get((game.playerTurn-1)/2);
				
				if(game.p.playerHealth != 0)
				{
					//send to server with message mouseReleased
					GameModel toSendGameData = new GameModel(game.getPlayer1Name(), game.getPlayer2Name(), game.gameID);
					toSendGameData.setSentBy(client.getClientPlayer());
					toSendGameData.setMethodCalled("mouseReleased");		//method called, name used by server
					toSendGameData.setMouseE(e);	//parameter used by server
			
			
			
					try {
						client.sendToServer(toSendGameData);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else
				{

					//set the view again
					CardLayout card = (CardLayout)container.getLayout();
					card.show(container, "GameView");
				}
			}
		}
		else
		{

			//set the view again
			CardLayout card = (CardLayout)container.getLayout();
			card.show(container, "GameView");
		}
		 

		/*
		int mousecode = e.getButton();
		if (game.weaponsUsedInTurn < game.MaxWeaponsPerTurn)
			if (game.playerTurn%2 ==1)
				if (game.team1.get(0).getGrenadesAvailable() > 0) 
				{
					game.mouseXY[0] = e.getX() - game.team1.get((game.playerTurn-1)/2).getX();// gets x of mouse
															// and takes away
															// player x
					game.mouseXY[1] = game.team1.get((game.playerTurn-1)/2).getY() - e.getY();// gets -y of mouse
															// and adds
					// player y

					if (mousecode == MouseEvent.BUTTON1) 
					{
						fire(game.clickVelocity); // fires weapon
						game.fired = false;						 
						game.clickVelocity = 0; // resets click velocity
						game.weaponsUsedInTurn++;
					}
				}
		if (game.weaponsUsedInTurn < game.MaxWeaponsPerTurn)
			if (game.playerTurn%2 == 0)
				if (game.team1.get(0).getGrenadesAvailable() > 0) 
				{
					game.mouseXY[0] = e.getX() - game.team2.get((game.playerTurn-1)/2).getX();// gets x of mouse
															// and takes away
															// player x
					game.mouseXY[1] = game.team2.get((game.playerTurn-1)/2).getY() - e.getY();// gets -y of mouse
															// and adds
					// player y

					if (mousecode == MouseEvent.BUTTON1) 
					{
						fire(game.clickVelocity); // fires weapon
						game.fired = false; // ends the log
						game.clickVelocity = 0; // resets click velocity
						game.weaponsUsedInTurn++;
					}
				}

		 */



	}

	@Override
	public void mousePressed(MouseEvent e) 
	{		

		if(client.getNumberPlayer().equals("P1"))
		{//client is labeled as player 1
		
			if(game.playerTurn == 1 || game.playerTurn == 3 || game.playerTurn == 5 || game.playerTurn == 7)
			{//team1
				
				game.p = game.team1.get((game.playerTurn-1)/2);
				
				if(game.p.playerHealth != 0)
				{
					//send to server with message mousePressed
					GameModel toSendGameData = new GameModel(game.getPlayer1Name(), game.getPlayer2Name(), game.gameID);
					//toSendGameData.setViewOfGame(game);
					//toSendGameData.setGameController(this);
					toSendGameData.setSentBy(client.getClientPlayer());
					toSendGameData.setMethodCalled("mousePressed");		//method called, name used by server
					toSendGameData.setMouseE(e);	//parameter used by server
	
	
	
	
					try {
						client.sendToServer(toSendGameData);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else
				{
					//set the view again
					CardLayout card = (CardLayout)container.getLayout();
					card.show(container, "GameView");
				}
					
			}
			

		}
		else if(client.getNumberPlayer().equals("P2"))
		{//client is labeled as player 2

			if(game.playerTurn == 2 || game.playerTurn == 4 || game.playerTurn == 6 || game.playerTurn == 8)
			{//team2

				game.p = game.team2.get((game.playerTurn-1)/2);
				
				if(game.p.playerHealth != 0)
				{
	
					//send to server with message mousePressed
					GameModel toSendGameData = new GameModel(game.getPlayer1Name(), game.getPlayer2Name(), game.gameID);
					//toSendGameData.setViewOfGame(game);
					//toSendGameData.setGameController(this);
					toSendGameData.setSentBy(client.getClientPlayer());
					toSendGameData.setMethodCalled("mousePressed");		//method called, name used by server
					toSendGameData.setMouseE(e);	//parameter used by server
	
	
	
	
					try {
						client.sendToServer(toSendGameData);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else
				{
					//set the view again
					CardLayout card = (CardLayout)container.getLayout();
					card.show(container, "GameView");
				}
			}
		}
		else
		{

			//set the view again
			CardLayout card = (CardLayout)container.getLayout();
			card.show(container, "GameView");
		}
		 
		

		/*
		//the actual implementation of the method
		game.fired = true;
		game.mouseXY[0] = e.getX() - game.team1.get((game.playerTurn-1)/2).getX();// gets x of mouse
		// and takes away
		// player x
		game.mouseXY[1] = game.team1.get((game.playerTurn-1)/2).getY() - e.getY();// gets -y of mouse
		// and adds
		// player y


		//set the view
		CardLayout card = (CardLayout)container.getLayout();
		card.show(container, "GameView");
		 */
	}




	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

		//set the view
		CardLayout card = (CardLayout)container.getLayout();
		card.show(container, "GameView");

	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

		//set the view
		CardLayout card = (CardLayout)container.getLayout();
		card.show(container, "GameView");

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

		//set the view
		CardLayout card = (CardLayout)container.getLayout();
		card.show(container, "GameView");

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

		//set the view
		CardLayout card = (CardLayout)container.getLayout();
		card.show(container, "GameView");

	}

	@Override
	public void keyPressed(KeyEvent e) 
	{ // fires automatically when a key is

		if(client.getNumberPlayer().equals("P1"))
		{//client is labeled as player 1
		
			if((game.playerTurn == 1 || game.playerTurn == 3 || game.playerTurn == 5 || game.playerTurn == 7))
			{//team1
				
				game.p = game.team1.get((game.playerTurn-1)/2);
				
				if(game.p.playerHealth != 0)
				{
					//send to server with message keyPressed
					GameModel toSendGameData = new GameModel(game.getPlayer1Name(), game.getPlayer2Name(), game.gameID);
					toSendGameData.setSentBy(client.getClientPlayer());
					toSendGameData.setMethodCalled("keyPressed");		//method called, name used by server
					toSendGameData.setKeyCode(e.getKeyCode());	//parameter used by server
					toSendGameData.setPlayerTurn(game.getPlayerTurn());	
					//send to client
					try {
						client.sendToServer(toSendGameData);
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				}
				else
				{
					//set the view
					CardLayout card = (CardLayout)container.getLayout();
					card.show(container, "GameView");
					
				}
			}
		

		}
		else if(client.getNumberPlayer().equals("P2"))
		{//client is labeled as player 2

			if((game.playerTurn == 2 || game.playerTurn == 4 || game.playerTurn == 6 || game.playerTurn == 8))
			{//team2
				
				game.p = game.team2.get((game.playerTurn-1)/2);
				
				if(game.p.playerHealth != 0)
				{
					//send to server with message keyPressed
					GameModel toSendGameData = new GameModel(game.getPlayer1Name(), game.getPlayer2Name(), game.gameID);
					toSendGameData.setSentBy(client.getClientPlayer());
					toSendGameData.setMethodCalled("keyPressed");		//method called, name used by server
					toSendGameData.setKeyCode(e.getKeyCode());	//parameter used by server
					toSendGameData.setPlayerTurn(game.getPlayerTurn());	
					//send to client
					try {
						client.sendToServer(toSendGameData);
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				}
				else
				{
					//set the view
					CardLayout card = (CardLayout)container.getLayout();
					card.show(container, "GameView");
				}
			}
			
		}
		else 
		{

			//set the view
			CardLayout card = (CardLayout)container.getLayout();
			card.show(container, "GameView");
			
		}
		/*
		if(game.move == true)
		{
			int keycode = e.getKeyCode();
			if (game.pressedKeys.contains(keycode) == false) 
			{
				game.pressedKeys.add(keycode);
			}

			if (game.playerTurn == 1 | game.playerTurn == 3 | game.playerTurn == 5 | game.playerTurn == 7) 
			{
				game.p = game.team1.get((game.playerTurn-1)/2);

				if (game.pressedKeys.contains(KeyEvent.VK_DOWN)) {
					changeWeapon(0);
				}
				if (game.pressedKeys.contains(KeyEvent.VK_UP)) {
					playerJump();
				}
				if (game.pressedKeys.contains(KeyEvent.VK_RIGHT))
					game.p.moveRight(3);
				if (game.pressedKeys.contains(KeyEvent.VK_LEFT))
					game.p.moveLeft(3);
				if (game.pressedKeys.contains(KeyEvent.VK_SPACE)) 
				{
					//weaponLaunch();
				}

			} 

			else 
			{
				game.p = game.team2.get((game.playerTurn-1)/2);

				if (game.pressedKeys.contains(KeyEvent.VK_DOWN)) {
					changeWeapon(1);
				}
				if (game.pressedKeys.contains(KeyEvent.VK_UP)) {
					playerJump();
				}
				if (game.pressedKeys.contains(KeyEvent.VK_RIGHT))
					game.p.moveRight(3);
				if (game.pressedKeys.contains(KeyEvent.VK_LEFT))
					game.p.moveLeft(3);
				if (game.pressedKeys.contains(KeyEvent.VK_SPACE)) 
				{
					//weaponLaunch();
				}
			}
			game.move = false;
		}

		//set the view
		CardLayout card = (CardLayout)container.getLayout();
		card.show(container, "GameView");

		 */
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{
		// TODO Auto-generated method stub
		// fires automatically when a key is
		// released
		if(client.getNumberPlayer().equals("P1"))
		{//client is labeled as player 1
		
			if((game.playerTurn == 1 || game.playerTurn == 3 || game.playerTurn == 5 || game.playerTurn == 7))
			{//team1
				
				game.p = game.team1.get((game.playerTurn-1)/2);
				
				if(game.p.playerHealth != 0)
				{
					//send to server with message keyReleased
					GameModel toSendGameData = new GameModel(game.getPlayer1Name(), game.getPlayer2Name(), game.gameID);
					toSendGameData.setSentBy(client.getClientPlayer());
					toSendGameData.setMethodCalled("keyReleased");		//method called, name used by server
					toSendGameData.setKeyCode(e.getKeyCode());	//parameter used by server
					toSendGameData.setPlayerTurn(game.getPlayerTurn());	
	
					//send to client
					try {
						client.sendToServer(toSendGameData);
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				}
				else
				{
					
				}
			}
		

		}
		else if(client.getNumberPlayer().equals("P2"))
		{//client is labeled as player 2

			if((game.playerTurn == 2 || game.playerTurn == 4 || game.playerTurn == 6 || game.playerTurn == 8))
			{//team2
				
				game.p = game.team2.get((game.playerTurn-1)/2);
				
				if(game.p.playerHealth != 0)
				{
					//send to server with message keyReleased
					GameModel toSendGameData = new GameModel(game.getPlayer1Name(), game.getPlayer2Name(), game.gameID);
					toSendGameData.setSentBy(client.getClientPlayer());
					toSendGameData.setMethodCalled("keyReleased");		//method called, name used by server
					toSendGameData.setKeyCode(e.getKeyCode());	//parameter used by server
					toSendGameData.setPlayerTurn(game.getPlayerTurn());	
	
					//send to client
					try {
						client.sendToServer(toSendGameData);
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				}
				else
				{
					//set the view
					CardLayout card = (CardLayout)container.getLayout();
					card.show(container, "GameView");
				}
			}
			
		}
		else 
		{

			//set the view
			CardLayout card = (CardLayout)container.getLayout();
			card.show(container, "GameView");
			
		}





		/*
		int index;
		index = game.pressedKeys.indexOf(e.getKeyCode());
		if (index != -1)
		game.pressedKeys.remove(index);*/




	}







	@Override
	public void actionPerformed(ActionEvent e) 
	{ // fires automatically when an


		if(game.moving%6 == 0) //slows movement
		{
			game.move = true;
		}
		game.moving++;
		// timer activates it
		// drop in box at end of turn



		/*if(game.timeLeftInTurn==0)
		{
			game.boxPos = -200;				
			game.box.make();
			game.drop=true;
			game.actionSwitcher = false;
		}*/
		if(game.timeLeftInTurn==28 && game.actionSwitcher==false)
		{
			SoundEffect.SHOTGUNRELOAD.play();
			game.actionSwitcher=true;
		}
		/*if(game.drop==true)
		{
			if(game.boxPos==-200)
			{
				Random rand = new Random();
				game.randX=rand.nextInt(600);
			}
			game.boxPos++;
			dropBox(game.boxPos, game.randX);
		}*/
		// drop in box at end of turn

		invisibleObjectCleaner(game.staticobjects, game.reactiveobjects, game.missiles,
				game.grenades, game.bullets);
		updateGameVariables();
		game.repaint();
		if (game.fired) 
		{
			game.clickVelocity += 20; // adds increments to the velocity every event
			// when log timer is active
		} 
		else 
		{
			game.clickVelocity = 0;
		}
		if(game.clickVelocity> 5000)
		{
			game.clickVelocity -= 20;
		}

		//set the view
		CardLayout card = (CardLayout)container.getLayout();
		card.show(container, "GameView");

	}

	public void dropBox(int boxPos, int randX) 
	{		


		int frame = 0;
		int condition = (boxPos + 200) %32;

		if(condition==0){frame = 1;}
		else if(condition==4){frame = 2;}
		else if(condition==8){frame = 3;}
		else if(condition==12){frame = 4;}
		else if(condition==16){frame = 5;}
		else if(condition==20){frame = 4;}
		else if(condition==24){frame = 3;}
		else if(condition==28){frame = 2;}			


		game.box.setImage(frame);
		game.box.setX(randX);
		game.box.setY(boxPos);
		game.box.updateRectangle();

	}

	public void updateGameVariables() 
	{



		game.team1.get(0).updateSystemVariables(game.staticobjects, game.reactiveobjects, game.team2);
		if (game.team2.get(0) != null)
			game.team2.get(0).updateSystemVariables(game.staticobjects, game.reactiveobjects,game.team1);
		if (game.missiles.isEmpty() == false)
			for (int i = 0; i < game.missiles.size(); i++)
				game.missiles.get(i).updateSystemVariables(game.staticobjects,game.reactiveobjects);
		if (game.grenades.isEmpty() == false)
			for (int i = 0; i < game.grenades.size(); i++)
				game.grenades.get(i).updateSystemVariables(game.staticobjects,game.reactiveobjects);
		for (int i = 0; i < game.bullets.size(); i++)
			game.bullets.get(i).updateSystemVariables(game.staticobjects, game.reactiveobjects);		
	}

	public void changeWeapon(int player) 
	{


		Image temp = (new ImageIcon("images/weapons/missile0.png").getImage());
		int tempW = 0;
		if (player == 0) 
		{
			tempW = game.player1Weapon;
		} else if (player == 1) 
		{
			tempW = game.player2Weapon;
		}

		tempW = (tempW + 1) % 3;
		switch (tempW) 
		{
		case 1:
			temp = (new ImageIcon("images/weapons/missile0.png").getImage());
			break;
		case 0:
			temp = (new ImageIcon("images/grenade/grenade0.png").getImage());
			break;
		case 2:
			temp = (new ImageIcon("images/weapons/bullet-right.png").getImage());
			break;
		}
		if (player == 0) 
		{
			this.game.player1WeaponImage = temp;
			game.player1Weapon = tempW;
		} 
		else 
		{
			this.game.player2WeaponImage = temp;
			game.player2Weapon = tempW;
		}

	}

	public void playerJump() 
	{



		if (game.p.injump == false && game.p.checkCollisionDown(game.staticobjects, game.reactiveobjects) == true)
			game.p.startJump(0);
		else if (game.p.injump == true && game.p.jumpSpeed > 0 && game.p.jumpSpeed < game.p.jumpSpeedBound - 5) 
		{
			game.p.startJump(1);
		}


	}



	public void invisibleObjectCleaner(ArrayList<StaticObjects> s, ArrayList<ReactiveObjects> r, ArrayList<Missile> m, ArrayList<Grenade> g, ArrayList<Bullet> b) 
	{


		if (s.isEmpty() == false)
			for (int i = 0; i < s.size(); i++)
				if (s.get(i).visible == false) 
				{
					s.remove(i);
					updateGameVariables();
				}
		if (r.isEmpty() == false)
			for (int i = 0; i < r.size(); i++)
				if (r.get(i).visible == false) 
				{
					r.remove(i);
					updateGameVariables();
				}
		if (m.isEmpty() == false)
			for (int i = 0; i < m.size(); i++)
				if (m.get(i).visible == false) 
				{
					m.remove(i);
					updateGameVariables();
				}
		if (g.isEmpty() == false)
			for (int i = 0; i < g.size(); i++)
				if (g.get(i).visible == false) 
				{
					g.remove(i);
					updateGameVariables();
				}
		if (b.isEmpty() == false)
			for (int i = 0; i < b.size(); i++)
				if (b.get(i).visible == false) 
				{
					b.remove(i);
					updateGameVariables();
				}



	}


	public void fire(double velocity) 
	{



		SoundEffect.COUGH.play();

		double mx = game.mouseXY[0];
		double my = game.mouseXY[1] * Math.PI;
		double angleR = Math.atan(my / mx); // calcs angle
		double angleL = Math.atan(my / -mx); // calcs -angle

		if (game.playerTurn == 1 | game.playerTurn == 3 | game.playerTurn == 5 | game.playerTurn == 7) 
		{
			if (game.weaponsUsedInTurn < game.MaxWeaponsPerTurn)
				if (game.team1.get((game.playerTurn-1)/2).directionRight == true) 
				{
					if (game.player1Weapon == 1 && game.team1.get((game.playerTurn-1)/2).getMissilesAvailable() > 0)
						game.missiles.add(new Missile(game.team1.get((game.playerTurn-1)/2), game.team2, game.staticobjects, game.reactiveobjects, true, game.clickVelocity + 10, angleR));
					if (game.player1Weapon == 2)
						game.bullets.add(new Bullet(game.team1.get((game.playerTurn-1)/2), game.team2.get((game.playerTurn-1)/2), game.staticobjects, game.reactiveobjects, true));
					if (game.player1Weapon == 0)
					{
						game.grenades.add(new Grenade(game.team1.get((game.playerTurn-1)/2), game.team2, game.staticobjects, game.reactiveobjects, true, game.clickVelocity, angleR));
					}						
					game.weaponsUsedInTurn++;
				} 
				else 
				{
					if (game.player1Weapon == 1 && game.team1.get((game.playerTurn-1)/2).getMissilesAvailable() > 0)
						game.missiles.add(new Missile(game.team1.get((game.playerTurn-1)/2), game.team2, game.staticobjects, game.reactiveobjects, false, game.clickVelocity + 10, angleL));
					if (game.player1Weapon == 2)
						game.bullets.add(new Bullet(game.team1.get((game.playerTurn-1)/2), game.team2.get((game.playerTurn-1)/2), game.staticobjects, game.reactiveobjects, false));
					if (game.player1Weapon == 0)
					{
						game.grenades.add(new Grenade(game.team1.get((game.playerTurn-1)/2), game.team2, game.staticobjects, game.reactiveobjects, false, game.clickVelocity, angleL));
					}
					game.weaponsUsedInTurn++;
				}


		} 
		else if (game.playerTurn == 2 | game.playerTurn == 4 | game.playerTurn == 6 | game.playerTurn == 8) 
		{
			if (game.weaponsUsedInTurn < game.MaxWeaponsPerTurn)
				if (game.team2.get((game.playerTurn-1)/2).directionRight == true) 
				{
					if (game.player2Weapon == 1 && game.team2.get((game.playerTurn-1)/2).getMissilesAvailable() > 0)
						game.missiles.add(new Missile(game.team2.get((game.playerTurn-1)/2), game.team1, game.staticobjects, game.reactiveobjects, true, game.clickVelocity + 10, angleR));
					if (game.player2Weapon == 2)
						game.bullets.add(new Bullet(game.team2.get((game.playerTurn-1)/2), game.team1.get((game.playerTurn-1)/2), game.staticobjects, game.reactiveobjects, true));
					if (game.player2Weapon == 0)
					{
						game.grenades.add(new Grenade(game.team2.get((game.playerTurn-1)/2), game.team1, game.staticobjects, game.reactiveobjects, true, game.clickVelocity, angleR));
					}
					game.weaponsUsedInTurn++;
				} 
				else 
				{
					if (game.player2Weapon == 1)
						game.missiles.add(new Missile(game.team2.get((game.playerTurn-1)/2), game.team1, game.staticobjects, game.reactiveobjects, false, game.clickVelocity + 10, angleL));			
					if (game.player2Weapon == 2)
						game.bullets.add(new Bullet(game.team2.get((game.playerTurn-1)/2), game.team1.get((game.playerTurn-1)/2), game.staticobjects, game.reactiveobjects, false));
					if (game.player2Weapon == 0)
					{
						game.grenades.add(new Grenade(game.team2.get((game.playerTurn-1)/2), game.team1, game.staticobjects, game.reactiveobjects, false, game.clickVelocity, angleL));
					}
					game.weaponsUsedInTurn++;
				}

		}			


	}

	
	
	//game related methods --------------------------------------------------------------------
	
	public void mousePressedGranted(MouseEvent e)
	{
		//the actual implementation of the method
		game.fired = true;
		game.mouseXY[0] = e.getX() - game.team1.get((game.playerTurn-1)/2).getX();// gets x of mouse
				// and takes away
				// player x
		game.mouseXY[1] = game.team1.get((game.playerTurn-1)/2).getY() - e.getY();// gets -y of mouse
				// and adds
				// player y
		

		//set the view
		CardLayout card = (CardLayout)container.getLayout();
		card.show(container, "GameView");
		
		
	}
	
	
	//--------------
	
	public void mouseReleasedGranted(MouseEvent e) 
	{

		int mousecode = e.getButton();
		if (game.weaponsUsedInTurn < game.MaxWeaponsPerTurn)
			if (game.playerTurn%2 ==1)
				if (game.team1.get(0).getGrenadesAvailable() > 0) 
				{
					game.mouseXY[0] = e.getX() - game.team1.get((game.playerTurn-1)/2).getX();// gets x of mouse
															// and takes away
															// player x
					game.mouseXY[1] = game.team1.get((game.playerTurn-1)/2).getY() - e.getY();// gets -y of mouse
															// and adds
					// player y
		
					if (mousecode == MouseEvent.BUTTON1) 
					{
						this.fire(game.clickVelocity); // fires weapon
						game.fired = false;						 
						game.clickVelocity = 0; // resets click velocity
						game.weaponsUsedInTurn++;
					}
				}
		if (game.weaponsUsedInTurn < game.MaxWeaponsPerTurn)
			if (game.playerTurn%2 == 0)
				if (game.team1.get(0).getGrenadesAvailable() > 0) 
				{
					game.mouseXY[0] = e.getX() - game.team2.get((game.playerTurn-1)/2).getX();// gets x of mouse
															// and takes away
															// player x
					game.mouseXY[1] = game.team2.get((game.playerTurn-1)/2).getY() - e.getY();// gets -y of mouse
															// and adds
					// player y
		
					if (mousecode == MouseEvent.BUTTON1) 
					{
						this.fire(game.clickVelocity); // fires weapon
						game.fired = false; // ends the log
						game.clickVelocity = 0; // resets click velocity
						game.weaponsUsedInTurn++;
					}
				}

		//set the view
		CardLayout card = (CardLayout)container.getLayout();
		card.show(container, "GameView");
		
	}
	
	
	//-----------------------
	
	public void keyPressedGranted(GameModel data) 
	{ // fires automatically when a key is

		
		if(game.move == true )
		{
			int keycode = data.getKeyCode();
			if (game.pressedKeys.contains(keycode) == false) 
			{
				game.pressedKeys.add(keycode);
			}
		
			if (game.playerTurn == 1 | game.playerTurn == 3 | game.playerTurn == 5 | game.playerTurn == 7) 
			{
				game.p = game.team1.get((game.playerTurn-1)/2);
				
				if (game.pressedKeys.contains(KeyEvent.VK_DOWN)) {
					this.changeWeapon(0);
				}
				if (game.pressedKeys.contains(KeyEvent.VK_UP)) {
					this.playerJump();
				}
				if (game.pressedKeys.contains(KeyEvent.VK_RIGHT))
					game.p.moveRight(8);
				if (game.pressedKeys.contains(KeyEvent.VK_LEFT))
					game.p.moveLeft(8);
				if (game.pressedKeys.contains(KeyEvent.VK_SPACE)) 
				{
					//weaponLaunch();
				}
		
			} 
			
			else 
			{
				game.p = game.team2.get((game.playerTurn-1)/2);
				
				if (game.pressedKeys.contains(KeyEvent.VK_DOWN)) {
					this.changeWeapon(1);
				}
				if (game.pressedKeys.contains(KeyEvent.VK_UP)) {
					this.playerJump();
				}
				if (game.pressedKeys.contains(KeyEvent.VK_RIGHT))
					game.p.moveRight(9);
				if (game.pressedKeys.contains(KeyEvent.VK_LEFT))
					game.p.moveLeft(9);
				if (game.pressedKeys.contains(KeyEvent.VK_SPACE)) 
				{
					//weaponLaunch();
				}
			}
			game.move = false;
		}

		

		//set the view
		CardLayout card = (CardLayout)container.getLayout();
		card.show(container, "GameView");

		


	}
	
	//--------------
	
	public void keyReleasedGranted(GameModel data) 
	{
		// TODO Auto-generated method stub
		// fires automatically when a key is
		// released

		int index;
		index = game.pressedKeys.indexOf(data.getKeyCode());
		if (index != -1)
		game.pressedKeys.remove(index);
		

		//set the view
		CardLayout card = (CardLayout)container.getLayout();
		card.show(container, "GameView");

		
	}




}

