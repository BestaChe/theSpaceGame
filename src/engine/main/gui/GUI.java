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
	
	/**
	 * 
	 * @param window
	 * @param dt
	 */
	public void updateMAIN( GameContainer window, int dt ) {
		
	}
	
	/**
	 * 
	 * @param window
	 * @param g
	 * @param p
	 */
	public void renderMAIN( GameContainer window, Graphics g, Player p ) {
		g.drawImage(this.img, this.x, this.y);
	}
	
	public int x( GameContainer window ) {
		return this.x;
	}
	
	public int y( GameContainer window ) {
		return this.y;
	}
}
