package engine.main;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class MainClass extends StateBasedGame {

	/**
	 * 
	 * CONSTANTS
	 * 
	 */
	// Window Constants
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	public static final boolean WINDOW_FULLSCREEN = false;
	
	// Game Constants
	public static final String GAME_NAME = "SpaceGame v1";
	
	/**
	 * Constructor
	 * @param title - string title of the game
	 */
	public MainClass(String title) {
		super(title);

	}
	
	/**
	 * The Main method! Magic happens here :D
	 * @param args
	 * @throws SlickException
	 */
	public static void main(String[] args) throws SlickException {

		AppGameContainer window = new AppGameContainer( new MainClass(MainClass.GAME_NAME) );
		
		window.setDisplayMode( MainClass.WINDOW_WIDTH, MainClass.WINDOW_HEIGHT, MainClass.WINDOW_FULLSCREEN );
		window.setTargetFrameRate( 60 );
		window.setAlwaysRender( false );
		window.setMouseGrabbed(true);
		window.start();
		
	}
	
	/**
	 * Initializes the stateList
	 */
	public void initStatesList(GameContainer window) throws SlickException {
		
		GameState_Playing gameStatePlaying = new GameState_Playing();
		GameState_Menu gameStateMenu = new GameState_Menu();
		
		this.addState(gameStatePlaying);
		this.addState(gameStateMenu);
		
	}
}
