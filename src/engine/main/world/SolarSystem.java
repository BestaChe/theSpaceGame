package engine.main.world;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;

import engine.main.Util;
import engine.main.entities.Planet;
import engine.main.entities.Star;

public class SolarSystem {
	
	private String name;
	private double size;
	private Circle area;
	private Star star;
	private int x;
	private int y;
	private ArrayList<Planet> solarPlanets;
	
	public SolarSystem( String name, Star star ) {
		
		this.name = name;
		this.star = star;
		this.x = this.star.x();
		this.y = this.star.y();
		this.size = (this.star.scale()*32)+300;
		this.area = new Circle( this.x, this.y, (float)this.size );
		
		this.solarPlanets = this.star.getChildPlanets();
		
	}
	
	/**
	 * 
	 * @param planet
	 */
	public void addPlanet( Planet planet ) {
		
		this.star.addPlanet(planet);
		
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
	public int x() {
		return this.x;
	}
	
	/**
	 * 
	 * @return
	 */
	public int y() {
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
		int oldPosX = this.x;
		int oldPosY = this.y;
		
		int movementX = x - oldPosX;
		int movementY = y - oldPosY;
		
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
	
}
