package engine.main;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import engine.main.entities.*;
import engine.main.world.*;

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
	private World world = new World( 0.9 );
	
	// CAMERA
	private Camera camera;
	
	/**
	 * 
	 * EVENTS
	 * 
	 */
	public void init(GameContainer window, StateBasedGame game)
			throws SlickException {
		
		camera = new Camera( window );
		
		world.generateWorld();
		
		player = new Player( 300, 300 );
	}
	
	public void update(GameContainer window, StateBasedGame game, int dt)
			throws SlickException {
		
		camera.centerOn(player.x(), player.y());
		world.update(window, dt);
		player.update(window, dt, camera);
		
	}

	public void render(GameContainer window, StateBasedGame game, Graphics g)
			throws SlickException {
		
		g.drawString("Game State: " + ID, 20, 50 );
		
		camera.translateGraphics();
		world.render(window, g);
		
		player.render( window, g );
		camera.untranslateGraphics();
		
	}
	
	/**
	 * 
	 * METHODS
	 * 
	 */
	public int getID() {
		return GameState_Playing.ID;
	}

}
