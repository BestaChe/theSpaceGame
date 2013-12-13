package engine.main.world;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import engine.main.entities.Planet;
import engine.main.entities.Star;

public class World {
	
	public static final int MAX_STARS = 5;
	public static final int MAX_PLANETS_ALONE = 10;
	public static final int MAX_PLANETS_PER_STAR = 3;
	
	private double density;
	
	private ArrayList<Planet> allPlanets;
	private ArrayList<Star> allStars;
	
	private Random generator = new Random();
	protected String worldDetails;
	
	/**
	 * 
	 * @param density
	 */
	public World( double density ) {
		
		this.density = density;
		
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
			
			int randTemp = generator.nextInt(15001) + 4000; // minimum 4000, maximum 11000 kelvin
			int randScale = generator.nextInt(4) + 1; // minimum 1, maximum 6
			int randX = generator.nextInt(6001)-3000; // minimum -3000, maximum 3000
			int randY = generator.nextInt(6001)-3000; // minimum -3000, maximum 3000
			Image starImage = new Image( "gfx/star_1.png");
			
			this.allStars.add( new Star( Names.generateStarName(generator), randX, randY, randScale, randTemp, starImage ) );
			
			countStars++;
			
		}
		
		// make orphan planets
		for ( int i = 1; i <= numberOfPlanets; i++ ) {
			
			int randTemp = generator.nextInt(451); // minimum 0, maximum 450 kelvin
			int randScale = generator.nextInt(2) + 1; // minimum 1, maximum 2
			int randX = generator.nextInt(8001)-4000; // minimum -4000, maximum 4000
			int randY = generator.nextInt(8001)-4000; // minimum -4000, maximum 4000
			boolean randTerrestrial = generator.nextBoolean();
			int randomImage = generator.nextInt(2) + 1;
			Image planetImage = new Image( "gfx/terrestrian_planet_small_" + randomImage +".png");
			
			this.allPlanets.add( new Planet( Names.generatePlanetName(generator), randX, randY, randScale, randTerrestrial, randTemp, planetImage ));
			
			countPlanets++;
			
		}
		
		// make child planets
		for ( Star e : this.allStars ) {
			
			int randAmmountOfPlanets = generator.nextInt(World.MAX_PLANETS_PER_STAR + 1) + 1;
			
			for ( int i = 1; i <= randAmmountOfPlanets; i++ ) {
				
				int randX = generator.nextInt(1001) + e.x();
				int randY = generator.nextInt(1001) + e.y();
				int randTemp = generator.nextInt(451); // minimum 0, maximum 450 kelvin
				int randScale = generator.nextInt(3) + 1; // minimum 1, maximum 2
				boolean randTerrestrial = generator.nextBoolean();
				int randomImage = generator.nextInt(2) + 1;
				Image planetImage = new Image( "gfx/terrestrian_planet_small_"+ randomImage +".png");
				
				e.addPlanet( new Planet( Names.generatePlanetName(e, generator), randX, randY, randScale, randTerrestrial, randTemp, planetImage ));
				
				for( Planet p : e.getChildPlanets())
					this.allPlanets.add( p );
				
				countChilds++;
			}
			
		}
		
		System.out.println("Created " + countStars + " stars, " + countPlanets + " planets and " + countChilds + " child planets!");
		
		
	}
	
	/**
	 * 
	 * @param window
	 * @param g
	 */
	public void render( GameContainer window, Graphics g ) {
		
		for( Star e : allStars ) {
			g.fill(e.shape());
			Image scaledImage = e.image().getScaledCopy( e.scale()*2);
			g.drawImage( scaledImage, e.x() - scaledImage.getWidth()/2, e.y() - scaledImage.getHeight()/2 );
		}
		
		for( Planet p : allPlanets ) {
			g.fill(p.shape());
			Image scaledImage = p.image().getScaledCopy( p.scale()*2 );
			g.drawImage( scaledImage, p.x() - scaledImage.getWidth()/2, p.y() - scaledImage.getHeight()/2 );
		}
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
		
}
