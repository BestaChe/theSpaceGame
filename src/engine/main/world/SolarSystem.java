package engine.main.world;

import java.util.ArrayList;

import engine.main.Util;
import engine.main.entities.Planet;
import engine.main.entities.Star;

public class SolarSystem {
	
	private String name;
	private double size;
	private Star star;
	private int x;
	private int y;
	private ArrayList<Planet> solarPlanets;
	
	public SolarSystem( String name, Star star ) {
		
		this.name = name;
		this.star = star;
		this.x = this.star.x();
		this.y = this.star.y();
		
		this.solarPlanets = this.star.getChildPlanets();
		
	}
	
	/**
	 * 
	 * @param planet
	 */
	public void addPlanet( Planet planet ) {
		
		this.star.addPlanet(planet);
		
		for ( Planet p : this.solarPlanets ) {
			if ( Util.dist(p.x(), p.y(), planet.x(), planet.y()) > this.size ) {
				this.size = Util.dist(p.x(), p.y(), planet.x(), planet.y());
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
	
	public int x() {
		return this.x;
	}
	
	public int y() {
		return this.y;
	}
	
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
	 * @param x
	 * @param y
	 */
	public void setPosition( int x, int y ) {
		this.x = x;
		this.y = y;
		this.star.setPosition(x, y);
		
		for ( Planet p : solarPlanets ) {
			p.setPosition(x, y);
		}
	}
	
}
