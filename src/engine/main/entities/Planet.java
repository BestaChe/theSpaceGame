package engine.main.entities;

import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import engine.main.Util;

public class Planet extends AstronomicalObject {

	private String name;
	private int temperature;
	private boolean terrestrian;
	private boolean hasAtmosphere;
	private int appearenceDetail;
	
	private Color planetColor;
	
	private int material;
	
	/**
	 * Constructs the planet
	 * @param name - string name of the planet
	 * @param x - int x position of the planet
	 * @param y - int y position of the planet
	 * @param scale - int scale
	 * @param terrestrian - boolean is the planet not gas typed
	 * @param temperature - int temperature of the planet
	 */
	public Planet(String name, int x, int y, int scale, boolean terrestrian, int temperature, 
			boolean hasAtmosphere, Image image) {
		
		super(x, y, scale*2, image);
		
		this.name = name;
		this.terrestrian = terrestrian;
		this.temperature = temperature;
		this.hasAtmosphere = hasAtmosphere;
		this.planetColor = new Color(255, 255, 255);
		
		this.material = 0;
		
		/* Details */
		if ( Math.abs(this.temperature - 278) < 30 )
			this.appearenceDetail = 1;
		
		else if ( Math.abs(this.temperature) > 308 )
			this.appearenceDetail = 2;

		else if ( Math.abs(this.temperature) < 248 )
			this.appearenceDetail = 3;
		else
			this.appearenceDetail = 0;
		
		/**
		 * Terrestrian Stuff
		 */
		Random rd = new Random();
		if ( this.terrestrian ) {
			
			/* Colours */
			if ( this.temperature <= 130 ) {
				int rVal = rd.nextInt( 100 );
				this.planetColor = new Color( 255 - rVal, 255 - rVal, 255 - rVal );
			}
			else if ( this.temperature > 130 && this.temperature < 300 ) {
				int rVal = rd.nextInt( 100 );
				this.planetColor = new Color( 255 - rVal, 255, 255 - rVal );
			}
			else {
				int rVal = rd.nextInt( 100 );
				this.planetColor = new Color( 255 , 255 -rVal , 255 - rVal );
			}
			
			/* Mining Materials */
			int chanceToHaveMaterials = rd.nextInt(3);
			
			if ( chanceToHaveMaterials < 2 ) {
				this.material = Util.randomBetween(1,4);
				System.out.println("Planet made with material ID: " + this.material);
			}
			
		}
		
	}
	
	/**
	 * Returns the name of the planet
	 * @return string name
	 */
	public String name() {
		return this.name;
	}
	
	/**
	 * Returns the temperature of the planet in kelvin
	 * @return int temperature
	 */
	public int temperature() {
		return this.temperature;
	}
	
	/**
	 * Returns whether the planet is terrestrian type or not (aka gas typed)
	 * @return boolean
	 */
	public boolean isTerrestrian() {
		return this.terrestrian;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean hasAtmosphere() {
		return this.hasAtmosphere;
	}
	
	/**
	 * Returns the mass of the planet ( 1 scale = 1 earth mass )
	 * @return int mass
	 */
	public int mass() {
		return this.scale();
	}
	
	/**
	 * Returns the color of the planet
	 * @return
	 */
	public Color color() {
		return this.planetColor;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean hasDetail() {
		return ( this.appearenceDetail > 0 ? true : false );
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean supportsLife() {
		return ( Math.abs(this.temperature - (long)278) < (long)30 && this.hasAtmosphere && this.terrestrian );
	}
	
	/**
	 * 
	 * @return
	 * @throws SlickException
	 */
	public String detail() {
		switch( this.appearenceDetail ) {
		case 1:
			return "gfx/astros/terr_planet_detail_water.png";
		case 2:
			return "gfx/astros/terr_planet_detail_volcano.png";
		case 3:
			return "gfx/astros/terr_planet_detail_wrecked.png";
		default:
			return "gfx/astros/terr_planet_detail_water.png";
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public int rawDetail() {
		return this.appearenceDetail;
	}
	
	/**
	 * 
	 * @return
	 */
	public int materials() {
		return this.material;
	}
	/**
	 * 
	 * @return
	 */
	public String details() {
		StringBuilder str = new StringBuilder();
		
		str.append("Name: " + this.name + "\n" );
		str.append("Mass: " + this.mass() + " times the mass of the Earth\n");
		str.append("Is terrestrian: " + this.terrestrian + "\n" );
		str.append("Has atmosphere: " + this.hasAtmosphere + "\n");
		str.append("Temperature: " + this.temperature + "K");
		
		return str.toString();
	}
	
}
