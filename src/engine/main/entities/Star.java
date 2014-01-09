package engine.main.entities;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import engine.main.Util;
import engine.main.world.World;

public class Star extends AstronomicalObject {

	private String name;
	private long temperature;
	
	private Color starColor;
	
	/**
	 * Constructs the Star Object
	 * @param name - string name of the star
	 * @param x - int x position of the star
	 * @param y - int y position of the star
	 * @param scale - int scale of the star
	 * @param temperature - int kelvin temperature of the star
	 */
	public Star(String name, float x, float y, float scale, long temperature, Image image ) {
		super(x, y, scale, image);
		
		this.name = name;
		this.temperature = temperature;
		
		
		// COLOR
		Random rd = new Random();
		
		// dark blue
		if ( this.temperature <= 50000 && this.temperature > 28000 )
			this.starColor = new Color(175, 175, 255, 255);
		// blue
		else if ( this.temperature <= 28000 && this.temperature > 10000 )
			this.starColor = new Color(200, 200, 255, 255);
		// light blue
		else if ( this.temperature <= 10000 && this.temperature > 7500 )
			this.starColor = new Color(225, 225, 255, 255);
		// white
		else if ( this.temperature <= 7500 && this.temperature > 6000 )
			this.starColor = new Color(255, 255, 255, 255);
		// yellow
		else if ( this.temperature <= 6000 && this.temperature > 5000 )
			this.starColor = new Color(255, 255, 150, 255);
		// orange
		else if ( this.temperature <= 5000 && this.temperature > 3500 )
			this.starColor = new Color(255, 170, 100, 255);
		// red
		else if ( this.temperature <= 3500 )
			this.starColor = new Color(255, 50, 50, 255);
		else
			this.starColor = new Color( 255 , 255 , 255 , 255 );
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
	 * 
	 * @return
	 */
	public Color color() {
		return this.starColor;
	}
	
	/**
	 * Render method
	 * @param window
	 * @param g
	 * @param player
	 */
	public void render( GameContainer window, Graphics g, Player player ) {
		
		if( Util.dist(this.x(), this.y(), player.x(), player.y()) < World.RENDER_DISTANCE ) {
			g.setColor(this.color());
			Image scaledImage = this.image().getScaledCopy( (float)(this.scale() / 2.0) );
			g.drawImage( scaledImage, this.x() - scaledImage.getWidth()/2, this.y() - scaledImage.getHeight()/2, this.color() );
		}
		
	}
	
	
}
