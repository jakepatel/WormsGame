package entities;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.Timer;

import javax.swing.ImageIcon;

public class Weapon implements ActionListener {
	ArrayList<Image> annimationImages = new ArrayList<Image>();
	ArrayList<Image> destructionImages = new ArrayList<Image>();
	Image currentImage;
	boolean launchDirectionRight, visible, isDestroyed = false,
			hasHitEnemyDirectly = false; // true if it is launched to the
	// right and false to the left
	int x, y; // x and y coordinate for drawing the image on the screen
	private int imageWidth, imageHeight; // width and height of the image of the
											// object
	public Rectangle rectangle;
	public Timer timer;
	private Player player;
	ArrayList<StaticObjects> staticobjects; // just pointers to the two
											// arraylists that are created
	ArrayList<ReactiveObjects> reactiveobjects; // in class MainClass
	ArrayList<Player> enemies;
	public int[][] trajectoryIncrements; // used to store the trajectory
	public int trajectoryIndex; // this represents the index in
	private int expIndex; // trajectoryIncrements
	
	public double velocity;
	public double angle;
	public int timeToExplode = 0;
	
	RoundCollision collisionCircle = new RoundCollision(this.x+(this.imageWidth/2), this.y+(this.imageHeight/2), 10);

	public Weapon(Player p, ArrayList<Player> enemies, ArrayList<StaticObjects> s, ArrayList<ReactiveObjects> r, boolean l, double velocity, double angle) {
		trajectoryIncrements = calculateTrajectory(velocity, angle);
		visible = true;
		staticobjects = s;
		reactiveobjects = r;
		this.enemies = enemies;
		this.velocity = velocity;
		this.angle = angle;

		loadAnnimationImages();
		loadDestructionImages();
		this.player = p;
		/*
		this.player.setGrenadesAvailable(player.getGrenadesAvailable() - 1);		
		if (l == true) {
			x = p.x + p.playerImage.getWidth(null);
			y = p.y;
		} else {
			x = p.x;
			y = p.y;
		}
		*/
		launchDirectionRight = l;
		rectangle = new Rectangle(x, y, currentImage.getWidth(null),
				currentImage.getHeight(null));
		imageHeight = currentImage.getHeight(null);
		imageWidth = currentImage.getWidth(null);
		timer = new Timer(20, this);
		timer.start();
	}
	
	public int[][] calculateTrajectory(double speed, double angle) {
		double vertSpeed = speed * Math.sin(angle);
		double t = 0.005; // timer updates every 0.005 therefore must be
							// calculated at those intrvals
		double horrSpeed = t * speed * Math.cos(angle);

		int[][] xyPos = new int[1000][2]; // calculates x and y for 1000
											// intervals ([i][0] = x [i][0] = y

		xyPos[0][1] = 0;
		xyPos[0][0] = 0;

		for (int i = 1; i < 1000; i++) {

			double s = vertSpeed * t + 0.5 * -600 * i * t * i * t; // slower
																	// gravity
																	// set from
																	// real
																	// -980m/s2
																	// to
																	// -200m/s2

			xyPos[i][1] = (int) (s) - xyPos[i - 1][1];
			xyPos[i][0] = (int) (horrSpeed);
			
		}

		return xyPos;
	}
	
	public void loadAnnimationImages() {

		for (int i = 1; i <= 16; i++) {
			ImageIcon tempImageIcon = new ImageIcon("images/grenade/grenade"
					+ i + ".png");
			annimationImages.add(tempImageIcon.getImage());
		}

		currentImage = annimationImages.get(0);

	}
	
	public void loadDestructionImages() {
		for (int i = 0; i < 18; i++) {
			ImageIcon tempImageIcon = new ImageIcon("images/grenade/explosion"
					+ i + ".png");
			destructionImages.add(tempImageIcon.getImage());
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (isDestroyed == false)
			if (checkCollisionStatic() == false) {
				if (checkCollisionReactive() == false) {
					if (checkCollisionEnemy() == false) {
						if (trajectoryIndex < trajectoryIncrements.length) {
							move();
							updateRectangle();
							currentImage = annimationImages
									.get(trajectoryIndex % 16);
							trajectoryIndex++;
							timeToExplode++;
						} else
							destroy();
					} else
						destroy();
				} else
					destroy();
			} else
				destroy();
		if (isDestroyed == true) {
			if (expIndex < annimationImages.size())
				explosion();
			else {
				timer.stop();
				visible = false;
			}

		}
		
	}
	
	public void destroy() { // applies the explosion images and loops through
		destroyRadius();
		isDestroyed = true;
		timer.stop();
		this.x -= 50;
		this.y -= 50;
		timer.setDelay(100);
		timer.start();
	}
	
	public void move() {
		int[][] xyPos = trajectoryIncrements;
		int index = trajectoryIndex;

		if (launchDirectionRight == true) {
			x += xyPos[index][0];
			y -= xyPos[index][1];
		}
		if (launchDirectionRight == false) {
			x -= xyPos[index][0];
			y -= xyPos[index][1];
		}

	}
	
	public void explosion() {
		if (expIndex == 0) { // plays once a random explosion
			Random rand = new Random();
			/*
			int r = rand.nextInt(2);
			if (r == 0) {
				SoundEffect.EXPLODE1.play(); // invokes the sound effect only
												// once
			} else if (r == 1) {
				SoundEffect.EXPLODE2.play(); // invokes the sound effect only
												// once
			} else if (r == 2) {
				SoundEffect.EXPLODE3.play(); // invokes the sound effect only
												// once
			}
			*/
		}

		currentImage = destructionImages.get(expIndex);
		expIndex++;
		updateRectangle();
		this.checkCollisionEnemy();
	}
	
	public void updateRectangle() {
		this.rectangle = new Rectangle(this.x, this.y,
				currentImage.getWidth(null), currentImage.getHeight(null));
		
		collisionCircle = new RoundCollision(this.x, this.y, currentImage.getWidth(null));
	}
	public boolean checkCollisionStatic() {
		if (y > 575)
			return true;
		for (int i = 0; i < staticobjects.size(); i++)
			if (collisionCircle.intersects(staticobjects.get(i).rectangle)) {				
				return true;
			}
		return false;
	}
	
	public boolean checkCollisionReactive() {
		for (int i = 0; i < reactiveobjects.size(); i=i+10) {	
						
			if (collisionCircle.intersects(reactiveobjects.get(i).rectangle)) {	
				reactiveobjects.get(i).destroy(player);
				return true;
			}
		}
		return false;

	}
	
	public boolean checkCollisionEnemy() {
		for(int i=0; i<4; i++){
			enemies.get(i);
			if (rectangle.intersects(enemies.get(i).CollisionRectangle)) {
				if (this.isDestroyed == false) {
					enemies.get(i).getsHit(20);
					this.hasHitEnemyDirectly = true;
				//} else if (this.hasHitEnemyDirectly == false){
				//	enemy.getsHit(15);					
				}
				return true;
			}
		}
		return false;
	}
	public void destroyRadius() {

		 
		Circle circle = new Circle(x,y,50);
		for (int i = 0; i < reactiveobjects.size(); i++) {
			if (circle.intersects(reactiveobjects.get(i).rectangle)) {
				reactiveobjects.get(i).destroy(player);								
			}
		}
		//damages players
		for(int i=0; i<4; i++){
			if(collisionCircle.intersects(enemies.get(i).CollisionRectangle)){
				enemies.get(i).getsHit(15);
			}
		}
		if(collisionCircle.intersects(player.CollisionRectangle)){
			player.getsHit(100);
		}
		//damages players
		
}
}
