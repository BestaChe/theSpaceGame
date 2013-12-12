package engine.main.entities;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

public class Player {
	
	private float x;
	private float y;
	private double rotation;
	private double velocity;
	private double acceleration;
	private Rectangle shape;
	private Image image;
	private SpriteSheet sSheet;
	
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
		this.velocity = 0.4;
		this.acceleration = 0.1;
		
		this.sSheet = new SpriteSheet("gfx/sprite_ships_small.png", 32, 32);
		this.image = sSheet.getSubImage(0,0);
		this.image.setRotation( (float)rotation );
		this.shape = new Rectangle(x-16, y-16, y+16, y+16);
		
		
	}
	/**
	 * Update event
	 * @param window - GameContainer window
	 * @param dt - int delta time
	 */
	public void update( GameContainer window, int dt ) {
		
		// controls bitch
		this.controls(window, dt);
		this.image.setRotation( (float)rotation );
		
	}
	
	/**
	 * Render event
	 * @param window - GameContainer window
	 * @param g - Graphics
	 */
	public void render( GameContainer window, Graphics g ) {
		
		// draws the player
		g.drawImage( this.image, this.x-image.getWidth()/2, this.y-image.getHeight()/2 );
	}
	
	/**
	 * Mouse and Keyboard control method. (Link with void update() method )
	 * @param window - GameContainer window
	 * @param dt - int delta time
	 */
	public void controls( GameContainer window, int dt ) {
		
		this.rotation = Math.toDegrees(Math.atan2(this.y - window.getInput().getMouseY(), this.x - window.getInput().getMouseX()));
		
		if ( window.getInput().isKeyPressed(Input.KEY_W) || window.getInput().isKeyDown(Input.KEY_W)) {
			
			this.velocity += acceleration * dt;
			this.x += Math.sin( Math.toRadians(this.rotation)-Math.PI/2 )*velocity;
			this.y -= Math.cos( Math.toRadians(this.rotation)-Math.PI/2 )*velocity;
		}
		this.velocity = 0.4;
		
		
	}
}
