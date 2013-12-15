package engine.main.entities;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

public class Star extends AstronomicalObject {

	private String name;
	private long temperature;
	private ArrayList<Planet> childs;
	
	private Color starColor;
	
	/**
	 * Constructs the Star Object
	 * @param name - string name of the star
	 * @param x - int x position of the star
	 * @param y - int y position of the star
	 * @param scale - int scale of the star
	 * @param temperature - int kelvin temperature of the star
	 */
	public Star(String name, int x, int y, int scale, long temperature, Image image ) {
		super(x, y, scale*4, image);
		
		this.name = name;
		this.temperature = temperature;
		this.childs = new ArrayList<Planet>();
		
		
		// COLOR
		Random rd = new Random();
		long difference = Math.abs( this.temperature - 5778 );
		int colInterval;
		if (  difference <= 1000 ) {
			colInterval = (int)Math.abs((rd.nextInt( (int)((difference/100) * 1.5 ) * 2 ) 
					- difference/100 * 1.5));
			
			this.starColor = new Color(175+colInterval, 175+colInterval, 175+colInterval);
		}
		else
		{
			colInterval = (int)Math.abs(rd.nextInt( (int)(( this.temperature / 100 ) * 1.5 )));
			if ( this.temperature < 4778 ) {
				this.starColor = new Color(205+colInterval, 175-colInterval/2, 150-colInterval );
			}
			else {
				this.starColor = new Color(150-colInterval, 175-colInterval/2, 205+colInterval );
			}
		}
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
		return (int)Math.pow(Math.E, this.scale() );
	}
	
	/**
	 * Adds a planet to this star system
	 * @param planet child
	 */
	public void addPlanet( Planet child ) {
		this.childs.add( child );
	}
	
	/**
	 * Returns the list of child planets!
	 * @return arraylist
	 */
	public ArrayList<Planet> getChildPlanets() {
		return this.childs;
	}
	/**
	 * Returns how many planets there are in this system
	 * @return int number of planets
	 */
	public int planetCount() {
		return this.childs.size();
	}
	/**
	 * 
	 * @return
	 */
	public String details() {
		StringBuilder str = new StringBuilder();
		
		str.append("Name: " + this.name + "\n" );
		str.append("Mass: " + this.mass() + " times the mass of the sun\n");
		str.append("Temperature: " + this.temperature + "K\n");
		str.append("Child Planets: " + this.planetCount() );
		
		return str.toString();
	}
	
	/**
	 * 
	 * @return
	 */
	public Color color() {
		return this.starColor;
	}
}
