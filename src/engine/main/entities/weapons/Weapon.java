package engine.main.entities.weapons;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Weapon {
	
	private double firerate;
	private int damage;
	private int maxBullets;
	
	private ArrayList<Bullet> allBullets;
	
	private boolean firing;
	private int timer = 0;
	private boolean canFire;
	
	/**
	 * 
	 * @param damage
	 * @param firerate
	 * @param maxBullets
	 */
	public Weapon ( int damage, double firerate, int maxBullets ) {
		
		this.damage = damage;
		this.firerate = firerate*10;
		this.maxBullets = maxBullets;
		
		this.firing = false;
		this.canFire = true;
		
		this.allBullets = new ArrayList<Bullet>();
		
	}
	
	/**
	 * 
	 * @param damage
	 * @param maxBullets
	 */
	public Weapon ( int damage, int maxBullets ) {
		
		this.damage = damage;
		this.firerate = 0.5;
		this.maxBullets = maxBullets;
		
		this.firing = false;
		this.canFire = true;
		
		this.allBullets = new ArrayList<Bullet>();
		
	}
	
	/**
	 * 
	 * @param window
	 * @param g
	 */
	public void render( GameContainer window, Graphics g ) {
	
		for( Bullet b : this.allBullets ) {
			b.image().setImageColor(255, 255, 0, 255);
			b.render(window, g);
		}
	}
	
	/**
	 * 
	 * @param window
	 * @param dt
	 */
	public void update( GameContainer window, int dt ) {
		
		for( int i = 0; i <= this.allBullets.size()-1; i++ ) {
			Bullet b = this.allBullets.get(i);
			
			if ( b.alive() )
				b.update(window, dt);
			else
				this.allBullets.remove(i);
		}
		
		if( this.firing ) {
			this.timer++;
		}
		else
			this.timer = 0;
		
	}
	
	public void enableFiring() {
		this.canFire = true;
	}
	
	public void disableFiring() {
		this.canFire = false;
	}
	
	/**
	 * 
	 * @param initialX
	 * @param initialY
	 * @param entityAngle
	 * @param firingAngle
	 * @throws SlickException
	 */
	public void fire( float initialX, float initialY, double entityAngle, double firingAngle ) throws SlickException {
		
		if ( !this.firing ) {
			if ( this.allBullets.size() <= this.maxBullets ) {
			
				this.firing = true;
				Bullet b;
				
				if ( this.canFire ) {
						Image a = new Image("gfx/main/bullet.png");
						b = new Bullet( a, initialX, initialY, entityAngle, firingAngle, this.damage );
						this.allBullets.add(b);
						
				}
				
				this.canFire = false;
			}
			else
				this.firing = false;
		}
		else {
			if ( this.timer >= this.firerate ) {
				this.canFire = true;
				this.firing = false;
				this.timer = 0;
			}
		}
	}
	
	public ArrayList<Bullet> bullets() {
		return this.allBullets;
	}
	
	public void removeBullet( Bullet b ) throws SlickException {
		if ( this.allBullets.contains(b) ) {
			b.remove();
			this.allBullets.remove(b);
		}
	}

}
