package engine.main.gui;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import engine.main.entities.Player;

public class GUI {
	
	private int x;
	private int y;
	private int width;
	private int height;
	private Image img;
	
	/**
	 * 
	 * @param img
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public GUI( Image img, int x, int y ) {
		
		this.img = img;
		this.x = x;
		this.y = y;
		this.width = img.getWidth();
		this.height = img.getHeight();
		
	}
	
	public void update( GameContainer window, int dt ) {
		
	}
	
	public void render( GameContainer window, Graphics g, Player p ) {
		
		g.drawImage(this.img, (int)(( this.x * window.getWidth() ) / 800.0) , (int)(( this.y * window.getWidth() ) / 600.0) );
		
	}
}
