package engine.main.entities;

import java.util.ArrayList;

public class Star extends AstronomicalObject {

	private String name;
	private long temperature;
	private ArrayList<Planet> childs;
	
	/**
	 * Constructs the Star Object
	 * @param name - string name of the star
	 * @param x - int x position of the star
	 * @param y - int y position of the star
	 * @param scale - int scale of the star
	 * @param temperature - int kelvin temperature of the star
	 */
	public Star(String name, int x, int y, int scale, long temperature ) {
		super(x, y, scale);
		
		this.name = name;
		this.temperature = temperature;
		this.childs = new ArrayList<Planet>();
	}
	
	/**
	 * Returns the name of the star
	 * @return string name
	 */
	public String name() {
		return this.name;
	}
	
	/**
	 * Returns the temperature of the star in kelvin
	 * @return long temperature
	 */
	public long temperature() {
		return this.temperature;
	}
	
	/**
	 * Returns the mass of the star ( 2^scale suns, so 1 scale = 2 mass of the suns )
	 * @return int mass
	 */
	public int mass() {
		return (int)Math.pow(2, this.scale() );
	}
	
	/**
	 * Adds a planet to this star system
	 * @param planet child
	 */
	public void addPlanet( Planet child ) {
		this.childs.add( child );
	}
	
	/**
	 * Returns how many planets there are in this system
	 * @return int number of planets
	 */
	public int planetCount() {
		return this.childs.size();
	}
}
