package engine.main.entities.buildings;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Circle;

import engine.main.entities.NPC;
import engine.main.entities.Player;

public class Colony extends Building {
	
	private int capacity;
	private int currentPopulation;
	private Image image;
	private SpriteSheet spriteSheet;
	private Circle shape;
	private boolean hasEnergy;
	private int timer;
	private int interval;
	
	
	public Colony(Player owner, float x, float y, int health, int level ) throws SlickException {
		super(owner, x, y, health, level);
		
		this.capacity = 1500*this.level();	// 
		this.currentPopulation = 0;
		
		this.hasEnergy = false;
		
		this.interval = 200;
		
		this.spriteSheet = new SpriteSheet( "gfx/buildings/colony.png", 64, 64 );
		this.image = this.spriteSheet.getSubImage( this.level()-1, 0 );
		this.shape = new Circle( this.x(), this.y(), 32 );
		
		this.timer = 0;
		
	}
	
	public Colony(NPC owner, float x, float y, int health, int level) {
		super(owner, x, y, health, level);
	}
	
	
	public void init() {
		this.owner().addMaxPopulation(this.capacity);
	}
	
	public void update( GameContainer window, int dt ) {
		
		if ( this.hasEnergy ) {
			if ( this.timer < this.interval ) {
				this.timer++;
			}
			else {
				this.addPopulation(1*this.level());
				this.timer = 0;
			}
		}

	}
	
	public void render( GameContainer window, Graphics g ) {
		
		if ( this.hasEnergy ) {
			g.drawImage(this.image, this.x()-(this.image.getWidth()/2.0f), this.y()-(this.image.getHeight()/2.0f));
		}
		else
			g.drawImage(this.image, this.x()-(this.image.getWidth()/2.0f), this.y()-(this.image.getHeight()/2.0f), new Color( 175, 125, 125, 255 ) );
	}
	
	public void remove() {
		
		this.owner().addMaxPopulation(-this.capacity);
		this.owner().addPopulation(-this.currentPopulation);
		
	}
	
	private void addPopulation( int amount ) {
		
		if( this.currentPopulation + amount <= this.capacity ) {
			this.currentPopulation += amount;
			
		}
		else
			this.currentPopulation = this.capacity;
		
		this.owner().addPopulation(amount);
		
	}
	
	public void setEnergy( boolean en ) {
		this.hasEnergy = en;
	}
	
	public boolean hasEnergy() {
		return this.hasEnergy;
	}
}
