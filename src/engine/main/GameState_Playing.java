package engine.main;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import engine.main.entities.*;

public class GameState_Playing extends BasicGameState {
	
	/**
	 * 
	 * CONSTANTS
	 * 
	 */
	// Playing State ID
	private static final int ID = 0;
	
	// Stars and Planets
	private ArrayList<Star> allStars;
	private ArrayList<Planet> allPlanets;
	
	// PLAYER
	private Player player;
	
	/**
	 * 
	 * EVENTS
	 * 
	 */
	public void init(GameContainer window, StateBasedGame game)
			throws SlickException {
		
		allStars = new ArrayList<Star>();
		allPlanets = new ArrayList<Planet>();
		
		player = new Player( 300, 300 );
	}
	
	public void update(GameContainer window, StateBasedGame game, int dt)
			throws SlickException {
		
		player.update(window, dt);
		
	}

	public void render(GameContainer window, StateBasedGame game, Graphics g)
			throws SlickException {
		
		g.drawString("Game State: " + ID, 20, 50 );
		
		
		player.render( window, g );
		
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
