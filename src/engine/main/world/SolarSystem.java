package engine.main.world;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;

import engine.main.Util;
import engine.main.entities.Planet;
import engine.main.entities.Player;
import engine.main.entities.Star;

public class SolarSystem {
	
	private String name;
	private double size;
	private Circle area;
	private Star star;
	private float x;
	private float y;
	private boolean drawArea;
	
	private ArrayList<Planet> solarPlanets;
	private int lastOrbit;
	
	public SolarSystem( String name, Star star ) {
		
		this.name = name;
		this.star = star;
		this.x = this.star.x();
		this.y = this.star.y();
		this.size = (this.star.scale()*128)+200;
		this.area = new Circle( this.x, this.y, (float)this.size );
		this.drawArea = false;
		
		this.solarPlanets = new ArrayList<Planet>();
		this.lastOrbit = (int)(this.star.scale()*128) + Util.randomBetween(500, 750);
		
	}
	
	/**
	 * 
	 * @param planet
	 */
	public void generatePlanet( Random rd ) {
		
		Image planetImage 	= null;
		String planetName	= Names.generatePlanetName(this.star, rd);
		float planetX 		= this.x + this.lastOrbit + Util.randomBetween(-1200, 1200);
		float planetY		= this.y + this.lastOrbit + Util.randomBetween(-1200, 1200);
		float planetScale;
		boolean planetTerrestrian;
		boolean planetAtmosphere;
		int planetTemperature;
		double distanceToStar = Util.dist(planetX, planetY, this.star.x(), this.star.y() );
		
		if ( distanceToStar < 8000 ) {
			try { planetImage  	= new Image("gfx/astros/terr_planet_base_1.png"); } 
			catch (SlickException e) { e.printStackTrace(); }
			planetScale 	= Util.randomFloatBetween(0.5f, 1.5f);
			planetTerrestrian = true;
			planetTemperature = (int)(Util.randomBetween(150, 500) / ( distanceToStar / 1500f ));
			planetAtmosphere = rd.nextBoolean();
			
			if ( distanceToStar < 1000 ) {
				planetAtmosphere = false;
			}
			
		}
		else {
			try { planetImage  	= new Image("gfx/astros/gas_planet_base_1.png"); } 
			catch (SlickException e) { e.printStackTrace(); }
			planetScale		= Util.randomFloatBetween(1.2f, 5.0f);
			planetTerrestrian = false;
			planetAtmosphere = true;
			planetTemperature = Util.randomBetween(1, 300);
		}
		
		Planet planet = new Planet( planetName, planetX, planetY, planetScale, planetTerrestrian, planetTemperature, planetAtmosphere, planetImage);
		
		this.solarPlanets.add(planet);
		this.lastOrbit 	   *= Util.randomFloatBetween(1.1f, 2.0f);
		
		for ( Planet p : this.solarPlanets ) {
			
			if ( Util.dist(this.star.x(), this.star.y(), planet.x(), planet.y()) > this.size ) {
				
				this.size = Util.dist(this.star.x(), this.star.y(), planet.x(), planet.y()) + 200 ;
				this.area.setRadius( (float)this.size );
				
			}
		}
		
	}
	
	/**
	 * 
	 * @return
	 */
	public Star getStar() {
		return this.star;
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
	public String name() {
		return this.name;
	}
	
	/**
	 * 
	 * @return
	 */
	public double size() {
		return this.size;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean canDrawArea() {
		return this.drawArea;
	}
	
	/**
	 * 
	 * @param a
	 */
	public void toggleDrawArea( boolean a ) {
		this.drawArea = a;
	}
	/**
	 * 
	 * @return
	 */
	public ArrayList<Planet> getSolarPlanets() {
		return this.solarPlanets;
	}
	
	/**
	 * 
	 * @param other
	 * @return
	 */
	public boolean equals( SolarSystem other ) {
		return ( this.name.equals(other.name) && this.size == other.size && this.x == other.x && this.y == other.y );
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 */
	public void setPosition( int x, int y ) {
		float oldPosX = this.x;
		float oldPosY = this.y;
		
		float movementX = x - oldPosX;
		float movementY = y - oldPosY;
		
		this.x = x;
		this.y = y;
		this.star.setPosition(x, y);
		
		this.area.setCenterX(this.x);
		this.area.setCenterY(this.y);
		
		for ( Planet p : solarPlanets ) {
			p.setPosition(p.x() + movementX, p.y() + movementY);
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public Circle area() {
		return this.area;
	}
	/**
	 * 
	 * @param window
	 * @param g
	 */
	public void renderArea( GameContainer window, Graphics g ) {
		
		this.area.setCenterX(this.x);
		this.area.setCenterY(this.y);
		
		g.setColor( new Color( 100, 255, 100, 255 ) );
		g.draw(this.area);
		
		g.setColor( new Color( 100, 255, 100, 25 ) );
		g.fill(this.area);
		
		g.setColor( new Color( 255, 255, 255, 255 ));
		
	}
	
	/**
	 * 
	 * @param window
	 * @param g
	 * @param player
	 * @throws SlickException
	 */
	public void render( GameContainer window, Graphics g, Player player ) throws SlickException {
		
		if ( this.drawArea )
			this.renderArea(window, g);
		
		this.star.render(window, g, player);
		
		for ( Planet p : this.solarPlanets ) {
			p.render(window, g, player);
			p.renderShadows(window, g, this.star, player);
		}
	}
	
	/**
	 * 
	 * @param window
	 * @param dt
	 * @param player
	 */
	public void update( GameContainer window, int dt, Player player) {
		
		for( Planet p : this.solarPlanets ) {
			double forwardRotation = Math.toDegrees( Math.atan2( p.y()-this.star.y(), p.x()-this.star.x() ) ) + 90.0;
			double distanceToStar = Util.dist(p.x(), p.y(), this.star.x(), this.star.y() );
			double velocity = ( 10.5f / ( distanceToStar / 1500.0 ) );
			
			float planetX = (float)(p.x() + Math.sin( Math.toRadians(forwardRotation)-Math.PI/2 )*velocity);
			float planetY = (float)(p.y() - Math.cos( Math.toRadians(forwardRotation)-Math.PI/2 )*velocity);
			
			p.setPosition( planetX, planetY );
			
		}
		
	}
	
}
