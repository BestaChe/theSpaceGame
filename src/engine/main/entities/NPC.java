package engine.main.entities;

import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

import engine.main.Util;
import engine.main.entities.weapons.Weapon;

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
	private int size;
	private int relationship;
	
	private Weapon weapon;
	
	private boolean patrolling;
	private boolean currentlyPatrolling;
	private boolean enemy;
	
	private double randomRotation;
	private int timer;
	
	private Random rd = new Random();
	
	/**
	 * 
	 * @param name
	 * @param x
	 * @param y
	 * @param path
	 * @param l
	 * @param c
	 * @param size
	 * @throws SlickException
	 */
	public NPC( String name, float x, float y, String path, int l, int c, int size, int relationship ) throws SlickException {
		
		this.name = name;
		this.x = x;
		this.y = y;
		
		this.relationship = relationship;
		
		sSheet = new SpriteSheet( path, 32*size, 32*size );
		this.image = sSheet.getSubImage(l, c);
		this.shape = new Rectangle( -(size*32), -(size*32), (size*32), (size*32));
		this.shape.setCenterX(this.x+((this.size*32)/2));
		this.shape.setCenterY(this.y+((this.size*32)/2));
		this.size = size;
		
		this.health = 100;
		
		this.patrolling = true;
		this.currentlyPatrolling = false;
		this.enemy = false;
		
		this.rotation = 0.0;
		this.velocity = 0.0;
		this.acceleration = 0.05;
		
		this.randomRotation = (rd.nextInt(361)*1.0);
		
		this.timer = 0;
		
		this.weapon = new Weapon( 2, 0.6, 1000 );
	}
	
	/**
	 * 
	 * @return
	 */
	public String name() {
		return this.name;
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
	public Image image() {
		return this.image;
	}
	
	/**
	 * 
	 * @return
	 */
	public int health() {
		return this.health;
	}
	
	/**
	 * 
	 * @param ammount
	 */
	public void harm( int ammount ) {
		
		if ( this.health-ammount > 0 )
			this.health -= ammount;
		else
			this.health = 0;
	}
	
	/**
	 * 
	 * @return
	 */
	public Rectangle shape() {
		return this.shape;
	}
	
	/**
	 * 
	 * @return
	 */
	public double rotation() {
		return this.rotation;
	}
	
	/**
	 * 
	 * @return
	 */
	public int relationship() {
		return this.relationship;
	}
	
	/**
	 * 
	 * @param a
	 */
	public void setRelationship( int a ) {
		this.relationship = a;
	}
	
	/**
	 * 
	 * @return
	 */
	public Weapon weapon() {
		return this.weapon;
	}
	
	/**
	 * NPC Thinking method...
	 * @throws SlickException 
	 */
	public void think(Player player) throws SlickException {
		
		double maxVelocityPatrolling = 4.0;
		double maxVelocityChasing = 6.0;
		
		/*
		 * Random movement
		 */
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
		//----------------------------------------
		
		/*
		 * Chasing / Running away
		 */
		if ( this.relationship < 0 ) {
			
			if ( this.health <= 30 ) {
				this.enemy = true;
			
				if ( Util.dist(player.x(), player.y(), this.x, this.y) < 1990.0 ) {
				
					this.rotation = Math.toDegrees( Math.atan2(player.y()-this.y, player.x()-this.x) );
				
					if ( this.velocity <= maxVelocityChasing )
						this.velocity += this.acceleration;
				
					this.x += Math.sin( Math.toRadians(this.rotation)-Math.PI/2 )*this.velocity;
					this.y -= Math.cos( Math.toRadians(this.rotation)-Math.PI/2 )*this.velocity;
				
					System.out.println("executing running away!!!");
				
				}
				else
					this.enemy = false;
			
			}
			else {

				if ( Util.dist(player.x(), player.y(), this.x, this.y) < 1990.0 ) {

					this.rotation = Math.toDegrees( Math.atan2((this.y+32)-((player.y()+16)), (this.x+32)-(player.x()+16) ) );

					if ( Util.dist(player.x(), player.y(), this.x, this.y) > 350.0 ) {
						
						if ( this.velocity <= maxVelocityChasing )
							this.velocity += this.acceleration;

						this.x -= Math.sin( Math.toRadians(this.rotation)+Math.PI/2 )*this.velocity;
						this.y += Math.cos( Math.toRadians(this.rotation)+Math.PI/2 )*this.velocity;

						this.weapon.fire(this.x+32, this.y+32, this.rotation, this.rotation);

						System.out.println("executing chasing!!!");
					}
					else {
						if ( this.velocity > 0.0 )
							this.velocity -= this.acceleration*2;
						else
							this.velocity = 0.0;

						this.x -= Math.sin( Math.toRadians(this.rotation)+Math.PI/2 )*this.velocity;
						this.y += Math.cos( Math.toRadians(this.rotation)+Math.PI/2 )*this.velocity;

						this.weapon.fire(this.x+32, this.y+32, this.rotation, this.rotation);

						System.out.println("executing chasing!!!");
					}

				}
				else
					this.enemy = false;
			}
		}
		else
			this.enemy = false;
		
		//-----------------------------------------------
		
		
		
		
		this.shape.setCenterX(this.x+((this.size*32)/2));
		this.shape.setCenterY(this.y+((this.size*32)/2));
	}
	
	public void update( GameContainer window, int dt) {
		this.weapon.update(window, dt);
	}
	
	public void render(GameContainer window, Graphics g) {
		this.weapon.render(window, g);
	}
}

