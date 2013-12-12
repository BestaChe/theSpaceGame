package engine.main;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameState_Menu extends BasicGameState {
	
	/**
	 * 
	 * CONSTANTS
	 * 
	 */
	// Playing State ID
	private static final int ID = 1;
	
	/**
	 * 
	 * EVENTS
	 * 
	 */
	public void init(GameContainer window, StateBasedGame game)
			throws SlickException {
		
	}
	
	public void update(GameContainer window, StateBasedGame game, int dt)
			throws SlickException {
		
	}

	public void render(GameContainer window, StateBasedGame game, Graphics g)
			throws SlickException {
		
		g.drawString("Game State: " + ID, 20, 50 );
		
	}
	
	/**
	 * 
	 * METHODS
	 * 
	 */
	public int getID() {
		return GameState_Menu.ID;
	}

}
