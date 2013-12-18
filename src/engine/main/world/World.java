package engine.main.world;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import engine.main.Camera;
import engine.main.Util;
import engine.main.entities.NPC;
import engine.main.entities.Planet;
import engine.main.entities.Player;
import engine.main.entities.Star;

public class World {
	
	public static final int MAX_STARS = 5;
	public static final int MAX_PLANETS_ALONE = 10;
	public static final int MAX_PLANETS_PER_STAR = 6;
	
	private String name;
	private double density;
	
	private ArrayList<Planet> allPlanets;
	private ArrayList<Star> allStars;
	private ArrayList<SolarSystem> allSolarSystems;
	
	private ArrayList<NPC> allNPCs;
	
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
		
		this.allSolarSystems = new ArrayList<SolarSystem>();
		
		this.allNPCs = new ArrayList<NPC>();
	}
	
	/**
	 * 
	 * @throws SlickException
	 */
	public void generateWorld() throws SlickException {
		
		int numberOfStars = (int)((World.MAX_STARS * this.density)+0.5);
		int numberOfPlanets = (int)((World.MAX_PLANETS_ALONE * this.density)+0.5);
		
		int distanceFactor = 5;
		
		int countStars = 0;
		int countPlanets = 0;
		int countChilds = 0;
		int countSystems = 0;
		
		// make stars & solar systems
		for ( int i = 1; i <= numberOfStars; i++ ) {
			
			int randX;
			int randY;
			String randName = Names.generateStarName(generator);
			int randTemp = generator.nextInt(10001) + 2000; // minimum 2000, maximum 10000 kelvin
			int randScale = generator.nextInt(4) + 2; // minimum 1, maximum 6
			randX = generator.nextInt(1001 * distanceFactor)-(int)(1000/(distanceFactor*1.0));
			randY = generator.nextInt(1001 * distanceFactor)-(int)(1000/(distanceFactor*1.0)); 
			Image starImage = new Image( "gfx/star_1.png" );
			
			Star current = new Star( randName , randX, randY, randScale, randTemp, starImage );
			
			SolarSystem solarSys = new SolarSystem( randName, current );
			
			int randAmmountOfPlanets = generator.nextInt(World.MAX_PLANETS_PER_STAR + 1);
			
			for ( int p = 1; p <= randAmmountOfPlanets; p++ ) {
				
				int randXP;
				int randYP;
				randXP = generator.nextInt(2001) + current.x() - 1000;
				randYP = generator.nextInt(2001) + current.y() - 1000 ;
				int randTempP = generator.nextInt(451); // minimum 0, maximum 450 kelvin
				int randScaleP = generator.nextInt(2) + 1; // minimum 1, maximum 2
				boolean randTerrestrial = generator.nextBoolean();
				boolean randAtmos = generator.nextBoolean();
				Image planetImage;
				if ( randTerrestrial )
					planetImage = new Image( "gfx/terr_planet_base_1.png");
				else
					planetImage = new Image( "gfx/gas_planet_base_1.png");
				
				Planet currentplanet = new Planet( Names.generatePlanetName(current, generator), randXP, randYP, 
						randScaleP, randTerrestrial, randTempP, randAtmos, planetImage );
				
				int buffer = 0;
				
				/*
				 *  // distance between planet and star
				 */
				while( Util.dist(current.x(), current.y(), currentplanet.x(), currentplanet.y()) 
						< ( current.scale()*32 + currentplanet.scale()*16 ) ) {
					
					if ( buffer > 10000 ) 
						break;
					
					randXP = (generator.nextInt(1501) + current.x())*current.scale();
					randYP = (generator.nextInt(1501) + current.y())*current.scale();
					
					currentplanet = new Planet( Names.generatePlanetName(current, generator), randXP+( current.scale()*32 ) + 50, 
							randYP+( current.scale()*32 ) + 50, randScaleP, randTerrestrial,
							randTempP, randAtmos, planetImage );
					buffer++;
				}
				buffer = 0;
				
				/*
				 * 	// distance between each planet
				 */
				if ( p > 1 ) {
					Planet closest = closestPlanet( currentplanet );
					randXP = generator.nextInt(2001) + current.x() - 1000;
					randYP = generator.nextInt(2001) + current.y() - 1000;
					while( Util.dist(closest.x(), closest.y(), currentplanet.x() , currentplanet.y()) < 200.0 ) {
						if ( buffer > 10000 )
							break;
						
						currentplanet = new Planet( Names.generatePlanetName(current, generator), randXP, randYP, 
								randScaleP, randTerrestrial, randTempP, randAtmos, planetImage );
						buffer++;
					}
				}
				
				/* Adds the planet to the solar system */
				solarSys.addPlanet( currentplanet );
				
				if ( currentplanet.supportsLife() ) {
					generateNPC( currentplanet, generator );
					System.out.println("Added a NPC!!");
				}
				
			}
			
			/*
			 *   // if there are more than 1 solar systems
			 */
			if ( this.allSolarSystems.size() > 1 ) {
				for ( SolarSystem sys : allSolarSystems ) {
					if( Util.dist( sys.x(), sys.y(), solarSys.x(), solarSys.y() ) < sys.size() ) {
						
						randX = generator.nextInt(1001 * distanceFactor)-(int)(1000/(distanceFactor*1.0));
						randY = generator.nextInt(1001 * distanceFactor)-(int)(1000/(distanceFactor*1.0)); 
						solarSys.setPosition(randX, randY);
						current = solarSys.getStar();
						
						System.out.println("Changed position of solar system: " + solarSys.name() + "! Star: " + current.name() );
					}
				}
			}
			
			/* Adds this solar system to the list */
			this.allSolarSystems.add( solarSys );
			
			System.out.println("Added solar system [" + solarSys.name() + "]" );
			countSystems++;
			
			/* Adds the solar system's star and planets to the appropriate lists */
			for ( SolarSystem s : this.allSolarSystems ) {
				
				if ( !this.allStars.contains(s.getStar()) ) {
					this.allStars.add(s.getStar());
					System.out.println("Added in solar system [" + s.name() + "] the star: " + s.getStar().name() );
					countStars++;
				}
				
				for ( Planet p : s.getSolarPlanets() ) {
					this.allPlanets.add(p);
					countChilds++;
				}
				
			}
			
		}
		
		// make orphan planets
		for ( int i = 1; i <= numberOfPlanets; i++ ) {
			
			int randTemp = generator.nextInt(451)+1; // minimum 0, maximum 450 kelvin
			int randScale = generator.nextInt(2) + 1; // minimum 1, maximum 2
			int randX = generator.nextInt(8001)-4000; // minimum -4000, maximum 4000
			int randY = generator.nextInt(8001)-4000; // minimum -4000, maximum 4000
			boolean randTerrestrial = generator.nextBoolean();
			boolean atmos = generator.nextBoolean();
			Image planetImage;
			
			if ( randTerrestrial )
				planetImage = new Image( "gfx/terr_planet_base_1.png");
			else
				planetImage = new Image( "gfx/gas_planet_base_1.png");
			
			Planet current = new Planet( Names.generatePlanetName(generator), randX, randY, 
					randScale, randTerrestrial, randTemp, atmos, planetImage );
			
			this.allPlanets.add( current );
			
			if ( current.supportsLife() ) {
				generateNPC( current, generator );
				System.out.println("Added a NPC!!");
			}
			
			countPlanets++;
			
		}
		
		System.out.println("Created " + countSystems + " Star systems with " + countStars + " stars and " + countChilds
				+ " planets! \n" + countPlanets + " orphan planets have been created!");
		
		
	}
	
	/*
	 * 
	 * 
	 * 
	 *  GAME EVENTS
	 * 
	 * 
	 * 
	 * 
	 */
	
	/**
	 * 
	 * @param window
	 * @param g
	 * @throws SlickException 
	 */
	public void render( GameContainer window, Graphics g, Player player) {
		
		// draw stars
		g.setColor(new Color(255,255,255,255));
		
		for( Star e : allStars ) {

			if( Util.dist(e.x(), e.y(), player.x(), player.y()) < 1980.0 ) {
				g.setColor(e.color());
				g.fill(e.shape());
				Image scaledImage = e.image().getScaledCopy( e.scale() );
				g.drawImage( scaledImage, e.x() - scaledImage.getWidth()/2, e.y() - scaledImage.getHeight()/2, e.color() );
			}
		}

		g.setColor(new Color(255,255,255,255));

		// draw planets
		for( Planet p : allPlanets ) {

			if( Util.dist(p.x(), p.y(), player.x(), player.y()) < 1980.0 ) {
				g.setColor(p.color());
				g.fill(p.shape());
				Image scaledImage = p.image().getScaledCopy( (float)Math.ceil(p.scale()/2.0) );
				g.drawImage( scaledImage, p.x() - scaledImage.getWidth()/2, p.y() - scaledImage.getHeight()/2, p.color() );

				if ( p.hasDetail() && p.isTerrestrian() ) {
					try {

						Image detailImage;
						detailImage = new Image( p.detail() ).getScaledCopy( (float)Math.ceil(p.scale()/2.0) );
						g.drawImage( detailImage, p.x() - detailImage.getWidth()/2, p.y() - detailImage.getHeight()/2 );

					} catch (SlickException e1) {

						e1.printStackTrace();
					}
				}

				if ( p.hasAtmosphere() ) {
					try {

						Image atmosImage;
						atmosImage = new Image( "gfx/terr_planet_atmosphere_1.png" ).getScaledCopy( (float)Math.ceil(p.scale()/2.0) );
						g.drawImage( atmosImage, p.x() - atmosImage.getWidth()/2, p.y() - atmosImage.getHeight()/2 );

					} catch (SlickException e1) {

						e1.printStackTrace();
					}
				}
			}
		}
		
		g.setColor(new Color(255,255,255,255));
		
		for( NPC npc : this.allNPCs ) {
			npc.image().setRotation( (float)npc.rotation() );
			g.drawImage(npc.image(), npc.x(), npc.y());
		}
	}
	
	
	/**
	 * 
	 * @param window
	 * @param dt
	 */
	public void update( GameContainer window, int dt, Player player ) {
		
		for( NPC npc : this.allNPCs ) {
			if ( Util.dist(player.x(), player.y(), npc.x(), npc.y() ) < 1920.0 ) {
				npc.think();
			}
		}
		
	}
	
	/**
	 * ------------------------------------------------------------------------------------------------------------------
	 */
	
	/*
	 *
	 * 
	 * 
	 *  METHODS
	 * 
	 * 
	 * 
	 * 
	 */
	
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
	public Star closestStar( Player a ) {
		
		Star closest = this.allStars.get(0);
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
	
	/**
	 * 
	 * @param p
	 * @return
	 * @throws SlickException 
	 */
	public void generateNPC( Planet p, Random rd ) throws SlickException {
		
		String name = p.name();
		int x = p.x();
		int y = p.y();
		String path = "gfx/sprite_ships_small.png";
		int randomShip = rd.nextInt(4);
		
		this.allNPCs.add(new NPC( name, x, y, path, randomShip, 0));
		
	}
		
}
