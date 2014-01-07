package engine.main.entities.weapons;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;

public class Bullet {

	private Image image;
	private Circle shape;
	private double velocity;
	private double bulletAngle;
	private double firingAngle;
	private float x;
	private float y;
	private int damage;
	
	private boolean alive;
	private int timer = 0;
	private int aliveTime = 100;
	
	/**
	 * 
	 * @param image
	 * @param x
	 * @param y
	 * @param damage
	 */
	public Bullet( Image image, float x, float y, double bulletAngle, double firingAngle, int damage ) {
		
		this.image = image;
		this.x = x;
		this.y = y;
		this.damage = damage;
		this.firingAngle = firingAngle;
		this.bulletAngle = bulletAngle;
		
		this.alive = true;
		
		this.shape = new Circle( this.x, this.y, (int)(this.image.getWidth() / 2.0) );
		this.velocity = 30.0;
		
	}
	
	
	/**
	 * 
	 * @param window
	 * @param g
	 */
	public void render( GameContainer window, Graphics g ) {
		
		/* DEBUG */
		g.drawImage(this.image, this.x-8, this.y-8);
		
	}
	
	public void update( GameContainer window, int dt ) {
		
		if ( this.timer < this.aliveTime) {
			this.x += Math.sin( Math.toRadians(this.firingAngle)-Math.PI/2 )*this.velocity;
			this.y -= Math.cos( Math.toRadians(this.firingAngle)-Math.PI/2 )*this.velocity;
			
			this.timer++;
			
			this.image.setRotation((float)this.bulletAngle);
			this.shape.setCenterX(this.x);
			this.shape.setCenterY(this.y);
		}
		else {
			this.alive = false;
		}
		
	}
	
	/**
	 * 
	 * @return
	 */
	public float x() {
		return this.x;
	}
	
	/**
	 * 
	 * @return
	 */
	public float y() {
		return this.y;
	}
	
	/**
	 * 
	 * @return
	 */
	public int damage() {
		return this.damage;
	}
	
	/**
	 * 
	 * @return
	 */
	public Circle shape() {
		return this.shape;
	}
	
	/**
	 * 
	 * @return
	 */
	public Image image() {
		return this.image;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean alive() {
		return this.alive;
	}
	
	public void remove() throws SlickException {
		this.shape = null;
		this.image.destroy();
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 */
	public void setPosition( float x, float y ) {
		this.x = x;
		this.y = y;
		
		this.shape.setCenterX(x);
		this.shape.setCenterY(y);
		
	}
}
