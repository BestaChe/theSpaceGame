package engine.main.entities;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

import engine.main.Camera;
import engine.main.particles.ShipParticle;
import engine.main.world.World;

public class Player {
	
	private float x;
	private float y;
	private double rotation;
	private double velocity;
	private double acceleration;
	private Rectangle shape;
	private Image image;
	private SpriteSheet sSheet;
	private ShipParticle particle;
	
	private boolean controlPressed;
	
	public String worldDetails = "No Details";
	
	/**
	 * Constructs the player class
	 * @param x - float x position of the player
	 * @param y - float y position of the player
	 * @throws SlickException
	 */
	public Player( float x, float y ) throws SlickException {
		
		this.x = x;
		this.y = y;
		this.rotation = 0.0;
		
		this.velocity = 0.0;
		this.acceleration = 0.4;
		
		this.sSheet = new SpriteSheet("gfx/sprite_ships_small.png", 32, 32);
		this.image = sSheet.getSubImage(3,0);
		this.image.setRotation( (float)rotation );
		this.shape = new Rectangle(x-16, y-16, y+16, y+16);
		
		this.particle = new ShipParticle("gfx/particles/ship_particle_yellow.png");
		
		this.controlPressed = false;
		
		
	}
	/**
	 * Update event
	 * @param window - GameContainer window
	 * @param dt - int delta time
	 */
	public void update( GameContainer window, int dt, Camera cam, World world ) {
		
		this.image.setRotation( (float)this.rotation );
		
		ArrayList<Planet> allPlanets = world.returnPlanets();
		ArrayList<Star> allStars = world.returnStars();
		
		for( Star s : allStars ) {
			
			if ( s.shape().contains( this.x, this.y ) )
				worldDetails = s.details();
		}
		
		for( Planet p : allPlanets ) {
			
			if ( p.shape().contains( this.x, this.y ) )
				worldDetails = p.details();
		}
		
		//this.particle.update(dt);
		
		
		/**
		 * 
		 *  CONTROLS
		 * 
		 *  
		 */
		
		this.rotation = Math.toDegrees(Math.atan2((this.y-window.getInput().getMouseY())-cam.camY(),
				(this.x-window.getInput().getMouseX())-cam.camX()));
		
		if ( window.getInput().isKeyPressed(Input.KEY_W) || window.getInput().isKeyDown(Input.KEY_W)) {
			
			double maxVelocity = 8.0; 
			
			if ( window.getInput().isKeyPressed(Input.KEY_LSHIFT) || window.getInput().isKeyDown(Input.KEY_LSHIFT) ) {
				maxVelocity = 14.0;
			}
			else
				maxVelocity = 8.0;
			
			if ( this.velocity < maxVelocity )
				this.velocity += this.acceleration;
			
			this.x += Math.sin( Math.toRadians(this.rotation)-Math.PI/2 )*this.velocity;
			this.y -= Math.cos( Math.toRadians(this.rotation)-Math.PI/2 )*this.velocity;
			this.controlPressed = true;
		}
		
		else if( window.getInput().isKeyPressed(Input.KEY_S) || window.getInput().isKeyDown(Input.KEY_S)) {
			if ( this.velocity > 0.0 ) {
				this.velocity -= this.acceleration;
				this.x += Math.sin( Math.toRadians(this.rotation)-Math.PI/2 )*velocity;
				this.y -= Math.cos( Math.toRadians(this.rotation)-Math.PI/2 )*velocity;
			}
			else
				this.velocity = 0.0;
		}
		else
		{
			if ( this.velocity > 0.0 )
				this.velocity -= this.acceleration*0.1;
			
			this.x += Math.sin( Math.toRadians(this.rotation)-Math.PI/2 )*velocity;
			this.y -= Math.cos( Math.toRadians(this.rotation)-Math.PI/2 )*velocity;
			this.controlPressed = false;
		}
		
		this.shape.setCenterX(this.x);
		this.shape.setCenterY(this.y);
		
	}
	
	/**
	 * Render event
	 * @param window - GameContainer window
	 * @param g - Graphics
	 */
	public void render( GameContainer window, Graphics g ) {
		
		// particles
		//this.particle.render();
		// draws the player
		g.drawImage( this.image, this.x-image.getWidth()/2, this.y-image.getHeight()/2 );

	}
	
	/**
	 * Mouse and Keyboard control method. (Link with void update() method )
	 * @param window - GameContainer window
	 * @param dt - int delta time
	 */
	
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
	
	public String worldDetails() {
		return this.worldDetails;
	}
	
	
	
}
