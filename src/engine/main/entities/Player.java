package engine.main.entities;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

import engine.main.Camera;
import engine.main.Util;
import engine.main.entities.buildings.*;
import engine.main.entities.weapons.Weapon;
import engine.main.particles.ShipParticle;
import engine.main.world.SolarSystem;
import engine.main.world.World;

public class Player {
	
	/* Movement Related Stuff */
	private float x;
	private float y;
	private double rotation;
	private double oldRotation;
	private double velocity;
	private double acceleration;
	private boolean canGoForward;
	private boolean controlPressed;
	
	/* Mouse control stuff */
	private float zoom;
	
	/* Image & Shape Related Stuff */
	private Rectangle shape;
	private Image image;
	private SpriteSheet sSheet;
	private ShipParticle particle;
	
	/* GUI related stuff */
	private Image crosshair;
	private Image arrow;
	private double arrowRotation;
	private Image[] arrowPlanet;
	private double[] arrowPlanetRotation;
	
	/* Weapon & Bullet stuff */
	private Weapon weapon;
	
	/* Data Related Stuff */
	private String currentObject;
	private String currentSystem;
	
	/* Buildings related stuff */
	private int population;
	private int maxPopulation;
	
	private ArrayList<Colony> allColonies;
	private ArrayList<Reactor> allReactors;
	
	private boolean paused;
	
	
	
	
	/**
	 * Constructs the player class
	 * @param x - float x position of the player
	 * @param y - float y position of the player
	 * @throws SlickException
	 */
	public Player( float x, float y ) throws SlickException {
		
		// Movement
		this.x = x;
		this.y = y;
		this.rotation = 0.0;
		this.oldRotation = 0.0;
		this.velocity = 0.0;
		this.acceleration = 0.4;
		this.controlPressed = false;
		this.canGoForward = true;
		this.zoom = 1.0f;
		
		// Image & Shape
		this.sSheet = new SpriteSheet("gfx/ships/sprite_ships_small.png", 32, 32);
		this.image = sSheet.getSubImage(0,0);
		this.image.setRotation( (float)rotation );
		this.shape = new Rectangle(-32, -32, 32, 32);
		this.shape.setCenterX(this.x);
		this.shape.setCenterY(this.y);
		
		// Weapon
		this.weapon = new Weapon( 2, 0.3, 1000 );
		
		// Particles
		this.particle = new ShipParticle("gfx/particles/ship_particle_yellow.png");
		
		// Crosshair GUI
		this.crosshair = new Image("gfx/main/crosshair.png");
		this.arrow 	   = new Image("gfx/main/arrow.png");
		this.arrowRotation = 0.0;
		
		this.arrowPlanet = new Image[World.MAX_PLANETS_PER_STAR];
		this.arrowPlanetRotation = new double[World.MAX_PLANETS_PER_STAR];
		
		for( int i = 0; i <= World.MAX_PLANETS_PER_STAR - 1; i++ ) {
			this.arrowPlanet[i] = this.arrow.getScaledCopy(0.7f);
		}
		
		for( int i = 0; i <= World.MAX_PLANETS_PER_STAR - 1; i++ ) {
			this.arrowPlanetRotation[i] = -1.0;
		}
		
		this.currentSystem = "  Not in a system";
		
		// Building stuff
		
		this.population = 0;
		this.maxPopulation = 0;
		
		this.allColonies = new ArrayList<Colony>();
		this.allReactors = new ArrayList<Reactor>();
		
		// Paused
		this.paused = false;
		
		
	}
	/**
	 * Update event
	 * @param window - GameContainer window
	 * @param dt - int delta time
	 * @throws SlickException 
	 */
	public void update( GameContainer window, int dt, Camera cam, World world ) throws SlickException {
		
		this.image.setRotation( (float)this.rotation );
		
		//this.particle.update(dt);
		
		
		/**
		 * 
		 *  CONTROLS
		 * 
		 *  
		 */
		
		this.rotation = Math.toDegrees(Math.atan2((this.y-window.getInput().getMouseY())-cam.camY(),
				(this.x-window.getInput().getMouseX())-cam.camX()));
		/**
		 * KEYBOARD
		 */
		
		// W - Forward
		if ( window.getInput().isKeyPressed(Input.KEY_W) || window.getInput().isKeyDown(Input.KEY_W)) {

			double maxVelocity = 8.0; 

			if ( window.getInput().isKeyPressed(Input.KEY_LSHIFT) || window.getInput().isKeyDown(Input.KEY_LSHIFT) ) {
				maxVelocity = 14.0;
			}
			else
				maxVelocity = 8.0;

			if ( this.velocity < maxVelocity )
				this.velocity += this.acceleration;

			this.oldRotation = this.rotation;
			this.x += Math.sin( Math.toRadians(this.oldRotation)-Math.PI/2 )*this.velocity;
			this.y -= Math.cos( Math.toRadians(this.oldRotation)-Math.PI/2 )*this.velocity;
			
			this.controlPressed = true;
		}
		
		// S - Backwards
		else if( window.getInput().isKeyPressed(Input.KEY_S) || window.getInput().isKeyDown(Input.KEY_S)) {
			double maxVelocity = -6.5; 

			if ( this.velocity > maxVelocity)
				this.velocity -= this.acceleration;
			
			this.oldRotation = this.rotation;
			this.x += Math.sin( Math.toRadians(this.oldRotation)-Math.PI/2 )*this.velocity;
			this.y -= Math.cos( Math.toRadians(this.oldRotation)-Math.PI/2 )*this.velocity;
			
			this.controlPressed = true;
			
		}
		
		// Space - Stop moving
		else if ( window.getInput().isKeyPressed(Input.KEY_SPACE) || window.getInput().isKeyDown(Input.KEY_SPACE) ) {

			if ( (int)this.velocity != 0 ) {
				if ( this.velocity > 0.0 ) 
					this.velocity -= this.acceleration;

				if ( this.velocity < 0.0 )
					this.velocity += this.acceleration;
				
				this.oldRotation = this.rotation;
				this.x += Math.sin( Math.toRadians(this.oldRotation)-Math.PI/2 )*this.velocity;
				this.y -= Math.cos( Math.toRadians(this.oldRotation)-Math.PI/2 )*this.velocity;
			}
			else
				this.velocity = 0.0;
			
			this.controlPressed = true;

		}
		
		// E - examine
		else if ( window.getInput().isKeyPressed(Input.KEY_E ) ) {
			
			if ( this.currentPlanet(world) != null || this.currentStar(world) != null ) {
				if ( !this.paused )
					this.paused = true;
				else
					this.paused = false;
			}
			else
				this.paused = false;
			
			this.controlPressed = true;
			
		}
		
		// Q - zoom out
		else if ( window.getInput().isKeyPressed(Input.KEY_Q) ) {
			
			if ( this.zoom > 0.0 ) {
				this.zoom -= 0.1f;
			}
			
			this.controlPressed = true;
		}
		
		// R - zoom in
		else if ( window.getInput().isKeyPressed(Input.KEY_R) ) {

			if ( this.zoom < 1.0 ) {
				this.zoom += 0.1f;
			}

			this.controlPressed = true;
		}
		
		// B - test create colony
		else if ( window.getInput().isKeyPressed(Input.KEY_B) ) {

			Colony current = new Colony(this, this.x, this.y, 2000, 1);
			current.init();
			this.allColonies.add(current);
			
		}
		
		// V - test create reactor
		else if ( window.getInput().isKeyPressed(Input.KEY_V) ) {

			Reactor current = new Reactor(this, this.x, this.y, 2000, 1);
			current.init();
			this.allReactors.add(current);

		}
		
		else
		{
			this.controlPressed = false;
		}
		
		/**
		 * MOUSE
		 */
		if ( window.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) ) {
			
			double mouseAngle = Math.toDegrees(Math.atan2((this.y-window.getInput().getMouseY())-cam.camY(),
				(this.x-window.getInput().getMouseX())-cam.camX()));
			
			this.weapon.fire(this.x, this.y, this.rotation, mouseAngle);
			
		}
			
		/*
		 * MOVEMENT 
		 */
		
		if ( !this.controlPressed ) {
			if ( (int)this.velocity != 0 ) {
				if ( this.velocity > 0.0 ) 
					this.velocity -= this.acceleration*0.1;

				if ( this.velocity < 0.0 )
					this.velocity += this.acceleration*0.1;

				this.x += Math.sin( Math.toRadians(this.oldRotation)-Math.PI/2 )*this.velocity;
				this.y -= Math.cos( Math.toRadians(this.oldRotation)-Math.PI/2 )*this.velocity;
			}
		}
		
		/*
		 * Update Shapes
		 */
		this.shape.setCenterX(this.x);
		this.shape.setCenterY(this.y);
		
		this.weapon.update(window, dt);
		
		/**
		 * 
		 * WORLD DATA TO GUI
		 * 
		 * 
		 */
		ArrayList<Planet> allPlanets = world.returnPlanets();
		ArrayList<Star> allStars = world.returnStars();
		
		boolean isInStar = false;
		boolean isInPlanet = false;
		
		for( Star s : allStars ) {
			if ( s.shape().contains(this.x, this.y) ) {
				isInStar = true;
				break;
			}
			else
				isInStar = false;
		}
		
		for ( Planet p : allPlanets ) {
			if ( p.shape().contains(this.x, this.y) ) {
				isInPlanet = true;
				break;
			}
			else
				isInPlanet = false;
		}
		
		if ( isInPlanet )
			this.currentObject = "Planet[E]";
		else if ( isInStar )
			this.currentObject = " Star[E]";
		else
			this.currentObject = "  Void";
		
		ArrayList<SolarSystem> allSolarSystems = world.returnSolarSystems();
		SolarSystem current = null;
		
		for( SolarSystem solar : allSolarSystems ) {
			if( Util.dist(this.x, this.y, solar.x(), solar.y()) < solar.size() ) {
				this.currentSystem = "      " + solar.name();
				current = solar;
				break;
			}
			else {
				this.currentSystem = "  Not in a system";
				current = null;
			}
		}
		
		if ( current != null ) {
			if( Util.dist(this.x, this.y, current.x(), current.y()) < current.size() ) {
				double arrowLookAt = Math.toDegrees(Math.atan2(this.y-current.y(), this.x-current.x()));
				this.arrow.setCenterOfRotation(this.arrow.getWidth()/2.0f, this.arrow.getHeight()/2.0f);
				this.arrowRotation = arrowLookAt;
				this.arrow.setRotation((float)this.arrowRotation);
			}



			for( int i = 0; i <= current.getSolarPlanets().size()-1; i++ ) {

				if( Util.dist(this.x, this.y, current.x(), current.y()) < current.size() ) {

					Planet p = current.getSolarPlanets().get(i);

					double arrowLookAt = Math.toDegrees(Math.atan2(this.y-p.y(), this.x-p.x()));
					this.arrowPlanet[i].setCenterOfRotation(this.arrowPlanet[i].getWidth()/2.0f, this.arrowPlanet[i].getHeight()/2.0f);
					this.arrowPlanetRotation[i] = arrowLookAt;
					this.arrowPlanet[i].setRotation((float)this.arrowPlanetRotation[i]);

				}
			}

			for( int i = World.MAX_PLANETS_PER_STAR - 1; i > current.getSolarPlanets().size() - 1 ; i-- ) {
				this.arrowPlanetRotation[i] = -1.0;
			}
		}
		else {
			for( int i = 0; i <= World.MAX_PLANETS_PER_STAR - 1; i++ ) {
				this.arrowPlanetRotation[i] = -1.0;
			}
		}
		
		/**
		 * 
		 * 
		 *  BUILDINGs
		 * 
		 * 
		 */
		
		for( Colony c : this.allColonies ) {
			c.update(window, dt);
		}
		
		for( Reactor r : this.allReactors ) {
			r.update(window, dt);
		}
	}
	
	/**
	 * Render event
	 * @param window - GameContainer window
	 * @param g - Graphics
	 */
	public void render( GameContainer window, Graphics g ) {
		
		/**
		 * 
		 * 
		 *  BUILDINGS
		 *  
		 * 
		 */
		
		for( Reactor r : this.allReactors ) {
			r.render(window, g);
		}
		
		for( Colony c : this.allColonies ) {
			c.render(window, g);
		}
		
		/**
		 * 
		 * 
		 *  PLAYER
		 * 
		 */
		
		
		// particles
		//this.particle.render();
		// draws the player
		
		/* DEBUG */
		//g.fill(this.shape);
		
		g.drawImage( this.image, this.x-image.getWidth()/2, this.y-image.getHeight()/2 );
		
		this.weapon.render(window, g);
		
		if( !this.currentSystem.equals("  Not in a system") ) {
			g.drawImage(this.arrow, 
					this.x-(this.arrow.getWidth()/2.0f), 
					this.y-(this.arrow.getHeight()/2.0f), 
					new Color(255, 100, 100, 255) );
		}
		
		for( int i = 0; i <= World.MAX_PLANETS_PER_STAR - 1; i++ ) {
			if ( this.arrowPlanetRotation[i] != -1.0 ) {
				
				g.drawImage(this.arrowPlanet[i], 
						this.x-(this.arrowPlanet[i].getWidth()/2.0f), 
						this.y-(this.arrowPlanet[i].getHeight()/2.0f), 
						new Color(100, 100, 255, 255) );
				
			}
		}

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
	public Weapon weapon() {
		return this.weapon;
	}
	
	/**
	 * 
	 * @return
	 */
	public String currentObject() {
		
		return this.currentObject;
	}
	
	/**
	 * 
	 * @return
	 */
	public float mouseZoom() {
		return this.zoom;
	}
	
	/**
	 * 
	 * @return
	 */
	public String currentSystem() {
		return this.currentSystem;
	}
	
	/**
	 * 
	 * @param world
	 * @return
	 */
	public Planet currentPlanet( World world ) {
		
		ArrayList<Planet> allPlanets = world.returnPlanets();
		Planet p = null;
		
		for ( Planet a : allPlanets ) {
			if ( a.shape().contains(this.x, this.y) ) {
				p = a;
				break;
			}
		}
		return p;
	}
	
	/**
	 * 
	 * @param world
	 * @return
	 */
	public Star currentStar( World world ) {
		
		ArrayList<Star> allStars = world.returnStars();
		Star s = null;
		
		for ( Star a : allStars ) {
			if ( a.shape().contains(this.x, this.y) ) {
				s = a;
				break;
			}
		}
		return s;
	}
	
	/**
	 * 
	 * @param world
	 * @return
	 */
	public SolarSystem closestSystem( World world ) {
		
		ArrayList<SolarSystem> allSolarSystems = world.returnSolarSystems();
		
		double lastDist = 10000000000000.0;
		SolarSystem current = null;
		
		for( SolarSystem solar : allSolarSystems ) {
			if ( Util.dist(this.x, this.x, solar.x(), solar.y()) < lastDist ) {
				lastDist = Util.dist(this.x, this.x, solar.x(), solar.y());
				current = solar;
			}
		}
		return current;
		
	}
	/**
	 * 
	 * @return
	 */
	public Image crosshair() {
		return this.crosshair;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isPaused() {
		return this.paused;
	}
	
	//------------------------------------------------------------------------
	
	/**
	 * 
	 * 
	 * BUILDING STUFF
	 * 
	 * 
	 */
	
	/**
	 * 
	 * @return
	 */
	public int getPopulation() {
		return this.population;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getMaxPopulation() {
		return this.maxPopulation;
	}
	
	/**
	 * 
	 * @param amount
	 */
	public void addPopulation( int amount ) {
		if ( this.population + amount <= this.maxPopulation ) {
			this.population += amount;
		}
		else
			this.population = this.maxPopulation;
	}
	
	/**
	 * 
	 * @param max
	 */
	public void setMaxPopulation( int max ) {
		this.maxPopulation = max;
	}
	
	/**
	 * 
	 * @param amount
	 */
	public void addMaxPopulation( int amount ) {
		this.maxPopulation += amount;
	}
	
	/**
	 * 
	 * @return
	 */
	public ArrayList<Colony> returnColonies() {
		return this.allColonies;
	}
	
	/**
	 * 
	 * @return
	 */
	public ArrayList<Reactor> returnReactors() {
		return this.allReactors;
	}
	
	
}
