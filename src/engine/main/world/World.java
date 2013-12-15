package engine.main.world;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import engine.main.Util;
import engine.main.entities.Planet;
import engine.main.entities.Star;

public class World {
	
	public static final int MAX_STARS = 5;
	public static final int MAX_PLANETS_ALONE = 10;
	public static final int MAX_PLANETS_PER_STAR = 6;
	
	private String name;
	private double density;
	
	private ArrayList<Planet> allPlanets;
	private ArrayList<Star> allStars;
	
	private Random generator = new Random();
	protected String worldDetails;
	
	/**
	 * 
	 * @param density
	 */
	public World( String name, double density ) {
		
		this.density = density;
		this.name = name;
		
		this.allPlanets = new ArrayList<Planet>();
		this.allStars = new ArrayList<Star>();
	}
	
	/**
	 * 
	 * @throws SlickException
	 */
	public void generateWorld() throws SlickException {
		
		int numberOfStars = (int)((World.MAX_STARS * this.density)+0.5);
		int numberOfPlanets = (int)((World.MAX_PLANETS_ALONE * this.density)+0.5);
		
		int countStars = 0;
		int countPlanets = 0;
		int countChilds = 0;
		
		// make stars
		for ( int i = 1; i <= numberOfStars; i++ ) {
			
			int randX;
			int randY;
			int randTemp = generator.nextInt(10001) + 2000; // minimum 2000, maximum 10000 kelvin
			int randScale = generator.nextInt(4) + 1; // minimum 1, maximum 6
			randX = generator.nextInt(6001)-3000; // minimum -3000, maximum 3000
			randY = generator.nextInt(6001)-3000; // minimum -3000, maximum 3000
			Image starImage = new Image( "gfx/star_1.png");
			
			Star current = new Star( Names.generateStarName(generator), randX, randY, 
					randScale, randTemp, starImage );
			
			if ( this.allStars.size() > 1 ) {
				Star closest = closestStar( current );
				
				if ( Util.dist(closest.x(), closest.y() , current.x(), current.y()) < current.scale()*32 + 1000.0 ) {
					randX = randX*generator.nextInt(3)+1;
					randY = randY*generator.nextInt(3)+1;
					current = new Star( Names.generateStarName(generator), randX, randY, 
							randScale, randTemp, starImage );
				}
			}

			
			this.allStars.add( current );
			
			countStars++;
			
		}
		
		// make orphan planets
		for ( int i = 1; i <= numberOfPlanets; i++ ) {
			
			int randTemp = generator.nextInt(451); // minimum 0, maximum 450 kelvin
			int randScale = generator.nextInt(2) + 1; // minimum 1, maximum 2
			int randX = generator.nextInt(8001)-4000; // minimum -4000, maximum 4000
			int randY = generator.nextInt(8001)-4000; // minimum -4000, maximum 4000
			boolean randTerrestrial = generator.nextBoolean();
			Image planetImage;
			if ( randTerrestrial )
				planetImage = new Image( "gfx/terr_planet_base_1.png");
			else
				planetImage = new Image( "gfx/gas_planet_base_1.png");
			
			this.allPlanets.add( new Planet( Names.generatePlanetName(generator), randX, randY, 
					randScale, randTerrestrial, randTemp, planetImage ));
			
			countPlanets++;
			
		}
		
		// make child planets
		for ( Star e : this.allStars ) {
			
			int randAmmountOfPlanets = generator.nextInt(World.MAX_PLANETS_PER_STAR + 1) + 1;
			
			for ( int i = 1; i <= randAmmountOfPlanets; i++ ) {
				
				int randX;
				int randY;
				randX = generator.nextInt(2001) + e.x() - 1000;
				randY = generator.nextInt(2001) + e.y() - 1000 ;
				int randTemp = generator.nextInt(451); // minimum 0, maximum 450 kelvin
				int randScale = generator.nextInt(2) + 1; // minimum 1, maximum 2
				boolean randTerrestrial = generator.nextBoolean();
				Image planetImage;
				if ( randTerrestrial )
					planetImage = new Image( "gfx/terr_planet_base_1.png");
				else
					planetImage = new Image( "gfx/gas_planet_base_1.png");
				Planet current = new Planet( Names.generatePlanetName(e, generator), randX, randY, 
						randScale, randTerrestrial, randTemp, planetImage );
				
				
				if ( Util.dist(e.x(), e.y(), current.x(), current.y()) < ( e.scale()*32 ) ) {
					randX = (generator.nextInt(501) + e.x())*e.scale();
					randY = (generator.nextInt(501) + e.y())*e.scale();
					current = new Planet( Names.generatePlanetName(e, generator), randX+( e.scale()*32 ) + 50, randY+( e.scale()*32 ) + 50, 
							randScale, randTerrestrial, randTemp, planetImage );
				}
				
				if ( i > 1 ) {
					Planet closest = closestPlanet( current );
					randX = generator.nextInt(2001) + e.x() - 1000;
					randY = generator.nextInt(2001) + e.y() - 1000;
					if ( Util.dist(closest.x(), closest.y(), current.x() , current.y()) < 200.0 )
						current = new Planet( Names.generatePlanetName(e, generator), randX, randY, 
								randScale, randTerrestrial, randTemp, planetImage );
				}
				
				e.addPlanet( current );
				
				for( Planet p : e.getChildPlanets())
					this.allPlanets.add( p );
				
				countChilds++;
			}
			
		}
		
		System.out.println("Created " + countStars + " stars, " + countPlanets 
				+ " planets and " + countChilds + " child planets!");
		
		
	}
	
	/**
	 * 
	 * @param window
	 * @param g
	 */
	public void render( GameContainer window, Graphics g ) {
		
		// draw stars
		g.setColor(new Color(255,255,255,255));
		
		for( Star e : allStars ) {
			g.setColor(e.color());
			g.fill(e.shape());
			Image scaledImage = e.image().getScaledCopy( e.scale() );
			scaledImage.setImageColor(e.color().getRed(), e.color().getGreen(), e.color().getBlue());
			g.drawImage( scaledImage, e.x() - scaledImage.getWidth()/2, e.y() - scaledImage.getHeight()/2 );
		}
		
		g.setColor(new Color(255,255,255,255));
		
		// draw planets
		for( Planet p : allPlanets ) {
			g.setColor(new Color(150,150,150,0));
			g.fill(p.shape());
			Image scaledImage = p.image().getScaledCopy( (float)Math.ceil(p.scale()/2.0) );
			g.drawImage( scaledImage, p.x() - scaledImage.getWidth()/2, p.y() - scaledImage.getHeight()/2 );
		}
		
		g.setColor(new Color(255,255,255,255));
	}
	
	
	/**
	 * 
	 * @param window
	 * @param dt
	 */
	public void update( GameContainer window, int dt ) {
		
	}
	
	/**
	 * 
	 * @return
	 */
	public ArrayList<Planet> returnPlanets() {
		return this.allPlanets;
	}
	
	/**
	 * 
	 * @return
	 */
	public ArrayList<Star> returnStars() {
		return this.allStars;
	}
	
	/**
	 * 
	 * @param a
	 * @return
	 */
	public Star closestStar( Star a ) {
		
		Star closest = a;
		for ( Star s : allStars ) {
			if ( Util.dist( s.x(), a.x(), s.y(), a.y()) < Util.dist( closest.x(), a.x(), closest.y(), a.y()) )
				closest = s;
		}
		
		return closest;
	}
	
	/**
	 * 
	 * @param a
	 * @return
	 */
	public Planet closestPlanet( Planet p ) {
		
		Planet closest = p;
		for ( Planet s : allPlanets ) {
			if ( Util.dist( s.x(), p.x(), s.y(), p.y()) < Util.dist( closest.x(), p.x(), closest.y(), p.y()) )
				closest = s;
		}
		
		return closest;
	}
		
}
