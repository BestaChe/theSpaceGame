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
	public static final int MAX_PLANETS_PER_STAR = 8;
	public static final double RENDER_DISTANCE = 4000.0;
	
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
		
		int maxRandomPositionX = 1000;
		int maxRandomPositionY = 1000;
		int distanceFactor = 100;
		
		int countPlanets = 0;
		int countSystems = 0;
		
		//* STAR & SOLAR SYSTEM GENERATION *\\
		/*
		for( int i = 0; i <= numberOfStars; i++ ) {
			
			String starName     = Names.generateStarName(generator);
			int starX			= Util.randomBetween(-maxRandomPositionX*distanceFactor, maxRandomPositionX*distanceFactor);
			int starY			= Util.randomBetween(-maxRandomPositionY*distanceFactor, maxRandomPositionY*distanceFactor);
			float starScale		= Util.randomFloatBetween(6.0f, 20.0f);
			int starTemperature = Util.randomBetween(2500, 50000);
			Image starImage		= new Image("gfx/astros/star_1.png");
			Star currentStar    = new Star( starName, starX, starY, starScale, (long)starTemperature, starImage );
			
			SolarSystem currentSolarSystem = new SolarSystem( starName, currentStar );
			
			int planetNumber = Util.randomBetween(0, World.MAX_PLANETS_PER_STAR);
			
			//* PLANET GENERATION *\\
			
			for( int j = 0; j <= planetNumber-1; j++ ) {
				currentSolarSystem.generatePlanet(generator);
			}
			
			if ( allSolarSystems.size() > 1 ) {
				for( SolarSystem other : allSolarSystems ) {
					while( Util.dist(other.x(), other.y(), currentSolarSystem.x(), currentSolarSystem.y()) <= currentSolarSystem.size()
							|| currentSolarSystem.area().contains(other.x(), other.y()) || currentSolarSystem.area().intersects(other.area()) ) {
						
						starX			= Util.randomBetween(-maxRandomPositionX*distanceFactor, maxRandomPositionX*distanceFactor);
						starY			= Util.randomBetween(-maxRandomPositionY*distanceFactor, maxRandomPositionY*distanceFactor);
						currentSolarSystem.setPosition(starX, starY);
						
						System.out.println("Changed position of: " + currentSolarSystem.name() + " due to collision with " + other.name() );
					}
				}
			}
			
			allSolarSystems.add(currentSolarSystem);
			allStars.add(currentStar);
			countSystems++;
			
			for( SolarSystem s : allSolarSystems ) {
				for( Planet p : s.getSolarPlanets() ) {
					if( !allPlanets.contains(p) ) {
						allPlanets.add(p);
						countPlanets++;
					}
				}
			}*/
		
		
		String starName     = Names.generateStarName(generator);
		int starX			= Util.randomBetween(0, 0);
		int starY			= Util.randomBetween(0, 0);
		float starScale		= Util.randomFloatBetween(6.0f, 20.0f);
		int starTemperature = Util.randomBetween(2500, 50000);
		Image starImage		= new Image("gfx/astros/star_1.png");
		Star currentStar    = new Star( starName, starX, starY, starScale, (long)starTemperature, starImage );
		
		SolarSystem currentSolarSystem = new SolarSystem( starName, currentStar );
		
		int planetNumber = Util.randomBetween(0, World.MAX_PLANETS_PER_STAR);
		
		//* PLANET GENERATION *\\
		
		for( int j = 0; j <= planetNumber-1; j++ ) {
			currentSolarSystem.generatePlanet(generator);
		}
		
		allSolarSystems.add(currentSolarSystem);
		allStars.add(currentStar);
		countSystems++;
		
		for( SolarSystem s : allSolarSystems ) {
			for( Planet p : s.getSolarPlanets() ) {
				if( !allPlanets.contains(p) ) {
					allPlanets.add(p);
					countPlanets++;
				}
			}
		}
		
		
		
		System.out.println("Created " + countSystems + " star systems with " + countPlanets + " planets!");
		
		
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
		
		// Draw all Solar System stuff
		for( SolarSystem solar : allSolarSystems ) {
				
			solar.toggleDrawArea(player.mouseZoom() != 1.0);
					
			try { solar.render(window, g, player); } 
			catch (SlickException e) { e.printStackTrace(); }
		}
		
		// Draw NPCs
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
		
		
		
		/**
		 * 
		 *  NPC STUFF
		 * 
		 */
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
		
		/**
		 * 
		 * WORLD
		 * 
		 */
		
		for ( SolarSystem solar : this.allSolarSystems ) {
			solar.update(window, dt, player);
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
		float x = p.x();
		float y = p.y();
		String path = "gfx/ships/sprite_ships_big.png";
		int randomShip = rd.nextInt(4);
		
		this.allNPCs.add(new NPC( name, x, y, path, 0, 0, 2, 0));
		
	}
		
}
