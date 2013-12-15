package engine.main;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import engine.main.entities.Player;
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
	private World world = new World( "DEFAULT", 0.9 );
	
	// CAMERA
	private static final int MAX_PARALLAX_SPRITES = 60;
	private Camera camera[] = new Camera[4];
	private ArrayList<Circle> parallax;
	private ArrayList<Circle> parallax2;
	private ArrayList<Circle> parallax3;
	
	/**
	 * 
	 * EVENTS
	 * 
	 */
	public void init(GameContainer window, StateBasedGame game)
			throws SlickException {
		
		camera[0] = new Camera( window, 0 );
		camera[1] = new Camera( window, 1 );
		camera[2] = new Camera( window, 2 );
		camera[3] = new Camera( window, 3 );
		
		world.generateWorld();
		
		player = new Player( 0, 0 );
		
		parallax = new ArrayList<Circle>();
		parallax2 = new ArrayList<Circle>();
		parallax3 = new ArrayList<Circle>();
		
		for ( int i = 1; i <= GameState_Playing.MAX_PARALLAX_SPRITES/3; i++ )
			generateParallaxStars( window, player );
	}
	
	public void update(GameContainer window, StateBasedGame game, int dt)
			throws SlickException {
		
		
		camera[0].centerOn(player.x(), player.y());
		camera[1].centerOn(player.x(), player.y());
		camera[2].centerOn(player.x(), player.y());
		camera[3].centerOn(player.x(), player.y());
		
		
		world.update(window, dt);
		player.update(window, dt, camera[0], world );
		
		//for ( Circle a : parallax )
		//	updateParallaxStars( a, window, player);
		
		
	}

	public void render(GameContainer window, StateBasedGame game, Graphics g)
			throws SlickException {
		
		g.drawImage( new Image("gfx/background.png"), 0, 0 );
		
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
		
		g.setColor(new Color( 0 , 0, 0, 125) );
		g.fill( new Rectangle( 0, 0, 400, 150 ) );
		g.setColor( new Color( 255, 255, 125, 255) );
		g.drawString(player.worldDetails(), 20, 30);
		g.setColor( new Color( 255, 255, 255, 255) );
		
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
			
			parallax.add( new Circle( player.x() + ( rd.nextInt(wW+1) - wW/2 ) 
					, player.y() + ( rd.nextInt(wH+1) - wH/2 )*10
					, rd.nextInt( 6 ) + 1 ));
			
			parallax2.add( new Circle( player.x() + ( rd.nextInt(wW+1) - wW/2 ) 
					, player.y() + ( rd.nextInt(wH+1) - wH/2 )*10
					, rd.nextInt( 5 ) + 1 ));
			
			parallax3.add( new Circle( player.x() + ( rd.nextInt(wW+1) - wW/2 ) 
					, player.y() + ( rd.nextInt(wH+1) - wH/2 )*10
					, rd.nextInt( 4 ) + 1 ));
			
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

}
