package engine.main.entities.buildings;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Circle;

import engine.main.Util;
import engine.main.entities.NPC;
import engine.main.entities.Player;

public class Reactor extends Building {

	
	private Image image;
	private SpriteSheet spriteSheet;
	private Circle shape;
	private int radius;
	private Circle radiusShape;
	
	
	public Reactor(Player owner, float x, float y, int health, int level ) throws SlickException {
		super(owner, x, y, health, level);
		
		this.radius = 256*this.level();
		this.radiusShape = new Circle( this.x(), this.y(), this.radius );
		
		this.spriteSheet = new SpriteSheet( "gfx/buildings/fusionreactor.png", 64, 64 );
		this.image = this.spriteSheet.getSubImage( this.level()-1, 0 );
		this.shape = new Circle( this.x(), this.y(), 32 );
		
		
	}
	
	public Reactor(NPC owner, float x, float y, int health, int level) {
		super(owner, x, y, health, level);
	}
	
	
	/**
	 * 
	 */
	public void init() {
		
	}
	
	/**
	 * 
	 * @param window
	 * @param dt
	 */
	public void update( GameContainer window, int dt ) {
		
		for( Colony c : this.owner().returnColonies() ) {
			
			if( !c.hasEnergy() ) {
				c.setEnergy(this.isWithinEnergyRadius(c.x(), c.y()));
			}
		}		
		
	}
	
	/**
	 * 
	 * @param window
	 * @param g
	 */
	public void render( GameContainer window, Graphics g ) {
		
		g.setColor(new Color( 100, 100, 255, 125 ) );
		g.draw(this.radiusShape);
		g.setColor(new Color( 100, 100, 255, 10 ) );
		g.fill(this.radiusShape);
		
		g.drawImage(this.image, this.x()-(this.image.getWidth()/2.0f), this.y()-(this.image.getHeight()/2.0f));
		
	}
	
	/**
	 * 
	 * @return
	 */
	public int energyRadius() {
		return this.radius;
	}
	
	/**
	 * 
	 * @return
	 */
	public Circle energyShapeRadius() {
		return this.radiusShape;
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isWithinEnergyRadius( float x, float y ) {
		return ( Util.dist(x, y, this.x(), this.y()) <= this.radius );
	}
	
	
}
