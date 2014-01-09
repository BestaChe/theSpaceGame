package engine.main.entities;

import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import engine.main.Util;
import engine.main.world.World;

public class Planet extends AstronomicalObject {

	private String name;
	private int temperature;
	private boolean terrestrian;
	private boolean hasAtmosphere;
	private int appearenceDetail;
	
	private Color planetColor;
	private int shadowFase;
	
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
	public Planet(String name, float x, float y, float scale, boolean terrestrian, int temperature, 
			boolean hasAtmosphere, Image image) {
		
		super(x, y, scale, image);
		
		this.name = name;
		this.terrestrian = terrestrian;
		this.temperature = temperature;
		this.hasAtmosphere = hasAtmosphere;
		this.planetColor = new Color(255, 255, 255);
		this.shadowFase = Util.randomBetween(1, 4);
		
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
		else {
			this.planetColor = new Color( Util.randomBetween(125, 255),Util. randomBetween(125, 255), Util.randomBetween(125, 255) );
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
	public float mass() {
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
	public int shadowFase() {
		return this.shadowFase;
	}
	
	public void setShadowFase( int fase ) {
		this.shadowFase = fase;
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
	 * Render method
	 * @param window
	 * @param g
	 * @param player
	 */
	public void render( GameContainer window, Graphics g, Player player ) {

		if( Util.dist(this.x(), this.y(), player.x(), player.y()) < World.RENDER_DISTANCE ) {
			
			// Draw the Scaled Planet
			g.setColor(this.color());
			Image scaledImage = this.image().getScaledCopy( (float)(this.scale() ) );
			
			g.drawImage( scaledImage, this.x() - scaledImage.getWidth()/2, this.y() - scaledImage.getHeight()/2, this.color() );
			
			// Draw the Details Layer
			if ( this.hasDetail() && this.isTerrestrian() ) {
				try {

					Image detailImage = new Image( this.detail() ).getScaledCopy( (float)( this.scale() ) );
					
					g.drawImage( detailImage, this.x() - detailImage.getWidth()/2, this.y() - detailImage.getHeight()/2 );

				} catch (SlickException e1) {

					e1.printStackTrace();
				}
			}

			// Draw the Atmosphere Layer
			if ( this.hasAtmosphere() && this.isTerrestrian() ) {
				try {

					Image atmosImage;
					atmosImage = new Image( "gfx/astros/terr_planet_atmosphere_1.png" ).getScaledCopy( (float)(this.scale() ) );
					
					g.drawImage( atmosImage, this.x() - atmosImage.getWidth()/2, this.y() - atmosImage.getHeight()/2 );
					atmosImage.setAlpha(125);

				} catch (SlickException e1) {

					e1.printStackTrace();
				}
			}

		}
	}
	
	/**
	 * Render Shadows
	 * @param window
	 * @param g
	 * @param star
	 * @param player
	 * @throws SlickException
	 */
	public void renderShadows( GameContainer window, Graphics g, Star star, Player player ) throws SlickException {
		
		//if( Util.dist(this.x(), this.y(), player.x(), player.y()) < World.RENDER_DISTANCE ) {
			double shadowRotation = Math.toDegrees( Math.atan2( this.y()-star.y(), this.x()-star.x() ) );
			Image shadow = new Image("gfx/astros/terr_planet_shadow_" + this.shadowFase() + ".png").getScaledCopy( (float)(this.scale() ) );
		
			shadow.setRotation((float)shadowRotation);
			g.drawImage( shadow, this.x() - shadow.getWidth()/2, this.y() - shadow.getHeight()/2 );
		//}
		
	}
	
}
