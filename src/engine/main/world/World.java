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
import engine.main.entities.weapons.Damage;

public class World {
	
	public static final int MAX_STARS = 10;
	public static final int MAX_PLANETS_ALONE = 5;
	public static final int MAX_PLANETS_PER_STAR = 6;
	public static final double RENDER_DISTANCE = 1980.0;
	
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
	public void generateWorld( ) throws SlickException {
		
		int numberOfStars = (int)((World.MAX_STARS * this.density)+0.5);
		int numberOfPlanets = (int)((World.MAX_PLANETS_ALONE * this.density)+0.5);
		
		int distanceFactor = 100;
		
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
			randX = generator.nextInt(1001 * distanceFactor)-(int)(500*(distanceFactor*1.0));
			randY = generator.nextInt(1001 * distanceFactor)-(int)(500*(distanceFactor*1.0)); 
			Image starImage = new Image( "gfx/astros/star_1.png" );
			
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
					planetImage = new Image( "gfx/astros/terr_planet_base_1.png");
				else
					planetImage = new Image( "gfx/astros/gas_planet_base_1.png");
				
				Planet currentplanet = new Planet( Names.generatePlanetName(current, generator), randXP, randYP, 
						randScaleP, randTerrestrial, randTempP, randAtmos, planetImage );
				
				int buffer = 0;
				
				/*
				 *  // distance between planet and star
				 */
				while( current.shape().intersects(currentplanet.shape()) || current.shape().contains(currentplanet.x(), currentplanet.y()) ) {
					
					if ( buffer > 750 ) 
						break;
					
					randXP = current.x() + generator.nextInt(2001) - 1000;
					randYP = current.y() + generator.nextInt(2001) - 1000 ;
					
					currentplanet = new Planet( Names.generatePlanetName(current, generator), randXP+( current.scale()*32 )*2, 
							randYP+( current.scale()*32 )*2, randScaleP, randTerrestrial,
							randTempP, randAtmos, planetImage );
					buffer++;
				}
				buffer = 0;
				
				/*
				 * 	// distance between each planet
				 */
				if ( p > 1 ) {
					Planet closest = closestPlanet( currentplanet );
					while( closest.shape().intersects(currentplanet.shape()) || closest.shape().contains(currentplanet.x(), currentplanet.y()) ) {
						if ( buffer > 750 )
							break;
						
						randXP = generator.nextInt(4001) + current.x() - 2000;
						randYP = generator.nextInt(4001) + current.y() - 2000 ;
						currentplanet = new Planet( Names.generatePlanetName(current, generator), randXP, randYP, 
								randScaleP, randTerrestrial, randTempP, randAtmos, planetImage );
						buffer++;
					}
				}
				buffer = 0;
				
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
			int buffer = 0;
			for ( SolarSystem sys : allSolarSystems ) {

				if ( !sys.equals(solarSys) ) {
					while( sys.area().intersects(solarSys.area() ) || sys.area().contains(solarSys.x(), solarSys.y()) ) {

						if ( buffer > 750 )
							break;

						randX = generator.nextInt(2001 * distanceFactor)-(int)(2000/(distanceFactor*1.0));
						randY = generator.nextInt(2001 * distanceFactor)-(int)(2000/(distanceFactor*1.0)); 
						solarSys.setPosition(randX, randY);
						current = solarSys.getStar();

						System.out.println("Changed position of solar system: " + solarSys.name() + "! Star: " + current.name() );
						System.out.println("New Position: " + solarSys.x() + " || " + solarSys.y() );
						buffer++;
					}
					buffer = 0;
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
			
			int randX;
			int randY;
			int randTemp = generator.nextInt(451)+1; // minimum 0, maximum 450 kelvin
			int randScale = generator.nextInt(2) + 1; // minimum 1, maximum 2
			randX = generator.nextInt(8001)-4000; // minimum -4000, maximum 4000
			randY = generator.nextInt(8001)-4000; // minimum -4000, maximum 4000
			boolean randTerrestrial = generator.nextBoolean();
			boolean atmos = generator.nextBoolean();
			Image planetImage;
			
			if ( randTerrestrial )
				planetImage = new Image( "gfx/astros/terr_planet_base_1.png");
			else
				planetImage = new Image( "gfx/astros/gas_planet_base_1.png");
			
			Planet current = new Planet( Names.generatePlanetName(generator), randX, randY, 
					randScale, randTerrestrial, randTemp, atmos, planetImage );
			
			for( Star s : this.allStars ) {
				while( Util.dist(s.x(), s.y(), current.x(), current.y()) < ( s.scale()*32 + 100 ) ) {
					randX = generator.nextInt(8001)-4000; // minimum -4000, maximum 4000
					randY = generator.nextInt(8001)-4000; // minimum -4000, maximum 4000
					
					current = new Planet( Names.generatePlanetName(generator), randX, randY, 
							randScale, randTerrestrial, randTemp, atmos, planetImage );
				}
			}
			
			for( Planet p : this.allPlanets ) {
				while( Util.dist(p.x(), p.y(), current.x() , current.y()) < 200.0 ) {
					
					randX = generator.nextInt(8001)-4000; // minimum -4000, maximum 4000
					randY = generator.nextInt(8001)-4000; // minimum -4000, maximum 4000
					
					current = new Planet( Names.generatePlanetName(generator), randX, randY, 
							randScale, randTerrestrial, randTemp, atmos, planetImage );
					
				}
			}
			
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
		
		// draw areas
		
		for( SolarSystem solar : allSolarSystems ) {
			if( Util.dist(solar.x(), solar.y(), player.x(), player.y()) < 
					World.RENDER_DISTANCE * ( 1 / player.mouseZoom() ) * 10 ) {
				
				if ( player.mouseZoom() != 1.0 ) {
					solar.renderArea(window, g);
				}
			}
		}
		
		// draw stars
		g.setColor(new Color(255,255,255,255));
		
		for( Star e : allStars ) {

			if( Util.dist(e.x(), e.y(), player.x(), player.y()) < World.RENDER_DISTANCE ) {
				g.setColor(e.color());
				g.fill(e.shape());
				Image scaledImage = e.image().getScaledCopy( e.scale() );
				g.drawImage( scaledImage, e.x() - scaledImage.getWidth()/2, e.y() - scaledImage.getHeight()/2, e.color() );
			}
		}

		g.setColor(new Color(255,255,255,255));

		// draw planets
		for( Planet p : allPlanets ) {

			if( Util.dist(p.x(), p.y(), player.x(), player.y()) < World.RENDER_DISTANCE ) {
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
						atmosImage = new Image( "gfx/astros/terr_planet_atmosphere_1.png" ).getScaledCopy( (float)Math.ceil(p.scale()/2.0) );
						g.drawImage( atmosImage, p.x() - atmosImage.getWidth()/2, p.y() - atmosImage.getHeight()/2 );

					} catch (SlickException e1) {

						e1.printStackTrace();
					}
				}
			}
		}
		
		g.setColor(new Color(255,255,255,255));
		
		for( NPC npc : this.allNPCs ) {
			
			/* DEBUG */
			//g.fill(npc.shape());
			
			npc.image().setRotation( (float)npc.rotation() );
			g.drawImage(npc.image(), npc.x(), npc.y());
			
			npc.render(window, g);
		}
		
	}
	
	
	/**
	 * 
	 * @param window
	 * @param dt
	 * @throws SlickException 
	 */
	public void update( GameContainer window, int dt, Player player ) throws SlickException {
		
		for( int i = 0; i <= this.allNPCs.size() - 1; i ++ ) {
			NPC npc = this.allNPCs.get(i);
			npc.update(window, dt);
			
			if ( Util.dist(player.x(), player.y(), npc.x(), npc.y() ) < World.RENDER_DISTANCE ) {
				npc.think(player);
				
				if ( npc.health() == 0 )
					this.allNPCs.remove(i);
				
				Damage.BulletCollisionWithNPC(player.weapon(), npc);
				Damage.BulletCollisionWithPlayer(npc.weapon(), npc, player);
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
	 * @return
	 */
	public ArrayList<SolarSystem> returnSolarSystems() {
		return this.allSolarSystems;
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
		String path = "gfx/ships/sprite_ships_big.png";
		int randomShip = rd.nextInt(4);
		
		this.allNPCs.add(new NPC( name, x, y, path, 0, 0, 2, 0));
		
	}
		
}
