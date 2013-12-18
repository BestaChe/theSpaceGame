package engine.main.entities;

import java.util.Random;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

public class NPC {
	
	private String name;
	private float x;
	private float y;
	private double rotation;
	private double velocity;
	private double acceleration;
	private Rectangle shape;
	private Image image;
	private SpriteSheet sSheet;
	private int health;
	
	private boolean patrolling;
	private boolean currentlyPatrolling;
	private boolean enemy;
	
	private double randomRotation;
	private int timer;
	
	private Random rd = new Random();
	
	public NPC( String name, float x, float y, String path, int l, int c ) throws SlickException {
		
		this.name = name;
		this.x = x;
		this.y = y;
		
		sSheet = new SpriteSheet( path, 32, 32 );
		this.image = sSheet.getSubImage(l, c);
		this.shape = new Rectangle( x-16, y-16, x+16, y+16);
		
		this.health = 100;
		
		this.patrolling = true;
		this.currentlyPatrolling = false;
		this.enemy = false;
		
		this.rotation = 0.0;
		this.velocity = 0.0;
		this.acceleration = 0.05;
		
		this.randomRotation = (rd.nextInt(361)*1.0);
		
		this.timer = 0;
	}
	
	public String name() {
		return this.name;
	}
	
	public float x() {
		return this.x;
	}
	
	public float y() {
		return this.y;
	}
	
	public Image image() {
		return this.image;
	}
	
	public int health() {
		return this.health;
	}
	
	public double rotation() {
		return this.rotation;
	}
	
	public void think() {
		
		double maxVelocityPatrolling = 4.0;
		double maxVelocityChasing = 6.0;
		
		if ( this.patrolling && !this.enemy ) {
			
			if ( (int)this.rotation != (int)this.randomRotation ) {
				
				if ( this.rotation > 360.0 )
					this.rotation = 0.0;
				else {
					if ( this.rotation > this.randomRotation )
						this.rotation -= 1.0;
					else
						this.rotation += 1.0;
				}
				
				this.currentlyPatrolling = true;
			}
			else
				this.currentlyPatrolling = false;
			
			if ( !this.currentlyPatrolling ) {
				if ( this.timer < 300 ) {

					if ( this.velocity <= maxVelocityPatrolling )
						this.velocity += this.acceleration;

					this.x += Math.sin( Math.toRadians(this.rotation)-Math.PI/2 )*this.velocity;
					this.y -= Math.cos( Math.toRadians(this.rotation)-Math.PI/2 )*this.velocity;
					this.timer++;

				}
				else {
					if ( this.velocity > 0.0 )
						this.velocity -= this.acceleration;
					else {
						this.timer = 0;
						this.randomRotation = rd.nextInt(361)*1.0;
					}

					this.x += Math.sin( Math.toRadians(this.rotation)-Math.PI/2 )*this.velocity;
					this.y -= Math.cos( Math.toRadians(this.rotation)-Math.PI/2 )*this.velocity;
				}
			}
		}
		
		
		this.shape.setCenterX(this.x);
		this.shape.setCenterY(this.y);
	}
	
	
}
