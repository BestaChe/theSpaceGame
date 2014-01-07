package engine.main;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import engine.main.entities.Player;
import engine.main.gui.EnergyBar;
import engine.main.gui.HealthBar;
import engine.main.gui.Info;
import engine.main.gui.InfoPopulation;
import engine.main.gui.InfoSolarSystem;
import engine.main.gui.InfoWindow;
import engine.main.gui.RadarMap;
import engine.main.world.World;

public class GameState_Playing extends BasicGameState {
	
	/**
	 * 
	 * CONSTANTS
	 * 
	 */
	// Playing State ID
	private static final int ID = 0;
	
	// PLAYER
	private Player player;
	
	// WORLD
	private World world = new World( "DEFAULT", 0.7 );
	private Image backgroundNebulae;
	
	// CAMERA
	private static final int MAX_PARALLAX_SPRITES = 800;
	private float zoom;
	private Camera camera[] = new Camera[5];
	private ArrayList<Circle> parallax;
	private ArrayList<Circle> parallax2;
	private ArrayList<Circle> parallax3;
	
	// GUI
	private HealthBar guiHealth;
	private EnergyBar guiEnergy;
	//private RadarMap guiRadar;
	private Info guiInfo;
	private InfoWindow guiAstroInfo;
	private InfoSolarSystem guiSystemInfo;
	private InfoPopulation guiPopulationInfo;
	
	/**
	 * 
	 * EVENTS
	 * 
	 */
	public void init(GameContainer window, StateBasedGame game)
			throws SlickException {
		
		zoom = 1.0f;
		camera[0] = new Camera( window, 0, zoom );
		camera[1] = new Camera( window, 1, zoom );
		camera[2] = new Camera( window, 2, zoom );
		camera[3] = new Camera( window, 3, zoom );
		camera[4] = new Camera( window, 15, zoom );
		
		world.generateWorld();
		backgroundNebulae = new Image("gfx/main/background_nebulae.png").getScaledCopy(5.0f);
		backgroundNebulae.setAlpha(50);
		
		player = new Player( 0, 0 );
		
		parallax = new ArrayList<Circle>();
		parallax2 = new ArrayList<Circle>();
		parallax3 = new ArrayList<Circle>();
		
		for ( int i = 1; i <= GameState_Playing.MAX_PARALLAX_SPRITES/3; i++ )
			generateParallaxStars( window, player );
		
		/*
		 * GUI
		 */
		
		guiHealth = new HealthBar( new Image( "gui/gui_health.png" ), 
				screenWidth(window, 650), 
				screenHeight(window , 10), 
				player );
		
		guiEnergy = new EnergyBar( new Image( "gui/gui_energy.png" ), 
				screenWidth(window, 650), 
				screenHeight(window , 40), 
				player );
		
		guiInfo = new Info( new Image("gui/gui_info.png"), 
				screenWidth(window, 352 ), 
				screenHeight(window , 552));
		
		guiSystemInfo = new InfoSolarSystem( new Image("gui/gui_info_up.png"), 
				screenWidth(window, 400-96 ), 
				screenHeight(window , 0));
		
		guiAstroInfo = new InfoWindow( new Image("gui/gui_window.png"), 
				screenWidth(window, 800-250), 
				screenHeight(window , 600-300) );
		
		guiPopulationInfo = new InfoPopulation( new Image("gui/gui_info_population.png"), 
				screenWidth(window, 0), 
				screenHeight(window , 600-48) );
		
		//guiRadar = new RadarMap(new Image( "gui/gui_map.png" ), 0, window.getHeight() - 350, player, window );
		
	}
	
	public void update(GameContainer window, StateBasedGame game, int dt)
			throws SlickException {
		
		zoom = player.mouseZoom();
		
		camera[0].centerOn(player.x(), player.y());
		camera[0].setZoom(zoom);
		camera[1].centerOn(player.x(), player.y());
		camera[1].setZoom(zoom);
		camera[2].centerOn(player.x(), player.y());
		camera[2].setZoom(zoom);
		camera[3].centerOn(player.x(), player.y());
		camera[3].setZoom(zoom);
		camera[4].centerOn(player.x(), player.y());
		camera[4].setZoom(zoom);
		
		
		world.update(window, dt, this.player);
		player.update(window, dt, camera[0], world );
		
		//for ( Circle a : parallax )
		//	updateParallaxStars( a, window, player);
		
		
		/**
		 * EXIT CODE
		 */
		if ( window.getInput().isKeyDown(Input.KEY_ESCAPE) ) {
			window.exit();
		}
		
		//guiRadar.update(window, dt, world, camera[0]);
		guiInfo.update(window, dt, player);
		guiInfo.updateMAIN(window, dt);
		
		guiSystemInfo.update(window, dt, player);
		guiSystemInfo.updateMAIN(window, dt);
		
		guiPopulationInfo.update(window, dt, player);
		guiPopulationInfo.updateMAIN(window, dt);
		
		
	}

	public void render(GameContainer window, StateBasedGame game, Graphics g)
			throws SlickException {
		
		g.drawImage( new Image("gfx/main/background.png"), 0, 0 );
		
		camera[4].translateGraphics();
		g.drawImage(this.backgroundNebulae, -1024*5, -1024*5);
		camera[4].untranslateGraphics();
		
		camera[3].translateGraphics();
		this.drawParallaxStars(window, g, 3, player);
		camera[3].untranslateGraphics();
		
		camera[2].translateGraphics();
		this.drawParallaxStars(window, g, 2, player);
		camera[2].untranslateGraphics();
		
		camera[1].translateGraphics();
		this.drawParallaxStars(window, g, 1, player);
		camera[1].untranslateGraphics();
		// WORLD MAIN STUFF
		camera[0].translateGraphics();
		world.render(window, g, player);
		player.render( window, g );
		camera[0].untranslateGraphics();
		
		/*
		 * GUI
		 */
		g.resetTransform();
		g.setColor( new Color( 255, 255, 255, 255) );
		//guiHealth.renderMAIN(window, g, player);
		//guiEnergy.renderMAIN(window, g, player);
		guiInfo.renderMAIN(window, g, player);
		guiInfo.render(window, g);
		
		guiSystemInfo.renderMAIN(window, g, player);
		guiSystemInfo.render(window, g);
		
		guiPopulationInfo.renderMAIN(window, g, player);
		guiPopulationInfo.render(window, g);
		
		if ( player.isPaused() ) {
			
			if ( player.currentPlanet(world) != null ) {
				guiAstroInfo.setPlanet(player.currentPlanet(world));
				guiAstroInfo.renderMAIN(window, g, player);
				guiAstroInfo.render(window, g);
			}
			else if ( player.currentStar(world) != null ) {
				guiAstroInfo.setStar(player.currentStar(world));
				guiAstroInfo.renderMAIN(window, g, player);
				guiAstroInfo.render(window, g);
			}
		}
		
		//guiRadar.render(window, g, player);
		//guiRadar.render(window, g, camera[0]);
		player.crosshair().draw(window.getInput().getMouseX()-8, window.getInput().getMouseY()-8);
		
	}
	
	/**
	 * 
	 * METHODS
	 * 
	 */
	public int getID() {
		return GameState_Playing.ID;
	}
	
	/**
	 * Generates all the background stars
	 * @param window - GameContainer
	 * @param player - Player
	 */
	public void generateParallaxStars( GameContainer window, Player player ) {
		Random rd = new Random();
		
		if ( parallax.size() + parallax2.size() + parallax3.size() <= GameState_Playing.MAX_PARALLAX_SPRITES ) {
			
			int wH = MainClass.WINDOW_HEIGHT;
			int wW = MainClass.WINDOW_WIDTH;
			
			parallax.add( new Circle( player.x() + ( rd.nextInt(wW+1) - wW/2 )*20 
					, player.y() + ( rd.nextInt(wH+1) - wH/2 )*10
					, rd.nextInt( 4 ) + 1 ));
			
			parallax2.add( new Circle( player.x() + ( rd.nextInt(wW+1) - wW/2 )*20 
					, player.y() + ( rd.nextInt(wH+1) - wH/2 )*10
					, rd.nextInt( 3 ) + 1 ));
			
			parallax3.add( new Circle( player.x() + ( rd.nextInt(wW+1) - wW/2 )*20 
					, player.y() + ( rd.nextInt(wH+1) - wH/2 )*10
					, rd.nextInt( 2 ) + 1 ));
			
		}

		
	}
	
	
	/**
	 * Draws all the background stars
	 * @param window - GameContainer
	 * @param g - Graphics
	 * @param layer - layer of the stars 1, 2 or 3
	 */
	public void drawParallaxStars( GameContainer window, Graphics g, int layer, Player player ) {
		
		switch( layer ) {
		case 1:
			for ( Circle a : parallax ) {
				g.fill(a);
			}
			break;
		case 2:
			for ( Circle a : parallax2 ) {
				g.fill(a);
			}
			break;
		case 3:
			for ( Circle a : parallax3 ) {
				g.fill(a);
			}
			break;
		}
	}
	
	/**
	 * 
	 * @param window
	 * @param a
	 * @return
	 */
	public int screenWidth( GameContainer window, int a ) {
		return (int)(((a) * window.getWidth() ) / 800.0 );
	}
	
	/**
	 * 
	 * @param window
	 * @param a
	 * @return
	 */
	public int screenHeight( GameContainer window, int a ) {
		return (int)(((a) * window.getHeight() ) / 600.0 );
	}

}
